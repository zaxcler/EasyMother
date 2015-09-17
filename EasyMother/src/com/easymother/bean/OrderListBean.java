package com.easymother.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class OrderListBean  extends Order implements Serializable{
	
	private Integer nurseId;

	private String realName;

	private Integer seniority;// 工龄

	private Integer age;

	private Date birthday;

	private String hometown;

	private String image;

	private String jobTitle;

	private String currentAddress;

	private Double price;

	private String marketPrice;
	
	private Integer nums;
	
	private String title;
	
	private String mobile;
	
	private String content;
	
	private  String  job;
	
	private Date employmentStartTime;

    private Date employmentEndTime;
    
    private Integer orderId;//订单ID
    
    private String status;//订单状态

	public Integer getNurseId() {
		return nurseId;
	}

	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSeniority() {
		return seniority;
	}

	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Date getEmploymentStartTime() {
		return employmentStartTime;
	}

	public void setEmploymentStartTime(Date employmentStartTime) {
		this.employmentStartTime = employmentStartTime;
	}

	public Date getEmploymentEndTime() {
		return employmentEndTime;
	}

	public void setEmploymentEndTime(Date employmentEndTime) {
		this.employmentEndTime = employmentEndTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
	// 计算年龄
		public Integer Age(Date birthday) {
			Calendar calendar = Calendar.getInstance();
			Integer yearNow = calendar.get(Calendar.YEAR);
			calendar.setTime(birthday);
			Integer yearBir = calendar.get(Calendar.YEAR);
			return yearNow - yearBir;
		}
    

}
