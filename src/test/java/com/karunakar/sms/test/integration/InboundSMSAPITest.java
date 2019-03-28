package com.karunakar.sms.test.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.karunakar.sms.SMSApplication;
import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;

@IntegrationTest("server.port:0")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SMSApplication.class)
//@TestPropertySource(locations={"classpath:application.properties"})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InboundSMSAPITest {
	
	@Autowired
	RestTemplateUtil templateUtil;
	
	@Value("${local.server.port}")
	int port;
	
	String baseURL="http://localhost:";
	
	String userName="plivo1";
	String pwd="20S0KPNOIM";
	String from="4924195509198";
	String to="4924195509196";
	
	@Test
	public void testInboundSMSAPI() {
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response resp;
		resp=restTemplate.postForObject(baseURL+port+"/inbound/sms", input, Response.class);
		assertNotNull(resp);
	}
	
	/*
	 * authinticated user does not have to phonenumber
	 */
	@Test(expected= HttpClientErrorException.class)
	public void testInboundSMSInValidToAPI() {
		RestTemplate restTemplate = templateUtil.getUserRestTemplate("test1","test");
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo("4926412");
		input.setText("hello,...");
		restTemplate.postForObject(baseURL+port+"/inbound/sms", input, Response.class);
	}
}
