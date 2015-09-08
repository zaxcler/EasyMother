package com.easymother.bean;

import java.util.List;

public class OrderListResult {
	private List<OrderListBean> CRS;
	private List<OrderListBean> YS;
	private List<OrderListBean> YYS;
	private List<OrderListBean> SHORT_YYS;
	private List<OrderListBean> SHORT_YS;
	
	public List<OrderListBean> getCRS() {
		return CRS;
	}
	public void setCRS(List<OrderListBean> cRS) {
		CRS = cRS;
	}
	public List<OrderListBean> getYS() {
		return YS;
	}
	public void setYS(List<OrderListBean> yS) {
		YS = yS;
	}
	public List<OrderListBean> getYYS() {
		return YYS;
	}
	public void setYYS(List<OrderListBean> yYS) {
		YYS = yYS;
	}
	public List<OrderListBean> getSHORT_YYS() {
		return SHORT_YYS;
	}
	public void setSHORT_YYS(List<OrderListBean> sHORT_YYS) {
		SHORT_YYS = sHORT_YYS;
	}
	public List<OrderListBean> getSHORT_YS() {
		return SHORT_YS;
	}
	public void setSHORT_YS(List<OrderListBean> sHORT_YS) {
		SHORT_YS = sHORT_YS;
	}
	
	
}
