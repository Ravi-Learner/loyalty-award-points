package com.awards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

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
public class TransactionDto {

	@JsonProperty("trans_id")
	private Long transactionId;
	
	@JsonProperty("customer_id")
	@NotNull(message = "Customer id is mandatory")
	private Long customerId;
	
	@NotNull(message = "Subtotal can't be null")
	@DecimalMin(value = "0.0", inclusive = false, message="Subtotal should be greater than 0")
	@JsonProperty("sub_total")
	private BigDecimal subtotal;
	
	@DecimalMin(value = "0.0", inclusive = true)
	@JsonProperty("tax")
	private BigDecimal tax;
	
	@NotNull(message = "Total can't be null")
	@DecimalMin(value = "0.0", inclusive = false, message="Total should be greater than 0")
	@JsonProperty("total")
	private BigDecimal total;
	
	@NotNull(message = "Transaction date (create_date) is mandatory")
	@JsonProperty("create_date")
	@DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss")
	private LocalDateTime createDate;

	@JsonProperty("create_user")
	private String createUser = "Admin";

	@JsonProperty("update_date")
	private LocalDateTime updateDate;

	@JsonProperty("update_user")
	private String updateUser;
	
	@JsonProperty("posted_flag")
	private Boolean postedFlag = false;
}
