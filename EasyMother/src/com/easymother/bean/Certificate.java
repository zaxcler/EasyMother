package com.easymother.bean;

public class Certificate {
	private Integer id;
	private Integer jobId;
	private Integer nurseId;
	private String createTime;
	private String updateTime;
	private String name;
	private String image;
	private String job;
	private String remarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getNurseId() {
		return nurseId;
	}
	public void setNurseId(Integer nurseId) {
		this.nurseId = nurseId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "Certificate [id=" + id + ", jobId=" + jobId + ", nurseId=" + nurseId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", name=" + name + ", image=" + image + ", job=" + job + ", remarks="
				+ remarks + "]";
	}
	
	

}
