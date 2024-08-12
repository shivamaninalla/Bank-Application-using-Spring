package com.techlabs.app.dto;



import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class AccountRequestDto {
	
	
	private long accountNumber;

	
	private BankRequestDto bankrequestDto;

	
	private CustomerRequestDto customerRequestDto;

	private List<TransactionRequestDto> sentTransactions;
	
	private List<TransactionRequestDto> receiverTransactions;

	@NotNull
	private double balance;


}