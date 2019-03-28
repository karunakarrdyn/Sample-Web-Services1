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
import com.karunakar.sms.service.InboundSMSProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SMSApplication.class)
//@TestPropertySource(locations={"classpath:application.properties"})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InBoundSMSProcessorTest {
	
	@Autowired
	InboundSMSProcessor inboundSMSProcessor;
	
	String from="4924195509198";
	String to="4924195509196";
	
	/*
	 * Here,we have assumed that input is valid.
	 */
	@Test
	public void validInboundSMSRequest() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response res=inboundSMSProcessor.processSMS(input);
		assertNotNull(res);
    }
	
	

}
