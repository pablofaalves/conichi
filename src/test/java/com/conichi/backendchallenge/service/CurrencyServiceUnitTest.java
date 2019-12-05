package com.conichi.backendchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.conichi.backendchallenge.exception.SameCurrencyException;
import com.conichi.backendchallenge.exchangerates.dto.LatestApiResponseDTO;
import com.conichi.backendchallenge.exchangerates.helper.ExchangeRatesCachedApiCall;
import com.conichi.backendchallenge.service.impl.CurrencyServiceImpl;

public class CurrencyServiceUnitTest {

	ExchangeRatesCachedApiCall exchangeRatesCacheCall;
	
	ICurrencyService currencyService;
	
	@BeforeEach
	void init() {
		exchangeRatesCacheCall = Mockito.mock(ExchangeRatesCachedApiCall.class);
		currencyService = new CurrencyServiceImpl(exchangeRatesCacheCall);
	}
	
	@Test
	void convertSuccessful() throws Exception {
		Map<String, String> requestParam = new HashMap<String, String>();
		requestParam.put("base", "EUR");
		requestParam.put("target", "BRL");
		
		BigDecimal requestAmount = BigDecimal.valueOf(45000, 2);
		BigDecimal apiRateResponse = BigDecimal.valueOf(423, 2);
		
		LatestApiResponseDTO responseMock = LatestApiResponseDTO.builder()
				.date(LocalDate.of(1990, 11, 1))
				.rates(Collections.singletonMap("BRL", apiRateResponse)).build();
		
		when(exchangeRatesCacheCall.callExchangeRatesApi(CurrencyServiceImpl.LATEST_API_TEMPLATE, 
				LatestApiResponseDTO.class, requestParam)).thenReturn(responseMock);
		
		BigDecimal convertedAmount = currencyService.convertCurrency(requestAmount, requestParam.get("base"), 
				requestParam.get("target"));
		
		assertNotNull(convertedAmount);
		assertEquals(requestAmount.multiply(apiRateResponse).setScale(2, RoundingMode.HALF_UP), convertedAmount);
	}
	
	@Test
	void convertSameCurrencyFail() throws Exception {
		String currency = "BRL";

		Assertions.assertThrows(SameCurrencyException.class, () -> {
			currencyService.convertCurrency(BigDecimal.ZERO, currency, currency);
		});
	}
}
