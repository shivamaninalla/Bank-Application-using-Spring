package com.techlabs.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{
	
	Page<Contact> findByUser(User user, Pageable pageable);

}
