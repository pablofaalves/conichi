package com.conichi.backendchallenge.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conichi.backendchallenge.dto.CurrencyRequestDTO;
import com.conichi.backendchallenge.exception.ExchangeRateApiException;
import com.conichi.backendchallenge.exception.SameCurrencyException;
import com.conichi.backendchallenge.service.ICurrencyService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * Rest Controller for the /api/currency API.
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyController {

	private final ICurrencyService currencyService;
	
	@ApiOperation(value = "Convert an amount of money based in a sourceCurrency to a targetCurrency")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully currency conversion"),
		    @ApiResponse(code = 400, message = "One or more request parameters are not valid"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
		    @ApiResponse(code = 422, message = "Source and target currency are the same")
		})
	@GetMapping(path = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, BigDecimal> convert(@Valid CurrencyRequestDTO request) throws ExchangeRateApiException,
		SameCurrencyException{
		
		BigDecimal amountConverted = currencyService.convertCurrency(request.getAmount(), 
				request.getSourceCurrency(), request.getTargetCurrency());
		
		return Collections.singletonMap("convertedAmount", amountConverted);
	}
}
