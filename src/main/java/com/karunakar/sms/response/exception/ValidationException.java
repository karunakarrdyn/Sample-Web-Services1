package com.karunakar.sms.response.exception;

import org.springframework.http.HttpStatus;

import com.karunakar.sms.response.Response;

public class ValidationException extends SMSException {
	
	public ValidationException(Response response, HttpStatus statusCode) {
		super(response,statusCode);
	}
	public ValidationException(Response response) {
		super(response,HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
