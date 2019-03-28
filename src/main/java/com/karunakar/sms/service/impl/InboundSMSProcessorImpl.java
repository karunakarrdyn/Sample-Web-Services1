package com.karunakar.sms.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.service.InboundSMSProcessor;

import redis.clients.jedis.Jedis;

@Service
public class InboundSMSProcessorImpl implements InboundSMSProcessor {
	
	@Value("${karunakar.sms.block.time}")
	private Long blockTime;
	
	/*
	 * Process the inbound SMS Request after successful input validation.
	 * 		if the text is STOP or STOP\n or STOP\r or STOP\r\n then blocks the from phone number
	 * sending sms to phone number for a configured number of seconds.
	 * return "inbound sms ok" message.
	 * @see com.karunakar.sms.service.InboundSMSProcessor#processSMS(com.karunakar.sms.input.SMSInput)
	 */
	@Override
	public Response processSMS(SMSInput input) {
		Response resp=new Response();
		if(input.getText().equals("STOP") || input.getText().equals("STOP\n") || 
				input.getText().equals("STOP\r") || input.getText().equals("STOP\r\n")) {
			cacheSTOPSMS(input.getFrom()+"#"+input.getTo(),"",blockTime);
		}
		resp.setMessage("inbound sms ok");
		resp.setError("");
		return resp;
	}
	
	/*
	 * adds block request in redis cache.
	 */
	void cacheSTOPSMS(String key,String value,Long ttl){
		Jedis redis=new Jedis();
		redis.set(key, value);
		redis.expire(key, ttl.intValue());
		redis.save();
		redis.close();
	}

}
