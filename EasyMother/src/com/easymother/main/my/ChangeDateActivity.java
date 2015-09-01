package com.easymother.main.my;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeDateActivity extends Activity {
	private EditText beizhu;//备注
	private Button complete;//完成
	private Intent intent;
	private int id;
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_changetime);
		EasyMotherUtils.initTitle(this, "申请改期", false);
		intent=getIntent();
		id=intent.getIntExtra("id", 0);
		findView();
		init();
		
	}

	private void findView() {
		beizhu=(EditText) findViewById(R.id.beizhu);
		complete=(Button) findViewById(R.id.complete);
		
	}

	private void init() {
		
		/**
		 * 时间更改未写
		 */
		
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				content =beizhu.getText().toString().trim();
				RequestParams params=new RequestParams();
				params.put("orderId", id);
				params.put("descrition",content);
				params.put("type", "CHANGEDATE");
				NetworkHelper.doGet(BaseInfo.CHANGE_ORDER_MSG, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							Toast.makeText(ChangeDateActivity.this, "提交成功，等待审核", 0).show();
							ChangeDateActivity.this.finish();
						}
//						Log.e("修改订单",  response.toString());
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("修改订单连接服务器失败",  responseString);
					}
				});
				
			}
		});
	}

}
