package com.monocept.demo.model;

import org.springframework.stereotype.Component;

//@Component
public class PdfResources implements Resource{

	@Override
	public String getResourceMaterial() {
		
		return "sending pdf resources";
	}

}
