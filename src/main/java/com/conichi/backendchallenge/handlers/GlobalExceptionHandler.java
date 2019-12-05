package com.conichi.backendchallenge.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.conichi.backendchallenge.exception.ApiCallException;

/**
 * Controller Advice for handling exceptions
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Exception handler for invalid method arguments with http response 400 (Bad Request)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(MethodArgumentNotValidException exception) {
		return error(exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList()));
	}

	/**
	 * Exception handler for invalid constraint with http response 400 (Bad Request)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(ConstraintViolationException exception) {
		return error(exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList()));
	}
	
	/**
	 * Exception handler for invalid constraint with http response 400 (Bad Request)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(BindException exception) {
		return error(exception.getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList()));
	}

	/**
	 * Exception handler for API client call error with http response according to the returned 
	 * API HTTP status.
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handle(ApiCallException exception) {
		return new ResponseEntity<Map<String, Object>>(error(Arrays.asList(exception.getMessage())), 
				exception.getHttpStatus());
	}
	
	private Map<String, Object> error(Object message) {
		return Collections.singletonMap("error", message);
	}
}
