package com.rihs.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rihs.binding.EmailReq;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender emailSender;
	
	public boolean sendEmail(EmailReq req) {
		
		boolean isSent = false;
		
		/*
		 * try { //construct the text body part MimeBodyPart textBodyPart = new
		 * MimeBodyPart(); textBodyPart.setText(req.getEmailText());
		 * 
		 * //now write the PDF content to the output stream byte[] bytes =
		 * req.getFileContent();
		 * 
		 * //construct the pdf body part DataSource dataSource = new
		 * ByteArrayDataSource(bytes, "application/pdf"); MimeBodyPart pdfBodyPart = new
		 * MimeBodyPart(); pdfBodyPart.setDataHandler(new DataHandler(dataSource));
		 * pdfBodyPart.setFileName("notice.pdf");
		 * 
		 * //construct the mime multi part MimeMultipart mimeMultipart = new
		 * MimeMultipart(); mimeMultipart.addBodyPart(textBodyPart);
		 * mimeMultipart.addBodyPart(pdfBodyPart);
		 * 
		 * //create the sender/recipient addresses InternetAddress iaSender = new
		 * InternetAddress(req.getEmailFrom()); InternetAddress iaRecipient = new
		 * InternetAddress(req.getEmailTo());
		 * 
		 * //construct the mime message MimeMessage mimeMessage =
		 * emailSender.createMimeMessage(); mimeMessage.setSender(iaSender);
		 * mimeMessage.setSubject(req.getEmailSubject());
		 * mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
		 * mimeMessage.setContent(mimeMultipart);
		 * 
		 * //send off the email Transport.send(mimeMessage);
		 * 
		 * System.out.println("sent from " + req.getEmailFrom() + ", to " +
		 * req.getEmailTo() ); isSent = true; } catch(Exception ex) {
		 * ex.printStackTrace(); }
		 */
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			
			helper.setTo(req.getEmailTo());
			helper.setSubject(req.getEmailSubject());
			helper.setText(req.getEmailText(), true);
			helper.setFrom(req.getEmailFrom());
			
			FileSystemResource file = new FileSystemResource(new File(req.getFileName()));
            helper.addAttachment("notice.pdf", file);
			
			emailSender.send(mimeMessage);
			isSent = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}
}
