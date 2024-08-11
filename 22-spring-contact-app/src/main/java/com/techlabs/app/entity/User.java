package com.techlabs.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int userId;
   
   @Email
   @NotBlank
   private String email;
   
   @NotBlank
   @Size(min=2,max=20,message="Please check the size of firstName")
   private String firstName;
   
   @NotBlank
   private String lastName;
   
   @NotNull
   private boolean admin ;  
   
   @NotNull
   private boolean active=true;
   
   @NotBlank
   private String password;
   
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
   private List<Contact> contacts;
}
