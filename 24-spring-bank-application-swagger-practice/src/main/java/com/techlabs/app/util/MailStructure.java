package com.techlabs.app.util;

import lombok.Data;

@Data
public class MailStructure {

	private String toEmail;
	private String subject;
	private String emailBody;
}