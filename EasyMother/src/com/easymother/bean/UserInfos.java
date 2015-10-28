package com.easymother.bean;

public class UserInfos {
	
	private String createTime;
	
	private String createUser;

	private String updateTime;
	
	private String updateUser;

	private int id;
	
	private String more1;

	private String more2;
	
	private String more3;
	
	private Integer int1;

	private Integer int2;

	private int sorting;

	private String account;

	private String nickname;

	private String image;

	private String preDate;

	private String isVip;

	private String hospitalName;

	private String password;

	private String mobile;

	private int score;
	
	private String identification;

	private String address;

	private String type;

	private String childTime;

	private String childType;

	private String profession;

	public void setCreateTime(String createTime){
	this.createTime = createTime;
	}
	public String getCreateTime(){
	return this.createTime;
	}
	public void setUpdateTime(String updateTime){
	this.updateTime = updateTime;
	}
	public String getUpdateTime(){
	return this.updateTime;
	}
	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
		if (int1!=null) {
			this.int1 = int1;
		}else {
			int1=0;
		}
		
	}
	public Integer getInt2() {
		return int2;
	}
	public void setInt2(Integer int2) {
		if (int2!=null) {
			this.int2 = int2;
		}else {
			int2=0;
		}
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public void setSorting(int sorting){
	this.sorting = sorting;
	}
	public int getSorting(){
	return this.sorting;
	}
	public void setAccount(String account){
	this.account = account;
	}
	public String getAccount(){
	return this.account;
	}
	public void setNickname(String nickname){
	this.nickname = nickname;
	}
	public String getNickname(){
	return this.nickname;
	}
	public void setImage(String image){
	this.image = image;
	}
	public String getImage(){
	return this.image;
	}
	public void setPreDate(String preDate){
	this.preDate = preDate;
	}
	public String getPreDate(){
	return this.preDate;
	}
	public void setIsVip(String isVip){
	this.isVip = isVip;
	}
	public String getIsVip(){
	return this.isVip;
	}
	public void setHospitalName(String hospitalName){
	this.hospitalName = hospitalName;
	}
	public String getHospitalName(){
	return this.hospitalName;
	}
	public void setPassword(String password){
	this.password = password;
	}
	public String getPassword(){
	return this.password;
	}
	public void setMobile(String mobile){
	this.mobile = mobile;
	}
	public String getMobile(){
	return this.mobile;
	}
	public void setScore(int score){
	this.score = score;
	}
	public int getScore(){
	return this.score;
	}
	public void setAddress(String address){
	this.address = address;
	}
	public String getAddress(){
	return this.address;
	}
	public void setType(String type){
	this.type = type;
	}
	public String getType(){
	return this.type;
	}
	public void setChildTime(String childTime){
	this.childTime = childTime;
	}
	public String getChildTime(){
	return this.childTime;
	}
	public void setChildType(String childType){
	this.childType = childType;
	}
	public String getChildType(){
	return this.childType;
	}
	public void setProfession(String profession){
	this.profession = profession;
	}
	public String getProfession(){
	return this.profession;
	}
	@Override
	public String toString() {
		return "UserInfos [createTime=" + createTime + ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + ", id=" + id + ", more1=" + more1 + ", more2=" + more2 + ", more3="
				+ more3 + ", int1=" + int1 + ", int2=" + int2 + ", sorting=" + sorting + ", account=" + account
				+ ", nickname=" + nickname + ", image=" + image + ", preDate=" + preDate + ", isVip=" + isVip
				+ ", hospitalName=" + hospitalName + ", password=" + password + ", mobile=" + mobile + ", score="
				+ score + ", identification=" + identification + ", address=" + address + ", type=" + type
				+ ", childTime=" + childTime + ", childType=" + childType + ", profession=" + profession + "]";
	}

	

}
