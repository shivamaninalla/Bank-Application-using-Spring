package com.monocept.app.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailProperties mailProperties;
	
	public void mailWithAttachmens(String to,String body, String subject, String filePath) throws MessagingException {
		
		MimeMessage mineMessage=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mineMessage,true);
		
		helper.setSubject(subject);
		helper.setFrom(mailProperties.getUsername());
		helper.setText(body);
		helper.setTo(to);
		
		FileSystemResource file=new FileSystemResource(new File(filePath));
		helper.addAttachment(file.getFilename(), file);
		
		mailSender.send(mineMessage);
		
		System.out.println("Mail sent successfully");
	}

}
