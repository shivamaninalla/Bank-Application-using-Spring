package com.techlabs.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;

public interface BankService {

PagedResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction);

PagedResponse<TransactionResponseDto> viewAllTransaction(LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction);

	//String deleteCustomerById(long id);

	CustomerResponseDto findCustomerByid(long id);

	CustomerResponseDto addAccount(long cid, int bid);

	TransactionResponseDto doTransaction(long senderAccountno, long receiverAccountno, double amount);

	//PagedResponse<TransactionResponseDto> getAllTransaction(LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction);

	PagedResponse<TransactionResponseDto> viewPassbook(long accountNo, LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction) throws DocumentException, IOException, MessagingException;

	UserResponseDto createCustomer(CustomerRequestDto customerRequestDto, long userID);

	String updateProfile(ProfileRequestDto profileRequestDto);

	AccountResponseDto depositAmount(long accountNumber, double amount);
	
	List<AccountResponseDto> getAccounts();



	String activateCustomer(long customerID);



	String deleteAccount(long accountNumber);



	String activateAccount(long accountNumber);



	AccountResponseDto viewBalance(long accountNumber);



	String deleteCustomer(long customerID);
}