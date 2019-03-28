package com.karunakar.sms.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.karunakar.sms.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(customUserDetailsService);
	}
	
	private static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
	
	

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		 {
			AuthenticationEntryPoint entryPoint = new CustomBasicAuthenticationEntryPoint();
			httpSecurity
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated()
				.and()
			.httpBasic()
				.authenticationEntryPoint(entryPoint)
				.and()
			.logout().disable()
			.csrf().disable()
			.headers().xssProtection().disable();
			
			httpSecurity
			.headers().frameOptions().disable();
		
			// disable page caching
			httpSecurity.headers().cacheControl();

			httpSecurity.sessionManagement()
		    .maximumSessions(10)
	        .sessionRegistry(getSessionRegistry());
		
		}
	}
	
	  @Bean
	  public SessionRegistry getSessionRegistry() {
	     return new SessionRegistryImpl();
	  }
	
}



@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class MethodSecurityConfig {

}




class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public CustomBasicAuthenticationEntryPoint() {
        this.setRealmName("SMS Application");
    }
    
    public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
    	response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
    	try {
    		response.setHeader("WWW-Authenticate", "realm=\"" + getRealmName() + "\"");
    	} catch (Throwable t) {
    		//do nothing
    	}
		response.sendError(HttpServletResponse.SC_FORBIDDEN,
				authException.getMessage());
	}

}