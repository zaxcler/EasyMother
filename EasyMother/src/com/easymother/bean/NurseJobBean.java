package com.easymother.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class NurseJobBean implements Serializable{
private Integer id;
    
    private String jobNumber;
    
    private Integer nurseId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String more1;

    private String more2;

    private String more3;

    private Integer int1;

    private Integer int2;

    private Integer sorting;

    private Integer level;

    private Integer levelScore;

    private Double price;

    private String workImages;
    
    private String photos;

    private String referee;

    private Date employmentDate;

    private Integer seniority;

    private String marketPrice;

    private String resume;

    private String certificateImages;

    private Byte isFreeze;

    private String skills;
    
    private String selfIntroduction;

    private String topStatus;

    private String topImage;

    private String nurseName;

    private String persionProfile;

    private String showPrice;
    
    private String job;

    private String jobTitle;
	
	private String[] workImageArrays;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Integer getNurseId() {
		return nurseId;
	}

	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevelScore() {
		return levelScore;
	}

	public void setLevelScore(Integer levelScore) {
		this.levelScore = levelScore;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getWorkImages() {
		return workImages;
	}

	public void setWorkImages(String workImages) {
		this.workImages = workImages;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public Date getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(Date employmentDate) {
		this.employmentDate = employmentDate;
	}

	public Integer getSeniority() {
		return seniority;
	}

	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getCertificateImages() {
		return certificateImages;
	}

	public void setCertificateImages(String certificateImages) {
		this.certificateImages = certificateImages;
	}

	public Byte getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Byte isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getSelfIntroduction() {
		return selfIntroduction;
	}

	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}

	public String getTopStatus() {
		return topStatus;
	}

	public void setTopStatus(String topStatus) {
		this.topStatus = topStatus;
	}

	public String getTopImage() {
		return topImage;
	}

	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public String getPersionProfile() {
		return persionProfile;
	}

	public void setPersionProfile(String persionProfile) {
		this.persionProfile = persionProfile;
	}

	public String getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String[] getWorkImageArrays() {
		return workImageArrays;
	}

	public void setWorkImageArrays(String[] workImageArrays) {
		this.workImageArrays = workImageArrays;
	}

	@Override
	public String toString() {
		return "NurseJobBean [id=" + id + ", jobNumber=" + jobNumber + ", nurseId=" + nurseId + ", createUser="
				+ createUser + ", createTime=" + createTime + ", updateUser=" + updateUser + ", updateTime="
				+ updateTime + ", more1=" + more1 + ", more2=" + more2 + ", more3=" + more3 + ", int1=" + int1
				+ ", int2=" + int2 + ", sorting=" + sorting + ", level=" + level + ", levelScore=" + levelScore
				+ ", price=" + price + ", workImages=" + workImages + ", photos=" + photos + ", referee=" + referee
				+ ", employmentDate=" + employmentDate + ", seniority=" + seniority + ", marketPrice=" + marketPrice
				+ ", resume=" + resume + ", certificateImages=" + certificateImages + ", isFreeze=" + isFreeze
				+ ", skills=" + skills + ", selfIntroduction=" + selfIntroduction + ", topStatus=" + topStatus
				+ ", topImage=" + topImage + ", nurseName=" + nurseName + ", persionProfile=" + persionProfile
				+ ", showPrice=" + showPrice + ", job=" + job + ", jobTitle=" + jobTitle + ", workImageArrays="
				+ Arrays.toString(workImageArrays) + "]";
	}
	
	
}
