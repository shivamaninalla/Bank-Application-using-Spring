package com.techlabs.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.Contact;

import lombok.Data;

@Data
@JsonInclude(value=Include.NON_NULL)
public class UserResponseDto {
	
	
	   private int userId;
	   
	   
	   private String email;
	   
	   private String firstName;
	   
	   private String lastName;
	   
	   private boolean admin ;  
	   
	   private boolean active;
	   	   
	   private List<Contact> contacts;

}
