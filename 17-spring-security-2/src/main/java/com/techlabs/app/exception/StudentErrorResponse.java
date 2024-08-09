// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.exception;

public class StudentErrorResponse {
   private int status;
   private String message;
   private long timeStamp;

   public StudentErrorResponse(int status, String message, long timeStamp) {
      this.status = status;
      this.message = message;
      this.timeStamp = timeStamp;
   }

   public StudentErrorResponse() {
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public long getTimeStamp() {
      return this.timeStamp;
   }

   public void setTimeStamp(long timeStamp) {
      this.timeStamp = timeStamp;
   }
}
