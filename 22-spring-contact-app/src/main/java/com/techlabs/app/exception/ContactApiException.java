// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.exception;

import org.springframework.http.HttpStatus;

public class ContactApiException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private HttpStatus status;
   private String message;

   public ContactApiException(HttpStatus status, String message) {
      this.status = status;
      this.message = message;
   }

   public ContactApiException(String message, HttpStatus status, String message1) {
      super(message);
      this.status = status;
      this.message = message1;
   }

   public HttpStatus getStatus() {
      return this.status;
   }

   public String getMessage() {
      return this.message;
   }
}
