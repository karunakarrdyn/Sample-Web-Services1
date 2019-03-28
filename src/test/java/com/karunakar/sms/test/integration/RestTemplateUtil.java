package com.karunakar.sms.test.integration;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {
	
	public RestTemplate getUserRestTemplate(String username, String password) {
		RestTemplate restTemplate = new BasicAuthRestTemplate(username, password);
		return configureDefaultRestTemplate(restTemplate);
	}

	private RestTemplate configureDefaultRestTemplate(RestTemplate restTemplate) {
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		return restTemplate;
	}

}
