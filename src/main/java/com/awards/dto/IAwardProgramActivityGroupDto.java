package com.awards.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface IAwardProgramActivityGroupDto {

	@JsonProperty("customer_id")
	Long getcustomerId();

	@JsonIgnore
	String getYear();

	@JsonIgnore
	String getMonth();

	@JsonProperty("year_month")
	default YearMonth getYearMonth() {
		try {
			return YearMonth.of(Integer.valueOf(getYear()),Integer.valueOf(getMonth()));
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	@JsonProperty("points")
	BigDecimal getPoints();

}
