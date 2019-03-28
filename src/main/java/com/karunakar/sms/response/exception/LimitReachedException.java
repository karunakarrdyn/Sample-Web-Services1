package com.karunakar.sms.response.exception;

import org.springframework.http.HttpStatus;

import com.karunakar.sms.response.Response;

public class LimitReachedException extends SMSException {
	
	public LimitReachedException(Response response, HttpStatus statusCode){
		super(response,statusCode);
	}
	public LimitReachedException(Response response){
		super(response,HttpStatus.TOO_MANY_REQUESTS);
	}
}
