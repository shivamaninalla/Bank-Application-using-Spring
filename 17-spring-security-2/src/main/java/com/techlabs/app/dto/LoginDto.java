// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.dto;

public class LoginDto {
   private String usernameOrEmail;
   private String password;

   public LoginDto() {
   }

   public LoginDto(String usernameOrEmail, String password) {
      this.usernameOrEmail = usernameOrEmail;
      this.password = password;
   }

   public String getUsernameOrEmail() {
      return this.usernameOrEmail;
   }

   public void setUsernameOrEmail(String usernameOrEmail) {
      this.usernameOrEmail = usernameOrEmail;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String toString() {
      return "LoginDto [usernameOrEmail=" + this.usernameOrEmail + ", password=" + this.password + "]";
   }
}
