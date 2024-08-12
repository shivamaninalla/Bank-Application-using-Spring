package com.techlabs.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetails;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Integer>{

	Page<ContactDetails> findByContact(Contact contact, PageRequest pageRequest);

}
