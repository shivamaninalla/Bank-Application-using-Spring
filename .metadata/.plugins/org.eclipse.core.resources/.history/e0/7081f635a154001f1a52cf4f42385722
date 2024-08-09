package com.techlabs.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.entity.Student;
import com.techlabs.app.exception.NoStudentRecordFoundException;
import com.techlabs.app.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<Student>> findAllStudents() {
		List<Student> studentList = studentService.findAll();
		if (studentList.size() == 0) {
			throw new NoStudentRecordFoundException("No record was found...");
		}
		return new ResponseEntity<>(studentList, HttpStatus.OK);

	}

	@GetMapping("/{sid}")
	public ResponseEntity<Student> findStudent(@PathVariable(name = "sid") Long id) {
		Student student = studentService.findById(id);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> saveStudent(@RequestBody Student stud) {
		stud.setId(0L); // to avoid update if we mistakenly give id in postman while adding student
		Student student = studentService.save(stud);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PostMapping("/save-all")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Student>> saveAllStudent(@RequestBody List<Student> studentList) {
		List<Student> student = studentService.saveAll(studentList);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PutMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> updateStudent(@RequestBody Student stud) {
		Student student = studentService.save(stud);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@DeleteMapping("/{sid}")
//	@PreAuthorize("hasRole('ADMIN')")
	private ResponseEntity<HttpStatus> deleteStudentById(@PathVariable(name = "sid") Long id) {
		studentService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}