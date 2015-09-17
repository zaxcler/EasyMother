package com.easymother.bean;

import java.util.List;

public class TopicHelpDetailResult {

	private NurseBaseBean nurseinfo;
	private List<ForumPost> replys;
	private ForumPost postInfo;
	public NurseBaseBean getNurseinfo() {
		return nurseinfo;
	}
	public void setNurseinfo(NurseBaseBean nurseinfo) {
		this.nurseinfo = nurseinfo;
	}
	public List<ForumPost> getReplys() {
		return replys;
	}
	public void setReplys(List<ForumPost> replys) {
		this.replys = replys;
	}
	public ForumPost getPostInfo() {
		return postInfo;
	}
	public void setPostInfo(ForumPost postInfo) {
		this.postInfo = postInfo;
	}
	
}
