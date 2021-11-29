package com.awards.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.awards.dto.CustomerDto;
import com.awards.entities.Customer;
import com.googlecode.jmapper.JMapper;

@Service
public class CustomerMapper {

	private final JMapper<CustomerDto, Customer> customerDtoMapper = new JMapper<>(CustomerDto.class, Customer.class);

	private final JMapper<Customer, CustomerDto> customerEntityDtoMapper = new JMapper<>(Customer.class,
			CustomerDto.class);

	public CustomerDto getCustomerDto(Customer customerEntity) {
		return customerDtoMapper.getDestination(customerEntity);
	}

	public Customer getCustomerEntity(CustomerDto customerDto) {
		return customerEntityDtoMapper.getDestination(customerDto);
	}

	public List<CustomerDto> getAllCustomerDtos(List<Customer> customers) {
		return customers.stream().map(this::getCustomerDto).collect(Collectors.toList());
	}

}
