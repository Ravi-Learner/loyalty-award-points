package com.awards.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="award_program_activity")
public class AwardProgramActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id")
	private Long activityId;
	
	@Column(name="program_id")
	private Long programId;
	
	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="points_earned")
	private BigDecimal pointsEarned;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "create_user")
	private String createUser = "Admin";

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "update_user")
	private String updateUser;
	
}
