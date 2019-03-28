package com.karunakar.sms.input.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.karunakar.sms.domain.PhoneNumber;
import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.repository.MyPhoneNumberRepository;
import com.karunakar.sms.repository.UserAccountRepository;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.ValidationException;
import com.karunakar.sms.security.CustomUserDetailsService.CustomUserDetails;
import com.karunakar.sms.valueobjects.Account;

@Component
public class InboundValidator {
	
	@Autowired 
	private MyPhoneNumberRepository myPhoneRepository;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	InputValidator inputValidator;
	/*
	 * it calls the inputvalidation service. 
	 * if there are no errors in input. 
	 *     then,it will check the to phonenumber is assigned to authenticated user. if not throws ValidationException.
	 * else Throws validationException.
	 */
	public Response doInputValidation(SMSInput input) {
		String message="";
		String error="";
		Response resp=inputValidator.doInputValidation(input);
		if(resp!=null)
			return resp;
		Account sessionUser=getUserAccount();
		com.karunakar.sms.domain.Account user = userAccountRepository.findOne(sessionUser.getUserId());
		PhoneNumber to = myPhoneRepository.findOneByAccountIdAndNumber(user, input.getTo());
		if(to==null) {
			error+="to parameter not found";
		}
		
		if(error.length()>0) {
			resp = new Response(message,error);
			throw new ValidationException(resp);
		}
		
		return null;
	}
	
	/*
	 * retruns the Authenticated user account details.
	 */
	public Account getUserAccount() {
		Account account = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth !=null && auth.getPrincipal() instanceof CustomUserDetails) {
			account =  ((CustomUserDetails)auth.getPrincipal()).getAccount();
		} 
		return account;
	}

}
