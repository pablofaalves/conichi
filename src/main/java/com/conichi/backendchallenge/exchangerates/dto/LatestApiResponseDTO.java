package com.conichi.backendchallenge.exchangerates.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the response of the Exchange Rates '/latest' API call.
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatestApiResponseDTO {
	private String base;
	private LocalDate date;
	private Map<String, BigDecimal> rates;
}
