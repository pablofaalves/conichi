package com.conichi.backendchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when the source and target currency are the same.
 * 
 * @author Pablo Alves
 * @since 04/12/2019
 */
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Source and target currency are the same")
public class SameCurrencyException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
