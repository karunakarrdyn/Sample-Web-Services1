package com.karunakar.sms.response.exception;

import org.springframework.http.HttpStatus;

import com.karunakar.sms.response.Response;

public class UserBlockedException extends SMSException {
	
	public UserBlockedException(Response response, HttpStatus statusCode) {
		super(response,statusCode);
	}
	
	public UserBlockedException(Response response) {
		super(response,HttpStatus.LOCKED);
	}

}
