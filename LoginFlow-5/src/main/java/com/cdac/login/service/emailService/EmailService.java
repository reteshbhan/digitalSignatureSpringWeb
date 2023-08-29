package com.cdac.login.service.emailService;

import java.io.File;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSenderObj;
	
	public void sendMimeEmail(String from, String[] to, String body, String subject, File[] fileArrayAttachments) {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		
		try {
			MimeMessage mimeMessageObj = javaMailSenderObj.createMimeMessage();
			MimeMessageHelper mimeMessageHelperObj = new MimeMessageHelper(mimeMessageObj,true);
			mimeMessageHelperObj.setFrom(from);
			mimeMessageHelperObj.setTo(to);
			mimeMessageHelperObj.setText(body);
			mimeMessageHelperObj.setSubject(subject);
			
			for(File file : fileArrayAttachments) {
				FileSystemResource fileSystemResourceObj = new FileSystemResource(file);
				mimeMessageHelperObj.addAttachment(fileSystemResourceObj.getFilename(), fileSystemResourceObj);
			}
			javaMailSenderObj.send(mimeMessageObj);
			
//			Properties props = new Properties();
//			props.put("mail.smtp.starttls.enable", "true");
			
			
			/** DELETING THE FILES FROM SERVER FOLDER.*/
			for(File file : fileArrayAttachments) {
				file.delete();
			}

			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
