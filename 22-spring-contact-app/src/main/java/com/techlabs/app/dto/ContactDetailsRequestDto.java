package com.techlabs.app.dto;

import com.techlabs.app.entity.ContactType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ContactDetailsRequestDto {
	
	
	private int id;
	
	
	private ContactRequestDto contact;  
	
	
	private ContactType contactType;  

}
