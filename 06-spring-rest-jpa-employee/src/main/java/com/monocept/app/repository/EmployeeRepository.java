package com.monocept.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
	Page<Employee> findByName(String name, Pageable pageable);

	Employee findByEmail(String email);

	Page<Employee> findByActiveTrue(Pageable pageable);

	Page<Employee> findByNameStartingWith(String letter, Pageable pageable);

	Integer countByActiveTrue();

	

	int countByDesignation(String dept);

	Page<Employee> findBySalaryGreaterThanAndDesignation(double salary, String designation, Pageable pageable);

	Page<Employee> findBySalaryBetween(int start, int end, Pageable pageAndSort);

	Page<Employee> findBySalaryGreaterThanAndActiveTrue(int salary, Pageable pageAndSort);

}
