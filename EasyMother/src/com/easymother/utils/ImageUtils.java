package com.easymother.utils;

import com.easymother.configure.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.widget.ImageView;

public class ImageUtils {
	
	private static ImageLoader imageLoader;
	
	public ImageUtils() {
		
	}
	//
	static{
		imageLoader=ImageLoader.getInstance();
	}
	
	//设置图片 
	public static void setImage(String url,ImageView imageView){
		imageLoader.displayImage(url, imageView,MyApplication.options_image);
	};
	//设置头像 
	public static void setPhoto(String url,ImageView imageView){
			imageLoader.displayImage(url, imageView,MyApplication.options_photo);
	};
		
		

}
