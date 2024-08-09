package com.monocept.demo.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JavaInstructor implements Instructor{

	
	private Resource resource;
	private Resource resource2;
	
	@Value("${myapp.collegename}")
	String college;
	

	public JavaInstructor(@Qualifier(value = "pdfResources") Resource resource, @Qualifier(value = "videoResource")Resource resource2) {
		super();
		this.resource = resource;
		this.resource2 = resource2;
	}

	@Override
	public String getTrainingPlan() {
		// TODO Auto-generated method stub
		return "Practice OPPs concepts for exams"+this.resource2.equals(resource);
	}

	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		return this.resource.getResourceMaterial()+college;
	}

}
