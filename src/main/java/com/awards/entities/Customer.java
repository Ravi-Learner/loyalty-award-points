package com.awards.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer implements Serializable{

	private static final long serialVersionUID = 2200007983288605723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	
	@OneToMany
	@JoinColumn(name = "customer_id")
	private List<CustomerProgram> customerPrograms;
	
	@OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY)
	@Where(clause = "posted_flag = 0")
 	private List<Transaction> transactions;

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
