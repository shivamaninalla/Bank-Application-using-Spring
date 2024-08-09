package com.monocept.app.exception;

public class BlogNotFoundException extends RuntimeException{
	
	public BlogNotFoundException(String message) {
		super(message);
	}

}
