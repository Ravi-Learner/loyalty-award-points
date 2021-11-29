package com.awards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.awards.entities.CustomerProgram;

public interface ICustomerProgramRepository extends JpaRepository<CustomerProgram, Long> {
}
