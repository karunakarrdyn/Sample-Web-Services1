package com.karunakar.sms.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.karunakar.sms.security.CustomUserDetailsService.CustomUserDetails;
import com.karunakar.sms.valueobjects.Account;

public class AccountArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return Account.class.isAssignableFrom(parameter.getParameterType());
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		Authentication auth = (Authentication) webRequest.getUserPrincipal();
		if(auth !=null && auth.getPrincipal() instanceof CustomUserDetails) {
			return ((CustomUserDetails)auth.getPrincipal()).getAccount();
		}
		return null;
	}

}