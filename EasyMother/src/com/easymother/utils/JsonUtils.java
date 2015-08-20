package com.easymother.utils;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.HomePageResult;
import com.easymother.bean.Root;
import com.easymother.bean.WishListResult;


public class JsonUtils {
	/**
	 * 根据 json 类型的String 转换成类
	 * @param json
	 * @return
	 */
	public static HomePageResult getHomePageResult(String json){
		HomePageResult result=JSON.parseObject(json, HomePageResult.class);
		
		return result;
		
	}
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static HomePageResult getHomePageResult(JSONObject json){
		Root root=JSON.parseObject(json.toString(), Root.class);
		HomePageResult result=JSON.parseObject(root.getResult(), HomePageResult.class);
		return result;
		
	}
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static WishListResult getWishListResult(JSONObject json){
		Root root=JSON.parseObject(json.toString(), Root.class);
		WishListResult result=JSON.parseObject(root.getResult(), WishListResult.class);
		return result;
		
	}
	
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static Root getRootResult(JSONObject json){
		Root root=JSON.parseObject(json.toString(), Root.class);
		return root;
		
	}

}
