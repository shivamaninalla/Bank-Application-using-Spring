package com.monocept.demo.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PythonInstructor implements Instructor{

	
	private Resource resource;
	public PythonInstructor(@Qualifier(value = "pdfResources") Resource resource) {
		super();
		this.resource = resource;
	}

	public PythonInstructor() {
		return;
	}

	@Override
	public String getTrainingPlan() {
		// TODO Auto-generated method stub
		return "Practice Linear Reggression";
	}

	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		return this.resource.getResourceMaterial();
	}

}
