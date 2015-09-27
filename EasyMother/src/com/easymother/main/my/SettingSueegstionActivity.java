package com.easymother.main.my;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingSueegstionActivity extends Activity {
	private EditText suggestion;//意见
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_setting_suggestion);
		suggestion = (EditText) findViewById(R.id.suggestion);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.save));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						submit();
					}
				});
				
			}
		});
		EasyMotherUtils.initTitle(this, "设置", true);
		
		
		
		
	}
	protected void submit() {
		String content=suggestion.getText().toString().trim();
		RequestParams params=new RequestParams();
		params.put("content", content);
		NetworkHelper.doGet(BaseInfo.SAVESUGGESTION, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					Toast.makeText(SettingSueegstionActivity.this, "谢谢您宝贵的意见！祝您生活愉快！", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(SettingSueegstionActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(SettingSueegstionActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	

}
