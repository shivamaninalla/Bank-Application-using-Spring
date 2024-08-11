package com.techlabs.app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Id;
import lombok.Data;

@Data

public class RegisterDto {
	
	@Email
	private String email;

	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	private boolean admin;

	
	@NotBlank
	private String password;

}
