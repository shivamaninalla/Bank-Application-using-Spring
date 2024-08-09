package com.monocept.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.dto.EmployeeDTO;
import com.monocept.app.dto.EmployeeResponseDTO;
import com.monocept.app.entity.Employee;
import com.monocept.app.service.EmployeeService;
import com.monocept.app.util.PagedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@GetMapping()
	public ResponseEntity<PagedResponse<EmployeeResponseDTO>> getAllEmployees(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction
			)
	{
		System.out.println(page);
		PagedResponse<EmployeeResponseDTO> employees = employeeService.getAllEmployees(page,size,sortBy,direction);

		return new ResponseEntity<PagedResponse<EmployeeResponseDTO>>(employees, HttpStatus.OK);

	}

	@GetMapping("/{sid}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable(name = "sid") long id) {
		EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<EmployeeResponseDTO>(employee, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDto) {
		EmployeeDTO addedEmployee = employeeService.save(employeeDto);
		return new ResponseEntity<EmployeeDTO>(addedEmployee, HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDto) {
		
		
		EmployeeDTO updateEmployee = employeeService.updateEmployee(employeeDto);
		return new ResponseEntity<EmployeeDTO>(updateEmployee, HttpStatus.OK);
	}

	@DeleteMapping("/{sid}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(name = "sid") long id) {
		
		employeeService.deleteEmployee(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getEmployeeByName(@PathVariable(name = "name") String name,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {
		PagedResponse<EmployeeDTO> employee1 = employeeService.getEmployeeByName(name,page,size,sortBy,direction);
		
		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employee1, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable(name = "email") String email) {
		EmployeeDTO employee1 = employeeService.getEmployeeByEmail(email);
		
		return new ResponseEntity<EmployeeDTO>(employee1, HttpStatus.OK);
	}

	@GetMapping("/activeTrue")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getAllActive(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {
		PagedResponse<EmployeeDTO> employees = employeeService.getAllActive(page,size,sortBy,direction);

		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employees, HttpStatus.OK);

	}

	@GetMapping("/start/{letter}")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getEmployeeStartWith(@PathVariable(name = "letter") String letter,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {

		PagedResponse<EmployeeDTO> employee1 = employeeService.getEmployeeStartWith(letter,page,size,sortBy,direction);
		
		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employee1, HttpStatus.OK);
	}

	@GetMapping("/countTrue")
	public Integer getCount() {

		Integer employee1 = employeeService.getCount();
		
		return employee1;
	}

	@GetMapping("/countDepartment/{dept}")
	public Integer getCountDepartment(@PathVariable(name = "dept") String dept) {

		Integer employee1 = employeeService.getCountDepartment(dept);
		
		return employee1;
	}

	@GetMapping("/salaryAndDepartment")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getSalaryAndDepartment(@RequestBody EmployeeDTO employeeDto,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {

		PagedResponse<EmployeeDTO> employee1 = employeeService.getSalaryAndDepartment(employeeDto.getSalary(),
				employeeDto.getDesignation(),page,size,sortBy,direction);
		
		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employee1, HttpStatus.OK);
	}

	@GetMapping("/salaryBetween/{start}/{end}")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getSalaryBetween(@PathVariable(name = "start") int start,
			@PathVariable(name = "end") int end,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {

		PagedResponse<EmployeeDTO> employee1 = employeeService.getSalaryBetween(start, end,page,size,sortBy,direction);
		
		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employee1, HttpStatus.OK);
	}
	
	
	@GetMapping("/salaryActive/{salary}")
	public ResponseEntity<PagedResponse<EmployeeDTO>> getSalaryGreaterActive(@PathVariable(name = "salary") int salary,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size" , defaultValue = "5")int size,
			@RequestParam(name="sort", defaultValue = "id")String sortBy,
			@RequestParam(name="direction", defaultValue = "id") String direction) {

		PagedResponse<EmployeeDTO> employee1 = employeeService.getSalaryGreaterActive(salary,page,size,sortBy,direction);
		
		return new ResponseEntity<PagedResponse<EmployeeDTO>>(employee1, HttpStatus.OK);
	}
	
	
	
	

}
