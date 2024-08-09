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
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.dao.StudentDaoImpl;
import com.monocept.app.entity.Student;
import com.monocept.app.exception.StudentNotFoundException;
import com.monocept.app.service.StudentService;

@RestController
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping("/students")
	public ResponseEntity<List<Student>> testfunc() {
		List<Student> students=studentService.getAllStudents();
		
		return new ResponseEntity<List<Student>>(students,HttpStatus.OK);
		
	}

	@GetMapping("students/{sid}")
	public ResponseEntity<Student> getStudentById(@PathVariable(name="sid") int id) {
	    Student student = studentService.getStudentById(id);
	    if (student != null) {
	        return new ResponseEntity<Student>(student,HttpStatus.OK) ;
	    } else {
	        throw new StudentNotFoundException("Student not found with ID: " + id);
	    }
	}
	
	
	@PostMapping("/students")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		Student addedStudent=studentService.save(student);
	    return new ResponseEntity<Student>(addedStudent,HttpStatus.CREATED);
	}

	 @PutMapping("/students")
	  public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
	    Student tempStudent = studentService.getStudentById(student.getId());
	    if (tempStudent == null) {
	      throw new StudentNotFoundException("Student with ID " + student.getId() + " is not found");
	    }
	    Student updatedStudent = studentService.save(student);
	    return new ResponseEntity<Student>(updatedStudent, HttpStatus.OK);
	  }

	@DeleteMapping("students/{sid}")

	 public ResponseEntity<HttpStatus> deleteStudent(@PathVariable(name = "sid") int id) {
	    Student student = studentService.getStudentById(id);
	    if (student == null) {
	      throw new StudentNotFoundException("Student with ID " + id + " is not found");
	    }
	    studentService.deleteStudent(id);
	    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	  }

}
