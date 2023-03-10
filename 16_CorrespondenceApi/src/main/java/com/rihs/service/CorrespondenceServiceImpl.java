package com.rihs.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rihs.binding.CorrespondenceResponse;
import com.rihs.binding.EligibilityDetailsResponse;
import com.rihs.binding.EmailReq;
import com.rihs.consumer.CasePlanApplicationConsumer;
import com.rihs.consumer.CitizenAppConsumerFeign;
import com.rihs.consumer.EligibilityDetailsConsumer;
import com.rihs.entity.Case;
import com.rihs.entity.CitizenRegistrationApplication;
import com.rihs.entity.EligibilityDetails;
import com.rihs.entity.Triggers;
import com.rihs.repository.TriggersRepository;
import com.rihs.util.EmailUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CorrespondenceServiceImpl implements ICorrespondenceService {

	@Autowired
	private TriggersRepository repo;

	@Autowired
	private EligibilityDetailsConsumer edConsumer;

	@Autowired
	private CasePlanApplicationConsumer caseConsumer;

	@Autowired
	private CitizenAppConsumerFeign citizenConsumer;

	@Autowired
	private EmailUtil util;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private Long successTriggers = 0L;

	public CorrespondenceResponse sendCorrespondence() {
		log.info("Entering into sendCorrespondence method");
		
		HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
		
		String address = (String) hashOps.get("DHS", "DHS_OFC_ADDRESS");

		List<Triggers> pendingTriggers = repo.findByTriggerStatus("Pending");
		
		ExecutorService exService = Executors.newFixedThreadPool(10); // create a thread pool
		for(Triggers pt:pendingTriggers){
			exService.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					processTrigger(pt, address);
					return null;
				}
			});
		};
		CorrespondenceResponse response = new CorrespondenceResponse();		
		response.setTotalTriggers(Long.valueOf(pendingTriggers.size()));
		response.setSuccessTriggers(successTriggers);
		response.setFailedTriggers(Long.valueOf(pendingTriggers.size())-successTriggers);
		log.info("Exiting from sendCorrespondence method");
		return response; // return the given stats of success and failure numbers
	}

	// processes each trigger
	private void processTrigger(Triggers pt, String address) {
		log.info("Entering into processTrigger method");
		// get Eligibility Details
		EligibilityDetailsResponse response = edConsumer.getEligibilityDetails(pt.getCaseNum()).getBody();
		EligibilityDetails ed = new EligibilityDetails();
		// get case Details
		Case caseInfo = caseConsumer.getCaseInfo(pt.getCaseNum()).getBody();
		// get Citizen Registration Application details
		CitizenRegistrationApplication citizen = citizenConsumer.getApplication(caseInfo.getAppId()).getBody();
		BeanUtils.copyProperties(response, ed);
		try {
			pt.setTriggerPdf(writeToPdf(ed, address)); // write data into pdf
		} catch (Exception e) {
			log.error("Exception occurred while writing data into pdf file");
			e.printStackTrace(); 
		}
		// send email
		EmailReq req = new EmailReq();
		req.setEmailFrom("noreply@rihs.com");
		req.setEmailTo(citizen.getCitizenEmail());
		req.setEmailSubject("Application Status Notice for AppId: " + caseInfo.getAppId());
		req.setEmailText(setEmailBody(ed));
		req.setFileName(ed.getHolderName() + "-" + ed.getCaseNum() + ".pdf");
		try {
			util.sendEmail(req);
		} catch (Exception e) {
			log.error("Exception occurred while sending email with pdf attachment");
			e.printStackTrace();
		}
		pt.setTriggerStatus("Completed");
		repo.save(pt); // finally after everything goes well save to db
		successTriggers++;
		log.info("Exiting from processTrigger method here");
	}
	
	// method to set the text for email
	private String setEmailBody(EligibilityDetails ed) {
		log.info("Entering into setEmailBody method");
		StringBuffer sb = new StringBuffer();
		String filename = "PlanStatusEmail.txt";
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace("{fullName}", ed.getHolderName());
				line = line.replace("{planName}", ed.getPlanName());
				line = line.replace("{planStatus}", ed.getPlanStatus());
				sb.append(line);
			});
		} catch (IOException e) {
			log.error("IOException occurred while working updating the email text " + e.getMessage());
			e.printStackTrace();
		}
		log.info("Exiting from setEmailBody method");
		return sb.toString();
	}

	// method to write data into pdf file
	private byte[] writeToPdf(EligibilityDetails ed, String address) throws DocumentException, IOException {
		log.info("Entering into writeToPdf method");
		// Creating the Object of Document
		Document document = new Document(PageSize.A4);
		Document document1 = new Document(PageSize.A4);

		// Getting instance of PdfWriter
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
		FileOutputStream fos = new FileOutputStream(ed.getHolderName() + "-" + ed.getCaseNum() + ".pdf");
		PdfWriter pdfWriter1 = PdfWriter.getInstance(document1, fos);

		// Opening the created document to change it
		document.open();
		document1.open();
		// Creating font
		// Setting font style and size
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		fontTitle.setColor(Color.DARK_GRAY);
		// Creating paragraph
		Paragraph p = new Paragraph(ed.getPlanStatus() + " Notice", fontTitle);
		// Aligning the paragraph in the document
		p.setAlignment(Paragraph.ALIGN_CENTER);
		// Adding the created paragraph in the document
		document.add(p);
		document1.add(p);

//		document.add(Chunk.NEWLINE);        //Blank line
//		document.add(new LineSeparator());      //Thick line
//
//		document1.add(Chunk.NEWLINE);        //Blank line
//		document1.add(new LineSeparator());      //Thick line

		// Creating a table of the 2 columns
		PdfPTable table = new PdfPTable(2);
		// Setting width of the table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 10, 20 });
		table.setSpacingBefore(5);
		// Create Table Cells for the table header
		PdfPCell cell = new PdfPCell();
		// Setting the background color and padding of the table cell
		cell.setBackgroundColor(CMYKColor.WHITE);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(CMYKColor.BLACK);
		// Adding headings in the created table cell or header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Case Number : ", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.valueOf(ed.getCaseNum()), font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name : ", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(ed.getHolderName(), font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SSN : ", font));
		table.addCell(cell);
		String ssn = "XXX XX " + String.valueOf(ed.getHolderSsn()).substring(5);
		cell.setPhrase(new Phrase(ssn, font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name : ", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(ed.getPlanName(), font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status : ", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase(ed.getPlanStatus(), font));
		table.addCell(cell);

		if ("Approved".equals(ed.getPlanStatus())) {
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			cell.setPhrase(new Phrase("Start Date : ", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(ed.getPlanStartDate().format(formatters), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("End Date : ", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(ed.getPlanEndDate().format(formatters), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Benefit Amount : ", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(ed.getBenefitAmount().toString(), font));
			table.addCell(cell);
		} else {
			cell.setPhrase(new Phrase("Denial Reason : ", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(ed.getDenialReason().toString(), font));
			table.addCell(cell);
		}

		// Adding the created table to the document
		document.add(table);
		document1.add(table);
		
		String[] tokens = address.split("#");
		
		// Creating paragraph
		document.add(new Paragraph("DHS Office Address : " + tokens[0], font));
		document1.add(new Paragraph("DHS Office Address : " + tokens[0], font));
		document.add(new Paragraph("Contact Number : " + tokens[1], font));
		document1.add(new Paragraph("Contact Number : " + tokens[1], font));
		document.add(new Paragraph("Website : " + tokens[2], font));
		document1.add(new Paragraph("Website : " + tokens[2], font));

		
		// Closing the document
		document.close();
		document1.close();
		fos.close();
		pdfWriter.flush();
		pdfWriter1.flush();
		log.info("Exiting from writeToPdf method");
		return baos.toByteArray();
	}
}
