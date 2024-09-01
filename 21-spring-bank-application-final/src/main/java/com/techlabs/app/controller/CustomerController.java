package com.techlabs.app.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;

@RequestMapping("api/customer")
@RestController
@PreAuthorize("hasRole('USER')")
public class CustomerController {
	BankService bankService;

	public CustomerController(BankService bankService) {
		super();
		this.bankService = bankService;
	}

	@Operation(summary = "Perform Transaction")
	@PostMapping("/transactions")
	public ResponseEntity<TransactionResponseDto> performTransaction(
			@RequestParam(name = "senderAccountNumber") long senderAccountNumber,
			@RequestParam(name = "receiverAccountNumber") long receiverAccountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<TransactionResponseDto>(
				bankService.doTransaction(senderAccountNumber, receiverAccountNumber, amount), HttpStatus.OK);
	}

	@Operation(summary = "View Passbook")
	@GetMapping("/passbook/{accountNumber}")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> getPassbook(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDate).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDate).now().toString()}") String to) throws DocumentException, IOException, MessagingException {
		System.out.println("In cust controller"+from);
		LocalDateTime fromDate = (from != null && !from.isEmpty()) 
	            ? parseDate(from).atStartOfDay() 
	            : LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0).withNano(0);

	    LocalDateTime toDate = (to != null && !to.isEmpty()) 
	            ? parseDate(to).atTime(23, 59, 59) 
	            : LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(bankService.viewPassbook(accountNumber, fromDate, toDate, page, size, sortBy, direction),HttpStatus.OK);
	}
	
	
	private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(DateTimeFormatter.ofPattern("yyyy-MM-dd"),
			DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	private LocalDate parseDate(String dateStr) {
		for (DateTimeFormatter formatter : FORMATTERS) {
			try {
				return LocalDate.parse(dateStr, formatter);
			} catch (DateTimeParseException e) {
			}
		}
		return LocalDate.now();
	}
	@Operation(summary = "View All Accounts")
	@GetMapping("/accounts")
	public ResponseEntity<PagedResponse<AccountResponseDto>> viewAllAccounts(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "accountNumber") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		return new ResponseEntity<PagedResponse<AccountResponseDto>>(bankService.viewAllAccounts(page,size,sortBy,direction), HttpStatus.OK);
	}

	@Operation(summary = "View Total Balance")
	@GetMapping("accounts/{accountNumber}/view-balance")
	public AccountResponseDto viewTotalBalance(@PathVariable(name = "accountNumber") long accountNumber) {
		return bankService.viewBalance(accountNumber);
	}

	@Operation(summary = "Alter Profile")
	@PutMapping("/profile")
	public ResponseEntity<String> alterProfile(@RequestBody ProfileRequestDto profileRequestDto) {
		String message = bankService.updateProfile(profileRequestDto);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	
	@Operation(summary = "Deposit Amount")
	@PutMapping("transactions/{accountNumber}/deposit")
	public ResponseEntity<AccountResponseDto> depositAmount(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<AccountResponseDto>(bankService.depositAmount(accountNumber, amount),
				HttpStatus.ACCEPTED);
	}
	
	

}
