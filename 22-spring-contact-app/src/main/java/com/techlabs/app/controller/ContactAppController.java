package com.techlabs.app.controller;

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

import com.techlabs.app.dto.ContactDetailsRequestDto;
import com.techlabs.app.dto.ContactDetailsResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.ContactService;
import com.techlabs.app.util.PagedResponse;

import jakarta.validation.Valid;

@RequestMapping("api/auth")
@RestController
public class ContactAppController {

	private ContactService contactService;

	public ContactAppController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {

		return new ResponseEntity<List<UserResponseDto>>(contactService.getAllUsers(), HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("{user_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name="user_id") int user_id) {

		return new ResponseEntity<UserResponseDto>(contactService.getUserById(user_id), HttpStatus.ACCEPTED);
	}
	
	

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {

		return new ResponseEntity<UserResponseDto>(contactService.createUser(userRequestDto), HttpStatus.ACCEPTED);
	}

	@PutMapping("update/{user_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> updateUserProfile(@RequestBody UserRequestDto userRequestDto,@PathVariable(name="user_id") int user_id) {

		return new ResponseEntity<UserResponseDto>(contactService.updateUserProfile(userRequestDto,user_id), HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("delete/{user_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteUser(@PathVariable(name="user_id") int user_id) {
		String user = contactService.deleteUser(user_id);

		return new ResponseEntity<String>(user, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("contact")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactResponseDto> createContactforStaff(@Valid @RequestBody ContactRequestDto contactRequestDto) {
		ContactResponseDto contact = contactService.createContactforStaff(contactRequestDto);

		return new ResponseEntity<ContactResponseDto>(contact, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("contacts")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<PagedResponse<ContactResponseDto>> getAllContactsofStaff(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<ContactResponseDto> message=contactService.getAllContactsofStaff(page,size,sortBy,direction);

		return new ResponseEntity<PagedResponse<ContactResponseDto>>(message, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("contacts/{contact_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactResponseDto> getContactByIdofStaff(@PathVariable(name="contact_id") int contact_id){
		return new ResponseEntity<ContactResponseDto>(contactService.getContactByIdofStaff(contact_id), HttpStatus.ACCEPTED);

	}
	
	
	@PutMapping("update_contact/{contact_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactResponseDto> updateContactForStaff( @RequestBody ContactRequestDto contactRequestDto,@PathVariable(name="contact_id") int contact_id){
		return new ResponseEntity<ContactResponseDto>(contactService.updateContactForStaff(contactRequestDto,contact_id), HttpStatus.ACCEPTED);

	}
	
	
	@DeleteMapping("{contact_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<String> deleteContactForStaff(@PathVariable(name="contact_id") int contact_id) {
		String message=contactService.deleteContactForStaff(contact_id);
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}
	
	
	@PostMapping("contacts/{contactId}/details")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactDetailsResponseDto> createContactDetailsforContact(@Valid @RequestBody ContactDetailsRequestDto contactDetailsRequestDto,@PathVariable(name="contactId") int contactId) {
		ContactDetailsResponseDto contact = contactService.createContactDetailsforContact(contactDetailsRequestDto,contactId);

		return new ResponseEntity<ContactDetailsResponseDto>(contact, HttpStatus.ACCEPTED);
	}

	
	
	@GetMapping("contactsDetails/{contactId}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<PagedResponse<ContactDetailsResponseDto>> getAllContactDetailsofContact(@PathVariable(name="contactId") int contactId,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<ContactDetailsResponseDto> message=contactService.getAllContactDetailsofContact(contactId,page,size,sortBy,direction);

		return new ResponseEntity<PagedResponse<ContactDetailsResponseDto>>(message, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("contact-details/{contact_id}/{contact_details_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactDetailsResponseDto> updateContactDetailsofContact( @RequestBody ContactDetailsRequestDto contactDetailsRequestDto,@PathVariable(name="contact_id") int contact_id,@PathVariable(name="contact_id") int contact_details_id){
		return new ResponseEntity<ContactDetailsResponseDto>(contactService.updateContactDetailsofContact(contactDetailsRequestDto,contact_id,contact_details_id), HttpStatus.ACCEPTED);

	}
	
	
	@GetMapping("contact-details/{contact_id}/{contact_details_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<ContactDetailsResponseDto> getContactDetailsById(@PathVariable(name="contact_id") int contact_id,@PathVariable(name="contact_details_id") int contact_details_id){
		return new ResponseEntity<ContactDetailsResponseDto>(contactService.getContactDetailsById(contact_id,contact_details_id), HttpStatus.ACCEPTED);

	}
	
	
	
	@DeleteMapping("contact-details/{contact_id}/{contact_details_id}")
	@PreAuthorize("hasRole('STAFF')")
	public ResponseEntity<String> deleteContactDetailsById(@PathVariable(name="contact_id") int contact_id,@PathVariable(name="contact_details_id") int contact_details_id){
		return new ResponseEntity<String>(contactService.deleteContactDetailsById(contact_id,contact_details_id), HttpStatus.ACCEPTED);

	}
	
	
	
	
	
	

}
