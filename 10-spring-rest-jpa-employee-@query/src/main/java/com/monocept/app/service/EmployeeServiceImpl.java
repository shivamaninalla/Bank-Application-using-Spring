package com.monocept.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.monocept.app.dto.EmployeeDTO;
import com.monocept.app.dto.EmployeeResponseDTO;
import com.monocept.app.entity.Employee;
import com.monocept.app.exception.EmployeeNotFoundException;
import com.monocept.app.repository.EmployeeRepository;
import com.monocept.app.util.PagedResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public PagedResponse<EmployeeResponseDTO> getAllEmployees(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Employee> pages = employeeRepository.findAll(pageable);
		// List<Employee> content = pages.getContent();
		List<EmployeeResponseDTO> convertEmployeeToResponseDto = convertEmployeeToResponseDto(pages.getContent());
		return new PagedResponse<EmployeeResponseDTO>(convertEmployeeToResponseDto, pages.getNumber(), pages.getSize(),
				pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
	}

	private List<EmployeeResponseDTO> convertEmployeeToResponseDto(List<Employee> employees) {
		List<EmployeeResponseDTO> employeeResponseDto = new ArrayList<>();
		for (Employee e : employees) {
			EmployeeResponseDTO dto = new EmployeeResponseDTO();

			dto.setDesignation(e.getDesignation());
			dto.setActive(e.isActive());
			dto.setEmail(e.getEmail());
			dto.setName(e.getName());

			employeeResponseDto.add(dto);

		}
		return employeeResponseDto;
	}

	private List<EmployeeDTO> convertEmployeeListToDto(List<Employee> employees) {
		List<EmployeeDTO> employeeDto = new ArrayList<>();
		for (Employee e : employees) {
			EmployeeDTO dto = new EmployeeDTO();
			dto.setId(e.getId());
			dto.setDesignation(e.getDesignation());
			dto.setActive(e.isActive());
			dto.setEmail(e.getEmail());
			dto.setName(e.getName());
			dto.setSalary(e.getSalary());
			employeeDto.add(dto);

		}
		return employeeDto;
	}

	@Override
	public EmployeeDTO save(EmployeeDTO employeeDto) {

		Employee employee = convertEmployeeDtoToObject(employeeDto);

		return convertEmployeeToDto(employeeRepository.save(employee));
	}

	private Employee convertEmployeeDtoToObject(EmployeeDTO employeeDto) {
		Employee employee = new Employee();
		employee.setId(employeeDto.getId());
		employee.setName(employeeDto.getName());
		employee.setActive(employeeDto.isActive());
		employee.setDesignation(employeeDto.getDesignation());
		employee.setEmail(employeeDto.getEmail());
		employee.setSalary(employeeDto.getSalary());
		return employee;
	}

	@Override
	public EmployeeResponseDTO getEmployeeById(Long i) {

		Employee e = employeeRepository.findById(i).orElse(null);
		EmployeeResponseDTO convertEmployeeResponseDto = convertEmployeeToResponseDtoSingle(e);
		if (e != null) {
			return convertEmployeeResponseDto;
		} else {
			throw new EmployeeNotFoundException("Employee not found with ID: " + i);
		}
//	Optional<Employee> byId = employeeRepository.findById(i);
//		if (byId.isPresent()) {
//			return byId.get();
//		}

	}

	private EmployeeResponseDTO convertEmployeeToResponseDtoSingle(Employee e) {
		EmployeeResponseDTO dto = new EmployeeResponseDTO();

		dto.setDesignation(e.getDesignation());
		dto.setActive(e.isActive());
		dto.setEmail(e.getEmail());
		dto.setName(e.getName());

		return dto;
	}

	private EmployeeDTO convertEmployeeToDto(Employee e) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(e.getId());
		dto.setDesignation(e.getDesignation());
		dto.setActive(e.isActive());
		dto.setEmail(e.getEmail());
		dto.setName(e.getName());
		dto.setSalary(e.getSalary());

		return dto;

	}

	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDto) {
		Employee save = null;
		Employee employee = convertEmployeeDtoToObject(employeeDto);
		Employee e = employeeRepository.findById(employee.getId()).orElse(null);
		// EmployeeDTO tempEmployee =
		// employeeService.getEmployeeById(employeeDto.getId());
		if (e == null) {
			throw new EmployeeNotFoundException("Employee with ID " + employee.getId() + " is not found");
		} else {
			save = employeeRepository.save(employee);
		}
		return convertEmployeeToDto(save);
	}

	@Override
	public void deleteEmployee(long i) {
		Employee e = employeeRepository.findById(i).orElse(null);
		if (e == null) {
			throw new EmployeeNotFoundException("Employee with ID " + i + " is not found");
		}
		employeeRepository.deleteById(i);

	}

	@Override
	public PagedResponse<EmployeeDTO> getEmployeeByName(String name, int page, int size, String sortBy,
			String direction) {

		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Employee> employee = employeeRepository.findByNamee(name, pageable);
		// List<Employee> employee = employeeRepository.findByName(name);
		if (employee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee with name " + name + " not found");
		}
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list, employee.getNumber(), employee.getSize(),
				employee.getTotalElements(), employee.getTotalPages(), employee.isLast());
	}

	@Override
	public EmployeeDTO getEmployeeByEmail(String email) {
		Employee employee = employeeRepository.findByEmail(email);
		if (employee == null) {
			throw new EmployeeNotFoundException("Employee with name " + email + " not found");
		}

		return convertEmployeeToDto(employee);
	}

	@Override
	public PagedResponse<EmployeeDTO> getAllActive(int page,int size,String sortBy, String direction) {
		
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable= PageRequest.of(page, size,sort);
		Page<Employee> employee = employeeRepository.findByActiveTrue(pageable);
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list,employee.getNumber(),employee.getSize(),employee.getTotalElements(),employee.getTotalPages(),employee.isLast());
	}

	@Override
	public PagedResponse<EmployeeDTO> getEmployeeStartWith(String letter, int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable= PageRequest.of(page, size,sort);
		Page<Employee> employee = employeeRepository.findByNameStartingWith(letter,pageable);
		if (employee.isEmpty()) {
			throw new EmployeeNotFoundException("No employee found with the starting letter " + letter);
		}
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list,employee.getNumber(),employee.getSize(),employee.getTotalElements(),employee.getTotalPages(),employee.isLast());
 
	}

	@Override
	public Integer getCount() {
		Integer employee = employeeRepository.countByActiveTrue();
		if (employee == 0) {
			throw new EmployeeNotFoundException("No active members");
		}
		return employeeRepository.countByActiveTrue();
	}

	@Override
	public Integer getCountDepartment(String dept) {
		Integer employee = employeeRepository.countByDesignation(dept);
		if (employee == 0) {
			throw new EmployeeNotFoundException("No employees found for this deignation " + dept);
		}
		return employeeRepository.countByDesignation(dept);
	}

	@Override
	public PagedResponse<EmployeeDTO> getSalaryAndDepartment(double salary, String designation, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable= PageRequest.of(page, size,sort);
		Page<Employee> employee = employeeRepository.findBySalaryGreaterThanAndDesignation(salary, designation,pageable);
		if (employee == null) {
			throw new EmployeeNotFoundException("Employee not found for this" + designation + "and" + salary);
		}
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list,employee.getNumber(),employee.getSize(),employee.getTotalElements(),employee.getTotalPages(),employee.isLast());
		
	}

	@Override
	public PagedResponse<EmployeeDTO> getSalaryBetween(int start, int end, int page, int size, String sortBy, String direction) {
		Pageable pageAndSort = pageAndSort(page, size,sortBy, direction);
		Page<Employee> employee = employeeRepository.findBySalaryBetween(start, end, pageAndSort);

		if (employee == null) {
			throw new EmployeeNotFoundException("No employees are existed between" + start + "and" + end);
		}
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list,employee.getNumber(),employee.getSize(),employee.getTotalElements(),employee.getTotalPages(),employee.isLast());
	}

	

	@Override
	public PagedResponse<EmployeeDTO> getSalaryGreaterActive(int salary, int page, int size, String sortBy, String direction) {
		
		Pageable pageAndSort = pageAndSort(page, size,sortBy, direction);

		Page<Employee> employee = employeeRepository.findBySalaryGreaterThanAndActiveTrue(salary,pageAndSort);
		if (employee == null) {
			throw new EmployeeNotFoundException("No active members greater than " + salary + "are active");
		}
		
		List<EmployeeDTO> list = convertEmployeeListToDto(employee.getContent());
		return new PagedResponse<EmployeeDTO>(list,employee.getNumber(),employee.getSize(),employee.getTotalElements(),employee.getTotalPages(),employee.isLast());
	}
	
	
	private Pageable pageAndSort(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable= PageRequest.of(page, size,sort);
		return pageable;
	}
	

}
