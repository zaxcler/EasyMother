package com.easymother.bean;

import java.util.List;

public class HomePageResult {
	

	private int wishCount;

	private List<YueSao> yuesao ;

	private List<CuiRuShi> cuirushi ;

	private List<YuYingShi> yuyingshi ;

	private List<BannerTexts> bannerTexts ;

	private List<Banners> banners ;

	public int getWishCount() {
		return wishCount;
	}

	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}

	public List<YueSao> getYuesao() {
		return yuesao;
	}

	public void setYuesao(List<YueSao> yuesao) {
		this.yuesao = yuesao;
	}

	public List<CuiRuShi> getCuirushi() {
		return cuirushi;
	}

	public void setCuirushi(List<CuiRuShi> cuirushi) {
		this.cuirushi = cuirushi;
	}

	public List<YuYingShi> getYuyingshi() {
		return yuyingshi;
	}

	public void setYuyingshi(List<YuYingShi> yuyingshi) {
		this.yuyingshi = yuyingshi;
	}

	public List<BannerTexts> getBannerTexts() {
		return bannerTexts;
	}

	public void setBannerTexts(List<BannerTexts> bannerTexts) {
		this.bannerTexts = bannerTexts;
	}

	public List<Banners> getBanners() {
		return banners;
	}

	public void setBanners(List<Banners> banners) {
		this.banners = banners;
	}


	
	
}
