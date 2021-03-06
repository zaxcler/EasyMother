package com.easymother.main.my;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.alidao.mama.WeiXinUtils;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.ConuntSize;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener {
	private LinearLayout about;
	private LinearLayout share;
	private LinearLayout suggestion;
	private LinearLayout delete_cache;
	private Button exit;

	private TextView size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_setting);
		EasyMotherUtils.initTitle(this, "设置", false);
		findView();
		init();
	}

	private void findView() {
		about = (LinearLayout) findViewById(R.id.about);
		share = (LinearLayout) findViewById(R.id.share);
		suggestion = (LinearLayout) findViewById(R.id.suggestion);
		delete_cache = (LinearLayout) findViewById(R.id.delete_cache);
		exit = (Button) findViewById(R.id.exit);
		size=(TextView) findViewById(R.id.size);
	}

	private void init() {
		File file=StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
		String sizeString=ConuntSize.getFormatSize(ConuntSize.getFolderSize(file));
		
		size.setText(sizeString);
		about.setOnClickListener(this);
		share.setOnClickListener(this);
		suggestion.setOnClickListener(this);
		delete_cache.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.about:
			EasyMotherUtils.goActivity(this, SettingAboutActivity.class);

			break;
		case R.id.share:
			WeiXinUtils.shareDownloadUrl(this);
			break;
		case R.id.suggestion:
			EasyMotherUtils.goActivity(this, SettingSueegstionActivity.class);
			break;
		case R.id.delete_cache:
			ConuntSize conuntSize=ConuntSize.getInstance();
			conuntSize.deleteFolderFile(Environment.getExternalStorageDirectory() + "/imageloader/Cache", true);
			ImageLoader.getInstance().clearMemoryCache();
			File file=StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
			String sizeString=ConuntSize.getFormatSize(ConuntSize.getFolderSize(file));
			size.setText(sizeString);
			Toast.makeText(this, "缓存清除成功！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.exit:
			Toast.makeText(SettingActivity.this, "成功退出", 0).show();
			MyApplication.editor.clear().commit();
			ImageLoader.getInstance().clearMemoryCache();
			NetworkHelper.doGet(BaseInfo.LOGOUT, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (JsonUtils.getRootResult(response).getIsSuccess()) {
						Toast.makeText(SettingActivity.this, "成功退出", 0).show();
						MyApplication.editor.clear().commit();
						ImageLoader.getInstance().clearMemoryCache();
					}else {
						Toast.makeText(SettingActivity.this, "未登录！", 0).show();
					}
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Toast.makeText(SettingActivity.this, "连接服务器失败", 0).show();
				}
			});
			
			break;
		}

	}

}
