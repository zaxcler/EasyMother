package com.easymother.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetworkHelper{
	
	private static String BASE_URL="192.168.1.125:8080/";
	private static AsyncHttpClient httpClient;
	
	//静态代码块优先于构造方法执行
	static {
		httpClient=new AsyncHttpClient();
	}
	
	public  NetworkHelper(){
		
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler
	 */
	public static void doGet(String url,AsyncHttpResponseHandler handler){
		httpClient.get(BASE_URL+url, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler
	 */
	public static void doGet(String url,RequestParams params,AsyncHttpResponseHandler handler){
		httpClient.get(BASE_URL+url,params, handler);
	}
	
}
