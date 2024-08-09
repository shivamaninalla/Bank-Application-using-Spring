package com.techlabs.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class CustomerResponseDTO {
private long customer_id;
	
	
	private String firstName;
	
	
	private String lastName;
	
	
	private double totalBalance;
	
	private List<AccountResponseDTO> accounts;
	

}
