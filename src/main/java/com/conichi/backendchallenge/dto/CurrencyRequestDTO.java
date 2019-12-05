package com.conichi.backendchallenge.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the parameters request of the /api/currency/convert GET method
 * 
 * @author Pablo Alves
 * @since 03/12/2019
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRequestDTO {
	
	@ApiParam("The amount of money to be converted. Ex.: 1234.98")
	@NotNull(message = "'amount' must be not null")
	@Positive(message = "'amount' must be higher than zero")
	private BigDecimal amount;
	
	@ApiParam("The source currency in 3 letter. Ex.: USD")
	@NotBlank(message = "'sourceCurrency' must be not blank")
	@Size(min = 3, max = 3, message = "'sourceCurrency' min/max of 3 characters allowed")
	private String sourceCurrency;
	
	@ApiParam("The source currency in 3 letter. Ex.: EUR")
	@NotBlank(message = "'targetCurrency' must be not blank")
	@Size(min = 3, max = 3, message = "'targetCurrency' min/max of 3 characters allowed")
	private String targetCurrency;
}
