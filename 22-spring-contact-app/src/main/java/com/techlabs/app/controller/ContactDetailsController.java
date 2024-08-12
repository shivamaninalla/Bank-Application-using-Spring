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
import com.techlabs.app.service.ContactService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@PreAuthorize("hasRole('STAFF')")
@RequestMapping("api/contacts")
@RestController
public class ContactDetailsController {

	private ContactService contactService;

	public ContactDetailsController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}

	@Operation(summary = "Create Contact Details for Contact")
	@PostMapping("{contactId}/details")
	public ResponseEntity<ContactDetailsResponseDto> createContactDetailsforContact(
			@Valid @RequestBody ContactDetailsRequestDto contactDetailsRequestDto,
			@PathVariable(name = "contactId") int contactId) {
		ContactDetailsResponseDto contact = contactService.createContactDetailsforContact(contactDetailsRequestDto,
				contactId);

		return new ResponseEntity<ContactDetailsResponseDto>(contact, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Get All Contact Details of Contact")
	@GetMapping("{contactId}/contactsDetails")
	public ResponseEntity<PagedResponse<ContactDetailsResponseDto>> getAllContactDetailsofContact(
			@PathVariable(name = "contactId") int contactId, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<ContactDetailsResponseDto> message = contactService.getAllContactDetailsofContact(contactId, page,
				size, sortBy, direction);

		return new ResponseEntity<PagedResponse<ContactDetailsResponseDto>>(message, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Get Contact Details by Id")
	@GetMapping("contact-details/{contact_id}/{contact_details_id}")
	public ResponseEntity<ContactDetailsResponseDto> getContactDetailsById(
			@PathVariable(name = "contact_id") int contact_id,
			@PathVariable(name = "contact_details_id") int contact_details_id) {
		return new ResponseEntity<ContactDetailsResponseDto>(
				contactService.getContactDetailsById(contact_id, contact_details_id), HttpStatus.ACCEPTED);

	}

	@Operation(summary = "Update Contact Details of Contact")
	@PutMapping("contact-details/{contact_id}/{contact_details_id}")
	public ResponseEntity<ContactDetailsResponseDto> updateContactDetailsofContact(
			@RequestBody ContactDetailsRequestDto contactDetailsRequestDto,
			@PathVariable(name = "contact_id") int contact_id,
			@PathVariable(name = "contact_details_id") int contact_details_id) {
		return new ResponseEntity<ContactDetailsResponseDto>(
				contactService.updateContactDetailsofContact(contactDetailsRequestDto, contact_id, contact_details_id),
				HttpStatus.ACCEPTED);

	}

	@Operation(summary = "Delete Contact Details by Id")
	@DeleteMapping("contact-details/{contact_id}/{contact_details_id}")
	public ResponseEntity<String> deleteContactDetailsById(@PathVariable(name = "contact_id") int contact_id,
			@PathVariable(name = "contact_details_id") int contact_details_id) {
		return new ResponseEntity<String>(contactService.deleteContactDetailsById(contact_id, contact_details_id),
				HttpStatus.ACCEPTED);

	}

}
