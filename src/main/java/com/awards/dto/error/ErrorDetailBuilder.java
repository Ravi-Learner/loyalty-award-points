//$Id$
package com.awards.dto.error;

import java.util.Objects;

import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;

public class ErrorDetailBuilder {
	/**
	 * status
	 */
	private int status;
	/**
	 * error
	 */
	private String error;
	/**
	 * message
	 */
	private String message;
	/**
	 * path
	 */
	private String path;

	/**
	 * Status
	 * @param argStatus the valid http status code
	 * @return the reference of builder
	 * @see HttpStatus
	 */
	public ErrorDetailBuilder status(int argStatus) {
		this.status = argStatus;
		return this;
	}

	/**
	 * Status
	 * @param argStatus the http status reference
	 * @return the reference of builder
	 * @see HttpStatus
	 */
	public ErrorDetailBuilder status(HttpStatus argStatus) {
		this.status = argStatus.value();
		if (argStatus.isError()) {
			this.error = argStatus.getReasonPhrase();
		}
		return this;
	}

	/**
	 * Error
	 * @param argError the error
	 * @return the reference of builder
	 */
	public ErrorDetailBuilder error(String argError) {
		this.error = argError;
		return this;
	}

	/**
	 * Exception
	 * 
	 * @param argException
	 * @return the reference of builder
	 */
	public ErrorDetailBuilder exception(ResponseStatusException argException) {
		HttpStatus tempStatus = argException.getStatus();
		this.status = tempStatus.value();
		if (!Objects.requireNonNull(argException.getReason()).isEmpty()) {
			this.message = argException.getReason();
		}
		if (tempStatus.isError()) {
			this.error = tempStatus.getReasonPhrase();
		}
		return this;
	}

	/**
	 * Message
	 * @param argMessage
	 * @return the reference of builder
	 */
	public ErrorDetailBuilder message(String argMessage) {
		this.message = argMessage;
		return this;
	}

	/**
	 * Path
	 * @param argPath the path
	 * @return the reference of builder
	 */
	public ErrorDetailBuilder path(String argPath) {
		this.path = argPath;
		return this;
	}

	/**
	 * Build error detail
	 * @return the reference of error detail
	 */
	public ErrorDetailDto build() {
		ErrorDetailDto errorDetail = new ErrorDetailDto();
		errorDetail.setStatus(status);
		errorDetail.setError(error);
		errorDetail.setMessage(message);
		errorDetail.setPath(path);
		return errorDetail;
	}

	/**
	 * Entity
	 * @return the reference of response entity
	 * @see ResponseEntity
	 */
	public ResponseEntity<ErrorDetailDto> entity() {
		return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
	}
}
