package com.techlabs.app.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;


@Data
@JsonInclude(value = Include.NON_NULL)
public class BankResponseDTO {
	
	private int bank_id;
	
	private String fullName;
	
	private String abbreviation;
	
	
	private List<AccountResponseDTO> accounts;

}
