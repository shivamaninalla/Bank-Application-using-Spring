// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(
   name = "users"
)
public class User {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   private String name;
   @Column(
      nullable = false,
      unique = true
   )
   private String username;
   @Column(
      nullable = false,
      unique = true
   )
   private String email;
   @Column(
      nullable = false
   )
   private String password;
   @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.ALL}
   )
   @JoinTable(
      name = "users_roles",
      joinColumns = {@JoinColumn(
   name = "user_id",
   referencedColumnName = "id"
)},
      inverseJoinColumns = {@JoinColumn(
   name = "role_id",
   referencedColumnName = "id"
)}
   )
   private Set<Role> roles;
   
   
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
   private Customer customer;

   public User() {
   }

   public User(Long id, String name, String username, String email, String password, Set<Role> roles) {
      this.id = id;
      this.name = name;
      this.username = username;
      this.email = email;
      this.password = password;
      this.roles = roles;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
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

   public Customer getCustomer() {
	return customer;
}

public void setCustomer(Customer customer) {
	this.customer = customer;
}

public Set<Role> getRoles() {
      return this.roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }
}
