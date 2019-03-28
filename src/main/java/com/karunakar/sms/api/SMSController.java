package com.karunakar.sms.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.input.validation.InboundValidator;
import com.karunakar.sms.input.validation.OutboundValidator;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.service.InboundSMSProcessor;
import com.karunakar.sms.service.OutboundSMSProcessor;

@RestController
@RequestMapping("/")
public class SMSController {
	
	@Autowired
	InboundValidator inboundValidator;
	
	@Autowired
	OutboundValidator outboundValidator;
	
	@Autowired
	InboundSMSProcessor inboundSMSProcessor;
	
	@Autowired
	OutboundSMSProcessor outboundSMSProcessor;
	
	 @RequestMapping(value="inbound/sms",method = RequestMethod.POST)
	 public Response inbound(@RequestBody SMSInput input) {
		 System.out.println("received call at inbound sms");
		 Response resp=inboundValidator.doInputValidation(input);
		 if(resp==null) {
			 resp=inboundSMSProcessor.processSMS(input);
		 }
		return resp;
	 }
	 
	 @RequestMapping(value="outbound/sms", method = RequestMethod.POST)
	 public Response outbound(@RequestBody SMSInput input) {
		 System.out.println("received call at outbound sms");
		 Response resp=outboundValidator.doInputValidation(input);
		 if(resp==null) {
			 resp=outboundSMSProcessor.processSMS(input);
		 }
		return resp;
	 }

}
