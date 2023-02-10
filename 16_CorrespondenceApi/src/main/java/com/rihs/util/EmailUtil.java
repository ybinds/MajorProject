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
		
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			
			helper.setTo(req.getEmailTo());
			helper.setSubject(req.getEmailSubject());
			helper.setText(req.getEmailText(), true);
			helper.setFrom(req.getEmailFrom());
			
//			FileSystemResource file = new FileSystemResource(new File(fileToAttach));
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.addAttachment("logo.jpg", file);
			emailSender.send(mimeMessage);
			isSent = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
        return isSent;
	}
}
