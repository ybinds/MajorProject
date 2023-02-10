package com.rihs.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

	public void sendCorrespondence() {
		List<Triggers> pendingTriggers = repo.findByTriggerStatus("Pending");
		pendingTriggers.forEach((pt) -> {
			EligibilityDetailsResponse response = edConsumer.getEligibilityDetails(pt.getCaseNum()).getBody();
			EligibilityDetails ed = new EligibilityDetails();
			Case caseInfo = caseConsumer.getCaseInfo(ed.getCaseNum()).getBody();
			CitizenRegistrationApplication citizen = citizenConsumer.getApplication(caseInfo.getAppId()).getBody();
			BeanUtils.copyProperties(response, ed);
			try {
				pt.setTriggerPdf(writeToPdf(ed));
			} catch (Exception e) {
				e.printStackTrace();
			}
			EmailReq req = new EmailReq();
			req.setEmailFrom("ybinds@gmail.com");
			req.setEmailTo(citizen.getCitizenEmail());
			req.setEmailSubject("Application Status Notice for AppId: " + caseInfo.getAppId());
			req.setEmailText(setEmailBody(ed));
			try {
				util.sendEmail(req);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pt.setTriggerStatus("Completed");
			repo.save(pt);
		});
	}

	private String setEmailBody(EligibilityDetails ed) {
		StringBuffer sb = new StringBuffer();
		String filename = "";
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
//				line = line.replace("${FName}", user.getUserFirstName());
//				line = line.replace("${LName}", user.getUserLastName());
//				line = line.replace("${Email}", user.getUserEmail());
//				line = line.replace("${Pwd}", user.getUserPassword());
				sb.append(line);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private byte[] writeToPdf(EligibilityDetails ed) throws DocumentException, IOException {
		// Creating the Object of Document
		Document document1 = new Document(PageSize.A4);
		Document document2 = new Document(PageSize.A4);
		// Getting instance of PdfWriter
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document1, baos);
		
		FileOutputStream fos = new FileOutputStream("/"+ed.getHolderName()+"-"+ed.getTraceId());
		PdfWriter.getInstance(document2, fos);
		// Opening the created document to change it
		document1.open();
		document2.open();
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
		document1.add(p);
		document2.add(p);

		// Creating a table of the 4 columns
		PdfPTable table = new PdfPTable(8);
		// Setting width of the table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 2, 3, 6, 4, 3, 4, 2, 3 });
		table.setSpacingBefore(5);
		// Create Table Cells for the table header
		PdfPCell cell = new PdfPCell();
		// Setting the background color and padding of the table cell
		cell.setBackgroundColor(CMYKColor.GRAY);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(CMYKColor.WHITE);
		// Adding headings in the created table cell or header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Case Number : " + ed.getCaseNum(), font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name : " + ed.getHolderName(), font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN : " + ed.getHolderSsn(), font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Name : " + ed.getPlanName(), font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Plan Status : " + ed.getPlanStatus(), font));
		table.addCell(cell);
		if ("Approved".equals(ed.getPlanStatus())) {
			cell.setPhrase(new Phrase("Start Date : " + ed.getPlanStartDate(), font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("End Date : " + ed.getPlanEndDate(), font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Benefit Amount : " + ed.getBenefitAmount(), font));
			table.addCell(cell);
		} else {
			cell.setPhrase(new Phrase("Denial Reason : " + ed.getDenialReason(), font));
			table.addCell(cell);
		}
		cell.setPhrase(new Phrase("DHS Office Address : Some Address", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Contact Number : Some Number", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Website : Some Url", font));
		table.addCell(cell);

		// Adding the created table to the document
		document1.add(table);
		document2.add(table);
		// Closing the document
		document1.close();
		document2.close();
		fos.close();
		
		pdfWriter.flush();
		return baos.toByteArray();
	}
}
