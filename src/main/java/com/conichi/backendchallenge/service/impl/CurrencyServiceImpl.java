package com.conichi.backendchallenge.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conichi.backendchallenge.exception.ExchangeRateApiException;
import com.conichi.backendchallenge.exception.SameCurrencyException;
import com.conichi.backendchallenge.exchangerates.dto.LatestApiResponseDTO;
import com.conichi.backendchallenge.exchangerates.helper.ExchangeRatesCachedApiCall;
import com.conichi.backendchallenge.service.ICurrencyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements ICurrencyService {
	
	public static final String LATEST_API_TEMPLATE = "/latest?base={base}&symbols={target}";

	private final ExchangeRatesCachedApiCall exchangeRatesCacheCall;

	/** Volatile date for multithread checking of cache evict **/
	private volatile LocalDate currentExchangeDate = LocalDate.now();

	@Override
	public BigDecimal convertCurrency(BigDecimal amount, String sourceCurrency, String targetCurrency)
			throws ExchangeRateApiException, SameCurrencyException {

		// Check if the user requested the same source and target currency
		if (sourceCurrency.equals(targetCurrency)) {
			throw new SameCurrencyException();
		}
		
		Map<String, String> requestParam = new HashMap<String, String>();
		requestParam.put("base", sourceCurrency.toUpperCase());
		requestParam.put("target", targetCurrency.toUpperCase());

		LatestApiResponseDTO response = exchangeRatesCacheCall.callExchangeRatesApi(LATEST_API_TEMPLATE,
				LatestApiResponseDTO.class, requestParam);

		// Check if response date changed and clear rates cache
		this.checkCacheEvict(response.getDate());

		BigDecimal targetRate = response.getRates().get(targetCurrency.toUpperCase());

		return amount.multiply(targetRate).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Check if the rates base date has changed, which means that the cached rates needs to be cleared.
	 * 
	 * @param dateCheck
	 */
	private void checkCacheEvict(LocalDate dateCheck) {
		if (currentExchangeDate.compareTo(dateCheck) != 0) {
			currentExchangeDate = dateCheck;
			exchangeRatesCacheCall.clearCache();
		}
	}
}
