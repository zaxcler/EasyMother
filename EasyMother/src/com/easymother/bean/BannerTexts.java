package com.easymother.bean;

public class BannerTexts {
	
	private String urlType;

	private String urlValue;
	
	private String id;

	private String title;

	private String logo;

	private String content;

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getUrlValue() {
		return urlValue;
	}

	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

}
