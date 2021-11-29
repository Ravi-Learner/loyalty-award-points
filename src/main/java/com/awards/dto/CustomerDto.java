package com.awards.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CustomerDto {

	@JsonProperty("customer_id")
	private Long customerId;
	
	@NotEmpty(message="First name is mandatory")
	@JsonProperty("first_name")
	private String firstName;
	
	@NotEmpty(message="Last name is mandatory")
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("create_date")
	@DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss")
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@JsonProperty("create_user")
	private String createUser = "Admin";
	
	@JsonProperty("update_date")
	@DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss")
	private LocalDateTime updateDate;
	
	@JsonProperty("update_user")
	private String updateUser;
	
	@JsonProperty("customer_programs")
	private List<CustomerProgramDto> customerPrograms;
	
	@JsonIgnore
	private List<TransactionDto> transactions;
	
}
