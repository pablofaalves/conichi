package com.conichi.backendchallenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Configuration class for properties files.
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
@Configuration
@PropertySources({
		@PropertySource("classpath:exchangeRatesApi.properties"),
		@PropertySource("classpath:validateApi.properties")
})
public class PropertiesConfig {

}
