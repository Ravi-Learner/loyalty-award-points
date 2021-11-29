package com.awards.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_programs")
@Getter
@Setter
public class CustomerProgram implements Serializable{
	
	private static final long serialVersionUID = -712294664772019659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="program_id")
	private Long programId;
	
	@ManyToOne
	@JoinColumn(name="customer_id", insertable = false, updatable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="program_id", insertable = false, updatable = false)
	private AwardProgram awardProgram;
	
	@Column(name = "create_date",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createDate;

	@Column(name = "create_user")
	private String createUser = "Admin";

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "update_user")
	private String updateUser;
	
}
