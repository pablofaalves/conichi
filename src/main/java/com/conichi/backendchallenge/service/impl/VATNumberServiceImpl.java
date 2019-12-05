package com.conichi.backendchallenge.service.impl;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cloudmersive.client.VatApi;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.model.VatLookupRequest;
import com.cloudmersive.client.model.VatLookupResponse;
import com.conichi.backendchallenge.exception.InvalidVATNumberException;
import com.conichi.backendchallenge.exception.VATLookupApiException;
import com.conichi.backendchallenge.service.IVATNumberService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Implementation of VAT Number Service.
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
@Service
@CacheConfig(cacheNames = "VATNumberCache")
@AllArgsConstructor
@NoArgsConstructor
public class VATNumberServiceImpl implements IVATNumberService {

	/** Cron definition for scheduled evict operation on every Sunday at mid-night **/
	private static final String CACHE_EVICT_SCHEDULE = "0 0 0 * * SUN";
	
	private VatApi apiInstance;
	
	@Override
	@Cacheable
	public String getVatCountryCode(String vatNumber) throws VATLookupApiException, InvalidVATNumberException {
		try {
			VatLookupResponse response = apiInstance.vatVatLookup(new VatLookupRequest().vatCode(vatNumber));
			
			// If VAT number has not been validates according to the API
			if (!response.isIsValid()) {
				throw new InvalidVATNumberException();
			}
			
			return response.getCountryCode();
		} catch (ApiException e) {
			HttpStatus httpStatus = Optional.ofNullable(HttpStatus.resolve(e.getCode()))
					.orElse(HttpStatus.SERVICE_UNAVAILABLE);
			throw new VATLookupApiException(
					httpStatus.getReasonPhrase() + " http error returned from VAT lookup API.",
					httpStatus, e);
		}
	}
	
	/**
	 * Scheduled method that clears the cache of VAT Numbers every Sunday at mid-night.
	 */
	@Scheduled(cron = CACHE_EVICT_SCHEDULE)
	@CacheEvict(allEntries = true)
	public void clearVATCache() { }
}
