package com.easymother.utils;

import android.widget.ImageView;

import com.easymother.main.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class UILUtils
{

	private static DisplayImageOptions options;
	private  static ImageLoader imageLoader = ImageLoader.getInstance();
	public static  void downSet(ImageView image, String imageUrls){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20)).build();
		imageLoader.displayImage(imageUrls, image, options, null);
	}
	public static  void downImageSet(ImageView image, String imageUrls){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		.build();
		imageLoader.displayImage(imageUrls, image, options, null);
	}
	public static  void clearImageLoader(){
		  imageLoader.clearMemoryCache();  
          imageLoader.clearDiscCache();  
	}
	
}
