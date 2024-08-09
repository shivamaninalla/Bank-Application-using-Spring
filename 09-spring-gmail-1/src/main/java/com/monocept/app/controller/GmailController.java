package com.monocept.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.service.MailService;

import jakarta.mail.MessagingException;

@RestController
public class GmailController {
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("email")
	public ResponseEntity<String> sendEmail(@RequestParam(name="to") String to,@RequestParam(name="body") String body,@RequestParam(name="subject") String subject,@RequestParam(name="filePath") String filePath) throws MessagingException {
		mailService.mailWithAttachmens(to, body, subject, filePath);
		return new ResponseEntity<String>("Successfully mailed to "+to,HttpStatus.OK);
		
	}

}
