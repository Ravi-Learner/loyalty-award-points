package com.awards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.awards.dto.IAwardProgramActivityGroupDto;
import com.awards.entities.AwardProgramActivity;

public interface IAwardProgramActivityRepository extends JpaRepository<AwardProgramActivity, Long> {

	List<AwardProgramActivity> findByCustomerId(Long customerId);

	// Find awards of a particular customer by month of earnings
	@Query(value = "select customer_id as customerId, month(create_date) as month,year(create_date) year, round(sum(points_earned),2) as points"
			+ " from  award_program_activity where customer_id = ?1 group by customer_id, year(create_date), month(create_date) order by customer_id, year, month asc", nativeQuery = true)
	List<IAwardProgramActivityGroupDto> groupAwardProgramActivityByMonthYear(Long customerId);
	
	// Find awards of a all customers grouped by customer, month of earnings
	@Query(value = "select customer_id as customerId, month(create_date) as month,year(create_date) year, round(sum(points_earned),2) as points"
			+ " from  award_program_activity group by customer_id, year(create_date), month(create_date) order by customer_id, year, month asc", nativeQuery = true)
	List<IAwardProgramActivityGroupDto> groupAwardProgramActivityByMonthYearAllCustomers();

}
