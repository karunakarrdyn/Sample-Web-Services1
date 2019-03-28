package com.karunakar.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karunakar.sms.domain.Account;
import com.karunakar.sms.domain.PhoneNumber;


@Repository
public interface MyPhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
	
	public PhoneNumber findOneByAccountIdAndNumber(Account accountId,String number);

}
