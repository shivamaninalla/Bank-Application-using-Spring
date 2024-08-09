package com.monocept.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.dto.CoursesDTO;
import com.monocept.app.dto.StudentsDTO;
import com.monocept.app.entity.Courses;
import com.monocept.app.entity.Students;
import com.monocept.app.service.StudentService;

@RestController
@RequestMapping("api")
public class StudentCourseController {

	private StudentService studentService;

	public StudentCourseController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping()
	public ResponseEntity<List<StudentsDTO>> getAllStudents() {
		List<StudentsDTO> students = studentService.getAllStudents();
		return new ResponseEntity<List<StudentsDTO>>(students,HttpStatus.OK);
	}

//	@PostMapping()
//	public ResponseEntity<StudentsDTO> addStudent(@RequestParam(name = "name") String name) {
//		StudentsDTO student = new StudentsDTO();
//		student.setName(name);
//		StudentsDTO studentDto = studentService.addStudent(student);
//		return new ResponseEntity<StudentsDTO>(studentDto,HttpStatus.OK); 
//	}
	@PostMapping()
	public ResponseEntity<StudentsDTO>  addStudent(@RequestBody StudentsDTO studentDto) {
		
		StudentsDTO students = studentService.addStudent(studentDto);
		return new ResponseEntity<StudentsDTO>(students,HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<StudentsDTO> getStudentById(@PathVariable(name = "id") int id) {
		StudentsDTO student = studentService.getStudentById(id);
		return new ResponseEntity<StudentsDTO>(student,HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable(name = "id") int id) {
		String message=studentService.deleteStudentById(id);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}

	@GetMapping("courses")
	public ResponseEntity<List<CoursesDTO>> getAllCourses() {
		List<CoursesDTO> courses = studentService.getAllCourses();
		return new ResponseEntity<List<CoursesDTO>>(courses,HttpStatus.OK);
	}

	@PostMapping("courses")
	public ResponseEntity<CoursesDTO> addCourse(@RequestBody CoursesDTO courseDto) {
		CoursesDTO courses = studentService.addCourse(courseDto);
		return new ResponseEntity<CoursesDTO>(courses,HttpStatus.OK);
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<CoursesDTO> getCourseById(@PathVariable(name = "id") int id) {
		 CoursesDTO courses = studentService.getCourseById(id);
		 return new ResponseEntity<CoursesDTO>(courses,HttpStatus.OK);
	}

	@DeleteMapping("courses/{id}")
	public ResponseEntity<String> deleteCourseById(@PathVariable(name = "id") int id) {
		String message=studentService.deleteCourseById(id);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}

	@PostMapping("/{student_id}/courses/{course_id}")
	public ResponseEntity<StudentsDTO> addStudentToCourse(@PathVariable(name = "student_id") int student_id,
			@PathVariable(name = "course_id") int course_id) {

		StudentsDTO students = studentService.addStudentToCourse(student_id, course_id);
		return new ResponseEntity<StudentsDTO>(students,HttpStatus.OK);
	}
	
	@DeleteMapping("/{student_id}/courses/{course_id}")
	public ResponseEntity<StudentsDTO> deleteStudentToCourse(@PathVariable(name = "student_id") int student_id,
			@PathVariable(name = "course_id") int course_id) {

		StudentsDTO students = studentService.deleteStudentToCourse(student_id, course_id);
		return new ResponseEntity<StudentsDTO>(students,HttpStatus.OK);

	}

}
