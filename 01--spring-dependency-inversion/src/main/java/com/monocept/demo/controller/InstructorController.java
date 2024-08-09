package com.monocept.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.demo.model.Instructor;

@RestController
public class InstructorController {

	private Instructor instructor;

//	public InstructorController(@Qualifier(value="pythonInstructor") Instructor instructor) {
//		super();
//		// TODO Auto-generated constructor stub
//		this.instructor=instructor;
//	}
//	@Autowired
//	@Qualifier(value="pythonInstructor")
//	private Instructor instructor;

	@Qualifier(value = "javaInstructor")
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	@GetMapping("/train-plan")
	public String getTrainingPlan() {
		return this.instructor.getTrainingPlan() + "<br>" + this.instructor.getResource();

	}

	public Instructor getInstructor() {
		return instructor;
	}

}
