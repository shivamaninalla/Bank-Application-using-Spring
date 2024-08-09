package com.monocept.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monocept.demo.model.PdfResources;
import com.monocept.demo.model.Resource;
import com.monocept.demo.model.VideoResource;

@Configuration
public class InstructorConfig {
	
	@Bean(name="pdfResources")
	Resource getResourceMaterial1() {
		return new PdfResources();
		
	}
	
	@Bean(name="videoResource")
	Resource getResourceMaterial2() {
		return new VideoResource();
	
	

}
}
