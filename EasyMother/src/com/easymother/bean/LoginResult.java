package com.easymother.bean;

public class LoginResult {

	private String appToken;

	private UserInfos userInfo;

	private int expiredIn;

	public void setAppToken(String appToken){
	this.appToken = appToken;
	}
	public String getAppToken(){
	return this.appToken;
	}
	public void setUserInfo(UserInfos userInfo){
	this.userInfo = userInfo;
	}
	public UserInfos getUserInfo(){
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
