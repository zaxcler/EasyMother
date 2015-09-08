package com.easymother.bean;

public class Letter {
	private String content;

	private int id;

	private String createTime;

	private String nurseJobId;

	private String title;

	private String updateTime;

	private String images;

	private String job;

	private int nurseId;

	public void setContent(String content){
	this.content = content;
	}
	public String getContent(){
	return this.content;
	}
	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	public void setCreateTime(String createTime){
	this.createTime = createTime;
	}
	public String getCreateTime(){
	return this.createTime;
	}
	public void setNurseJobId(String nurseJobId){
	this.nurseJobId = nurseJobId;
	}
	public String getNurseJobId(){
	return this.nurseJobId;
	}
	public void setTitle(String title){
	this.title = title;
	}
	public String getTitle(){
	return this.title;
	}
	public void setUpdateTime(String updateTime){
	this.updateTime = updateTime;
	}
	public String getUpdateTime(){
	return this.updateTime;
	}
	public void setImages(String images){
	this.images = images;
	}
	public String getImages(){
	return this.images;
	}
	public void setJob(String job){
	this.job = job;
	}
	public String getJob(){
	return this.job;
	}
	public void setNurseId(int nurseId){
	this.nurseId = nurseId;
	}
	public int getNurseId(){
	return this.nurseId;
	}
}
