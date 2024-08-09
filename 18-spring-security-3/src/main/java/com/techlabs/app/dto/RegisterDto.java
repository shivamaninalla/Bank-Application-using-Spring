// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.dto;

import lombok.ToString;

@ToString

public class RegisterDto {
   private String name;
   private String username;
   private String email;
   private String password;
   private boolean admin;

   public RegisterDto() {
   }

   public RegisterDto(String name, String username, String email, String password, boolean admin) {
      this.name = name;
      this.username = username;
      this.email = email;
      this.password = password;
      this.admin=admin;
   }



public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

public boolean isAdmin() {
	return admin;
}

public void setAdmin(boolean admin) {
	this.admin = admin;
}


}
