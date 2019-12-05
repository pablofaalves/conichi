package com.conichi.backendchallenge.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest Controller for the /api/datetime API.
 * 
 * @author Pablo Alves
 * @since 02/12/2019
 */
@RequestMapping("/api/datetime")
@RestController
public class DateTimeController {

	@ApiOperation(value = "Get current time with time zone")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully return time"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	@GetMapping(value = "/currenttime", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getCurrentTime() {
		return Collections.singletonMap("currentTime", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_TIME));
	}
}
