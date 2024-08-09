package com.techlabs.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

	
	
	@Query("select t from Transaction t where t.senderAccount=:account or t.receiverAccount=:account")
	List<Transaction> viewPassbook(@Param("account")Account account);

	
	
	List<Transaction> findByTransactionDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
