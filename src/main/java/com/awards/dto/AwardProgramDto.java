package com.awards.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.awards.entities.AwardProgramRule;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
public class AwardProgramDto {
	
	@JsonProperty("program_id")
	private int programId;

	@JsonProperty("program_name")
	private String programName;

	@JsonProperty("rules")
	private List<AwardProgramRule> rules;

	@JsonProperty("begin_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate beginDate;

	@JsonProperty("end_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate endDate;

	@JsonProperty("create_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@JsonProperty("create_user")
	private String createUser = "Admin";

	@JsonProperty("update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	@JsonProperty("update_user")
	private String updateUser;

}
