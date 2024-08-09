package com.monocept.hibernate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.monocept.hibernate.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private EntityManager entityManager;

	public EmployeeDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void addEmployee(Employee employee) {
		this.entityManager.persist(employee);

	}

	@Override
	@Transactional
	public List<Employee> getAllEmployees() {
		TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e",Employee.class);
		List<Employee> resultList = query.getResultList();
		
		return resultList;
		
		
	}

	@Override
	public List<String> getNames() {
		TypedQuery<String> query = entityManager.createQuery("select name from Employee",String.class);
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Employee getEmployeeById(int i) {
		Employee employee = entityManager.find(Employee.class,i);
		return employee;
	}

	@Override
	public List<Employee> getStudentByName(String string) {
		Query query = entityManager.createQuery("select e from Employee e where e.name=?1");
		query.setParameter(1, string);
		List<Employee> resultList = query.getResultList();
		return resultList;
	}

	@Override
	@Transactional
	public void updateEmployee(Employee employee) {
		Employee employee2 = entityManager.find(Employee.class, employee.getId());
		if(employee2!=null) {
			this.entityManager.merge(employee);
		}
		else {
			System.out.println("Employee not found");
		}
		
	}

	@Override
	@Transactional
	public void updateEmployeeWithoutMerge(Employee employee) {
		Query query = entityManager.createQuery("update Employee e set e.name=?1, e.salary=?2 where e.id=?3");
		query.setParameter(1, employee.getName());
		query.setParameter(2, employee.getSalary());
		query.setParameter(3, employee.getId());
		int executeUpdate = query.executeUpdate();
		System.out.println(executeUpdate);
	}

	@Override
	@Transactional
	public void deleteEmployee(int i) {
		Employee employee = entityManager.find(Employee.class, i);
		if(employee!=null) {
			this.entityManager.remove(employee);
		}
		else {
			System.out.println("Employee not found with this id");
		}
	}

	@Override
	@Transactional
	public void deleteEmployeeWithoutRemoveLessThanHundred(int i) {
//		Query query = entityManager.createQuery("delete from Employee e where e.id<?1");
//		query.setParameter(1, i);
//		query.executeUpdate();
		
		Query nativeQuery = entityManager.createNativeQuery("delete from employee where id<?1");
		nativeQuery.setParameter(1, i);
		nativeQuery.executeUpdate();
	}

	@Override
	@Transactional
	public List<Employee> getNameSalary() {
		Query query = entityManager.createQuery("select e from Employee e",Employee.class);
	//	query.setParameter(1, i);
		List<Employee> resultList = query.getResultList();
		Employee employee=resultList.get(0);
		return new Employee(employee.getName(),employee.getSalary());
		
	}

}
