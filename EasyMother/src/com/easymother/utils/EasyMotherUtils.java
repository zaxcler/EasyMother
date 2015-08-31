package com.easymother.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.main.homepage.CuiRuiShiProjectActivity;
import com.easymother.main.homepage.OrderCRSProcess;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	 * 打开activity 返回结果
	 * @param activity
	 * @param clazz
	 * @param requestCode
	 */
	public static void goActivityForResult(Activity activity,Class clazz,int requestCode){
		Intent intent=new Intent(activity,clazz);
		activity.startActivityForResult(intent, requestCode);
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
	 * path 设置保存的uri地址
	 */
	public static void chosePhoto(Activity activity,int requestCode,Uri path){
		Intent intent=new Intent();
		intent.setType("image/*");
		//对于Android版本 大于4.4的和小于4.4的操作不同
		if (Build.VERSION.SDK_INT<19) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
		}else {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}
		
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, path);//设置保存的uri地址
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
	public static void cropPhoto(Activity activity,int outputX ,int outputY,int requestCode,Uri path,Uri savepath){
		Intent intent=new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(path, "image/*");//设置数据path（地址） 和类型
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);//X方向上的比例
		intent.putExtra("aspectY", 1);//Y方向上的比例
		intent.putExtra("outputX", outputX);//宽
		intent.putExtra("outputY", outputY);//高
		intent.putExtra("scale", false);//是否保持保留缩放比例
		intent.putExtra(MediaStore.EXTRA_OUTPUT, savepath);//设置保存地址
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}
	
	
	
	/**
	 * 显示弹窗（详细信息）
	 * @param resource
	 */
	public static void showDialog( final Activity activity,String path,int resource){

		View view=LayoutInflater.from(activity).inflate(R.layout.cuiruishi_project1_detail, null);
		WebView content=(WebView) view.findViewById(R.id.contents);
		ImageView imageView=(ImageView) view.findViewById(R.id.imageView1);
		imageView.setBackgroundResource(resource);
		
		content.loadUrl(path);
		
	    final Dialog dialog=new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		view.findViewById(R.id.cancle).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		view.findViewById(R.id.yes).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				if (activity instanceof CuiRuiShiProjectActivity) {
					EasyMotherUtils.goActivity(activity,OrderCRSProcess.class);
					activity.finish();
				}
				
			}
		});
		dialog.setContentView(view);
		dialog.show();
		
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

	protected static boolean uploadstatu;
	
	/**
	 * 上传图片 
	 * @param 图片
	 * @param url 上传地址
	 * @return
	 */
	public static boolean uploadPhoto(final Bitmap bitmap,final String url,final String type){
		/*
		 * 判断是否存在sd卡
		 */
		String sdStatu=Environment.getExternalStorageState();
		if (!sdStatu.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		File file=new File("/sdcard/easymother");
		file.mkdirs();
		//文件名字
		String name=DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
		String filename="/sdcard/easymother"+name;
		Log.e("上传图片名字", filename);
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//压缩并写入
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		RequestParams params=new RequestParams();
		File photo=new File(filename);
		if (photo!=null) {
			try {
				String namecode=photo.getName();
				params.put("user_photo", photo);
				NetworkHelper.doPostNoToken(url, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
						super.onSuccess(statusCode, headers, response);
						uploadstatu=false;
						List<String> photos=JSON.parseArray(response.toString(), String.class);
						
						if ("user_image".equals(type)) {
							MyApplication.editor.putString("image", photos.get(0)).commit();
							//成功上传，就修改服务器的头像名字
							RequestParams params=new RequestParams();
							params.put("image", photos.get(0));
							params.put("id", MyApplication.preferences.getInt("id", 0));
							NetworkHelper.doGet(BaseInfo.CHANGEINFO, params, new JsonHttpResponseHandler(){
								public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
									if (JsonUtils.getRootResult(response).getIsSuccess()) {
										Log.e("图片名字上传成功", response.toString());
									}
								};
								public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
									
								};
							});
						}
						if ("baby_image".equals(type)) {
							MyApplication.editor.putString("baby_image", photos.get(0)).commit();
						}
						if (type==null) {
							MyApplication.editor.putString("upload_images", photos.get(0)).commit();
						}
						Log.e("onSuccess——uploadstatu", uploadstatu+"");
						Log.e("onSuccess——response", response.toString()+"");
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable throwable,
							JSONArray errorResponse) {
						super.onFailure(statusCode, headers, throwable, errorResponse);
						uploadstatu=false;
						Log.e("onFailure——uploadstatu", uploadstatu+"");
						Log.e("onFailure——response", errorResponse.toString()+"");
					}
						
					
					@Override
					public void onRetry(int retryNo) {
						super.onRetry(retryNo);
						Log.e("onRetry——uploadstatu", uploadstatu+"");
						if (!uploadstatu) {
							
							uploadPhoto(bitmap, url,type);
						}
					}
				});
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
		
		
	}
}
