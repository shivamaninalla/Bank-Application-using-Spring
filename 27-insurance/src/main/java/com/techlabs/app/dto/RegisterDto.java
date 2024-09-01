package com.techlabs.app.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDto {

	private String name;
	private String username;
	private String email;
	private String password;
	// private String address;
	private long phone_number;
	private Set<String> roles;

}