package com.techlabs.app.dto;



import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class AccountRequestDTO {
	
	
	private long accountNumber;

	
	private BankRequestDTO bankrequestDto;

	
	private CustomerRequestDTO customerRequestDto;

	private List<TransactionRequestDTO> sentTransactions;
	
	private List<TransactionRequestDTO> receiverTransactions;

	@NotNull
	private double balance;


}