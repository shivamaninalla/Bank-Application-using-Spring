package com.monocept.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.monocept.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "select * from employee e where e.name=?1", nativeQuery = true)
	Page<Employee> findByName(String name, Pageable pageable);

	@Query(value = "select * from employee e where e.email=?1", nativeQuery = true)
	Employee findByEmail(String email);

	@Query(value = "select * from employee e where e.active=true", nativeQuery = true)
	Page<Employee> findByActiveTrue(Pageable pageable);

	 @Query(value="select * from employee e where e.name like ?1%",nativeQuery=true)
	//@Query(value = "select * from employee e where e.name like :letter%", nativeQuery = true)
	Page<Employee> findByNameStartingWith(@Param("letter") String letter, Pageable pageable);

	@Query(value = "select count(*) from employee e where e.active=true", nativeQuery = true)
	Integer countByActiveTrue();

	@Query(value = "select count(e.designation) from employee e where e.designation=?1", nativeQuery = true)
	int countByDesignation(String dept);

	@Query(value = "select * from employee e where e.salary between ?1 and ?2", nativeQuery = true)
	Page<Employee> findBySalaryGreaterThanAndDesignation(double salary, String designation, Pageable pageable);

	@Query(value = "select * from employee e where e.salary>?1 and ?2", nativeQuery = true)
    Page<Employee> findBySalaryBetween(int start, int end, Pageable pageAndSort);

	@Query(value = "select * from employee e where e.salary>?1 and e.active=true", nativeQuery = true)
	Page<Employee> findBySalaryGreaterThanAndActiveTrue(int salary, Pageable pageAndSort);

}
