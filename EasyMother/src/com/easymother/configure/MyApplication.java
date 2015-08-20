package com.easymother.configure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class MyApplication  extends Application{
	
	private static int SCREEN_WIDTH;//屏幕宽度
	private static int SCREEN_HEIGHT;//屏幕高度
	
	private static Map<String,Set<Activity>> activityMAP;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		WindowManager manager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm=new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH=dm.widthPixels;
		SCREEN_HEIGHT=dm.heightPixels;
		Set<Activity> activities=new HashSet<Activity>();
		activityMAP=new HashMap();
		Log.e("screen_width--------", SCREEN_WIDTH+"");
	}
	
	public static void initImageLoader(Context context)
	{
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCacheSize(50 * 1024 * 1024)
				// 50 Mb
//				.discCache(new UnlimitedDiscCache(cacheDir))
				//设置缓存路径
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
	
	public static int getScreen_width() {
//		Log.e("screen_width", SCREEN_WIDTH+"");
		return SCREEN_WIDTH;
	}

	public static void setScreen_width(int sCREEN_WIDTH) {
		SCREEN_WIDTH = sCREEN_WIDTH;
	}

	public static int getScreen_height() {
		return SCREEN_HEIGHT;
	}

	public static void setScreen_height(int sCREEN_HEIGHT) {
		SCREEN_HEIGHT = sCREEN_HEIGHT;
	}
	/**
	 * 添加activity到map集合(同一流程的要一起关闭的添加到一个group)
	 * @param activity
	 * @param tag activity的标志（用来控制开关）
	 */
	public synchronized static void addActivityToMap(Activity activity,String tag){

		Set<Activity> group=activityMAP.get(tag);
		if (group==null) {
			group=new HashSet<Activity>();
			activityMAP.put(tag, group);
		}
		group.add(activity);
	}
	
	/**
	 * 根据标签关闭activity
	 * @param tag
	 */
	public synchronized static void destoryActivity(String tag){
		
		Set<Activity> group=activityMAP.get(tag);
		if (group==null) {
			return;
		}
		for (Activity one : group) {
			if (one!=null) {
				one.finish();
			}
			activityMAP.remove(tag);
		}
		
	}
	
	/**
	 * 关闭所有activity  调用上面关闭每个group的代码
	 */
	public synchronized static void destoryAllActivity(){
		
		for (Map.Entry<String, Set<Activity>> entry : activityMAP.entrySet()) {
			
			destoryActivity(entry.getKey());
		}
		activityMAP=null;
	}
	
	
	

}
