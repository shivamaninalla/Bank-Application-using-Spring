package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class RegisterDto {
	
	@Email
	private String email;

	
	@NotBlank
    @Size(min=2,max=20,message="please check the size of firstName.")
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	private boolean admin;

	
	@NotBlank
	private String password;

}
