package com.techlabs.app.dto;

import java.util.List;
import java.util.Set;

import com.techlabs.app.entity.City;
import com.techlabs.app.entity.Commission;
import com.techlabs.app.entity.Customer;

import lombok.Data;

@Data
public class AgentResponseDto {

	private long agentId;

	private UserResponseDto userResponseDto;

	private String name;

	private String phoneNumber;

	private City city;

//	private Set<Customer> customers;
	private List<Customer> customers;


	private boolean isActive;

//	private Set<Commission> commissions;
	private List<Commission> commissions;


}
