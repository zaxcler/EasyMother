package com.easymother.utils;

import com.easymother.configure.MyApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkHelper{
	
	
	
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
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,BinaryHttpResponseHandler handler){
		httpClient.get(MyApplication.BASE_URL+url, handler);
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理string数据
	 */
	public static void doGet(String url,TextHttpResponseHandler handler){
//		httpClient.get(MyApplication.BASE_URL+url, handler);
		httpClient.get(url, handler);
	}
	/**
	 * 无参数的get请求
	 * @param url
	 * @param handler 处理json数据
	 */
	public static void doGet(String url,JsonHttpResponseHandler handler){
//		httpClient.get(MyApplication.BASE_URL+url, handler);
		httpClient.get(url, handler);
	}
	
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,BinaryHttpResponseHandler handler){
		httpClient.get(MyApplication.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doGet(String url,RequestParams params,TextHttpResponseHandler handler){
		httpClient.get(MyApplication.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doGet(String url,RequestParams params,JsonHttpResponseHandler handler){
		httpClient.get(MyApplication.BASE_URL+url,params, handler);
	}
}
