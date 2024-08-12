package com.techlabs.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value=Include.NON_NULL)
public class ContactResponseDto {
	
private int id;
	
	private String firstName;
	
	private String lastName;
	
	private boolean active;

	private UserResponseDto user;
	
	
	private List<ContactDetailsResponseDto> contactDetails; 

}
