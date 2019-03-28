package com.karunakar.sms.test.unit;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.karunakar.sms.SMSApplication;
import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.LimitReachedException;
import com.karunakar.sms.response.exception.UserBlockedException;
import com.karunakar.sms.service.InboundSMSProcessor;
import com.karunakar.sms.service.OutboundSMSProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SMSApplication.class)
//@TestPropertySource(locations={"classpath:application.properties"})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutBoundSMSProcessorTest {
	@Autowired
	InboundSMSProcessor inboundSMSProcessor;
	
	@Autowired
	OutboundSMSProcessor outboundSMSProcessor;
	
	String from="4924195509198";
	String to="4924195509196";
	
	@Test
	public void avalidOutBoundSMSRequest() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response res=outboundSMSProcessor.processSMS(input);
		assertNotNull(res);
    }
	
	@Test
	public void bvalidOutBoundSMSRequest() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response res=outboundSMSProcessor.processSMS(input);
		assertNotNull(res);
    }
	
	/*
	 * i have set max limit to 2 in 10 secs for testing in application.properties file
	 */
	@Test(expected = LimitReachedException.class)
	public void climitReachedExceptionTest() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		outboundSMSProcessor.processSMS(input);
    }
	
	
	/*
	 * block time set to 5 secs in appliaction.properties
	 * make STOP SMS first to test userblockedexception
	 */
	@Test(expected = UserBlockedException.class)
	public void duserBlockedExceptionTest() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("STOP");
		inboundSMSProcessor.processSMS(input);
		outboundSMSProcessor.processSMS(input);
    }


}
