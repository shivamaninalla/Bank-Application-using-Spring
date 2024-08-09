package com.monocept.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.entity.Student;
import com.monocept.app.service.StudentService;

@RestController
public class StudentController {
	
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	@GetMapping("students")
	List<Student> getAllStudents(){
		
		List<Student> students=studentService.getAllStudents();
		
				return students;
		
	}
	
	//@PostMapping("students")
	

}
