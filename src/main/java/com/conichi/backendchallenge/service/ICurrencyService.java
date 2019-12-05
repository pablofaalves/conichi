package com.conichi.backendchallenge.service;

/**
 * Interface for Currency Service.
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
import java.math.BigDecimal;

import com.conichi.backendchallenge.exception.ExchangeRateApiException;
import com.conichi.backendchallenge.exception.SameCurrencyException;

public interface ICurrencyService {

	/**
	 * Convert an amount represented in source currency to a target currency.
	 * 
	 * @param amount
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return BigDecimal - converted amount on target currency
	 * @throws ExchangeRateApiException
	 * @throws SameCurrencyException
	 */
	BigDecimal convertCurrency(BigDecimal amount, String sourceCurrency, String targetCurrency)
			throws ExchangeRateApiException, SameCurrencyException;
}
