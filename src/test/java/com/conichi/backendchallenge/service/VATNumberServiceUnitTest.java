package com.conichi.backendchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cloudmersive.client.VatApi;
import com.cloudmersive.client.model.VatLookupRequest;
import com.cloudmersive.client.model.VatLookupResponse;
import com.conichi.backendchallenge.exception.InvalidVATNumberException;
import com.conichi.backendchallenge.service.impl.VATNumberServiceImpl;

public class VATNumberServiceUnitTest {

	IVATNumberService vatService;
	VatApi mockedVatApi;
	@BeforeEach
	void init() {
		mockedVatApi = Mockito.mock(VatApi.class);
		vatService = new VATNumberServiceImpl(mockedVatApi);
	}
	
	@Test
	void getCountryCodeSuccess() throws Exception {
		VatLookupRequest vatApiRequest = new VatLookupRequest();
		vatApiRequest.setVatCode("test12312");
		
		VatLookupResponse vatApiResponse = new VatLookupResponse();
		vatApiResponse.setIsValid(true);
		vatApiResponse.setCountryCode("BR");
		
		when(mockedVatApi.vatVatLookup(vatApiRequest)).thenReturn(vatApiResponse);
		
		String returnedCountryCode = vatService.getVatCountryCode(vatApiRequest.getVatCode());
		
		assertNotNull(returnedCountryCode);
		assertEquals(vatApiResponse.getCountryCode(), returnedCountryCode);
	}
	
	@Test
	void convertSameCurrencyFail() throws Exception {
		Assertions.assertThrows(InvalidVATNumberException.class, () -> {
			VatLookupRequest vatApiRequest = new VatLookupRequest();
			vatApiRequest.setVatCode("test12312");
			
			VatLookupResponse vatApiResponse = new VatLookupResponse();
			vatApiResponse.setIsValid(false);
			
			when(mockedVatApi.vatVatLookup(vatApiRequest)).thenReturn(vatApiResponse);
			
			vatService.getVatCountryCode(vatApiRequest.getVatCode());
		});
	}
}
