package com.awards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

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
public class AwardProgramActivityDto {

	@JsonProperty("activity_id")
	private Long activityId;
	
	@JsonProperty("program_id")
	private Long programId;
	
	@JsonProperty("customer_id")
	private Long customerId;
	
	@JsonProperty("points_earned")
	private BigDecimal pointsEarned;
	
	@JsonProperty("create_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;

	@JsonProperty("create_user")
	private String createUser = "Admin";

	@JsonProperty("update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	@JsonProperty("update_user")
	private String updateUser;
	
}
