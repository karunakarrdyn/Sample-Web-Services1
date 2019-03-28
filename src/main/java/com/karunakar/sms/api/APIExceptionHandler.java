package com.karunakar.sms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.LimitReachedException;
import com.karunakar.sms.response.exception.SMSException;
import com.karunakar.sms.response.exception.UserBlockedException;
import com.karunakar.sms.response.exception.ValidationException;

@ControllerAdvice
public class APIExceptionHandler {
	
	
	@ExceptionHandler({ ValidationException.class,UserBlockedException.class,LimitReachedException.class})
	@ResponseBody
	public ResponseEntity<Response> handleApplicationExceptions(SMSException e) {
		return new ResponseEntity<Response>(e.getResponse(),e.getStatusCode());
	}
	
	@ExceptionHandler({ RuntimeException.class})
	@ResponseBody
	public ResponseEntity<Response> unexpectedException(RuntimeException e) {
		Response em= new Response();
		em.setMessage("");
		em.setError("unknown failure");
		return new ResponseEntity<Response>(em,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
