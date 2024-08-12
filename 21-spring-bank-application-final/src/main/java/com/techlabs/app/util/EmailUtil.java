package com.techlabs.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailUtil {
	@Value("${spring.mail.username}")
	private String fromEmail;
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(MailStructure mailStructure) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(mailStructure.getToEmail());
		mailMessage.setText(mailStructure.getEmailBody());
		mailMessage.setSubject(mailStructure.getSubject());
		javaMailSender.send(mailMessage);
	}

    public void sendEmailWithAttachment(MailStructure mailStructure, byte[] pdfBytes) throws MessagingException {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(mailStructure.getToEmail());
        helper.setSubject(mailStructure.getSubject());
        helper.setText(mailStructure.getEmailBody());

        // Attach PDF as byte array
        InputStreamSource attachment = new ByteArrayResource(pdfBytes);
        helper.addAttachment("passbook.pdf", attachment, "application/pdf");

        javaMailSender.send(message);
    }
}