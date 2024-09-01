package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
