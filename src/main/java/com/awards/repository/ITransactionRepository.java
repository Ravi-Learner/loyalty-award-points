package com.awards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awards.entities.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long>{

	Transaction findByTransactionId(Long transId);
	
}
