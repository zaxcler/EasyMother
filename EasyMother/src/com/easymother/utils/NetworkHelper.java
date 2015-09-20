package com.easymother.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R.drawable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

public class NetworkHelper {

	public final static String APP_TOKEN = "1";

	private static AsyncHttpClient httpClient;

	// 静态代码块优先于构造方法执行
	static {
		httpClient = new AsyncHttpClient();
		// httpClient.setTimeout(5000);
	}

	/*
	 * 显示富文本的imageter
	 */
	public static Html.ImageGetter imageGetter =new Html.ImageGetter() {
		
		@Override
		public Drawable getDrawable(String source) {
			Drawable drawable=null;
				
				try {
					URL url=new URL(source);
					drawable=Drawable.createFromStream(url.openStream(), null);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return drawable;
		}
	};
	/**
	 * 显示富文本
	 * @param resource
	 * @return
	 */
	public static Spanned showFWBText(String resource){
		return Html.fromHtml(resource,NetworkHelper.imageGetter,null);
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
		Log.e("访问地址是", BaseInfo.BASE_URL+url);
//		httpClient.get(url,params, handler);
	}
	
	/**
	 * 带参数的post请求
	 * @param url
	 * @param params
	 * @param handler 处理二进制数据
	 */
	public static void doPost(String url,RequestParams params,BinaryHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的Post请求
	 * @param url
	 * @param params
	 * @param handler 处理文本数据
	 */
	public static void doPost(String url,RequestParams params,TextHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
	}
	/**
	 * 带参数的Post请求
	 * @param url
	 * @param params
	 * @param handler 处理json数据
	 */
	public static void doPost(String url,RequestParams params,JsonHttpResponseHandler handler){
		params.put("appToken", getAppToken());
		httpClient.get(BaseInfo.BASE_URL+url,params, handler);
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
