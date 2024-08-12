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

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/users")
@RestController
public class AdminController {

	private ContactService contactService;

	public AdminController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}


	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get All Users")
	public ResponseEntity<PagedResponse<UserResponseDto>> getAllUsers(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction){
		return new ResponseEntity<PagedResponse<UserResponseDto>>(contactService.getAllUsers(page, size, sortBy, direction),HttpStatus.OK);
				
	}
	
	@Operation(summary = "Get User By Id")
	@GetMapping("{user_id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name="user_id") int user_id) {

		return new ResponseEntity<UserResponseDto>(contactService.getUserById(user_id), HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Create User")
	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {

		return new ResponseEntity<UserResponseDto>(contactService.createUser(userRequestDto), HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Update User Profile")
	@PutMapping("{user_id}")
	public ResponseEntity<UserResponseDto> updateUserProfile(@RequestBody UserRequestDto userRequestDto,@PathVariable(name="user_id") int user_id) {

		return new ResponseEntity<UserResponseDto>(contactService.updateUserProfile(userRequestDto,user_id), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Delete User")
	@DeleteMapping("{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name="user_id") int user_id) {
		String user = contactService.deleteUser(user_id);

		return new ResponseEntity<String>(user, HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	
	

}
