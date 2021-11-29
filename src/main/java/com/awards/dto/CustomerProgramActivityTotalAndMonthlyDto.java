package com.awards.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerProgramActivityTotalAndMonthlyDto {
	
	@JsonProperty("customer_id")
	private Long customerId;

	private BigDecimal total;
	
	@JsonProperty("monthly_gains")
	private Map<YearMonth, BigDecimal> awards;

}
