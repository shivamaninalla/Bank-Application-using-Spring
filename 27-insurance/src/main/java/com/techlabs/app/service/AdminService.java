package com.techlabs.app.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.dto.AgentRequestDto;
import com.techlabs.app.dto.AgentResponseDto;
import com.techlabs.app.dto.EmployeeRequestDto;
import com.techlabs.app.dto.EmployeeResponseDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.dto.TaxSettingRequestDto;
import com.techlabs.app.util.PagedResponse;

public interface AdminService{
    String registerEmployee(EmployeeRequestDto employeeRequestDto);
	String registerAgent(AgentRequestDto agentRequestDto);
	String createTaxSetting(TaxSettingRequestDto taxSettingRequestDto);
	PagedResponse<AgentResponseDto> getAllAgents(int page, int size, String sortBy, String direction);
}