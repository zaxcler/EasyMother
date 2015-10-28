package com.easymother.bean;

import java.util.Date;

public class TopicItemBean {
	private int id;
	
	private String updateTime;
	private String showTime;
	private String type;
	private String images;
	private Integer userId;
	private String userNickname;
	private String userImage;
	private String content;
	private String job;
	private Integer collectionAmount;
	private Integer msgAmount;
	private Integer upAmount;
	private Integer nurseId;
	private int int2;//收藏次数
	private String more1;//收藏状态
	private String more2;//点赞次数
	private String more3;//点赞状态
	private Date createTime;
	private String realName;
	
	
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public Integer getNurseId() {
		return nurseId;
	}
	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getInt2() {
		return int2;
	}
	public void setInt2(int int2) {
		this.int2 = int2;
	}
	public String getMore1() {
		return more1;
	}
	public void setMore1(String more1) {
		this.more1 = more1;
	}
	public String getMore2() {
		return more2;
	}
	public void setMore2(String more2) {
		this.more2 = more2;
	}
	public String getMore3() {
		return more3;
	}
	public void setMore3(String more3) {
		this.more3 = more3;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(Integer collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	public Integer getMsgAmount() {
		return msgAmount;
	}
	public void setMsgAmount(Integer msgAmount) {
		this.msgAmount = msgAmount;
	}
	public Integer getUpAmount() {
		return upAmount;
	}
	public void setUpAmount(Integer upAmount) {
		this.upAmount = upAmount;
	}
	
	

}
