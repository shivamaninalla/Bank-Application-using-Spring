package com.techlabs.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.AgentRequestDto;
import com.techlabs.app.dto.AgentResponseDto;
import com.techlabs.app.dto.EmployeeRequestDto;
import com.techlabs.app.dto.TaxSettingRequestDto;
import com.techlabs.app.service.AdminService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Add Employee")
	@PostMapping("addEmployee")
	public ResponseEntity<String> registerEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
		return new ResponseEntity<String>(adminService.registerEmployee(employeeRequestDto),
				HttpStatus.ACCEPTED);
	}
    
    @Operation(summary = "Add Agent")
	@PostMapping("addAgent")
	public ResponseEntity<String> registerAgent(@RequestBody AgentRequestDto agentRequestDto) {
		return new ResponseEntity<String>(adminService.registerAgent(agentRequestDto),
				HttpStatus.ACCEPTED);
	}
    
    
//    @GetMapping()
//    public ResponseEntity<AgentResponseDto> getAllAgents() {
//		return new ResponseEntity<AgentResponseDto>(adminService.getAllAgents(),
//				HttpStatus.ACCEPTED);
//	}
    
    @GetMapping
	public ResponseEntity<PagedResponse<AgentResponseDto>> getAllAgents(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "agentId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		PagedResponse<AgentResponseDto> agents = adminService.getAllAgents(page, size, sortBy, direction);
		return new ResponseEntity<PagedResponse<AgentResponseDto>>(agents, HttpStatus.ACCEPTED);
	}

    
    
    @PostMapping("/tax-setting")
    public ResponseEntity<String> createTaxSetting(@RequestBody TaxSettingRequestDto taxSettingRequestDto) {
      return new ResponseEntity<String>(adminService.createTaxSetting(taxSettingRequestDto),HttpStatus.CREATED);
      
    }


}