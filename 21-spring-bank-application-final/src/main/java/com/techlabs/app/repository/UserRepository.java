package com.techlabs.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);



	Boolean existsByEmail(String email);



	User findByCustomer(Customer i);
}