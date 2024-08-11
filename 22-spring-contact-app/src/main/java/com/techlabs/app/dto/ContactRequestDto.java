package com.techlabs.app.dto;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactRequestDto {
	
	
	private int id;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	private boolean active;

	private UserRequestDto user;
	
	private List<ContactDetailsRequestDto> contactDetails; 

}
