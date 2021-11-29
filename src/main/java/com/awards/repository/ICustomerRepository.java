package com.awards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awards.entities.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

	public Customer findByCustomerId(Long id);
}
