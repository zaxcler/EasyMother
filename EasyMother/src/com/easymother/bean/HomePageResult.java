package com.easymother.bean;

import java.util.List;

public class HomePageResult {
	

	private int wishCount;

	private List<YueSao> yuesaos ;

	private List<CuiRuShi> cuirushis ;

	private List<YuYingShi> yuyingshis ;

	private List<BannerTexts> bannerTextss ;

	private List<Banners> bannerss ;

	public void setWishCount(int wishCount){
	this.wishCount = wishCount;
	}
	public int getWishCount(){
	return this.wishCount;
	}
	public void setYuesao(List<YueSao> yuesao){
	this.yuesaos = yuesao;
	}
	public List<YueSao> getYuesao(){
	return this.yuesaos;
	}
	public void setCuirushi(List<CuiRuShi> cuirushi){
	this.cuirushis = cuirushi;
	}
	public List<CuiRuShi> getCuirushi(){
	return this.cuirushis;
	}
	public void setYuyingshi(List<YuYingShi> yuyingshi){
	this.yuyingshis = yuyingshi;
	}
	public List<YuYingShi> getYuyingshi(){
	return this.yuyingshis;
	}
	public void setBannerTexts(List<BannerTexts> bannerTexts){
	this.bannerTextss = bannerTexts;
	}
	public List<BannerTexts> getBannerTexts(){
	return this.bannerTextss;
	}
	public void setBanners(List<Banners> banners){
	this.bannerss = banners;
	}
	public List<Banners> getBanners(){
	return this.bannerss;
	}
}
