package com.easymother.bean;

public class LoginResult {

	private String appToken;

	private UserInfo userInfo;

	private int expiredIn;

	public void setAppToken(String appToken){
	this.appToken = appToken;
	}
	public String getAppToken(){
	return this.appToken;
	}
	public void setUserInfo(UserInfo userInfo){
	this.userInfo = userInfo;
	}
	public UserInfo getUserInfo(){
	return this.userInfo;
	}
	public void setExpiredIn(int expiredIn){
	this.expiredIn = expiredIn;
	}
	public int getExpiredIn(){
	return this.expiredIn;
	}
	@Override
	public String toString() {
		return "LoginResult [appToken=" + appToken + ", userInfo=" + userInfo + ", expiredIn=" + expiredIn + "]";
	}
	
}
