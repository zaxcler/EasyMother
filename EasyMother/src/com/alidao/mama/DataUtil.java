package com.alidao.mama;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.sax.RootElement;
import android.util.Log;

public class DataUtil {
	private static DataUtil mDataUtil;
	private static int lang;
	private static int userid;

	public static DataUtil getInstance(Context context) {
		if (null == mDataUtil) {
			Log.i("DataUtil", "NULL");
			mDataUtil = new DataUtil();
		}
		return mDataUtil;
	}

	private boolean chao() {
		long nowt = 1369934619500L;

		long snd = System.currentTimeMillis();
		if (snd - nowt > 1720839860L) {
			return true;
		} else
			return false;
	}

	
	public static String getSettingStringValueByKey(Context context, String key) {
		SharedPreferences sharedata = context.getSharedPreferences("Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		return sharedata.getString(key, null);
	}

	public static int getSettingIntValueByKey(Context context, String key) {
		SharedPreferences sharedata = context.getSharedPreferences("Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		return sharedata.getInt(key, -1);
	}
	public static boolean getSettingBoolValueByKey(Context context, String key) {
		SharedPreferences sharedata = context.getSharedPreferences("Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
		return sharedata.getBoolean(key, false);
	}

	public static void setSettingStringValueByKey(Context context, String key,
			String value) {
		SharedPreferences.Editor sharedata = context.getSharedPreferences(
				"Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE)
				.edit();
		sharedata.putString(key, value);
		sharedata.commit();
	}

	public static void setSettingIntValueByKey(Context context, String key,
			Integer value) {
		SharedPreferences.Editor sharedata = context.getSharedPreferences(
				"Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE)
				.edit();
		sharedata.putInt(key, value);
		sharedata.commit();
	}
	public static void setSettingBoolValueByKey(Context context, String key,
			boolean value) {
		SharedPreferences.Editor sharedata = context.getSharedPreferences(
				"Setting",
				Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE)
				.edit();
		sharedata.putBoolean(key, value);
		sharedata.commit();
	}
	
	
}
