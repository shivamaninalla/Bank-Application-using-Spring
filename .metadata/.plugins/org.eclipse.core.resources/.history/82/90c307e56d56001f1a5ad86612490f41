package com.techlabs.app.dto;



import java.time.LocalDateTime;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class TransactionRequestDTO {
	
	private long id;
	
	
	private Account senderAccount;
	
	
	private Account receiverAccount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	
	private LocalDateTime transactionDate;
	
	@NotNull
	private double amount;

}