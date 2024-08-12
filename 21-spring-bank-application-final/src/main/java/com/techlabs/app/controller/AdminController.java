package com.techlabs.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	BankService bankService;

	public AdminController(BankService bankService) {
		super();
		this.bankService = bankService;
	}

	@Operation(summary = "Add Customer")
	@PostMapping("/{userID}")
	public ResponseEntity<UserResponseDto> addCustomer(@RequestBody CustomerRequestDto customerRequestDto,
			@PathVariable(name = "userID") long userID) {
		return new ResponseEntity<UserResponseDto>(bankService.createCustomer(customerRequestDto, userID),
				HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Create Account")
	@PostMapping("{cid}/account/{bid}")
	public ResponseEntity<CustomerResponseDto> createAccount(@PathVariable(name = "cid") long cid,
			@PathVariable(name = "bid") int bid) {
		return new ResponseEntity<CustomerResponseDto>(bankService.addAccount(cid, bid), HttpStatus.OK);
	}
	
	@Operation(summary = "View All Customers")
	@GetMapping
	public ResponseEntity<PagedResponse<CustomerResponseDto>> viewAllCustomers(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<CustomerResponseDto> customer = bankService.getAllCustomers(page, size, sortBy, direction);
		return new ResponseEntity<PagedResponse<CustomerResponseDto>>(customer, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "View Customer By Id")
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDto> viewCustomerbyId(@PathVariable(name = "id") long id) {
		return new ResponseEntity<CustomerResponseDto>(bankService.findCustomerByid(id), HttpStatus.OK);
	}

	@Operation(summary = "View All Transactions")
	@GetMapping("/transactions")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> viewAllTransactions(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to) {

		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);

		PagedResponse<TransactionResponseDto> transactions = bankService.viewAllTransaction(fromDate, toDate, page,
				size, sortBy, direction);
		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(transactions, HttpStatus.OK);
	}

	
	@Operation(summary = "Activate Existing Customer")
	@PutMapping("/active/{customerID}")
	public String activateExistingCustomer(@PathVariable(name = "customerID") long customerID) {
		return bankService.activateCustomer(customerID);
	}

	
	@Operation(summary = "Activate Existing Account")
	@PutMapping("customer/accounts/active/{accountNumber}")
	public String activateExistingAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return bankService.activateAccount(accountNumber);
	}

	
	@Operation(summary = "Delete Account")
	@DeleteMapping("accounts/{accountNumber}")
	public String deleteAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return bankService.deleteAccount(accountNumber);
	}

	@Operation(summary = "Delete Customer")
	@DeleteMapping("customer/{customerID}")
	public String deleteCustomer(@PathVariable(name = "customerID") long customerID) {
		return bankService.deleteCustomer(customerID);
	}

}