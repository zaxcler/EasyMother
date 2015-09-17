package com.easymother.bean;

public class VersionBean {
	private String version;

	// 0,不更新 1，可以更新 2，强制更新
	private String updateStatus;
	
	//
	private String updateMsg;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getUpdateMsg() {
		return updateMsg;
	}

	public void setUpdateMsg(String updateMsg) {
		this.updateMsg = updateMsg;
	}
	
	
}
