package com.techlabs.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.CustomerRequestDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.dto.TransactionResponseDTO;
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
//	public ResponseEntity <CustomerResponseDTO> addCustomer(@RequestBody CustomerRequestDTO customerrequestdto) {
//	 return new ResponseEntity<CustomerResponseDTO>(bankService.addCustomer(customerrequestdto),HttpStatus.ACCEPTED);
//	}
	
	@PostMapping("/admin/customers/{userID}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createCustomer(@RequestBody CustomerRequestDTO customerRequestDto,@PathVariable(name = "userID") long userID) {
        return bankService.createCustomer(customerRequestDto,userID);
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
	
	
	@PostMapping("{senderAccountno}/transaction/{receiverAccountno}/{amount}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<TransactionResponseDTO> doTransaction(@PathVariable(name="senderAccountno") long senderAccountno,@PathVariable(name="receiverAccountno") long receiverAccountno,@PathVariable(name="amount") double amount){
		return new ResponseEntity<TransactionResponseDTO>(bankService.doTransaction(senderAccountno,receiverAccountno,amount),HttpStatus.OK);
	}
	
	
	@GetMapping("/transaction")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> viewTransaction(@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="sortBy",defaultValue = "id")String sortBy,
			@RequestParam(name="direction",defaultValue="asc")String direction){
		return new  ResponseEntity<PagedResponse<TransactionResponseDTO>>(bankService.viewAllTransaction(page,size,sortBy,direction),HttpStatus.OK);
	}
	
	@GetMapping("/passbook/{Accountno}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> viewPassbook(@PathVariable(name="Accountno")long accountNo,@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="sortBy",defaultValue = "id")String sortBy,
			@RequestParam(name="direction",defaultValue="asc")String direction){
		return new ResponseEntity<PagedResponse<TransactionResponseDTO>>(bankService.viewPassbook(accountNo,page,size,sortBy,direction),HttpStatus.OK);
	} 
	
//	@GetMapping("/searchByDate")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<List<TransactionResponseDTO>> searchByDate(@RequestParam(name="fromDate") LocalDateTime fromDate, @RequestParam(name="toDate") LocalDateTime toDate){
//		return new ResponseEntity<List<TransactionResponseDTO>>(bankService.searchByDate(fromDate,toDate),HttpStatus.OK);
//	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/searchByDate")
    public ResponseEntity<List<TransactionResponseDTO>> searchByDate(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

        List<TransactionResponseDTO> transactions = bankService.searchByDate(fromDate, toDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
	
	
	
	

}
