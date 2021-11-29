package com.awards.mapper;

import org.springframework.stereotype.Service;

import com.awards.dto.TransactionDto;
import com.awards.entities.Transaction;
import com.googlecode.jmapper.JMapper;

@Service
public class TransactionMapper {

	private final JMapper<TransactionDto, Transaction> transactionDtoMapper = new JMapper<>(TransactionDto.class,
			Transaction.class);

	private final JMapper<Transaction, TransactionDto> transactionEntityDtoMapper = new JMapper<>(Transaction.class,
			TransactionDto.class);

	public TransactionDto getTransactionDto(Transaction transactionEntity) {
		return transactionDtoMapper.getDestination(transactionEntity);
	}

	public Transaction getTransactionEntity(TransactionDto transactionDto) {
		return transactionEntityDtoMapper.getDestination(transactionDto);
	}

}
