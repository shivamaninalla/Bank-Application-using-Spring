package com.monocept.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monocept.app.entity.Address;
import com.monocept.app.entity.Employee;
import com.monocept.app.repository.AddressRepository;
import com.monocept.app.repository.EmployeeRepository;
@Service
public class EmployeeServiceImpl implements EmployeeService{

	
	private EmployeeRepository employeeRepository;
	
	private AddressRepository adressRepository;
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, AddressRepository adressRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.adressRepository = adressRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee findEmployeeById(int id) {
		// TODO Auto-generated method stub
		 Optional<Employee> optional = employeeRepository.findById(id);
	if(optional.isPresent()) {
		return optional.get();
	}
	
	return null;
	}

	@Override
	public void deleteEmployeeByid(int id) {
		// TODO Auto-generated method stub
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		Employee employeeById = findEmployeeById(employee.getId());
		if(employeeById!=null) {
			return employeeRepository.save(employee);
		}
		return null;
	}

	@Override
	public Address findAddressById(int id) {
		
		Optional<Address>optional= adressRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public Employee findEmployeeAddresswithAddress(int id) {
		Optional<Address> optionalAddress = adressRepository.findById(id);
		if(optionalAddress.isPresent()) {
			Address address = optionalAddress.get();
			Employee employee = address.getEmployee();
			return employee;
		}
		return null;
	}
	
	
	
	
	

}
