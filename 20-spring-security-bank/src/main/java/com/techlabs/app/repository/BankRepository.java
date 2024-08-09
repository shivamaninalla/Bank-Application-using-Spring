package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{

}
