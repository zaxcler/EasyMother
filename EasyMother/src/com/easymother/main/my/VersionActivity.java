package com.easymother.main.my;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.VersionBean;
import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class VersionActivity extends Activity {

	protected static final int DOWN_ERROR = 0;
	private VersionBean bean;
	Handler handler = new Handler(){        
	    @Override    
	    public void handleMessage(Message msg) {    
	        // TODO Auto-generated method stub     
	        super.handleMessage(msg);    
	        switch (msg.what) {    
	       
	            case DOWN_ERROR:    
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
		findView();
		try {
			init();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findView() {

	}

	private void init() throws NameNotFoundException {

		checkVersion();
	}

	// 获取版本名称
	public String getVersionName() throws NameNotFoundException {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		return packInfo.packageName;
	}

	// 检查版本
	public void checkVersion() throws NameNotFoundException {
		RequestParams params = new RequestParams();
		params.put("version", getVersionName());
		NetworkHelper.doGet(BaseInfo.CHECK_VERSION, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					bean = JsonUtils.getResult(response, VersionBean.class);
					if ("0".equals(bean.getUpdateStatus())) {
						Toast.makeText(VersionActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
					}else if ("1".equals(bean.getUpdateStatus())) {
						showUpdataDialog();
					}else if ("2".equals(bean.getUpdateStatus())) {
						downLoadApk();
					}
					
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}

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
					// 获取当前下载量
					pd.setProgress(total);
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
	    builer.setPositiveButton("确定", new OnClickListener() {    
	    public void onClick(DialogInterface dialog, int which) {    
	            Log.i("下载apk,更新","下载apk,更新");    
	            downLoadApk();
	        }       
	    });    
	    //当点取消按钮时进行登录     
	    builer.setNegativeButton("取消", new OnClickListener() {    
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
	    final ProgressDialog pd;    //进度条对话框     
	    pd = new  ProgressDialog(this);    
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);    
	    pd.setMessage("正在下载更新");    
	    pd.show();    
	    new Thread(){    
	        @Override    
	        public void run() {    
	            try {    
	                File file = downloadAPK("dizhi", pd);    
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
