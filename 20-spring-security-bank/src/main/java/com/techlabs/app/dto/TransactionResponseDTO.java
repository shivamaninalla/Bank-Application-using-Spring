package com.techlabs.app.dto;



import java.time.LocalDateTime;

import com.techlabs.app.entity.TransactionType;

import lombok.Data;

@Data

public class TransactionResponseDTO {
	
	private long id;
	
	
	private AccountResponseDTO senderAccount;
	 
	
	
	private AccountResponseDTO receiverAccount;
	

	private TransactionType transactionType;
	
	
	private LocalDateTime transactionDate;

	private double amount;

}