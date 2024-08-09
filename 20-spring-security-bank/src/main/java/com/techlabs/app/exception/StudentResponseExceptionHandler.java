// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.exception;

import java.nio.file.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentResponseExceptionHandler {
   public StudentResponseExceptionHandler() {
   }

   @ExceptionHandler
   public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
      StudentErrorResponse error = new StudentErrorResponse();
      error.setStatus(HttpStatus.NOT_FOUND.value());
      error.setMessage(exc.getMessage());
      error.setTimeStamp(System.currentTimeMillis());
      return new ResponseEntity(error, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler
   public ResponseEntity<StudentErrorResponse> handleException(BankApiException exc) {
      StudentErrorResponse error = new StudentErrorResponse();
      error.setStatus(HttpStatus.BAD_REQUEST.value());
      error.setMessage(exc.getMessage());
      error.setTimeStamp(System.currentTimeMillis());
      return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({AccessDeniedException.class})
   public ResponseEntity<StudentErrorResponse> handleException(AccessDeniedException exc) {
      StudentErrorResponse error = new StudentErrorResponse();
      error.setStatus(HttpStatus.UNAUTHORIZED.value());
      error.setMessage(exc.getClass().getSimpleName());
      error.setTimeStamp(System.currentTimeMillis());
      return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler
   public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
      StudentErrorResponse error = new StudentErrorResponse();
      System.out.println("printing error");
      error.setStatus(HttpStatus.BAD_REQUEST.value());
      error.setMessage(exc.getClass().getSimpleName());
      exc.printStackTrace();
      error.setTimeStamp(System.currentTimeMillis());
      return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler
   public ResponseEntity<StudentErrorResponse> handleException(NoRecordFoundException exc) {
      StudentErrorResponse error = new StudentErrorResponse();
      error.setStatus(HttpStatus.NOT_FOUND.value());
      error.setMessage(exc.getMessage());
      error.setTimeStamp(System.currentTimeMillis());
      return new ResponseEntity(error, HttpStatus.NOT_FOUND);
   }
}
