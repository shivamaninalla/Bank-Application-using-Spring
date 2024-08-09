package com.monocept.hibernate.dao;

import java.util.List;

import com.monocept.hibernate.entity.Employee;

public interface EmployeeDao {

	void addEmployee(Employee employee);

	List<Employee> getAllEmployees();

	List<String> getNames();

	Employee getEmployeeById(int i);

	List<Employee> getStudentByName(String string);

	void updateEmployee(Employee employee);

	void updateEmployeeWithoutMerge(Employee employee);

	void deleteEmployee(int i);

	void deleteEmployeeWithoutRemoveLessThanHundred(int i);

	List<Employee> getNameSalary();




}
