package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.List;

import com.techlabs.app.dto.CustomerRequestDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.dto.UserResponseDTO;
import com.techlabs.app.util.PagedResponse;

public interface BankService {

	PagedResponse<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction);

	CustomerResponseDTO addCustomer(CustomerRequestDTO customerrequestdto);

	String deleteCustomerById(long id);

	CustomerResponseDTO findCustomerByid(long id);

	CustomerResponseDTO addAccount(long cid, int bid);

	TransactionResponseDTO doTransaction(long senderAccountno, long receiverAccountno, double amount);

	PagedResponse<TransactionResponseDTO> viewAllTransaction(int page, int size, String sortBy, String direction);

	PagedResponse<TransactionResponseDTO> viewPassbook(long accountNo, int page, int size, String sortBy, String direction);

	List<TransactionResponseDTO> searchByDate(LocalDateTime fromDate, LocalDateTime toDate);

	UserResponseDTO createCustomer(CustomerRequestDTO customerRequestDto, long userID);

}
