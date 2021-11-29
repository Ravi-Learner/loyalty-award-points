package com.awards.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.awards.dto.TransactionDto;
import com.awards.exception.ApiException;
import com.awards.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> saveTransaction(@Valid @RequestBody TransactionDto transactionDto){
		try {
			TransactionDto transaction = transactionService.saveTransaction(transactionDto);
			return new ResponseEntity<>(transaction.getTransactionId(), HttpStatus.CREATED);
		}catch(ApiException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}
	
}
