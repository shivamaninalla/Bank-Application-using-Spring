package com.techlabs.app.controller;

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

import com.techlabs.app.dto.ContactDetailsRequestDto;
import com.techlabs.app.dto.ContactDetailsResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.service.ContactService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@PreAuthorize("hasRole('STAFF')")
@RequestMapping("api/contacts")
@RestController
public class StaffController {
	
	private ContactService contactService;

	public StaffController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}
	
	
	@Operation(summary = "Create Contact for Staff")
	@PostMapping()
	public ResponseEntity<ContactResponseDto> createContactforStaff(@Valid @RequestBody ContactRequestDto contactRequestDto) {
		ContactResponseDto contact = contactService.createContactforStaff(contactRequestDto);

		return new ResponseEntity<ContactResponseDto>(contact, HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Get All Contacts of Staff")
	@GetMapping()
	public ResponseEntity<PagedResponse<ContactResponseDto>> getAllContactsofStaff(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<ContactResponseDto> message=contactService.getAllContactsofStaff(page,size,sortBy,direction);

		return new ResponseEntity<PagedResponse<ContactResponseDto>>(message, HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Get Contact By Id")
	@GetMapping("{contact_id}")
	public ResponseEntity<ContactResponseDto> getContactByIdofStaff(@PathVariable(name="contact_id") int contact_id){
		return new ResponseEntity<ContactResponseDto>(contactService.getContactByIdofStaff(contact_id), HttpStatus.ACCEPTED);

	}
	
	
	@Operation(summary = "Update Contact of Staff")
	@PutMapping("{contact_id}")
	public ResponseEntity<ContactResponseDto> updateContactForStaff( @RequestBody ContactRequestDto contactRequestDto,@PathVariable(name="contact_id") int contact_id){
		return new ResponseEntity<ContactResponseDto>(contactService.updateContactForStaff(contactRequestDto,contact_id), HttpStatus.ACCEPTED);

	}
	
	
	@Operation(summary = "Delete Contact for Staff")
	@DeleteMapping("{contact_id}")
	public ResponseEntity<String> deleteContactForStaff(@PathVariable(name="contact_id") int contact_id) {
		String message=contactService.deleteContactForStaff(contact_id);
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}
	
	
	

}
