package com.easymother.bean;

import java.util.List;

public class NurseSpaceDetailBean {

	private NurseBaseBean nurseinfo;
	private List<TopicItemBean> posts;
	private int nums1;//发表文章
	private int nums2;//雇主喜欢
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
	public int getNums1() {
		return nums1;
	}
	public void setNums1(int nums1) {
		this.nums1 = nums1;
	}
	public int getNums2() {
		return nums2;
	}
	public void setNums2(int nums2) {
		this.nums2 = nums2;
	}
	
	
}
