package com.easymother.utils;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.util.Log;

public class NetworkHelper{
	
	public final static String APP_TOKEN="1";
	
	private static AsyncHttpClient httpClient;
	
	//静态代码块优先于构造方法执行
	static {
		httpClient=new AsyncHttpClient();
//		httpClient.setTimeout(5000);
	}
	
	public  NetworkHelper(){
		
	}
	/*
	 * 获取登录后保存在本地的apptoken
	 */
	public static String getAppToken(){
		Log.e("appToken", MyApplication.preferences.getString("appToken", APP_TOKEN));
		return MyApplication.preferences.getString("appToken", APP_TOKEN);
	}
	
	
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,BinaryHttpResponseHandler handler){
		RequestParams params=new RequestParams();
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
		
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理string数据
	 */
	public static void doGet(String url,TextHttpResponseHandler handler){
		RequestParams params=new RequestParams();
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url, params,handler);
//		httpClient.get(url, params,handler);
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理json数据
	 */
	public static void doGet(String url,JsonHttpResponseHandler handler){
		
		RequestParams params=new RequestParams();
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url, params,handler);
		Log.e("地址是------", BaseInfo.BASE_URL+url);
//		httpClient.get(url, params,handler);
	}
	
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,BinaryHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,TextHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doGet(String url,RequestParams params,JsonHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
		Log.e("注册地址是----》", BaseInfo.BASE_URL+url);
//		httpClient.get(url,params, handler);
	}
	
	
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理二进制数据
	 */
	public static void doGetNoToken(String url,BinaryHttpResponseHandler handler){
		httpClient.get(BaseInfo.BASE_URL+url, handler);
		
	}
	/**
	 * 无参数的get请求,不带token
	 * @param url
	 * @param handler 处理string数据
	 */
	public static void doGetNoToken(String url,TextHttpResponseHandler handler){
		httpClient.get(BaseInfo.BASE_URL+url,handler);
	}
	/**
	 * 无参数的get请求,不带token
	 * @param url
	 * @param handler 处理json数据
	 */
	public static void doGetNoToken(String url,JsonHttpResponseHandler handler){
		
		httpClient.get(BaseInfo.BASE_URL+url,handler);
		Log.e("地址是------", BaseInfo.BASE_URL+url);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGetNoToken(String url,RequestParams params,BinaryHttpResponseHandler handler){
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGetNoToken(String url,RequestParams params,TextHttpResponseHandler handler){
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doGetNoToken(String url,RequestParams params,JsonHttpResponseHandler handler){
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
		Log.e("注册地址是----》", BaseInfo.BASE_URL+url);
	}
	
	
	/**
	 * 无参数的post请求
	 * @param url
	 * @param handler 处理二进制数据
	 */
	public static void doPostNoToken(String url,BinaryHttpResponseHandler handler){
		httpClient.post(BaseInfo.BASE_URL+url, handler);
		
	}
	/**
	 * 无参数的get请求,不带token
	 * @param url
	 * @param handler 处理string数据
	 */
	public static void doPostNoToken(String url,TextHttpResponseHandler handler){
		httpClient.post(BaseInfo.BASE_URL+url,handler);
	}
	/**
	 * 无参数的get请求,不带token
	 * @param url
	 * @param handler 处理json数据
	 */
	public static void doPostNoToken(String url,JsonHttpResponseHandler handler){
		
		httpClient.post(BaseInfo.BASE_URL+url,handler);
		Log.e("地址是------", BaseInfo.BASE_URL+url);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doPostNoToken(String url,RequestParams params,BinaryHttpResponseHandler handler){
		httpClient.post(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理文本数据
	 */
	public static void doPostNoToken(String url,RequestParams params,TextHttpResponseHandler handler){
		httpClient.post(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求，不带token
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doPsotNoToken(String url,RequestParams params,JsonHttpResponseHandler handler){
		httpClient.post(BaseInfo.BASE_URL+url,params, handler);
		Log.e("注册地址是----》", BaseInfo.BASE_URL+url);
	}
	
	
}
