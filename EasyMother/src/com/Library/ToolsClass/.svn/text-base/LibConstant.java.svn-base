package com.Library.ToolsClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.TextView;

public abstract class LibConstant
{
	/**
	 * 屏幕宽度
	 */
	private static int DisplayWidth; // 屏幕宽度
	/**
	 * 屏幕高度
	 */
	private static int DisplayHeight; // 屏幕高度
	/**
	 * 屏幕密度
	 */
	public static float Density;
	/**
	 * 程序名称
	 */
	public static String 程序名称 = "";

	/**
	 * 服务器IP
	 */
	public static String ServerIP = "";
	/**
	 * 服务器端口
	 */
	public static int ServerPort = 726;

	public static String InstallFilePackage = "MyHomeBusiness"; 
	/**
	 * 手机自带内存卡路径
	 */
	public static String InternalSdCardPath = Environment.getExternalStorageDirectory().getPath() + File.separator + InstallFilePackage;
	/**
	 * 手机扩展内存卡路径
	 */
	public static String ExternalSdCardPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "external_sd" + InstallFilePackage;// "/extStorages/SdCard"

	public static String MOIVE = "MOIVE";

	/**
	 * 系统运行时读取的路径(默认读取本机路径)
	 */
	public static String SdCardPath = InternalSdCardPath;

	public static String IMAGE_PATH = SdCardPath + File.separator + "Image" + File.separator;

	public enum 验证码
	{
		注册(1),

		取回密码(2);

		public final int value;

		private 验证码(int value)
		{
			this.value = value;
		}
	}

	// public static String WhichPath = "InternalSdCardPath";

	public static void initInstallationDirectory(Context context)
	{
		if (checkSaveLocationExists() == true && MemorySpaceCheck.getSDAvailableSize() > (2 * 1024) == true)
			SdCardPath = Environment.getExternalStorageDirectory().getPath() + File.separator + InstallFilePackage;
		else
			SdCardPath = context.getCacheDir().getAbsolutePath() + File.separator + InstallFilePackage;
	}

	/**
	 * 检查是否安装SD卡
	 * 
	 * @return
	 */
	public static boolean checkSaveLocationExists()
	{
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED))
		{
			status = true;
		}
		else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装外置的SD卡
	 * 
	 * @return
	 */
	public static boolean checkExternalSDExists()
	{

		Map<String, String> evn = System.getenv();
		return evn.containsKey("SECONDARY_STORAGE");
	}

	/**
	 * 初始化配置
	 */
	public static boolean init(Context context)
	{
		// 获取屏幕宽高
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Density = displayMetrics.density;
		DisplayWidth = displayMetrics.widthPixels;
		DisplayHeight = displayMetrics.heightPixels;

		return true;
	}

	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context)
	{
		if (DisplayWidth == 0)
		{
			init(context);
		}
		
		return DisplayWidth;
	}
	
	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context)
	{
		if (DisplayHeight == 0)
		{
			init(context);
		}
		
		return DisplayHeight;
	}
	

	/**
	 * 注意：关闭整个应用程序
	 * 
	 * @param context
	 */
	public static void exit(Context context)
	{
		ActivityManager activityMgr = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		activityMgr.restartPackage(context.getPackageName());
		System.exit(0);
	}

	/**
	 * 拨打电话
	 * 
	 * @param context
	 */
	public static void callPhone(Context context, String telNumber)
	{
		// 用intent启动拨打电话
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + telNumber));
		context.startActivity(intent);
	}

	/**
	 * 判断字符串是否是数字、小数、负数、整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDoubleNumeric(Object str)
	{
		if (StringUtils.isEmpty(str) == true)
			return false;
		Pattern pattern = Pattern.compile("^[-]?\\d+\\.?\\d*$");
		Matcher isNum = pattern.matcher(str + "");
		return isNum.matches();
	}
	
	/**
	 * 取小数点后两位
	 * 
	 * @param str
	 * @return
	 */
	public static String getDoubleTwoPoint(Object str)
	{
		if (StringUtils.isEmpty(str) == true)
			return "";
		
		DecimalFormat fmt=new DecimalFormat("##");
		return fmt.format(str);
		  
	}
	
	

	/**
	 * 判断字符串是否是负数、整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isIntNumeric(Object str)
	{
		if (StringUtils.isEmpty(str) == true)
			return false;
		Pattern pattern = Pattern.compile("^[-]?\\d+");
		Matcher isNum = pattern.matcher(str + "");
		return isNum.matches();
	}

	public static float getTextLength(TextView tvView)
	{
		if (tvView == null)
			return 0;
		Paint paint = new Paint();
		paint.setTextSize(tvView.getTextSize());
		float size = paint.measureText(tvView.getText().toString());
		return size;

	}
	
	//private static String logFilePath = LibConstant.SdCardPath + "/Log/log.txt";
	
	/**
	 * 保存异常日志
	 * 
	 * @param excp
	 */
	public static void saveLog(String excp)
	{
		// String errorlog = "log.txt";

//		excp = excp + "\r\n";
//		String savePath = "";
//		// String logFilePath = "";
//		FileWriter fw = null;
//		PrintWriter pw = null;
//		try
//		{
//			// 判断是否挂载了SD卡
//			String storageState = Environment.getExternalStorageState();
//			if (storageState.equals(Environment.MEDIA_MOUNTED))
//			{
//				savePath = LibConstant.SdCardPath + "/Log/";
//				File file = new File(savePath);
//				if (!file.exists())
//				{
//					file.mkdirs();
//				}
//				// logFilePath = savePath + errorlog;
//			}
//			else
//			// 没有挂载SD卡，无法写文件
//			{
//				return;
//			}
//
//			File logFile = new File(logFilePath);
//			if (!logFile.exists())
//			{
//				logFile.createNewFile();
//			}
//			fw = new FileWriter(logFile, true);
//			pw = new PrintWriter(fw);
//			// pw.println("--------------------" + (new Date().toLocaleString()) +
//			// "---------------------");
//
//			pw.write(excp);
//			// excp.printStackTrace(pw);
//			pw.close();
//			fw.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			if (pw != null)
//			{
//				pw.close();
//			}
//			if (fw != null)
//			{
//				try
//				{
//					fw.close();
//				}
//				catch (IOException e)
//				{
//				}
//			}
//		}

	}

}
