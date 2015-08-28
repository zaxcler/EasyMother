package com.easymother.bean;

public class Root {
private Object result;

private String message;

private String code;

private boolean isSuccess;

public void setResult(Object result){
this.result = result;
}
public Object getResult(){
return this.result;
}
public void setIsSuccess(boolean isSuccess){
this.isSuccess = isSuccess;
}
public boolean getIsSuccess(){
return this.isSuccess;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
}

}
