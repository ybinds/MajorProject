package com.rihs.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.rihs.binding.EmailReq;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender emailSender;
	
	public boolean sendEmail(EmailReq req) {
		
		boolean isSent = false;
		
		try {			
			//construct the text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(req.getEmailText());
			
			//now write the PDF content to the output stream
			byte[] bytes = req.getFileContent();
			
			//construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			pdfBodyPart.setFileName("notice.pdf");
						
			//construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);
			
			//create the sender/recipient addresses
			InternetAddress iaSender = new InternetAddress(req.getEmailFrom());
			InternetAddress iaRecipient = new InternetAddress(req.getEmailTo());
			
			//construct the mime message
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			mimeMessage.setSender(iaSender);
			mimeMessage.setSubject(req.getEmailSubject());
			mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
			mimeMessage.setContent(mimeMultipart);
			
			//send off the email
			Transport.send(mimeMessage);
			
			System.out.println("sent from " + req.getEmailFrom() + 
					", to " + req.getEmailTo() );
			isSent = true;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
        return isSent;
	}
}
