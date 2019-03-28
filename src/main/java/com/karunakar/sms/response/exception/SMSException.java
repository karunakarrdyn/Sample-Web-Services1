package com.karunakar.sms.response.exception;

import org.springframework.http.HttpStatus;

import com.karunakar.sms.response.Response;

public class SMSException extends RuntimeException {
	
	private Response response;
	
	private HttpStatus statusCode;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public SMSException(Response response, HttpStatus statusCode) {
		super();
		this.response = response;
		this.statusCode = statusCode;
	}
	
	public SMSException() {
		
	}

}
