package com.conichi.backendchallenge.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for when an error occurs calling Exchange Rates API.
 * 
 * @author Pablo Alves
 * @since 04/12/2019
 */
public class ExchangeRateApiException extends ApiCallException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public ExchangeRateApiException(String message, HttpStatus httpStatus, Throwable cause) {
		super(httpStatus, message, cause);
	}
}
