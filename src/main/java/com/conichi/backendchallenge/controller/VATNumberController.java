package com.conichi.backendchallenge.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conichi.backendchallenge.exception.InvalidVATNumberException;
import com.conichi.backendchallenge.exception.VATLookupApiException;
import com.conichi.backendchallenge.service.IVATNumberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * Rest Controller for the /api/vat API.
 * 
 * @author Pablo Alves
 * @since 04/12/2019
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vat")
@Validated
public class VATNumberController {

	private final IVATNumberService vatService;

	@ApiOperation(value = "Validate and return country code from a VAT number")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully validate and return country code"),
		    @ApiResponse(code = 400, message = "One or more request parameters are not valid"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
		    @ApiResponse(code = 422, message = "The VAT number informed is invalid")
		    
		})
	@GetMapping(path = "/countrycode", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getVatCountryCode(
			@ApiParam(value = "The VAT Number to be validated. Ex.: ATU66889218")
			@RequestParam(required = false)
			@Size(min = 4, max = 15, message = "VAT number must have min of 4 and max of 15 characters") 
			@NotBlank(message = "'vatNumber' is blank") String vatNumber)
			throws VATLookupApiException, InvalidVATNumberException {

		return Collections.singletonMap("countryCode", vatService.getVatCountryCode(vatNumber));
	}
}
