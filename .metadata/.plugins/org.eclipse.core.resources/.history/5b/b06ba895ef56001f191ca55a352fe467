package com.techlabs.app.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;

import lombok.Data;


@Data
@JsonInclude(value = Include.NON_NULL)
public class AccountResponseDto {
	
	private long accountNumber;

	
	private Bank bank;

	
	private Customer customer;

	private List<TransactionResponseDto> sentTransactions;
	
	private List<TransactionResponseDto> receiverTransactions;

	@JsonInclude(value = Include.NON_DEFAULT)
	private double balance;


}