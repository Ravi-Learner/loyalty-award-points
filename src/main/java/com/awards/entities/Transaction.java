package com.awards.entities;

import java.io.Serializable;
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
@Table(name = "transactions")
@Getter
@Setter
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1429319184385220310L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
    @Column(name = "customer_id")
	private Long customerId;
	
	@Column(name="subtotal")
	private BigDecimal subtotal;
	
	@Column(name="tax")
	private BigDecimal tax;
	
	@Column(name="total")
	private BigDecimal total;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "create_user")
	private String createUser = "Admin";
	
	@Column(name="posted_flag", columnDefinition="TINYINT(1) default 0")
	private Boolean postedFlag;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "update_user")
	private String updateUser;

}
