// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ContactResponseExceptionHandler {
	public ContactResponseExceptionHandler() {
	}

	private static final Logger logger = LoggerFactory.getLogger(ContactResponseExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<ContactErrorResponse> handleException(ContactNotFoundException exc) {
		ContactErrorResponse error = new ContactErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ContactErrorResponse> handleException(ContactApiException exc) {
		ContactErrorResponse error = new ContactErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<ContactErrorResponse> handleException(AccessDeniedException exc) {
		ContactErrorResponse error = new ContactErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(exc.getClass().getSimpleName());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ContactErrorResponse> handleException(Exception exc) {
		ContactErrorResponse error = new ContactErrorResponse();
		logger.error(exc.getMessage());
		System.out.println("printing error");
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getClass().getSimpleName());
		exc.printStackTrace();
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ContactErrorResponse> handleException(NoUserRecordFoundException exc) {
		ContactErrorResponse error = new ContactErrorResponse();
		logger.error(exc.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
