package com.conichi.backendchallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.conichi.backendchallenge.dto.CurrencyRequestDTO;
import com.conichi.backendchallenge.service.ICurrencyService;
import com.conichi.backendchallenge.test.AbstractMvcTest;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerUnitTest extends AbstractMvcTest {

	private static final String CONVERT_API_BASE_URL = "/api/currency/convert";
	private static final String CONVERT_API_TEMPLATE = CONVERT_API_BASE_URL
			+ "?amount={amount}&sourceCurrency={source}&targetCurrency={target}";
	@MockBean
	ICurrencyService currencyService;
	
	@Test
	void convertSuccess() throws Exception {
		CurrencyRequestDTO request = CurrencyRequestDTO.builder()
				.amount(new BigDecimal("450.00"))
				.sourceCurrency("EUR")
				.targetCurrency("BRL").build();
		
		when(currencyService.convertCurrency(request.getAmount(), request.getSourceCurrency(), 
				request.getTargetCurrency())).thenReturn(new BigDecimal("2500.00"));
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(CONVERT_API_TEMPLATE, 
				request.getAmount(), 
				request.getSourceCurrency(), 
				request.getTargetCurrency())).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
		Map<String, Double> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "convertedAmount";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(2500.0d, returnMap.get(mapKey));
	}
	
	@Test
	void convertFailNullParameters() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(new URI(CONVERT_API_BASE_URL))).andReturn();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		
		Map<String, List<String>> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "error";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(3, returnMap.get(mapKey).size());
	}
	
	@Test
	void convertFailCurrencySize() throws Exception {
		CurrencyRequestDTO request = CurrencyRequestDTO.builder()
				.amount(new BigDecimal("450.00"))
				.sourceCurrency("UR")
				.targetCurrency("BRLS").build();
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(CONVERT_API_TEMPLATE, 
				request.getAmount(), 
				request.getSourceCurrency(), 
				request.getTargetCurrency())).andReturn();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		
		Map<String, List<String>> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "error";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(2, returnMap.get(mapKey).size());
	}
}
