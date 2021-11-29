//$Id$
package com.awards.exception.handler;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.awards.dto.error.ErrorDetailDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiResponseEntityDefaultExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Handle {@link ResponseStatusException}
	 * 
	 * @param argException the exception {@link ResponseStatusException}
	 * @param argRequest   the request {@link WebRequest}
	 * @return the response entity {@link ResponseEntity}
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorDetailDto> handleStatusException(ResponseStatusException argException,
			WebRequest argRequest) {
		return handleResponseStatusException(argException, argRequest);
	}

	/**
	 * Handle all exception
	 * 
	 * @param argException the exception {@link Exception}
	 * @param argRequest   the request {@link WebRequest}
	 * @return the response entity {@link ResponseEntity}
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailDto> handleAllExceptions(Exception argException, WebRequest argRequest) {
		log.error("handleAllExceptions >> {}", argException.getLocalizedMessage(), argException);
		return handleAllException(argException, argRequest);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception argException, Object argBody,
			HttpHeaders argHeaders, HttpStatus argStatus, WebRequest argRequest) {
		ResponseEntity<?> responseEntity;
		if (!argStatus.isError()) {
			responseEntity = handleStatusException(argException, argStatus, argRequest);
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(argStatus)) {
			argRequest.setAttribute("javax.servlet.error.exception", argException, 0);
			responseEntity = handleAllException(argException, argRequest);
		} else {
			responseEntity = handleAllException(argException, argRequest);
		}
		return (ResponseEntity<Object>) responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException argException,
			HttpHeaders argHeaders, HttpStatus argStatus, WebRequest argRequest) {
		log.error("handleMethodArgumentNotValid ==> ", argException);
		String message = argException.getBindingResult().getAllErrors().stream().map(this::getFieldError)
				.collect(Collectors.joining(","));
		ResponseEntity<?> responseEntity = ErrorDetailDto.builder().status(argStatus).message(message)
				.path(this.getPath(argRequest)).entity();
		return (ResponseEntity<Object>) responseEntity;
	}

	/**
	 * Field Error
	 * 
	 * @param argObjectError the object error {@link ObjectError}
	 * @return the string
	 */
	private String getFieldError(ObjectError argObjectError) {
		return ((FieldError) argObjectError).getField().concat(": ").concat(argObjectError.getDefaultMessage());
	}

	/**
	 * Handle response status exception
	 * 
	 * @param argException the exception {@link ResponseStatusException}
	 * @param argRequest   the request {@link WebRequest}
	 * @return the response entity {@link ResponseEntity}
	 */
	protected ResponseEntity<ErrorDetailDto> handleResponseStatusException(ResponseStatusException argException,
			WebRequest argRequest) {
		return ErrorDetailDto.builder().exception(argException).path(this.getPath(argRequest)).entity();
	}

	/**
	 * Handle status exception
	 * 
	 * @param argException the exception {@link Exception}
	 * @param argStatus    the http status {@link HttpStatus}
	 * @param argRequest   the request {@link WebRequest}
	 * @return the response entity {@link ResponseEntity}
	 */
	protected ResponseEntity<ErrorDetailDto> handleStatusException(Exception argException, HttpStatus argStatus,
			WebRequest argRequest) {
		return ErrorDetailDto.builder().status(argStatus).message(argException.getLocalizedMessage())
				.path(getPath(argRequest)).entity();
	}

	/**
	 * Handle all exception
	 * 
	 * @param argException the exception {@link Exception}
	 * @param argRequest   the request {@link WebRequest}
	 * @return the response entity {@link ResponseEntity}
	 */
	protected ResponseEntity<ErrorDetailDto> handleAllException(Exception argException, WebRequest argRequest) {
		return ErrorDetailDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(argException.getLocalizedMessage()).path(this.getPath(argRequest)).entity();
	}

	/**
	 * Path
	 * 
	 * @param argRequest the request
	 * @return
	 */
	private String getPath(WebRequest argRequest) {
		return argRequest.getDescription(false).substring(4);
	}
}
