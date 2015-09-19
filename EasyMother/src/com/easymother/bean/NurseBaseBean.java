package com.easymother.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NurseBaseBean implements Serializable{
	private Integer id;

	private Integer nurseId;

	private  String  job;
	
	private Integer age;
	
	private Integer  seniority;

	private String showPrice;
	
	private Date employmentStartTime;
	
	private Date employmentEndTime;
	
    private String marketPrice;
    
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
    
    private Integer price;

    private String account;

    private String password;

    private String realName;

    private String mobile;

    private Integer level;

    private String identificationCode;
    
    private Integer collectionId;

    private String image;

    private Date birthday;

    private String hometown;

    private String currentAddress;

    private String proficiency;

    private String education;
    
    private String  persionCharacter;

    private Double height;

    private String constellation;

    private String yearLunar;

    private String identificationPositiveImage;

    private String identificationNegativeImage;

    private String healthImages;

    private String certificateImages;

    private Byte isFreeze;
    
    private String jobTitle;
    
    private  Double  weight;
    
    private Integer nums;
    // 家庭遗传病
    private String familyDisease;
    
    private String lifeImages;
    
    private List<String> workImageArrays;
    
    
    public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getLifeImages() {
		return lifeImages;
	}
	public void setLifeImages(String lifeImages) {
		this.lifeImages = lifeImages;
	}
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
	
	public List<String> getWorkImageArrays() {
		return workImageArrays;
	}
	public void setWorkImageArrays(List<String> workImageArrays) {
		this.workImageArrays = workImageArrays;
	}
	public Integer getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
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
	
	public Integer getNurseId() {
		return nurseId;
	}
	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer  getSeniority() {
		return seniority;
	}
	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}
	public String getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
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
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getIdentificationCode() {
		return identificationCode;
	}
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getProficiency() {
		return proficiency;
	}
	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getPersionCharacter() {
		return persionCharacter;
	}
	public void setPersionCharacter(String persionCharacter) {
		this.persionCharacter = persionCharacter;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getYearLunar() {
		return yearLunar;
	}
	public void setYearLunar(String yearLunar) {
		this.yearLunar = yearLunar;
	}
	public String getIdentificationPositiveImage() {
		return identificationPositiveImage;
	}
	public void setIdentificationPositiveImage(String identificationPositiveImage) {
		this.identificationPositiveImage = identificationPositiveImage;
	}
	public String getIdentificationNegativeImage() {
		return identificationNegativeImage;
	}
	public void setIdentificationNegativeImage(String identificationNegativeImage) {
		this.identificationNegativeImage = identificationNegativeImage;
	}
	public String getHealthImages() {
		return healthImages;
	}
	public void setHealthImages(String healthImages) {
		this.healthImages = healthImages;
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
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getFamilyDisease() {
		return familyDisease;
	}
	public void setFamilyDisease(String familyDisease) {
		this.familyDisease = familyDisease;
	}
	@Override
	public String toString() {
		return "NurseBaseBean [id=" + id + ", createUser=" + createUser + ", createTime=" + createTime + ", updateUser="
				+ updateUser + ", updateTime=" + updateTime + ", more1=" + more1 + ", more2=" + more2 + ", more3="
				+ more3 + ", int1=" + int1 + ", int2=" + int2 + ", sorting=" + sorting + ", account=" + account
				+ ", password=" + password + ", realName=" + realName + ", mobile=" + mobile + ", level=" + level
				+ ", identificationCode=" + identificationCode + ", image=" + image + ", birthday=" + birthday
				+ ", hometown=" + hometown + ", currentAddress=" + currentAddress + ", proficiency=" + proficiency
				+ ", education=" + education + ", persionCharacter=" + persionCharacter + ", height=" + height
				+ ", constellation=" + constellation + ", yearLunar=" + yearLunar + ", identificationPositiveImage="
				+ identificationPositiveImage + ", identificationNegativeImage=" + identificationNegativeImage
				+ ", healthImages=" + healthImages + ", certificateImages=" + certificateImages + ", isFreeze="
				+ isFreeze + ", weight=" + weight + ", familyDisease=" + familyDisease + "]";
	}
	
	
	
}
