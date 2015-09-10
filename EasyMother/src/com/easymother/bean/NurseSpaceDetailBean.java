package com.easymother.bean;

import java.util.List;

public class NurseSpaceDetailBean {

	private NurseBaseBean nurseinfo;
	private List<TopicItemBean> posts;
	public NurseBaseBean getNurseinfo() {
		return nurseinfo;
	}
	public void setNurseinfo(NurseBaseBean nurseinfo) {
		this.nurseinfo = nurseinfo;
	}
	public List<TopicItemBean> getPosts() {
		return posts;
	}
	public void setPosts(List<TopicItemBean> posts) {
		this.posts = posts;
	}
	
}
