package com.conichi.backendchallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.conichi.backendchallenge.service.IVATNumberService;
import com.conichi.backendchallenge.test.AbstractMvcTest;

@WebMvcTest(VATNumberController.class)
public class VATNumberControllerUnitTest extends AbstractMvcTest {

	private static final String VAT_COUNTRYCODE_API_BASE_URL = "/api/vat/countrycode";
	private static final String VAT_COUNTRYCODE_API_TEMPLATE = VAT_COUNTRYCODE_API_BASE_URL
			+ "?vatNumber={vat}";
	@MockBean
	IVATNumberService vatNumberService;
	
	@Test
	void getCountryCodeSuccess() throws Exception {
		
		String vatNumber = "TEST21354";
		String responseCountryCode = "BR";
		
		when(vatNumberService.getVatCountryCode(vatNumber)).thenReturn(responseCountryCode);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(VAT_COUNTRYCODE_API_TEMPLATE, 
				vatNumber)).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
		Map<String, String> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "countryCode";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(responseCountryCode, returnMap.get(mapKey));
	}
	
	@Test
	void getCountryCodeFailNullParameters() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(new URI(VAT_COUNTRYCODE_API_BASE_URL))).andReturn();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		
		Map<String, List<String>> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "error";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(1, returnMap.get(mapKey).size());
		assertEquals("'vatNumber' is blank", returnMap.get(mapKey).get(0));
	}
	
	@Test
	void getCountryCodeFailVatNumberSize() throws Exception {
		String vatNumber = "21";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(VAT_COUNTRYCODE_API_TEMPLATE, 
				vatNumber)).andReturn();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		
		Map<String, List<String>> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "error";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertEquals(1, returnMap.get(mapKey).size());
		assertEquals("VAT number must have min of 4 and max of 15 characters", returnMap.get(mapKey).get(0));
	}
}
