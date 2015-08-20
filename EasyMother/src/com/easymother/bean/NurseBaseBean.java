package com.easymother.bean;

public class NurseBaseBean {
	private int nurseId;
	
	private int collectionId;//心愿单id

	private String realName;

	private String seniority;//工龄
	
	private int age;
	
	private String birthday;
	
	private String hometown;

	private String image;

	private String jobTitle;

	private String currentAddress;
	
	private String showPrice;

	private String marketPrice;
	
	private String nums;
	
	private String title;
	
	private String content;

	private String job;

	private String employmentStartTime;

	private String employmentEndTime;

	public void setNurseId(int nurseId){
	this.nurseId = nurseId;
	}
	public int getNurseId(){
	return this.nurseId;
	}
	
	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setRealName(String realName){
	this.realName = realName;
	}
	public String getRealName(){
	return this.realName;
	}
	public void setAge(int age){
	this.age = age;
	}
	public int getAge(){
	return this.age;
	}
	public void setHometown(String hometown){
	this.hometown = hometown;
	}
	public String getHometown(){
	return this.hometown;
	}
	public void setImage(String image){
	this.image = image;
	}
	public String getImage(){
	return this.image;
	}
	public void setJobTitle(String jobTitle){
	this.jobTitle = jobTitle;
	}
	public String getJobTitle(){
	return this.jobTitle;
	}
	public void setCurrentAddress(String currentAddress){
	this.currentAddress = currentAddress;
	}
	public String getCurrentAddress(){
	return this.currentAddress;
	}
	public void setMarketPrice(String marketPrice){
	this.marketPrice = marketPrice;
	}
	public String getMarketPrice(){
	return this.marketPrice;
	}
	public void setJob(String job){
	this.job = job;
	}
	public String getJob(){
	return this.job;
	}
	public void setEmploymentStartTime(String employmentStartTime){
	this.employmentStartTime = employmentStartTime;
	}
	public String getEmploymentStartTime(){
	return this.employmentStartTime;
	}
	public void setEmploymentEndTime(String employmentEndTime){
	this.employmentEndTime = employmentEndTime;
	}
	public String getEmploymentEndTime(){
	return this.employmentEndTime;
	}
}
