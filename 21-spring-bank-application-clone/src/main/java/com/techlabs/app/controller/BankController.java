package com.techlabs.app.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.dto.CustomerRequestDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.dto.ProfileRequestDTO;
import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.dto.UserRequestDTO;
import com.techlabs.app.dto.UserResponseDTO;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;

@RestController
@RequestMapping("/api/auth/customer")

public class BankController {
	BankService bankService;
	
	
	
	public BankController(BankService bankService) {
		super();
		this.bankService = bankService;
	}



	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<CustomerResponseDTO>> getAllCustomers(@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="sortBy",defaultValue = "firstName")String sortBy,
			@RequestParam(name="direction",defaultValue="asc")String direction)
	{
		PagedResponse <CustomerResponseDTO> customer=bankService.getAllCustomers(page,size,sortBy,direction);
		return new ResponseEntity<PagedResponse<CustomerResponseDTO>>(customer,HttpStatus.ACCEPTED);
	}
	
//	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity <CustomerResponseDto> addCustomer(@RequestBody CustomerRequestDto customerrequestdto) {
//	 return new ResponseEntity<CustomerResponseDto>(bankService.addCustomer(customerrequestdto),HttpStatus.ACCEPTED);
//	}
	@PostMapping("/admin/{userID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO>createCustomer(@RequestBody CustomerRequestDTO customerRequestDto,@PathVariable(name = "userID") long userID) {
        return new ResponseEntity<UserResponseDTO>(bankService.createCustomer(customerRequestDto,userID),HttpStatus.ACCEPTED);
    }
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<String>deleteCustomerById(@PathVariable(name="id") long id){
		String message=bankService.deleteCustomerById(id);
		  return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity <CustomerResponseDTO> findCustomerbyId(@PathVariable(name="id") long id){
		return new ResponseEntity <CustomerResponseDTO>(bankService.findCustomerByid(id),HttpStatus.OK);
	}
	
	
	@PostMapping("{cid}/account/{bid}")
	public ResponseEntity<CustomerResponseDTO> addAccount(@PathVariable(name="cid")long cid ,@PathVariable(name="bid") int bid){
		return new ResponseEntity<CustomerResponseDTO>(bankService.addAccount(cid,bid),HttpStatus.OK);
	}
	
	
//	@PostMapping("{senderAccountno}/transaction/{receiverAccountno}/{amount}")
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<TransactionResponseDto> doTransaction(@PathVariable(name="senderAccountno") long senderAccountno,@PathVariable(name="receiverAccountno") long receiverAccountno,@PathVariable(name="amount") double amount){
//		return new ResponseEntity<TransactionResponseDto>(bankService.doTransaction(senderAccountno,receiverAccountno,amount),HttpStatus.OK);
//	}
	
	@PostMapping("/transactions")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<TransactionResponseDTO> doTransaction(
			@RequestParam(name = "senderAccountNumber") long senderAccountNumber,
			@RequestParam(name = "receiverAccountNumber") long receiverAccountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<TransactionResponseDTO>(bankService.doTransaction(senderAccountNumber, receiverAccountNumber, amount),HttpStatus.OK);
	}
	
	
	@GetMapping("/customers/passbook/{accountNumber}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> getPassbook(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);
		PagedResponse<TransactionResponseDTO> passbook=bankService.viewPassbook(accountNumber,fromDate,toDate,page,size,sortBy,direction);
		
		return new  ResponseEntity<PagedResponse<TransactionResponseDTO>>(passbook,HttpStatus.OK);
	}
	
//	@GetMapping("/passbook/{Accountno}")
//	@PreAuthorize("hasRole('USER')")
//	
//	public ResponseEntity<List<TransactionResponseDto>> viewPassbook(@PathVariable(name="Accountno")long accountNo){
//		return new ResponseEntity<List<TransactionResponseDto>>(bankService.viewPassbook(accountNo),HttpStatus.OK);
//	} 
	
	

	
	
	@PutMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileRequestDTO profileRequestDto) {
		String message=bankService.updateProfile(profileRequestDto);
		return new  ResponseEntity<String>(message,HttpStatus.OK);
		

	}
	
	
	
	
	@GetMapping("/transactions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> getAllTransactions(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to) {

		System.out.println(from);
		System.out.println(to);
		
		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);

		PagedResponse<TransactionResponseDTO> transactions=	bankService.viewAllTransaction(fromDate, toDate, page, size, sortBy, direction);
		return new ResponseEntity<PagedResponse<TransactionResponseDTO>>(transactions,HttpStatus.OK);
	}
	
	@PutMapping("transactions/{accountNumber}/deposit")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<AccountResponseDTO> deposit(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<AccountResponseDTO>(bankService.depositAmount(accountNumber, amount),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/customers/accounts")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
		return new ResponseEntity<List<AccountResponseDTO>>(bankService.getAccounts(), HttpStatus.OK);
	}
	
	
	
	
	
	

}