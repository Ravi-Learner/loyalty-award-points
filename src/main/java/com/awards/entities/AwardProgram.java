package com.awards.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="award_programs")
public class AwardProgram implements Serializable{

	private static final long serialVersionUID = -8511638580721091742L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "program_id")
	private Long programId;

	@Column(name = "program_name")
	private String programName;

	@OneToMany(mappedBy = "programId", fetch = FetchType.LAZY)
	private List<AwardProgramRule> rules;

	@Column(name = "begin_date")
	private LocalDate beginDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "create_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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
