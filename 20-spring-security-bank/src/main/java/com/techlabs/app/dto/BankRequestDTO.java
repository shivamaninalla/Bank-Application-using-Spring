package com.techlabs.app.dto;



import java.util.List;




import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class BankRequestDTO {
	
	private int bank_id;
	
	@NotBlank(message = "Bank name cannot be empty")
	private String fullName;
	
	@NotBlank
	private String abbreviation;
	
	
	private List<AccountRequestDTO> accounts;

}