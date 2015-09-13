package com.easymother.bean;

import java.util.List;

public class CollectionListResult {
	
	List<NewsBean> news;
	List<ForumBean> forum;
	public List<NewsBean> getNews() {
		return news;
	}
	public void setNews(List<NewsBean> news) {
		this.news = news;
	}
	public List<ForumBean> getForum() {
		return forum;
	}
	public void setForum(List<ForumBean> forum) {
		this.forum = forum;
	}
	

}
