package com.awards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.awards.AwardsCalculationApplication;
import com.awards.dto.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AwardsCalculationApplication.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void saveTransactionWhenValidatedAndCustomerExists() throws Exception {
		
		// Will fail as customer id with 11 does not exist
		// Customer id inserted are (1-5)
		
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setCustomerId(11L); // Required
		transactionDto.setTotal(BigDecimal.valueOf(55.00)); // Required
		transactionDto.setTax(BigDecimal.valueOf(5.00));
		transactionDto.setSubtotal(BigDecimal.valueOf(50.00)); // Required
		transactionDto.setCreateDate(LocalDateTime.now());	// Required
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		this.mvc.perform(post("/transactions/create").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transactionDto))).andExpect(status().isCreated());
	}
}
