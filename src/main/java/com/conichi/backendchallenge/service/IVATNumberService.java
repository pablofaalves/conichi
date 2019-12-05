package com.conichi.backendchallenge.service;

import com.conichi.backendchallenge.exception.InvalidVATNumberException;
import com.conichi.backendchallenge.exception.VATLookupApiException;

/**
 * Interface for VAT number service
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
public interface IVATNumberService {
	
	/**
	 * Validate and get country code from a VAT number.
	 * 
	 * @param vatNumber
	 * @return String - Country code
	 * @throws VATLookupApiException
	 * @throws InvalidVATNumberException
	 */
	String getVatCountryCode(String vatNumber) throws VATLookupApiException, InvalidVATNumberException; 
}
