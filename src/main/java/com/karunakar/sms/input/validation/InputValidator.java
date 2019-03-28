package com.karunakar.sms.input.validation;

import org.springframework.stereotype.Component;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.ValidationException;

@Component
public class InputValidator {
	
	/*
	 * validates the request body params and throws ValidationException if any validation fails and sets appropriate error
	 * message.
	 */
	public Response doInputValidation(SMSInput input) {
		String message="";
		String error="";
		if(input.getFrom()==null) {
			error+="from is missing";
		}else if(input.getFrom().length()<6 || input.getFrom().length()>16) {
			error+="from is invalid";
		}
		
		if(error.length()>0) {
			Response resp = new Response(message,error);
			throw new ValidationException(resp);
		}
		
		if(input.getTo()==null) {
			error+="to is missing";
		}else if(input.getTo().length()<6 || input.getTo().length()>16) {
			error+="to is invalid";
		}
		
		if(error.length()>0) {
			Response resp = new Response(message,error);
			throw new ValidationException(resp);
		}
		
		if(input.getText()==null) {
			error+="text is missing";
		}else if(input.getText().length()<1 || input.getText().length()>120) {
			error+="text is invalid";
		}
		
		if(error.length()>0) {
			Response resp = new Response(message,error);
			throw new ValidationException(resp);
		}
		return null;
	}
}
