package com.alidao.mama;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer.Form;

import com.easymother.configure.MyApplication;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


public class WeiXinUtils {
	
	
	/**
	 * 打开微信
	 */
	public static void openWX(){
		MyApplication.WX_API.openWXApp();
	}
	/**
	 * 分享链接
	 * @param url
	 * @param resource 缩略图
	 * @param where 分享到哪儿 SendMessageToWX.Req.WXSceneSession代表朋友， SendMessageToWX.Req.WXSceneTimeline代表朋友圈
	 * 
	 */
	public static void shareDownloadUrl(Activity activity, String url,int resource,int where){
		//第一步创建一个wxwebpageobject对象封装url
		WXWebpageObject object=new WXWebpageObject();
		object.webpageUrl=url;
		//第二步创建一个wxmediamessage对象，封装要要发送的信息
		WXMediaMessage message=new WXMediaMessage(object);
		message.title="轻松妈妈下载链接";
		message.description="这是轻松妈妈的链接";
		
		//第三步设置缩略图
		Bitmap thumb=BitmapFactory.decodeResource(activity.getResources(), resource);
		message.thumbData=bmpToByteArray(thumb, true);
		//第四步创建发送对象
		SendMessageToWX.Req req=new SendMessageToWX.Req();
		req.message=message;
		req.transaction=buildTransaction(message.title);
		req.scene=where;
		MyApplication.WX_API.sendReq(req);
		
	}
	/**
	 * 将bitmap转换成二进制数组
	 * @param bitmap
	 * @param isRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(Bitmap bitmap,boolean isRecycle){
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		//将图片压缩，并写到输出流里
		bitmap.compress(CompressFormat.PNG, 100, outputStream);
		if (isRecycle) {
			bitmap.recycle();
		}
		return outputStream.toByteArray();
		
	}
	/*
	 * 创建唯一标识
	 */
	public static String buildTransaction(String mark){
		return mark==null?String.valueOf(System.currentTimeMillis()):String.valueOf(System.currentTimeMillis())+mark;
	}
	

}
