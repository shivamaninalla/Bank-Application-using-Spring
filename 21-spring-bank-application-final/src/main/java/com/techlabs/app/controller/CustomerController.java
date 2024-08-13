package com.techlabs.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
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
	public ResponseEntity<PagedResponse<TransactionResponseDto>> viewPassbook(
			@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction)
			throws DocumentException, IOException, MessagingException {
		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);
		PagedResponse<TransactionResponseDto> passbook = bankService.viewPassbook(accountNumber, fromDate, toDate, page,
				size, sortBy, direction);

		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(passbook, HttpStatus.OK);
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
