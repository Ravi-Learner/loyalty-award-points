package com.awards.exception;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4662919761330920741L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
}
