package com.awards.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.awards.dto.CustomerDto;
import com.awards.dto.CustomerProgramActivityTotalAndMonthlyDto;
import com.awards.exception.ApiException;
import com.awards.exception.CustomerNotFoundException;
import com.awards.service.AwardService;
import com.awards.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	AwardService awardService;
	
	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * This end-point is used to calculate and return awards for particular customer
	 * if that customer is member of loyalty program and if transactions done by that customer
	 * fall under certain rules of this loyalty program
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/awards/{id}")
	public Map<Long, CustomerProgramActivityTotalAndMonthlyDto> getCustomerAwards(@PathVariable("id") Long id) {
		try {
			customerService.issueAwards(id);
			return awardService.getAwardsGroupByMonth(id);
		}catch(CustomerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(),ex);
		}
	}
	
	@GetMapping("/all")
	public List<CustomerDto> findAllCustomers(){
		return customerService.getAllCustomers();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public CustomerDto findCustomerById(@PathVariable("id") Long id) {
		try {
			return customerService.getCustomerById(id);
		} catch (CustomerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * @return
	 * This will return points of all the customers grouped by month and 
	 * also total points of the customer
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/awards/all")
	public Map<Long, CustomerProgramActivityTotalAndMonthlyDto> getAllCustomersAwards() {
		return awardService.getAllAwardsGroupByMonth();
	}
	
	/**
	 * 
	 * @param customerDto
	 * @return
	 * Used to create new customer
	 * Sample data is loaded through data.sql
	 */
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> saveCustomer(@Valid @RequestBody CustomerDto customerDto){
		try {
			CustomerDto customer = customerService.saveCustomer(customerDto);
			return new ResponseEntity<>(customer.getCustomerId(), HttpStatus.CREATED);
		}catch(ApiException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}
	
}
