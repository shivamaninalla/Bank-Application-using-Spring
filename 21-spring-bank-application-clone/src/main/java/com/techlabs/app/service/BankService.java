package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.List;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.dto.CustomerRequestDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.dto.ProfileRequestDTO;
import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.dto.UserResponseDTO;
import com.techlabs.app.util.PagedResponse;

public interface BankService {

	PagedResponse<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction);

	//CustomerResponseDto addCustomer(CustomerRequestDto customerrequestdto);

	String deleteCustomerById(long id);

	CustomerResponseDTO findCustomerByid(long id);

	CustomerResponseDTO addAccount(long cid, int bid);

	TransactionResponseDTO doTransaction(long senderAccountno, long receiverAccountno, double amount);

	PagedResponse<TransactionResponseDTO> viewAllTransaction(LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction);

	PagedResponse<TransactionResponseDTO> viewPassbook(long accountNo, LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction);

	//List<TransactionResponseDto> searchByDate(LocalDateTime fromDate, LocalDateTime toDate);

	UserResponseDTO createCustomer(CustomerRequestDTO customerRequestDto, long userID);

	String updateProfile(ProfileRequestDTO profileRequestDto);

	AccountResponseDTO depositAmount(long accountNumber, double amount);

	List<AccountResponseDTO> getAccounts();

}