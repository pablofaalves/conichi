package com.conichi.backendchallenge.exchangerates.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.conichi.backendchallenge.exception.ExchangeRateApiException;

import lombok.RequiredArgsConstructor;

/**
 * Static class for caching exchange rates returned by the API
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
@RequiredArgsConstructor
@EnableCaching
@CacheConfig(cacheNames = "ExchangeRatesCache")
public class ExchangeRatesCachedApiCall {

	@Value("${currency.converter.api.base.url}")
	private String apiBaseUrl;
	
	private final RestTemplate restTemplate;

	@Cacheable(sync = true)
	public <T> T callExchangeRatesApi(String api, Class<T> responseClass, Map<String, String> requestParameters)
			throws ExchangeRateApiException {
		try {
			return restTemplate.getForObject(apiBaseUrl + api, responseClass, requestParameters);
		} catch (HttpStatusCodeException e) {
			throw new ExchangeRateApiException(
					e.getStatusCode().getReasonPhrase() + " http error returned from exchange rates API.",
					e.getStatusCode(), e);
		}
	}

	/**
	 * Clear all entries of the exchange rates cache
	 */
	@CacheEvict(allEntries = true)
	public void clearCache() { }
}
