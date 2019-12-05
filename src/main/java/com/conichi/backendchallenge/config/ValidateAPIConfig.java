package com.conichi.backendchallenge.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;

/**
 * Configuration class for Cloudmersive Validate API.
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
@org.springframework.context.annotation.Configuration
public class ValidateAPIConfig {
	
	@Value("${validate.api.key}")
	private String apiKey;
	
	@PostConstruct
	public void configure() {
		ApiClient defaultClient = Configuration.getDefaultApiClient();

		ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
		Apikey.setApiKey(apiKey);
	}

}
