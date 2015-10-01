package com.shelwee.update;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Library.ToolsClass.CustomDialog;
import com.Library.ToolsClass.LibConstant;
import com.Library.ToolsClass.StringUtils;
import com.alidao.mama.R;
import com.shelwee.update.listener.OnUpdateListener;
import com.shelwee.update.pojo.UpdateInfo;
import com.shelwee.update.utils.HttpRequest;
import com.shelwee.update.utils.JSONHandler;
import com.shelwee.update.utils.NetWorkUtils;
import com.shelwee.update.utils.URLUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by ShelWee on 14-5-8.<br/>
 * Usage:
 * 
 * <pre>
 * UpdateManager updateManager = new UpdateManager.Builder(this).checkUrl(&quot;http://localhost/examples/version.jsp&quot;).isAutoInstall(false).build();
 * updateManager.check();
 * </pre>
 * 
 * @author ShelWee(<a href="http://www.shelwee.com">http://www.shelwee.com</a>)
 * @version 0.1 beta
 */
public class UpdateHelper
{

	private Context mContext;
	private String checkUrl;
	private boolean isAutoInstall;
	private OnUpdateListener updateListener;
	private NotificationManager notificationManager;
	private Notification ntfBuilder;

	private static final int UPDATE_NOTIFICATION_PROGRESS = 0x1;
	private static final int COMPLETE_DOWNLOAD_APK = 0x2;
	private static final int DOWNLOAD_NOTIFICATION_ID = 0x3;
	private static final String PATH = Environment.getExternalStorageDirectory().getPath();
	private static final String SUFFIX = ".apk";
	private static final String APK_PATH = "APK_PATH";
	private static final String APP_NAME = "APP_NAME";
	private SharedPreferences preferences_update;

	private HashMap<String, String> cache = new HashMap<String, String>();

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case UPDATE_NOTIFICATION_PROGRESS:
				showDownloadNotificationUI((UpdateInfo) msg.obj, msg.arg1);
				break;
			case COMPLETE_DOWNLOAD_APK:

				// 把下载的文件路径保存在 SharedPreferences 中
				SharedPreferences.Editor editor = preferences_update.edit();
				editor.putString("filePath", cache.get(APK_PATH));
				editor.commit();

				if (UpdateHelper.this.isAutoInstall)
				{
					installApk(Uri.parse("file://" + cache.get(APK_PATH)));
				}
				else
				{
					ntfBuilder = new Notification(mContext.getApplicationInfo().icon, cache.get(APP_NAME), System.currentTimeMillis());
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse("file://" + cache.get(APK_PATH)), "application/vnd.android.package-archive");
					PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					ntfBuilder.setLatestEventInfo(mContext, "下载完成", "文件已下载完毕", contentIntent);
					//
					if (notificationManager == null)
					{
						notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
					}
					notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, ntfBuilder);
				}
				break;
			}
		}

	};
	public HttpURLConnection hurlconn;

	private UpdateHelper(Builder builder)
	{
		this.mContext = builder.context;
		this.checkUrl = builder.checkUrl;
		this.isAutoInstall = builder.isAutoInstall;
		preferences_update = mContext.getSharedPreferences("Updater", Context.MODE_PRIVATE);
	}

	/**
	 * 检查app是否有新版本，check之前先Builer所需参数
	 */
	public void check()
	{
		// 如果当前的版本 小于或等于上次升级的版本，则删除旧版APK
		String lastestVersionCode = preferences_update.getString("lastestVersionCode", "0");
		if (isIntNumeric(lastestVersionCode) == true)
		{
			int versionCode = Integer.parseInt(lastestVersionCode);
			if (versionCode <= getPackageInfo().versionCode)
			{
				String filePath = preferences_update.getString("filePath", "");
				if (StringUtils.isEmpty(filePath) != true)
				{
					File f = new File(filePath);
					f.delete();
				}
			}
		}

		check(null);
	}

	/**
	 * 判断字符串是否是负数、整数
	 * 
	 * @param str
	 * @return
	 */
	public boolean isIntNumeric(Object str)
	{
		if (StringUtils.isEmpty(str) == true)
			return false;
		Pattern pattern = Pattern.compile("^[-]?\\d+");
		Matcher isNum = pattern.matcher(str + "");
		return isNum.matches();
	}

	public void check(OnUpdateListener listener)
	{
		if (listener != null)
		{
			this.updateListener = listener;
		}
		if (mContext == null)
		{
			Log.e("NullPointerException", "The context must not be null.");
			return;
		}
		AsyncCheck asyncCheck = new AsyncCheck();
		asyncCheck.execute(checkUrl);

	}

	/**
	 * 2014-10-27新增流量提示框，当网络为数据流量方式时，下载就会弹出此对话框提示
	 * 
	 * @param updateInfo
	 */
	private void showNetDialog(final UpdateInfo updateInfo)
	{
		CustomDialog.Builder ibuilder = new CustomDialog.Builder(mContext);

		ibuilder.setTitle("下载提示");
		ibuilder.setMessage("您在目前的网络环境下继续下载将可能会消耗手机流量，请确认是否继续下载？");
		ibuilder.setNegativeButton("取消下载", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		ibuilder.setPositiveButton("继续下载", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				downAPPToast(mContext);

				AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
				asyncDownLoad.execute(updateInfo);
			}
		});
		ibuilder.create(false).show();
	}

	/**
	 * 下载app提示
	 * 
	 * @param context
	 */
	private void downAPPToast(Context context)
	{
		Toast.makeText(context, "应用进入后台下载，请到消息通知栏查看下载进度！", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 弹出提示更新窗口
	 * 
	 * @param updateInfo
	 */
	public UpdateInfo updateInfo2;

	public void showUpdateUI(final UpdateInfo updateInfo)
	{
		this.updateInfo2 = updateInfo;
		CustomDialog.Builder ibuilder = new CustomDialog.Builder(mContext);
		ibuilder.setTitle(StringUtils.StringToUIF8Format(updateInfo.getUpdateTips()));
		ibuilder.setMessage(StringUtils.StringToUIF8Format(updateInfo.getChangeLog()));

		ibuilder.setNegativeButton("下载", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
				int type = netWorkUtils.getNetType();
				if (type != 1)
				{
					showNetDialog(updateInfo);
				}
				else
				{
					downAPPToast(mContext);
					AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
					updateInfo2.setGoOn(false);
					asyncDownLoad.execute(updateInfo2);
				}
			}
		});
		ibuilder.setPositiveButton("下次再说", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		ibuilder.create(false).show();
	}

	/**
	 * 通知栏弹出下载提示进度
	 * 
	 * @param updateInfo
	 * @param progress
	 */
	private void showDownloadNotificationUI(UpdateInfo updateInfo, final int progress)
	{
		if (mContext != null)
		{
			String contentText = new StringBuffer().append(progress).append("%").toString();
			PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
			if (notificationManager == null)
			{
				notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			}
			ntfBuilder = new Notification(mContext.getApplicationInfo().icon, cache.get(APP_NAME), System.currentTimeMillis());
			ntfBuilder.flags = Notification.FLAG_ONGOING_EVENT;

			RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification_layout);
			// 指定个性化视图
			ntfBuilder.contentView = contentView;

			RemoteViews contentview = ntfBuilder.contentView;
			contentview.setTextViewText(R.id.tv_progress, progress + "%");
			contentview.setProgressBar(R.id.progressbar, 100, progress, false);
			notificationManager.notify(DOWNLOAD_NOTIFICATION_ID, ntfBuilder);
		}
	}

	/**
	 * 获取当前app版本
	 * 
	 * @return
	 * @throws android.content.pm.PackageManager.NameNotFoundException
	 */
	private PackageInfo getPackageInfo()
	{
		PackageInfo pinfo = null;
		if (mContext != null)
		{
			try
			{
				pinfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
			}
			catch (PackageManager.NameNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		return pinfo;
	}

	/**
	 * 检查更新任务
	 */
	private class AsyncCheck extends AsyncTask<String, Integer, UpdateInfo>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			if (UpdateHelper.this.updateListener != null)
			{
				UpdateHelper.this.updateListener.onStartCheck();
			}
		}

		@Override
		protected UpdateInfo doInBackground(String... params)
		{
			UpdateInfo updateInfo = null;
			if (params.length == 0)
			{
				Log.e("NullPointerException", " Url parameter must not be null.");
				return null;
			}
			String url = params[0];
			if (!URLUtils.isNetworkUrl(url))
			{
				Log.e("IllegalArgumentException", "The URL is invalid.");
				return null;
			}
			try
			{
				updateInfo = JSONHandler.toUpdateInfo(HttpRequest.get(url));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
			return updateInfo;
		}

		@Override
		protected void onPostExecute(UpdateInfo updateInfo)
		{
			super.onPostExecute(updateInfo);
			SharedPreferences.Editor editor = preferences_update.edit();
			if (mContext != null && updateInfo != null)
			{
				if (Integer.parseInt(updateInfo.getVersionCode()) > getPackageInfo().versionCode)
				{
					showUpdateUI(updateInfo);
					editor.putBoolean("hasNewVersion", true);
					editor.putString("lastestVersionCode", updateInfo.getVersionCode());
					editor.putString("lastestVersionName", updateInfo.getVersionName());
				}
				else
				{
					// Toast.makeText(mContext, "当前已是最新版",
					// Toast.LENGTH_LONG).show();
					editor.putBoolean("hasNewVersion", false);
				}
			}
			else
			{
				// Toast.makeText(mContext, "当前已是最新版",
				// Toast.LENGTH_LONG).show();
			}
			editor.putString("currentVersionCode", getPackageInfo().versionCode + "");
			editor.putString("currentVersionName", getPackageInfo().versionName);
			editor.commit();
			if (UpdateHelper.this.updateListener != null)
			{
				UpdateHelper.this.updateListener.onFinishCheck(updateInfo);
			}
		}
	}

	/**
	 * hurlconn.setRequestProperty("Range",
	 * "bytes="+downloadinfo.getCompleteload()+"-"+downloadinfo.getEndPoint()); hurlconn.connect();
	 * //if(hurlconn.getResponseCode()== HttpURLConnection.HTTP_OK){ InputStream instream =
	 * hurlconn.getInputStream(); RandomAccessFile rasf = new RandomAccessFile(file, "rwd");
	 * rasf.seek(downloadinfo.getCompleteload());
	 */
	/**
	 * 异步下载app任务
	 */
	public int completeData = 0;
	public long endData = 0;

	public class AsyncDownLoad extends AsyncTask<UpdateInfo, Integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(UpdateInfo... params)
		{
			try
			{
				URL url = new URL(params[0].getApkUrl());
				hurlconn = (HttpURLConnection) url.openConnection();
				// hurlconn.setDoInput(true);
				// hurlconn.setDoOutput(true);
				// hurlconn.setUseCaches(false);
				hurlconn.setRequestMethod("GET");
				hurlconn.setConnectTimeout(20000);
				// HttpGet httpGet = new HttpGet(params[0].getApkUrl());
				if (params[0].isGoOn())
				{
					hurlconn.setRequestProperty("Range", "bytes=" + completeData + "-" + endData);
					hurlconn.connect();
				}
				// hurlconn.connect();
				// HttpResponse response = httpClient.execute(httpGet);
				if (hurlconn.getResponseCode() != HttpURLConnection.HTTP_OK && !params[0].isGoOn())
				{

					Log.e("IllegalArgumentException", "APK路径出错，请检查服务端配置接口。");
					return false;
				}
				else
				{
					InputStream inputStream = hurlconn.getInputStream();
					// InputStream inputStream = entity.getContent();
					if (!params[0].isGoOn())
					{
						long total = hurlconn.getContentLength();
						endData = total;
					}
					String apkName = params[0].getAppName() + params[0].getVersionName() + SUFFIX;
					cache.put(APP_NAME, params[0].getAppName());
					cache.put(APK_PATH, PATH + File.separator + LibConstant.InstallFilePackage + File.separator + apkName);
					File savePath = new File(PATH + File.separator + LibConstant.InstallFilePackage);
					if (!savePath.exists())
						savePath.mkdirs();
					File apkFile = new File(savePath, apkName);
					if (apkFile.exists() && !params[0].isGoOn())
					{
						apkFile.delete();
					}
					RandomAccessFile fos = new RandomAccessFile(apkFile, "rwd");
					if (params[0].isGoOn())
					{
						fos.seek(completeData);
					}
					byte[] buf = new byte[1024];
					int count = 0;
					int length = -1;
					while ((length = inputStream.read(buf)) != -1)
					{
						fos.write(buf, 0, length);
						completeData += length;
						// completeData = count;
						int progress = (int) ((completeData / (float) endData) * 100);
						if (progress % 5 == 0)
						{
							handler.obtainMessage(UPDATE_NOTIFICATION_PROGRESS, progress, -1, params[0]).sendToTarget();
						}
						if (UpdateHelper.this.updateListener != null)
						{
							UpdateHelper.this.updateListener.onDownloading(progress);
						}
					}
					inputStream.close();
					fos.close();
				}

			}
			catch (IOException e)
			{

				// 断网情况下

				new Thread()
				{
					public void run()
					{
						super.run();
						boolean b = false;
						while (!isNetworkConnected(mContext))
						{
							b = false;
							try
							{
								Thread.sleep(1000);

							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
						// 网络恢复，重新下载
						AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
						updateInfo2.setGoOn(true);
						asyncDownLoad.execute(updateInfo2);
					};
				}.start();

				return false;
			}
			return true;
		}

		public boolean isNetworkConnected(Context context)
		{
			if (context != null)
			{
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
				if (mNetworkInfo != null)
				{
					return mNetworkInfo.isAvailable();
				}
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean flag)
		{
			if (flag)
			{
				handler.obtainMessage(COMPLETE_DOWNLOAD_APK).sendToTarget();
				if (UpdateHelper.this.updateListener != null)
				{
					UpdateHelper.this.updateListener.onFinshDownload();
				}
			}
			else
			{
				Log.e("Error", "下载失败。");
			}
		}
	}

	public static class Builder
	{
		private Context context;
		private String checkUrl;
		private boolean isAutoInstall = true;

		public Builder(Context ctx)
		{
			this.context = ctx;
		}

		/**
		 * 检查是否有新版本App的URL接口路径
		 * 
		 * 升级文件格式必须是UTF-8
		 * 
		 * @param checkUrl
		 * @return
		 */
		public Builder checkUrl(String checkUrl)
		{
			this.checkUrl = checkUrl;
			return this;
		}

		/**
		 * 是否需要自动安装, 不设置默认自动安装
		 * 
		 * @param isAuto
		 *            true下载完成后自动安装，false下载完成后需在通知栏手动点击安装
		 * @return
		 */
		public Builder isAutoInstall(boolean isAuto)
		{
			this.isAutoInstall = isAuto;
			return this;
		}

		/**
		 * 构造UpdateManager对象
		 * 
		 * @return
		 */
		public UpdateHelper build()
		{
			return new UpdateHelper(this);
		}
	}

	private void installApk(Uri data)
	{
		if (mContext != null)
		{
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(data, "application/vnd.android.package-archive");
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(i);
			if (notificationManager != null)
			{
				notificationManager.cancel(DOWNLOAD_NOTIFICATION_ID);
			}
		}
		else
		{
			Log.e("NullPointerException", "The context must not be null.");
		}

	}

}