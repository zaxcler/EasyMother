package com.alidao.mama;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer.Form;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class WeiXinUtils {

	/**
	 * 打开微信
	 */
	public static void openWX() {
		MyApplication.WX_API.openWXApp();
	}

	/**
	 * 分享链接
	 * 
	 * @param url
	 * @param resource
	 *            缩略图
	 * @param where
	 *            分享到哪儿 SendMessageToWX.Req.WXSceneSession代表朋友，
	 *            SendMessageToWX.Req.WXSceneTimeline代表朋友圈
	 * 
	 */
	public static void shareDownloadUrl(Activity activity) {
		// 第一步创建一个wxwebpageobject对象封装url
		WXWebpageObject object = new WXWebpageObject();
		object.webpageUrl = BaseInfo.DOWNLOAD_URL;
		// 第二步创建一个wxmediamessage对象，封装要要发送的信息
		WXMediaMessage message = new WXMediaMessage(object);
		message.title = "轻松妈妈下载链接";
		message.description = "这是轻松妈妈的链接";

		// 第三步设置缩略图
		Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.drawable.app);
		message.thumbData = bmpToByteArray(thumb, true);
		// 第四步创建发送对象
		final SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.message = message;
		req.transaction = buildTransaction(message.title);

		Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_share);
		dialog.getWindow().setLayout(MyApplication.getScreen_width(), MyApplication.getScreen_height()/4);
		dialog.findViewById(R.id.share1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//发送给朋友
				req.scene = SendMessageToWX.Req.WXSceneSession;
				MyApplication.WX_API.sendReq(req);
			}
		});
		dialog.findViewById(R.id.share2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//发送到朋友
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				MyApplication.WX_API.sendReq(req);
			}
		});
		dialog.show();

	}

	/**
	 * 将bitmap转换成二进制数组
	 * 
	 * @param bitmap
	 * @param isRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(Bitmap bitmap, boolean isRecycle) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 将图片压缩，并写到输出流里
		bitmap.compress(CompressFormat.PNG, 100, outputStream);
		if (isRecycle) {
			bitmap.recycle();
		}
		return outputStream.toByteArray();

	}

	/*
	 * 创建唯一标识
	 */
	public static String buildTransaction(String mark) {
		return mark == null ? String.valueOf(System.currentTimeMillis())
				: String.valueOf(System.currentTimeMillis()) + mark;
	}

}
