package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
