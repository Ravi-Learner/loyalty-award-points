//$Id$
package com.awards.dto.error;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;


@Getter
@Setter
@ToString
public class ErrorDetailDto implements Serializable {

	private static final long serialVersionUID = -8045485585245319984L;
	/**
	 * time stamp
	 */
	@Setter(value = AccessLevel.NONE)
	private LocalDateTime timestamp = LocalDateTime.now();
	/**
	 * message
	 */
	private String message;
	/**
	 * status
	 */
	private int status;
	/**
	 * error
	 */
	private String error;
	/**
	 * path
	 */
	private String path;

	/**
	 * Builder instance
	 * 
	 * @return
	 */
	public static ErrorDetailBuilder builder() {
		return new ErrorDetailBuilder();
	}
}
