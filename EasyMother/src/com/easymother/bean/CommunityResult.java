package com.easymother.bean;

import java.util.List;

public class CommunityResult {
	
	private Integer unread;
	
	private List<Blocks> blocks;
	
	private List<Banners> banners;

	public Integer getUnread() {
		return unread;
	}

	public void setUnread(Integer unread) {
		this.unread = unread;
	}

	public List<Blocks> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Blocks> blocks) {
		this.blocks = blocks;
	}

	public List<Banners> getBanners() {
		return banners;
	}

	public void setBanners(List<Banners> banners) {
		this.banners = banners;
	}
	

}
