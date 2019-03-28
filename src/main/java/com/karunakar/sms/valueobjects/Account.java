package com.karunakar.sms.valueobjects;

import java.io.Serializable;

public class Account implements Serializable {
	private long userId;
	private String userName;
	
	public Account() {}
	public long getUserId() {
		return userId;
	}
	public Account setUserId(long userId) {
		this.userId = userId;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public Account setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	@Override
	public String toString() {
		return "Account [userId=" + userId + ", userName=" + userName + "]";
	}
	
	
	
}
