package com.easymother.bean;

import java.util.List;

public class WishListResult {
	

	private List<Short_YYS> SHORT_YYSs ;

	private List<YueSao> yuesaos ;

	private List<Short_YS> SHORT_YSs ;

	private List<CuiRuShi> cuirushis ;

	private List<YuYingShi> yuyingshis ;

	public void setSHORT_YYS(List<Short_YYS> SHORT_YYS){
	this.SHORT_YYSs = SHORT_YYS;
	}
	public List<Short_YYS> getSHORT_YYS(){
	return this.SHORT_YYSs;
	}
	public void setYuesao(List<YueSao> yuesao){
	this.yuesaos = yuesao;
	}
	public List<YueSao> getYuesao(){
	return this.yuesaos;
	}
	public void setSHORT_YS(List<Short_YS> SHORT_YS){
	this.SHORT_YSs = SHORT_YS;
	}
	public List<Short_YS> getSHORT_YS(){
	return this.SHORT_YSs;
	}
	public void setCuirushi(List<CuiRuShi> cuirushi){
	this.cuirushis = cuirushi;
	}
	public List<CuiRuShi> getCuirushi(){
	return this.cuirushis;
	}
	public void setYuyingshi(List<YuYingShi> yuyingshi){
	this.yuyingshis = yuyingshi;
	}
	public List<YuYingShi> getYuyingshi(){
	return this.yuyingshis;
	}
	
}
