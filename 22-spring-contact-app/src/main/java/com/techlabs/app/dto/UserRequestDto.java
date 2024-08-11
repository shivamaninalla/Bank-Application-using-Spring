package com.techlabs.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class UserRequestDto {
	
	   private int userId;

	   @Email(message="please check the email")
	   @NotBlank
	   private String email;
	   
	   @NotBlank
	   @Size(min=2,max=20,message="Please check the size of firstName")
	   private String firstName;
	   
	   @NotBlank
	   @Size(min=2,max=20,message="Please check the size of LastName")
	   private String lastName;
	   
	   @NotNull(message="please check admin datamember")
	   private boolean admin ;  
	   
	   @NotNull
	   private boolean active=true;
	   
	   @NotBlank
	   private String password;
	   
	   
	 

}
