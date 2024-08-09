package com.techlabs.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customer_id;
	
	@NotBlank(message="first name cannot be blank")
	@Size(min = 2 , max = 50)
	private String firstName;
	
	@NotBlank(message="last name cannot be blank")
	@Size(min = 2,max = 50)
	private String lastName;
	
	@NotNull
	private double totalBalance;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	private List<Account> accounts;
	
	@OneToOne(mappedBy = "customer")
	private User user;

	public void addbankAccount(Account account) {
		accounts.add(account);
		
	}
	
	
}