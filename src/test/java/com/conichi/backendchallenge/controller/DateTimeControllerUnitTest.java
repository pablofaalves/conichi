package com.conichi.backendchallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.conichi.backendchallenge.test.AbstractMvcTest;

@WebMvcTest(DateTimeController.class)
class DateTimeControllerUnitTest extends AbstractMvcTest {
	
	@Test
	void getCurrentTime() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/datetime/currenttime"))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
		Map<String, String> returnMap = super.convertResultContent(result, Map.class);
		
		String mapKey = "currentTime";
		assertNotNull(returnMap);
		assertEquals(1, returnMap.keySet().size());
		assertTrue(returnMap.containsKey(mapKey));
		assertNotNull(returnMap.get(mapKey));
		assertFalse(StringUtils.isEmpty(returnMap.get(mapKey)));
	}

}
