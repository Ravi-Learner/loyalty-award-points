package com.awards.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3243853995787081367L;
	/**
	 * error code
	 */
	private final String errorCode;

	/**
	 * Constructs a <code>DeviceFalloutException</code>.
	 * 
	 * @param argErrorCode the error code
	 * @param argMessage   the message
	 */
	public ApiException(String argErrorCode, String argMessage) {
		super(argMessage);
		this.errorCode = argErrorCode;
	}
}
