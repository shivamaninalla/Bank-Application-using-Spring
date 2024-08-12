package com.techlabs.app.exception;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerResponseExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerResponseExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException exc) {

		CustomerErrorResponse error = new CustomerErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());


		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(BankApiException exc) {

		CustomerErrorResponse error = new CustomerErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<CustomerErrorResponse> handleException(AccessDeniedException exc) {

		CustomerErrorResponse error = new CustomerErrorResponse();
		logger.error(exc.getMessage());

		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(exc.getClass().getSimpleName());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(Exception exc) {
		CustomerErrorResponse error = new CustomerErrorResponse();
		logger.error(exc.getMessage());
		System.out.println("printing error");
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getClass().getSimpleName());
		exc.printStackTrace();
		error.setTimeStamp(System.currentTimeMillis());


		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	

	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(NoRecordFoundException exc) {

		CustomerErrorResponse error = new CustomerErrorResponse();
		logger.error(exc.getMessage());

		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());


		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}