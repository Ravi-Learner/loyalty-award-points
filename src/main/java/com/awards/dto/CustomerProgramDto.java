package com.awards.dto;

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
public class CustomerProgramDto {

	@JsonProperty("customer_id")
	private Long customerId;
	
	@JsonProperty("program_id")
	private Long programId;
	
	@JsonProperty("create_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;

	@JsonProperty("create_user")
	private String createUser = "Admin";

	@JsonProperty("update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	@JsonProperty("update_user")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String updateUser;
	
}
