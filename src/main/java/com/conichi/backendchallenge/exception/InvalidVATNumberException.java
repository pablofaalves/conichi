package com.conichi.backendchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when the VAT number is invalidated by the VAT Lookup API response.
 * 
 * @author Pablo Alves
 * @since 04/12/2019
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "VAT number is not valid.")
public class InvalidVATNumberException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
}
