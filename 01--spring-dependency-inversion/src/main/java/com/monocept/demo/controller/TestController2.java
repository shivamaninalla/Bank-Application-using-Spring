package com.monocept.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController2 {
	
	@GetMapping("/hello1")
	public String getMessage() {
		return "Hello World";
	}
	
	@GetMapping("/greet2")
	public String getGreetings() {
		return "Hello Worl";
	}

}
