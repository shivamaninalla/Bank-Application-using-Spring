package com.techlabs.app.dto;

import java.util.Set;

import com.techlabs.app.entity.Role;

import lombok.Data;
@Data
public class UserResponseDTO {
	private Long id;

	private String email;
	private String password;
	private Set<Role> roles;
	private CustomerResponseDTO customerResponseDto;
}