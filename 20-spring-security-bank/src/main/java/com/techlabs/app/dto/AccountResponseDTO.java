package com.techlabs.app.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;

import lombok.Data;


@Data
@JsonInclude(value = Include.NON_NULL)
public class AccountResponseDTO {
	
	private long accountNumber;

	
	private Bank bank;

	
	private Customer customer;

	private List<TransactionResponseDTO> sentTransactions;
	
	private List<TransactionResponseDTO> receiverTransactions;

	private double balance;


}