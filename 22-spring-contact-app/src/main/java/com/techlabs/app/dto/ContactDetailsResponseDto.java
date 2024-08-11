package com.techlabs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.ContactType;

import lombok.Data;

@Data
@JsonInclude(value=Include.NON_NULL)
public class ContactDetailsResponseDto {
	
	@JsonInclude(value=Include.NON_DEFAULT)
    private int id;
	
	
	private ContactResponseDto contact;  
	
	private ContactType contactType;  

}
