package com.techlabs.app.dto;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequestDto {
	
	
	private int id;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private boolean active;

	private UserRequestDto user;
	
	private List<ContactDetailsRequestDto> contactDetails; 

}
