package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Customer;

public interface AccountRepository extends JpaRepository<Account,Long>{

	
	@Query("select coalesce(sum(a.balance), 0) from Account a where a.customer = ?1")
	double getTotalBalance(Customer customer);

}