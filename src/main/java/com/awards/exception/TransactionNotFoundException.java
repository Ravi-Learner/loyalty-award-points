package com.awards.exception;

public class TransactionNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 5923831073184571684L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
