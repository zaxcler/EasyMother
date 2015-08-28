package com.easymother.utils;

import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.DetailResult;
import com.easymother.bean.HomePageResult;
import com.easymother.bean.LoginResult;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.Order;
import com.easymother.bean.OrderDetailResult;
import com.easymother.bean.Root;
import com.easymother.bean.TestResult;
import com.easymother.bean.WishListResult;
import com.easymother.bean.YueSao;

import android.util.Log;


public class JsonUtils {
	/**
	 * 根据 json 类型的String 转换成类
	 * @param json
	 * @return
	 */
	public static HomePageResult getHomePageResult(String json){
		HomePageResult result=JSON.parseObject(json, HomePageResult.class);
		Log.e("result----------", json);
		return result;
		
	}
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static HomePageResult getHomePageResult(JSONObject json){
		Root root=JSON.parseObject(json.toString(), Root.class);
		HomePageResult result = null;
		Log.e("json----------", json.toString());
		//若根元素result返回的是String
		
			String object=root.getResult().toString();
			result=JSON.parseObject(object, HomePageResult.class);
			Log.e("result----------", result.toString());
		
		
		return result;
//		HomePageResult result=JSON.parseObject(root.getResult(), HomePageResult.class);
//		return result;
		
	}
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static WishListResult getWishListResult(JSONObject json){
		Root root=JSON.parseObject(json.toString(), Root.class);
		WishListResult result=null;
//		WishListResult result=JSON.parseObject(root.getResult(), WishListResult.class);
		Log.e("result----++++----", root.getResult().toString());
			String object= root.getResult().toString();
			result=JSON.parseObject(object, WishListResult.class);
			Log.e("WishListResult----++++----", result.toString());
		return result;
		
	}
	
	/**
	 * 根据 jsonObject 转换成类
	 * @param json
	 * @return
	 */
	public static Root getRootResult(JSONObject json){
		Log.e("json----------", json.toString());
		Root root=JSON.parseObject(json.toString(), Root.class);
		return root;
		
	}
	
	/**
	 * 解析为list
	 * @param json
	 * @return
	 */
	public static List<YueSao> getYSList(JSONObject json){
		Root root=getRootResult(json);
		String jsonArray=root.getResult().toString();
		List<YueSao> list=JSON.parseArray(jsonArray, YueSao.class);
		return list;
	}

	/**
	 * 解析为list
	 * @param json
	 * @return
	 */
	public static List<NurseBaseBean> getNurseBaseBeanList(JSONObject json){
		Root root=getRootResult(json);
		String jsonArray=root.getResult().toString();
		List<NurseBaseBean> list=JSON.parseArray(jsonArray, NurseBaseBean.class);
		return list;
	}
	
	
	/**
	 * 获取登录返回的类
	 * @param json
	 * @return
	 */
	public static LoginResult getLoginResult(JSONObject json){
		Root root=getRootResult(json);
		LoginResult result=JSON.parseObject(root.getResult().toString(), LoginResult.class);
		
		return result;
	}
	
	/**
	 * 获取详情页信息
	 * @param json
	 * @return
	 */
	public static DetailResult getDeatilResult(JSONObject json){
		Root root=getRootResult(json);
		DetailResult result=JSON.parseObject(root.getResult().toString(), DetailResult.class);
		
		return result;
	}
	/**
	 * 获取订单详细信息
	 * @param json
	 * @return
	 */
	public static OrderDetailResult getOrder(JSONObject json){
		Root root=getRootResult(json);
		OrderDetailResult orderDetail=null;
		if (root.getIsSuccess()) {
			orderDetail=JSON.parseObject(root.getResult().toString(), OrderDetailResult.class);
		}
		return orderDetail;
	}
	
	
	
	
}
