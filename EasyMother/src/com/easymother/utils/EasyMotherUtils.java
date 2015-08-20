package com.easymother.utils;

import java.util.ArrayList;
import java.util.List;

import com.easymother.main.R;
import com.easymother.main.homepage.YueSaoListActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EasyMotherUtils {
	private static TextView title;// 标题
	private static ImageView back;// 返回键
	private static ImageView rightbutton;//右侧按钮
	private static RightButtonLisenter button;//右边按钮的回调 
	
	private static List<String> list;//时间表
	
	/**
	 * 跳转activity 不带数据直接跳转
	 * @param activity
	 * @param clazz
	 */
	public static void goActivity(Activity activity,Class clazz){
		Intent intent=new Intent(activity,clazz);
		activity.startActivity(intent);
	}
	/**
	 * 初始化title
	 * @param activity 依附的activity
	 * @param titleString 名字
	 * @param isShowRightButton 是否有右侧按钮
	 */
	public static void initTitle(final Activity activity,String titleString,boolean isShowRightButton ){
		title = (TextView) activity.findViewById(R.id.title);
		back = (ImageView) activity.findViewById(R.id.back);
		rightbutton=(ImageView) activity.findViewById(R.id.more);
		title.setText(titleString);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.finish();
			}
		});
		if (isShowRightButton) {
			button.addRightButton(rightbutton);
		}else{
			rightbutton.setVisibility(View.GONE);
		}
		
	}
	/*
	 * title右侧按键的接口  如果右侧有按钮需调用该接口 实现按钮功能
	 */
	public interface RightButtonLisenter{
		public void addRightButton(ImageView imageView);
	}
	/*
	 * 设置右侧接口的方法
	 */
	public static void setRightButton(RightButtonLisenter lisenter){
		button=lisenter;
	}
	/*
	 * 打开系统摄像头拍照的方法
	 */
	public static void takePhoto(Activity activity,int requestCode,Uri path){
		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, path);//设置保存的uri地址
		activity.startActivityForResult(intent, requestCode);
		
	}
	
	/*
	 * 打开系统相册的方法
	 */
	public static void chosePhoto(Activity activity,int requestCode,Uri path){
		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, path);//设置保存的uri地址
		activity.startActivityForResult(intent, requestCode);
		
	}
	/**
	 * 剪裁图片 
	 * @param activity 上下文
	 * @param outputX 宽
	 * @param outputY 高
	 * @param resquestCode 请求码
	 * @param path  保存图片的uri
	 */
	public static void cropPhoto(Activity activity,int outputX ,int outputY,int requestCode,Uri path){
		Intent intent=new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(path, "image/*");//设置数据path（地址） 和类型
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);//X方向上的比例
		intent.putExtra("aspectY", 1);//Y方向上的比例
		intent.putExtra("outputX", outputX);//宽
		intent.putExtra("outputY", outputY);//高
		intent.putExtra("scale", false);//是否保持保留缩放比例
		intent.putExtra(MediaStore.EXTRA_OUTPUT, path);//设置保存地址
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}
	/**
	 * 获取时间表
	 * @return
	 */
	public static List<String> getTime(){
		list=new ArrayList<String>();
		list.add("8:00");
		list.add("8:30");
		list.add("9:00");
		list.add("9:30");
		list.add("10:00");
		list.add("10:30");
		list.add("11:00");
		list.add("11:30");
		list.add("12:00");
		list.add("12:30");
		list.add("13:00");
		list.add("13:30");
		list.add("14:00");
		list.add("14:30");
		list.add("15:00");
		list.add("15:30");
		list.add("16:00");
		list.add("16:30");
		list.add("17:00");
		list.add("17:30");
		list.add("18:00");
		list.add("18:30");
		list.add("19:00");
		list.add("19:30");
		list.add("20:00");
		list.add("20:30");
		list.add("21:00");
		list.add("20:30");
		return list;
		
	}
}
