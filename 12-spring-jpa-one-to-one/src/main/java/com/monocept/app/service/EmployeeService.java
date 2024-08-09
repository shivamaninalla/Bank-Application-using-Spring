package com.monocept.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monocept.app.entity.Address;
import com.monocept.app.entity.Employee;


public interface EmployeeService {
	
	public Employee saveEmployee(Employee employee);

	public List<Employee> findAllEmployees();

	public Employee findEmployeeById(int id);

	public void deleteEmployeeByid(int id);

	public Employee updateEmployee(Employee employee);

	public Address findAddressById(int id);

	public Employee findEmployeeAddresswithAddress(int id);
}
