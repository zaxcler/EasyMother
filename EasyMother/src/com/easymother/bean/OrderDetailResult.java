package com.easymother.bean;

public class OrderDetailResult {
	
	private NurseBaseBean nurseInfo;
	
	private Order order;
	
	private NurseJobBean nurseJob;

	public NurseBaseBean getNurseInfo() {
		return nurseInfo;
	}
	public void setNurseInfo(NurseBaseBean nurseInfo) {
		this.nurseInfo = nurseInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public NurseJobBean getNurseJob() {
		return nurseJob;
	}
	public void setNurseJob(NurseJobBean nurseJob) {
		this.nurseJob = nurseJob;
	}
	
}
