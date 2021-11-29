package com.awards.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="award_program_rules")
public class AwardProgramRule implements Serializable{

	private static final long serialVersionUID = -3235874840357225625L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_id")
	private Long ruleId;

	@Column(name = "program_id")
	private Long programId;

	@Column(name = "multiplier")
	private int multiplier;

	@Column(name = "minimum_amt")
	private BigDecimal minimumAmt;

	@Column(name = "maximum_pts")
	private BigDecimal maximumPts;

	@Column(name = "tier")
	private int tier;
	
	@Column(name = "create_date",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createDate;

	@Column(name = "create_user")
	private String createUser = "Admin";

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "update_user")
	private String updateUser;
}
