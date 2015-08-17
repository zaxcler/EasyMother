package com.easymother.utils;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.HomePageResult;


public class JosnUtils {
	
	public HomePageResult getHomePageResult(String json){
		HomePageResult result=JSON.parseObject(json, HomePageResult.class);
		
		return result;
		
	}

}
