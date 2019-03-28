package com.karunakar.sms.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.karunakar.sms.repository.UserAccountRepository;
import com.karunakar.sms.valueobjects.Account;

@Service
public class CustomUserDetailsService implements UserDetailsService, Serializable {
	private Logger shortError = LoggerFactory.getLogger("app-errors-short-async");
	private Logger appErrorLog = LoggerFactory.getLogger("app-errors-async");
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private transient UserAccountRepository userRepo;

	public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
		
		public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities, Account account) {
			super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			this.account = account;
		}

		private static final long serialVersionUID = 1L;
		private Account account;

		public Account getAccount() {
			return account;
		}

		public void setAccount(Account user) {
			this.account = user;
		}

		@Override
		public String toString() {
			return "CustomUserDetails [account=" + account + "]";
		}
		
		
	}
	
	
	private void addGrantedAuthority(List<GrantedAuthority> authorities, final String role) {
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		});
	}
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		com.karunakar.sms.domain.Account user=null;
		try {
			
			user=userRepo.findOneByUserName(arg0);
			
		} catch(Throwable t) {
			System.out.println("hi");
			shortError.error("loadUserByUsername:error while finding user by name."+t.getMessage());
			appErrorLog.error("loadUserByUsername",t);
			t.printStackTrace();
			throw t;
		}
		if(user == null){
			throw new UsernameNotFoundException(arg0);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		Account account = new Account();
		account.setUserId(user.getId())
				.setUserName(user.getUserName());
		CustomUserDetails customUserDetails = 
				new CustomUserDetails(user.getUserName(), 
						user.getAuthId(), true, true, true, true, authorities, account);
		return customUserDetails;
	}


}



