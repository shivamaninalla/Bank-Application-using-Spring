package com.monocept.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeResponseDTO {
	@NotBlank(message = "Please enter the name")
	@Size(min = 2, max = 50, message = "please check the size")
	private String name;

	@Email(message = "Email should be well formatted")
	@NotBlank(message = "Please enter valid email")
	private String email;

	@NotBlank(message = "Please specify the designation")
	private String designation;
	
	@NotNull
	private boolean active;

	public EmployeeResponseDTO(
			@NotBlank(message = "Please enter the name") @Size(min = 2, max = 50, message = "please check the size") String name,
			@Email(message = "Email should be well formatted") @NotBlank(message = "Please enter valid email") String email,
			@NotBlank(message = "Please specify the designation") String designation, @NotNull boolean active) {
		super();
		this.name = name;
		this.email = email;
		this.designation = designation;
		this.active = active;
	}

	public EmployeeResponseDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
