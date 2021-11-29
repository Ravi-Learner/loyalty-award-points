package com.awards.service;

import static com.awards.config.AwardsConstants.EXCEPTION_RECORD_NOT_SAVED;
import static com.awards.config.AwardsConstants.NO_TRANSACTIONS_FOUND_FOR_CUSTOMER;
import static com.awards.config.AwardsConstants.NO_AWARD_RULES;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awards.dto.AwardProgramActivityDto;
import com.awards.dto.CustomerDto;
import com.awards.dto.TransactionDto;
import com.awards.entities.AwardProgramRule;
import com.awards.entities.Customer;
import com.awards.exception.ApiException;
import com.awards.exception.CustomerNotFoundException;
import com.awards.mapper.AwardProgramActivityMapper;
import com.awards.mapper.CustomerMapper;
import com.awards.repository.IAwardProgramActivityRepository;
import com.awards.repository.ICustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CustomerService {

	@Autowired
	ICustomerRepository customerRepository;

	@Autowired
	AwardService awardService;

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	AwardProgramActivityMapper awardProgramActivityMapper;

	@Autowired
	IAwardProgramActivityRepository awardProgramActivityRepository;

	public CustomerDto getCustomerById(Long id) {
		Customer customer = customerRepository.findByCustomerId(id);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer with id " + id + " not found");
		} else
			return customerMapper.getCustomerDto(customer);
	}

	public CustomerDto saveCustomer(CustomerDto customerDto) {
		try {
			Customer customer = customerRepository.saveAndFlush(customerMapper.getCustomerEntity(customerDto));
			return customerMapper.getCustomerDto(customer);
		} catch (Exception ex) {
			throw new ApiException(EXCEPTION_RECORD_NOT_SAVED,
					"Error record could not be saved, please check the sent data!");
		}
	}

	public void issueAwards(Long customerId) {
		CustomerDto customer = getCustomerById(customerId);
		List<AwardProgramRule> awardRules = customer != null
				? awardService.getAwardRules(customer.getCustomerPrograms())
				: new ArrayList<>();

		if (customer != null && customer.getTransactions().isEmpty()) {
			log.warn(String.format(NO_TRANSACTIONS_FOUND_FOR_CUSTOMER, customerId));
		}
		if (awardRules.isEmpty()) {
			log.warn(String.format(NO_AWARD_RULES, customerId));
		}

		// if customer is there, award rules are present and transactions are associated
		// with
		// customer then proceed to award calculation

		if (customer != null && !awardRules.isEmpty() && !customer.getTransactions().isEmpty()) {
			Function<TransactionDto, BigDecimal> totalMapper = transactionDto -> awardService
					.calculateAwardPoints(customerId, awardRules, transactionDto);
			customer.getTransactions().stream().map(totalMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
		}

	}

	public void delteCustomerById(Long customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId);
		if (customer != null) {
			customerRepository.deleteById(customerId);
		} else {
			throw new CustomerNotFoundException(String.format("Customer with id %d not found ", customerId));
		}
	}

	public List<AwardProgramActivityDto> getAwardProgramActivitiesForCustomer(Long customerId) {
		return this.awardProgramActivityMapper
				.getAwardProgramActivityDtos(this.awardProgramActivityRepository.findByCustomerId(customerId));
	}

	public List<CustomerDto> getAllCustomers() {
		return this.customerMapper.getAllCustomerDtos(this.customerRepository.findAll());
	}

}
