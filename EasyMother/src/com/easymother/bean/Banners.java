package com.easymother.bean;

public class Banners {
	
	private String urlType;

	private String urlValue;

	private String title;

	private String logo;

	private String images;

	private String content;

	public void setUrlType(String urlType){
	this.urlType = urlType;
	}
	public String getUrlType(){
	return this.urlType;
	}
	public void setUrlValue(String urlValue){
	this.urlValue = urlValue;
	}
	public String getUrlValue(){
	return this.urlValue;
	}
	public void setTitle(String title){
	this.title = title;
	}
	public String getTitle(){
	return this.title;
	}
	public void setLogo(String logo){
	this.logo = logo;
	}
	public String getLogo(){
	return this.logo;
	}
	public void setImages(String images){
	this.images = images;
	}
	public String getImages(){
	return this.images;
	}
	public void setContent(String content){
	this.content = content;
	}
	public String getContent(){
	return this.content;
	}
	@Override
	public String toString() {
		return "Banners [urlType=" + urlType + ", urlValue=" + urlValue + ", title=" + title + ", logo=" + logo
				+ ", images=" + images + ", content=" + content + "]";
	}
	
}
