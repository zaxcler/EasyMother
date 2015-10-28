package com.easymother.main.my;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Library.ToolsClass.StringUtils;
import com.alidao.mama.R;
import com.alidao.mama.StartAnimationActivity;
import com.easymother.bean.VersionBean;
import com.easymother.configure.BaseInfo;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.NetworkHelper;
import com.shelwee.update.UpdateHelper;
import com.shelwee.update.listener.OnUpdateListener;
import com.shelwee.update.pojo.UpdateInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class VersionActivity extends Activity {
	private LinearLayout linearLayout1;
	private LinearLayout linearLayout2;
	private LinearLayout about;

	protected static final int DOWN_ERROR = 0;
	protected static final int UPDATE_PROGRESSBAR = 1;//更新progressbar值
	private VersionBean bean;
	private ProgressDialog pd;
	private int progress=0;
	private File file;
	private SharedPreferences preferences_update;
	Handler handler = new Handler(){        
	    @Override    
	    public void handleMessage(Message msg) {    
	        // TODO Auto-generated method stub     
	        super.handleMessage(msg);   
	        pd.setProgress(msg.what);
	        switch (msg.what) {    
	       
	            case DOWN_ERROR:    
	                //下载apk失败     
	                Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();    
	            break;    
	            case UPDATE_PROGRESSBAR:    
	                //下载apk失败     
	                Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();    
	            break; 
	            }    
	    }    
	}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_version);
		EasyMotherUtils.initTitle(this, "版本更新", false);
		preferences_update = getSharedPreferences("Updater", Context.MODE_PRIVATE);
		findView();
		try {
			init();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findView() {

		about=(LinearLayout) findViewById(R.id.about);
		linearLayout1=(LinearLayout) findViewById(R.id.linearLayout1);
		linearLayout2=(LinearLayout) findViewById(R.id.linearLayout2);
		
	}

	private void init() throws NameNotFoundException {
//		pd=new ProgressDialog(getApplicationContext());
		if (!NetworkHelper.isNetworkConnected(this)) {
			Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
			return;
		}
		linearLayout2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EasyMotherUtils.goActivity(VersionActivity.this, StartAnimationActivity.class);
			}
		});
		
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkVersion();
				
			}
		});
	}

	private void checkVersion()
	{
		String url=BaseInfo.BASE_URL+"resources/images/update.json";
		Log.e("url", url);
		final UpdateHelper updateHelper = new UpdateHelper.Builder(this).checkUrl(url).isAutoInstall(true) // 设为false需在下载完手动点击安装;默认为true，下载后自动安装。
				.build();
		updateHelper.check(new OnUpdateListener() {
			
			@Override
			public void onStartDownload() {
				
			}
			
			@Override
			public void onStartCheck() {
				Toast.makeText(VersionActivity.this, "检查最新版本...", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFinshDownload() {
				// TODO Auto-generated method stub
				Toast.makeText(VersionActivity.this, "下载完成...", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFinishCheck(UpdateInfo info) {
				// 如果当前的版本 小于或等于上次升级的版本，则删除旧版APK
//				String lastestVersionCode = preferences_update.getString("lastestVersionCode", "0");
//				if (isIntNumeric(lastestVersionCode) == true)
//				{
//					int versionCode = Integer.parseInt(lastestVersionCode);
					try {
						if (Integer.valueOf(info.getVersionCode())>getPackageManager().getPackageInfo(getPackageName(),0).versionCode)
						{
//							updateHelper.check();
							updateHelper.showUpdateUI(info);
						}else {
							Toast.makeText(VersionActivity.this, "当前已是最新版本！", Toast.LENGTH_SHORT).show();
						}
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}
//				
			}
			
			@Override
			public void onDownloading(int progress) {
				
			}
		});
	}
	
	public boolean isIntNumeric(Object str)
	{
		if (StringUtils.isEmpty(str) == true)
			return false;
		Pattern pattern = Pattern.compile("^[-]?\\d+");
		Matcher isNum = pattern.matcher(str + "");
		return isNum.matches();
	}
	
	
	
	//---------下面没有用到-----------
//	// 获取版本名称
//	public int getVersionName() throws NameNotFoundException {
//		// 获取packagemanager的实例
//		PackageManager packageManager = getPackageManager();
//		// getPackageName()是你当前类的包名，0代表是获取版本信息
//		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
//		return packInfo.versionCode;
//	}

//	// 检查版本
//	public void checkVersion() throws NameNotFoundException {
//		RequestParams params = new RequestParams();
//		params.put("version", getVersionName());
//		NetworkHelper.doGet(BaseInfo.CHECK_VERSION, params, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//				super.onSuccess(statusCode, headers, response);
//				if (JsonUtils.getRootResult(response).getIsSuccess()) {
//					bean = JsonUtils.getResult(response, VersionBean.class);
//					if ("0".equals(bean.getUpdateStatus())) {
//						Toast.makeText(VersionActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
//					}else if ("1".equals(bean.getUpdateStatus())) {
//						showUpdataDialog();
//					}else if ("2".equals(bean.getUpdateStatus())) {
//						downLoadApk();
//					}
//				}else {
//					Toast.makeText(VersionActivity.this, JsonUtils.getRootResult(response).getMessage(), Toast.LENGTH_SHORT).show();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				super.onFailure(statusCode, headers, responseString, throwable);
//				Toast.makeText(VersionActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
//			}
//		});
//	}

	// 下载新的apk
	public File downloadAPK(String path, ProgressDialog pd) throws Exception {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file;
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				// 获取到文件的大小
				pd.setMax(conn.getContentLength());
				InputStream is = conn.getInputStream();
				file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				int total = 0;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total += len;
					progress=total;
					// 获取当前下载量
//					pd.setProgress(total);
				}
				fos.close();
				bis.close();
				is.close();
				return file;
		}
		else {
			return null;
		}
		
	}
	/*  
	 *   
	 * 弹出对话框通知用户更新程序   
	 *   
	 * 弹出对话框的步骤：  
	 *  1.创建alertDialog的builder.    
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮  
	 *  3.通过builder 创建一个对话框  
	 *  4.对话框show()出来    
	 */    
	protected void showUpdataDialog() {    
	    AlertDialog.Builder builer = new Builder(this) ; 
	    AlertDialog dialog;
	    builer.setTitle("版本升级");    
	    builer.setMessage(bean.getUpdateMsg());    
	    //当点确定按钮时从服务器上下载 新的apk 然后安装      
	    builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {    
	    public void onClick(DialogInterface dialog, int which) {    
	            Log.i("下载apk,更新","下载apk,更新");    
	            downLoadApk();
	        }       
	    });    
	    //当点取消按钮时进行登录     
	    builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {    
	        public void onClick(DialogInterface dialog, int which) {  
	        	dialog.dismiss();
	        }    
	    });    
	    dialog = builer.create();    
	    dialog.show();    
	}  
	
	/*  
	 * 从服务器中下载APK  
	 */    
	protected void downLoadApk() {    
	    pd = new  ProgressDialog(this);    
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);    
	    pd.setMessage("正在下载更新"); 
	    pd.setProgress(0);
	    pd.setMax(100);
	    pd.show();    
	    new Thread(){    
	        @Override    
	        public void run() {    
	            try {    
	                File file = downloadAPK("http://zhonglv.hzlianhai.com/resources/images/EasyMother.apk", pd); 
					
	                for (int i = 0; i < 20; i++) {
	                	 Message msg=Message.obtain();
	 					msg.what=progress;
	 					handler.sendMessage(msg);
					}
	               
	                sleep(3000);    
	                installApk(file);    
	                pd.dismiss(); //结束掉进度条对话框     
	            } catch (Exception e) {    
	                Message msg = new Message();    
	                msg.what = DOWN_ERROR;    
	                handler.sendMessage(msg);    
	                e.printStackTrace();    
	            }    
	        }}.start();    
	}  
	
	
	//安装apk      
	protected void installApk(File file) {    
	    Intent intent = new Intent();    
	    //执行动作     
	    intent.setAction(Intent.ACTION_VIEW);    
	    //执行的数据类型     
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了      
	    startActivity(intent);    
	}   

}
