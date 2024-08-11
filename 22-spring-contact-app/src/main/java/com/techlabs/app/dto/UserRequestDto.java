package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {
	
	   private int userId;

	   @Email
	   @NotBlank
	   private String email;
	   
	   @NotBlank
	   private String firstName;
	   
	   @NotBlank
	   private String lastName;
	   
	   @NotNull
	   private boolean admin ;  
	   
	   @NotNull
	   private boolean active=true;
	   
	   @NotBlank
	   private String password;
	   
	   
	 

}
