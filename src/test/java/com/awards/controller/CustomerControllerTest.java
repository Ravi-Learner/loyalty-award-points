package com.awards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.awards.AwardsCalculationApplication;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AwardsCalculationApplication.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void canRetrieveCustomerByIdWhenExists() throws Exception {
		this.mvc.perform(get("/customers/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.customer_id", Matchers.is(2)));
	}
	
	@Test
	void canRetrieveCustomerByIdAndCalculateAwardsWhenTransactionsAndRulesExists() throws Exception {
		this.mvc.perform(get("/customers/awards/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.2.monthly_gains.length()", Matchers.greaterThan(0)))
		.andExpect(jsonPath("$.2.total", Matchers.greaterThan(0.0D)));
	}

}
