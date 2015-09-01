package com.easymother.bean;

import java.util.List;

public class OrderListResult {
	private List<Order> CRS;
	private List<Order> YS;
	private List<Order> YYS;
	private List<Order> SHORT_YYS;
	private List<Order> SHORT_YS;
	public List<Order> getCRS() {
		return CRS;
	}
	public void setCRS(List<Order> cRS) {
		CRS = cRS;
	}
	public List<Order> getYS() {
		return YS;
	}
	public void setYS(List<Order> yS) {
		YS = yS;
	}
	public List<Order> getYYS() {
		return YYS;
	}
	public void setYYS(List<Order> yYS) {
		YYS = yYS;
	}
	public List<Order> getSHORT_YYS() {
		return SHORT_YYS;
	}
	public void setSHORT_YYS(List<Order> sHORT_YYS) {
		SHORT_YYS = sHORT_YYS;
	}
	public List<Order> getSHORT_YS() {
		return SHORT_YS;
	}
	public void setSHORT_YS(List<Order> sHORT_YS) {
		SHORT_YS = sHORT_YS;
	}
	
}
