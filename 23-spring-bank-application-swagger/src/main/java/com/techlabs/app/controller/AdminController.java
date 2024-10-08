package com.techlabs.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	BankService bankService;

	public AdminController(BankService bankService) {
		super();
		this.bankService = bankService;
	}

	@PostMapping("/{userID}")
	public ResponseEntity<UserResponseDto> addCustomer(@RequestBody CustomerRequestDto customerRequestDto,
			@PathVariable(name = "userID") long userID) {
		return new ResponseEntity<UserResponseDto>(bankService.createCustomer(customerRequestDto, userID),
				HttpStatus.ACCEPTED);
	}

	@PostMapping("{cid}/account/{bid}")
	public ResponseEntity<CustomerResponseDto> createAccount(@PathVariable(name = "cid") long cid,
			@PathVariable(name = "bid") int bid) {
		return new ResponseEntity<CustomerResponseDto>(bankService.addAccount(cid, bid), HttpStatus.OK);
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDto> viewCustomerbyId(@PathVariable(name = "id") long id) {
		return new ResponseEntity<CustomerResponseDto>(bankService.findCustomerByid(id), HttpStatus.OK);
	}

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

	@PutMapping("/active/{customerID}")
	public String activateExistingCustomer(@PathVariable(name = "customerID") long customerID) {
		return bankService.activateCustomer(customerID);
	}

	@PutMapping("customer/accounts/active/{accountNumber}")
	public String activateExistingAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return bankService.activateAccount(accountNumber);
	}

	@DeleteMapping("accounts/{accountNumber}")
	public String deleteAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return bankService.deleteAccount(accountNumber);
	}

	@DeleteMapping("customer/{customerID}")
	public String deleteCustomer(@PathVariable(name = "customerID") long customerID) {
		return bankService.deleteCustomer(customerID);
	}
	
	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@GetMapping("/transactions")
//	@Operation(summary = "Retrieve all transactions within a specified date range.", description = "Fetches all transactions for the bank between the specified dates. Pagination and sorting are supported.", tags = {"Transactions", "Get"})
//	public ResponseEntity<PagedResponse<TransactionResponseDto>> getAllTransactions(
//	        @RequestParam(name = "page", defaultValue = "0") int page,
//	        @RequestParam(name = "size", defaultValue = "5") int size,
//	        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
//	        @RequestParam(name = "direction", defaultValue = "asc") String direction,
//	        @RequestParam(name = "from", required = false) String from,
//	        @RequestParam(name = "to", required = false) String to) {
//
//	    // Correctly checking if from and to are null or empty
//	    LocalDateTime fromDate = (from != null && !from.isEmpty()) 
//	            ? parseDate(from).atStartOfDay() 
//	            : LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0).withNano(0);
//
//	    LocalDateTime toDate = (to != null && !to.isEmpty()) 
//	            ? parseDate(to).atTime(23, 59, 59) 
//	            : LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
//
//	    return new ResponseEntity<>(
//	            bankService.viewAllTransaction(fromDate, toDate, page, size, sortBy, direction),
//	            HttpStatus.OK
//	    );
//	}
//	
//
//	private LocalDate parseDate(String dateStr) {
//        for (DateTimeFormatter formatter : FORMATTERS) {
//            try {
//                return LocalDate.parse(dateStr, formatter);
//            } catch (DateTimeParseException e) {
//            }
//        }
//        return LocalDate.now();
//    }
//	private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
//            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
//            DateTimeFormatter.ofPattern("dd-MM-yyyy")
//    );

}