package com.easymother.configure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.UserInfo;
import com.easymother.main.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class MyApplication  extends Application{
	
	private static int SCREEN_WIDTH;//屏幕宽度
	private static int SCREEN_HEIGHT;//屏幕高度
	public static SharedPreferences preferences;//本地保存用户信息
	public static SharedPreferences.Editor editor;//信息编辑器
	
	public static DisplayImageOptions options_image;//下载图片的默认配置
	public static DisplayImageOptions options_photo;//下载图片的默认配置
	
	private static Map<String,Set<Activity>> activityMAP;
	
	
//	public static final String APP_ID="wxcacf2de19303ba3";//微信的id
	public static final String APP_ID="wx65cc3cea8ede40f9";//微信的id
	public static IWXAPI WX_API;//微信的api根，所有微信api根据 WX_API 调用；
	@Override
	public void onCreate() {
		super.onCreate();
		preferences=getSharedPreferences("user",Activity.MODE_PRIVATE);//初始化sharepreference
		editor=preferences.edit();//初始化编辑器
		
		initImageLoader(getApplicationContext());//初始化imageloader
		//初始化时得到屏幕尺寸
		WindowManager manager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm=new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH=dm.widthPixels;
		SCREEN_HEIGHT=dm.heightPixels;
		//初始化保存activity的集合
		@SuppressWarnings("unused")
		Set<Activity> activities=new HashSet<Activity>();
		activityMAP=new HashMap();
		Log.e("screen_width--------", SCREEN_WIDTH+"");
		
		//初始化微信
		initWX();
		
		
	}
	
	
	/*
	 * 初始化微信
	 */
	public void initWX(){
		//初始化微信
		WX_API=WXAPIFactory.createWXAPI(getApplicationContext(),APP_ID);//初始化微信api
		WX_API.registerApp(APP_ID);//将app注册到微信中
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
		
		L.disableLogging();
		ImageLoader.getInstance().init(config);
		options_image=new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).showImageOnLoading(R.drawable.default_image).showImageOnFail(R.drawable.default_image).build();
		options_photo=new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).showImageOnLoading(R.drawable.default_photo).showImageOnFail(R.drawable.default_photo).build();
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
	
	public static void saveUserInfo(UserInfo info){
		SharedPreferences.Editor editor=MyApplication.editor;
		if (info.getCreateTime()!=null) {
			editor.putString("createTime", info.getCreateTime());
		}
		if (info.getCreateUser()!=null) {
			editor.putString("createUser", info.getCreateUser());
		}
		if (info.getUpdateTime()!=null) {
			editor.putString("updateTime", info.getUpdateTime());
		}
		if (info.getUpdateUser()!=null) {
			editor.putString("updateUser", info.getUpdateUser());
		}
		editor.putInt("id", info.getId());
		if (info.getMore1()!=null) {
			editor.putString("more1", info.getMore1());
		}
		if (info.getMore2()!=null) {
			editor.putString("more2", info.getMore2());
		}
		if (info.getMore3()!=null) {
			editor.putString("more3", info.getMore3());
		}
		
		if (info.getInt1()!=null) {
			editor.putInt("int1", info.getInt1());
		}
		if (info.getInt2()!=null) {
			editor.putInt("int2", info.getInt2());
		}
			editor.putInt("sorting", info.getSorting());
			
		if (info.getAccount()!=null) {
			editor.putString("account", info.getAccount());
		}
		if (info.getNickname()!=null) {
			editor.putString("nickname", info.getNickname());
		}
		
		
		if (info.getImage()!=null) {
			editor.putString("image", info.getImage());
		}
		if (info.getPreDate()!=null) {
			editor.putString("preDate", info.getPreDate());
		}
		if (info.getIsVip()!=null) {
			editor.putString("isVip", info.getIsVip());
		}
		if (info.getHospitalName()!=null) {
			editor.putString("hospitalName", info.getHospitalName());
		}
		
		
		if (info.getPassword()!=null) {
			editor.putString("password", info.getPassword());
		}
		if (info.getMobile()!=null) {
			editor.putString("mobile", info.getMobile());
		}
			editor.putInt("score", info.getScore());
			
		if (info.getIdentification()!=null) {
				editor.putString("identificationCode", info.getIdentification());
		}
		
		if (info.getAddress()!=null) {
			editor.putString("address", info.getAddress());
	    }
		if (info.getType()!=null) {
			editor.putString("type", info.getType());
	    }
		if (info.getChildTime()!=null) {
			editor.putString("childTime", info.getChildTime());
	    }
		
		if (info.getChildType()!=null) {
			editor.putString("childType", info.getChildType());
	    }
		if (info.getProfession()!=null) {
			editor.putString("profession", info.getProfession());
	    }
		editor.commit();
		
	}
	
	

}
