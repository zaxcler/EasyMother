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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangeNurseActivity extends Activity {
	private Intent intent;
	private int id;
	private Spinner spinner;
	private EditText beizhu;
	private Button complete;
	private String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_change);
		intent=getIntent();
		type=intent.getStringExtra("type");
		id=intent.getIntExtra("id", 0);
		if ("CHANGENURSE".equals(type)) {
			EasyMotherUtils.initTitle(this, "申请更换", false);
		}
		if ("UNORDER".equals(type)) {
			EasyMotherUtils.initTitle(this, "申请退订", false);
		}
		findView();
		init();
		
	}

	private void findView() {
		spinner=(Spinner) findViewById(R.id.spinner);
		beizhu=(EditText) findViewById(R.id.beizhu);
		complete=(Button) findViewById(R.id.complete);
	}

	private void init() {
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content=spinner.getSelectedItem().toString()+"\n"+beizhu.getText().toString().trim();
				RequestParams params=new RequestParams();
				params.put("orderId;", id);
				if ("CHANGENURSE".equals(type)) {
					params.put("type", "申请更换");
				}
				if ("UNORDER".equals(type)) {
					params.put("type", "申请退订");
				}
				params.put("descrition;", content);
				NetworkHelper.doGet(BaseInfo.CHANGE_ORDER_MSG, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							Toast.makeText(ChangeNurseActivity.this, "申请成功，等待审核", 0).show();
							ChangeNurseActivity.this.finish();
						}else {
							Toast.makeText(ChangeNurseActivity.this, "申请失败", 0).show();
						}
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Toast.makeText(ChangeNurseActivity.this, "连接服务器失败", 0).show();
					}
				});
			}
		});
		
	}

}
