package com.monocept.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.monocept.app.service.MailService;

import jakarta.mail.MessagingException;

@SpringBootApplication
public class Application {
	
	@Autowired
	private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		mailService.mailWithAttachmens("shivamani.nalla692@gmail.com", "This is body", "Demo mail","C:/Users/shiva/Downloads/agile and scrum.pptx");
//	}

}
