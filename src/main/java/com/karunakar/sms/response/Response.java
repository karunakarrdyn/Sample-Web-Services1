package com.karunakar.sms.response;

public class Response {
	
	private String message;
	
	private String error;
	
	public Response() {
		// TODO Auto-generated constructor stub
	}

	public Response(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	

}
