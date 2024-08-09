package com.monocept.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.monocept.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
	//@Query("select e from Employee e where e.name=?1")
	@Query("select e from Employee e where e.name=:name")
	Page<Employee> findByNamee(@Param("name")String name, Pageable pageable);

	//@Query("select e from Employee e where e.email=?1")
	@Query("select e from Employee e where e.email=:email")
	Employee findByEmail(@Param("email") String email);

	@Query("select e from Employee e where e.active=true")
	Page<Employee> findByActiveTrue(Pageable pageable);

	//@Query("select e from Employee e where e.name like ?1%")
	@Query("select e from Employee e where e.name like :letter%")
	Page<Employee> findByNameStartingWith(@Param("letter") String letter, Pageable pageable);

	@Query("select count(e) from Employee e where e.active=true")
	Integer countByActiveTrue();

	
	@Query("select count(e) from Employee e where e.designation=?1")
	int countByDesignation(String dept);

	@Query("select e from Employee e where e.salary>?1 and designation=?2")
	Page<Employee> findBySalaryGreaterThanAndDesignation(double salary, String designation, Pageable pageable);

	@Query("select e from Employee e where e.salary between ?1 and ?2")
	Page<Employee> findBySalaryBetween(int start, int end, Pageable pageAndSort);

	@Query("select e from Employee e where e.salary>?1 and e.active=true")
	Page<Employee> findBySalaryGreaterThanAndActiveTrue(int salary, Pageable pageAndSort);

}
