package com.conichi.backendchallenge.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Abstraction for Exceptions caused by external API calls.
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiCallException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	private HttpStatus httpStatus;

	public ApiCallException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}
}
