package com.monocept.app.service;

import java.util.List;

import com.monocept.app.dto.EmployeeDTO;
import com.monocept.app.dto.EmployeeResponseDTO;
import com.monocept.app.entity.Employee;
import com.monocept.app.util.PagedResponse;


public interface EmployeeService {
	
	public PagedResponse<EmployeeResponseDTO> getAllEmployees(int page, int size, String sortBy, String direction);
	
	public EmployeeDTO save(EmployeeDTO employeeDto);
	

	public EmployeeResponseDTO getEmployeeById(Long i);
	
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDto);
	
	public void deleteEmployee(long i);

	public PagedResponse<EmployeeDTO> getEmployeeByName(String name, int page, int size, String sortBy, String direction);

	public EmployeeDTO getEmployeeByEmail(String email);

	public PagedResponse<EmployeeDTO> getAllActive(int page, int size, String sortBy, String direction);

	public PagedResponse<EmployeeDTO> getEmployeeStartWith(String letter, int page, int size, String sortBy, String direction);

	public Integer getCount();

	public Integer getCountDepartment(String dept);

	public PagedResponse<EmployeeDTO> getSalaryAndDepartment(double salary, String designation, int page, int size, String sortBy, String direction);

	public PagedResponse<EmployeeDTO> getSalaryBetween(int start, int end, int page, int size, String sortBy, String direction);

	public PagedResponse<EmployeeDTO> getSalaryGreaterActive(int salary, int page, int size, String sortBy, String direction);


	

}
