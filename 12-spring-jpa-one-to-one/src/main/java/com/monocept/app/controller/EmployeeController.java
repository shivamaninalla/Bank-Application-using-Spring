package com.monocept.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.entity.Address;
import com.monocept.app.entity.Employee;
import com.monocept.app.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping()
	public List<Employee> getAllEmployees(){
		return employeeService.findAllEmployees();
		
	}

	
	@PostMapping()
	public Employee addNewEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	
	
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable int id) {
		return employeeService.findEmployeeById(id);
	}
	
	@GetMapping("address/{id}")
	public Address getEmployeeByAddress(@PathVariable int id) {
		return employeeService.findAddressById(id);
	}
	
	
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployeeByid(id);
	}
	
	
	@PutMapping
	public Employee updateEmployee(@RequestBody Employee employee) {
		
		return employeeService.updateEmployee(employee);
	}
	
	
	@GetMapping("addresswithemployee/{id}")
	public Employee getEmployeeWithAdress(@PathVariable int id) {
		return employeeService.findEmployeeAddresswithAddress(id);
	}

	
}
