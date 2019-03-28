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

import redis.clients.jedis.Jedis;

@IntegrationTest("server.port:0")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SMSApplication.class)
//@TestPropertySource(locations={"classpath:application.properties"})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutboundSMSAPITest {
	
	@Autowired
	RestTemplateUtil templateUtil;
	
	@Value("${local.server.port}")
	int port;
	
	String baseURL="http://localhost:";
	
	String userName="plivo1";
	String pwd="20S0KPNOIM";
	String from="4924195509198";
	String to="4924195509196";
	
	/*
	 * when there no block request and limit threshold has not reached case.
	 */
	@Test
	public void atestOutboundSMSAPI() {
		
		clearRedisSTOPAndLimitCache(from,to);
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response resp;
		resp=restTemplate.postForObject(baseURL+port+"/outbound/sms", input, Response.class);
		assertNotNull(resp);
	}
	
	/*
	 * when the sms from is blocked.
	 */
	@Test(expected= HttpClientErrorException.class)
	public void btestOutboundSMSAPI() {
		
		clearRedisSTOPAndLimitCache(from,to);
		stopSMSRequestAPI();
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		restTemplate.postForObject(baseURL+port+"/outbound/sms", input, Response.class);
	}
	
	/*
	 * when limit threshold has reached.
	 */
	@Test(expected= HttpClientErrorException.class)
	public void ctestOutboundSMSAPI() {
		
		clearRedisSTOPAndLimitCache(from,to);
		outBoundSMSRequestAPI();
		outBoundSMSRequestAPI();
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		restTemplate.postForObject(baseURL+port+"/outbound/sms", input, Response.class);
	}
	
	private void clearRedisSTOPAndLimitCache(String from,String to) {
		Jedis redis=new Jedis();
		redis.expire(from+"#"+to, 0);
		redis.save();
		redis.expire(from, 0);
		redis.save();
		redis.close();
	}
	
	
	public void stopSMSRequestAPI() {
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("STOP");
		restTemplate.postForObject(baseURL+port+"/inbound/sms", input, Response.class);
	}
	
	public void outBoundSMSRequestAPI() {
		RestTemplate restTemplate = templateUtil.getUserRestTemplate(userName,pwd);
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		restTemplate.postForObject(baseURL+port+"/outbound/sms", input, Response.class);
	}

}
