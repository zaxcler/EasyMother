package com.easymother.bean;

import java.util.Date;

import org.json.JSONObject;

public class Order {
	private Integer id;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String more1;

    private String more2;

    private String more3;
    
    private String orderCode;

    private Integer int1;

    private Integer int2;

    private Integer sorting;

    private Integer userId;
    
    private String userName;
    
    private String userMobile;
    
    private String userAddress;
    
    private Integer nurseId;

    private String nurseName;

    private String job;

    private Double price;

    private Integer payTypeDay;

    private Date hireStartTime;

    private Date hireEndTime;

    private String subject;

    private String description;

    private String status;

    private Integer commentTimes;

    private Date realHireStartTime;

    private Date realHireEndTime;

    private Double realAllAmount;

    private Byte userDelete;

    private Date expireTime;

    private Integer nurseJobId;

    private Byte isSee;

    private Date seeTime;

    private Double allServerPrice;

    private Integer deposit;

    private Double payMoney;

    private Date payTime;
    
    private JSONObject jsonObject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getInt1() {
		return int1;
	}

	public void setInt1(Integer int1) {
		this.int1 = int1;
	}

	public Integer getInt2() {
		return int2;
	}

	public void setInt2(Integer int2) {
		this.int2 = int2;
	}

	public Integer getSorting() {
		return sorting;
	}

	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Integer getNurseId() {
		return nurseId;
	}

	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPayTypeDay() {
		return payTypeDay;
	}

	public void setPayTypeDay(Integer payTypeDay) {
		this.payTypeDay = payTypeDay;
	}

	public Date getHireStartTime() {
		return hireStartTime;
	}

	public void setHireStartTime(Date hireStartTime) {
		this.hireStartTime = hireStartTime;
	}

	public Date getHireEndTime() {
		return hireEndTime;
	}

	public void setHireEndTime(Date hireEndTime) {
		this.hireEndTime = hireEndTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCommentTimes() {
		return commentTimes;
	}

	public void setCommentTimes(Integer commentTimes) {
		this.commentTimes = commentTimes;
	}

	public Date getRealHireStartTime() {
		return realHireStartTime;
	}

	public void setRealHireStartTime(Date realHireStartTime) {
		this.realHireStartTime = realHireStartTime;
	}

	public Date getRealHireEndTime() {
		return realHireEndTime;
	}

	public void setRealHireEndTime(Date realHireEndTime) {
		this.realHireEndTime = realHireEndTime;
	}

	public Double getRealAllAmount() {
		return realAllAmount;
	}

	public void setRealAllAmount(Double realAllAmount) {
		this.realAllAmount = realAllAmount;
	}

	public Byte getUserDelete() {
		return userDelete;
	}

	public void setUserDelete(Byte userDelete) {
		this.userDelete = userDelete;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getNurseJobId() {
		return nurseJobId;
	}

	public void setNurseJobId(Integer nurseJobId) {
		this.nurseJobId = nurseJobId;
	}

	public Byte getIsSee() {
		return isSee;
	}

	public void setIsSee(Byte isSee) {
		this.isSee = isSee;
	}

	public Date getSeeTime() {
		return seeTime;
	}

	public void setSeeTime(Date seeTime) {
		this.seeTime = seeTime;
	}

	public Double getAllServerPrice() {
		return allServerPrice;
	}

	public void setAllServerPrice(Double allServerPrice) {
		this.allServerPrice = allServerPrice;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
    
    

}
