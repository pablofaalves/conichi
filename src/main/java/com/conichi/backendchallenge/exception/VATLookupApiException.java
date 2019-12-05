package com.conichi.backendchallenge.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for when an error occurs calling VAT Lookup API.
 * 
 * @author Pablo Alves
 * @since 04/12/2019
 */
public class VATLookupApiException extends ApiCallException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public VATLookupApiException(String message, HttpStatus httpStatus, Throwable cause) {
		super(httpStatus, message, cause);
	}
}

