package com.easymother.utils;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkHelper{
	
	public final static String APP_TOKEN="vers155d45697566ced1768XQG2RLTA9354DA727D7E2CBB661703BE4C6CEDABC0A3F0F178BCB41BBAFD5569ECC9B32E";
	
	private static AsyncHttpClient httpClient;
	
	//静态代码块优先于构造方法执行
	static {
		httpClient=new AsyncHttpClient();
		httpClient.setTimeout(3000);
	}
	
	public  NetworkHelper(){
		
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,BinaryHttpResponseHandler handler){
		RequestParams params=new RequestParams();
		params.put("apptoken", APP_TOKEN);
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
		
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理string数据
	 */
	public static void doGet(String url,TextHttpResponseHandler handler){
		RequestParams params=new RequestParams();
		params.put("apptoken", APP_TOKEN);
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
		params.put("apptoken", APP_TOKEN);
		httpClient.get(BaseInfo.BASE_URL+url, params,handler);
//		httpClient.get(url, params,handler);
	}
	
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,BinaryHttpResponseHandler handler){
		params.put("apptoken", APP_TOKEN);
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,TextHttpResponseHandler handler){
		params.put("apptoken", APP_TOKEN);
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doGet(String url,RequestParams params,JsonHttpResponseHandler handler){
		params.put("apptoken", APP_TOKEN);
//		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
		httpClient.get(url,params, handler);
	}
	
	
	
}
