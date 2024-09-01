package com.techlabs.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerResponseExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerResponseExceptionHandler.class);
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomerErrorResponse> handleGenericException(BadCredentialsException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException exc) {
        return buildResponseEntity(exc, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AllExceptions.AccountNotFoundException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.AccountNotFoundException exc) {
        return buildResponseEntity(exc, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AllExceptions.CustomerIsNotActiveException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.CustomerIsNotActiveException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.AccountNumberWrongException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.AccountNumberWrongException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.InsufficientFundsException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.InsufficientFundsException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.UserNotFoundException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.UserNotFoundException exc) {
        return buildResponseEntity(exc, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AllExceptions.CustomerAlreadyAssignedException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.CustomerAlreadyAssignedException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.CustomerAlreadyDeletedException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.CustomerAlreadyDeletedException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.CustomerAlreadyActiveException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.CustomerAlreadyActiveException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.AccountDeletedException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.AccountDeletedException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.AccountAlreadyActiveException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.AccountAlreadyActiveException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllExceptions.AccountInactiveException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.AccountInactiveException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AllExceptions.BankNotFoundException.class)
    public ResponseEntity<CustomerErrorResponse> handleException(AllExceptions.BankNotFoundException exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerErrorResponse> handleException(Exception exc) {
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

   
    
    
    private ResponseEntity<CustomerErrorResponse> buildResponseEntity(Exception exc, HttpStatus status) {
        CustomerErrorResponse error = new CustomerErrorResponse();
        logger.error(exc.getMessage());
        error.setStatus(status.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, status);
    }
}
