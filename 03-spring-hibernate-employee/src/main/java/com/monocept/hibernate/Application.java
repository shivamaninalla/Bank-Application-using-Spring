package com.monocept.hibernate;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.monocept.hibernate.dao.EmployeeDao;
import com.monocept.hibernate.entity.Employee;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	private EmployeeDao employeeDao;

	public Application(EmployeeDao employeeDao) {
		super();
		this.employeeDao = employeeDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//addEmployee();
		//getAllEmployees();
		//getNames();
		//getEmployeeById();
		//getStudentByName();
		//updateEmployee();
		//updateEmployeeWithoutMerge();
		//deleteEmployee();
		//deleteEmployeeWithoutRemoveLessThanHundred();
		getNameSalary();
		
	}

	private void getNameSalary() {
		List<Employee> employee=employeeDao.getNameSalary();
		for(Employee e:employee) {
			System.out.println(e);
		}
		
	}

	private void deleteEmployeeWithoutRemoveLessThanHundred() {
		employeeDao.deleteEmployeeWithoutRemoveLessThanHundred(102);		
	}

	private void deleteEmployee() {
		employeeDao.deleteEmployee(101);
		
	}

	private void updateEmployeeWithoutMerge() {
		Employee employee=new Employee("bangi",105,7000);
		employeeDao.updateEmployeeWithoutMerge(employee);
		
	}

	private void updateEmployee() {
		Employee employee=new Employee("aman",104,40000);
		employeeDao.updateEmployee(employee);
		
	}

	private void getStudentByName() {
		List<Employee> employee=employeeDao.getStudentByName("aditya");
		if(employee!=null) {
			System.out.println(employee);
		}
		else {
			System.out.println("Student with name does not exist");
		}
		
	}

	private void getEmployeeById() {
		Employee employee=employeeDao.getEmployeeById(102);
		if(employee!=null) {
			System.out.println(employee);
		}
		else {
			System.out.println("Student with id does not exist");
		}
	}

	private void getNames() {
		List<String> names=employeeDao.getNames();
		for(String s:names) {
			System.out.println(s);
		}
		
	}

	private void getAllEmployees() {
		List<Employee> employee=employeeDao.getAllEmployees();
		for(Employee e:employee) {
			System.out.println(e);
		}
		
	}

	private void addEmployee() {
		Employee employee=new Employee("aditya",3000);
		employeeDao.addEmployee(employee);
		
	}

}
