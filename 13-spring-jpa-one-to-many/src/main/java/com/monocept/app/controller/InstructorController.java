package com.monocept.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.entity.Course;
import com.monocept.app.entity.Instructor;
import com.monocept.app.service.InstructorService;

@RestController
@RequestMapping("instructors")
public class InstructorController {
	
	InstructorService instructorService;
	
	
	public InstructorController(InstructorService instructorService) {
		super();
		this.instructorService = instructorService;
	}


	@GetMapping()
	public List<Instructor> getAllInstructors(Instructor instructor) 
	{
		return instructorService.getAllInstructors();
	}
	
	@PostMapping()
	public Instructor addNewInstructor(@RequestBody Instructor instructor) {
		return instructorService.saveInstructor(instructor);
	}
	
	
	
	
	@GetMapping("/{id}")
	public Instructor getInstructorById(@PathVariable int id) {
		return instructorService.findInstructorById(id);
	}
	
	
	
	
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable int id) {
		instructorService.deleteInstructorByid(id);
	}
	
	
	@PutMapping
	public Instructor updateEmployee(@RequestBody Instructor instructor) {
		
		return instructorService.updateInstructor(instructor);
	}
	
	
	
	@GetMapping("courses")
	public List<Course> getAllCourse(Course course) 
	{
		return instructorService.getAllCourses();
	}
	
	@PostMapping("/course")
	public Course addNewCourse(@RequestBody Course course) {
		return instructorService.saveCourse(course);
	}
	
	
	
	@PostMapping("{instructorId}/course/{courseId}")
	public Instructor addInstructorToCourse(@PathVariable int instructorId,@PathVariable int courseId) {
		return instructorService.addCourseToInstructor(instructorId,courseId);
	}
	
	
	@PutMapping("{instructorId}/remove_course/{courseId}")
	public Instructor removeInstructorToCourse(@PathVariable int instructorId,@PathVariable int courseId) {
		return instructorService.removeCourseToInstructor(instructorId,courseId);
	}
	
}
