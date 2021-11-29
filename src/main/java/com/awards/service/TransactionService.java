package com.awards.service;

import static com.awards.config.AwardsConstants.EXCEPTION_RECORD_NOT_SAVED;
import static com.awards.config.AwardsConstants.EXCEPTION_RECORD_NOT_FOUND;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awards.dto.CustomerDto;
import com.awards.dto.TransactionDto;
import com.awards.entities.Transaction;
import com.awards.exception.ApiException;
import com.awards.exception.TransactionNotFoundException;
import com.awards.mapper.TransactionMapper;
import com.awards.repository.ITransactionRepository;

//@Slf4j
@Service
@Transactional
public class TransactionService {

	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	TransactionMapper transactionMapper;
	
	@Autowired
	CustomerService customerService;
	
	public void markTransactionAsPosted(TransactionDto transaction) {
		transaction.setPostedFlag(true);
		transactionRepository.save(transactionMapper.getTransactionEntity(transaction));
	}
	
	
	public TransactionDto saveTransaction(TransactionDto transactionDto) {
		try {
			CustomerDto customerDto = customerService.getCustomerById(transactionDto.getCustomerId());
			if (customerDto != null) {
				Transaction transaction = transactionRepository
						.save(transactionMapper.getTransactionEntity(transactionDto));
				return transactionMapper.getTransactionDto(transaction);
			}
			return null;

		} catch (Exception ex) {
			throw new ApiException(EXCEPTION_RECORD_NOT_SAVED,
					"Error record could not be saved ==> " + ex.getLocalizedMessage());
		}
	}
	
	public TransactionDto getTransactionById(Long transId){
		try {
			Transaction transaction = transactionRepository.findByTransactionId(transId);
			if(transaction != null) {
				return transactionMapper.getTransactionDto(transaction);
			}else {
				throw new TransactionNotFoundException(String.format("Transaction not found for Id %d",transId));
			}
		}catch (Exception ex) {
			throw new ApiException(EXCEPTION_RECORD_NOT_FOUND,
					"Error record could not be saved ==> " + ex.getLocalizedMessage());
		}
	}
	
}
