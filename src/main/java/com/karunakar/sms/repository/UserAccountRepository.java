package com.karunakar.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karunakar.sms.domain.Account;

@Repository
public interface UserAccountRepository extends JpaRepository<Account, Long> {

	public Account findOneByUserNameAndAuthId(String username,String authId);
	
	public Account findOneByUserName(String username);
}
