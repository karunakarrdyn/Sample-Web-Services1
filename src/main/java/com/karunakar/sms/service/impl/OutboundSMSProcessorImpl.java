package com.karunakar.sms.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.LimitReachedException;
import com.karunakar.sms.response.exception.UserBlockedException;
import com.karunakar.sms.response.exception.ValidationException;
import com.karunakar.sms.service.OutboundSMSProcessor;

import redis.clients.jedis.Jedis;

@Service
public class OutboundSMSProcessorImpl implements OutboundSMSProcessor {
	@Value("${karunakar.sms.block.time}")
	private Long blockTime;
	@Value("${karunakar.sms.limit.window.time}")
	private Integer limitTime;
	@Value("${karunakar.sms.limit.count}")
	private Integer limitCount;
    
	/*
	 * Process the outbound SMS Request after successful input validation.
	 *      case 1:if the from phonenumber is blocked by to phonenumber then this would throw a UserBlockedException.
	 *      case 2:if the configured window limit has reached for from phonenumber then this would throw a LimitReachedException.
	 *      case 3:if case 1 and 2 are passed. then it will update the from count in redis and return "outbound sms ok" message
	 *      in response.
	 * (non-Javadoc)
	 * @see com.karunakar.sms.service.OutboundSMSProcessor#processSMS(com.karunakar.sms.input.SMSInput)
	 */
	@Override
	public Response processSMS(SMSInput input) {
		Response resp=new Response();
		String error="";
		String message="";
		Boolean blocked=doBlockCheck(input);
		if(blocked) {
			error="sms from "+input.getFrom()+" to "+input.getTo()+" blocked by STOP request";
			resp.setError(error);
			resp.setMessage(message);
			throw new UserBlockedException(resp);
		}
		
		Boolean limitReached=doLimitCheck(input);
		if(limitReached) {
			error="limit reached for from "+input.getFrom();
			resp.setError(error);
			resp.setMessage(message);
			throw new LimitReachedException(resp);
		}
		incrSMSCount(input);
		message="outbound sms ok";
		resp.setError(error);
		resp.setMessage(message);
		return resp;
	}

	/*
	 * increment the sms count against from phonenumber in redis.
	 */
	private void incrSMSCount(SMSInput input) {
		Integer seconds=limitTime;
		String key=input.getFrom();
		String[] keys= {key};
		Jedis redis=new Jedis();
		if(redis.get(key)==null) {
			redis.set(key, "1");
			redis.expire(key, seconds);
		}else {
			redis.watch(keys);
			redis.incr(key);
		}
		
		redis.save();
		redis.unwatch();
		redis.close();
	}

	/*
	 * checks whether limit has reached or not.
	 */
	private Boolean doLimitCheck(SMSInput input) {
		Jedis redis=new Jedis();
		String count=redis.get(input.getFrom());
		if(count!=null && count.length()>0) {
			if(Integer.parseInt(count)>limitCount-1) {
				redis.close();
				return true;
			}
		}
		redis.close();
		return false;
	}

	/*
	 * checks whether from is blocked by to phonenumber
	 */
	private Boolean doBlockCheck(SMSInput input) {
		String key=input.getFrom()+"#"+input.getTo();
		Jedis redis=new Jedis();
		String val=redis.get(key);
		if(val!=null) {
			redis.close();
			return true;
		}	
		redis.close();
	    return false;
	}

}
