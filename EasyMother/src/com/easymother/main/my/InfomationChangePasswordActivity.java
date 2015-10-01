package com.easymother.main.my;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InfomationChangePasswordActivity extends Activity {
	private EditText oldPassword;
	private EditText newPassword;
	private Button comfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_infomation_chengetpassword);
		EasyMotherUtils.initTitle(this, "修改密码", false);
		findView();
		init();
	}

	private void findView() {
		oldPassword = (EditText) findViewById(R.id.oldpassword);
		newPassword = (EditText) findViewById(R.id.newpassword);
		comfirm = (Button) findViewById(R.id.comfirm);

	}

	private void init() {
		comfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 如果密码和旧密码不一致就不能修改
				if ("".equals(oldPassword.getText().toString().trim()) || oldPassword.getText().toString().trim() == null || "".equals(newPassword.getText().toString().trim()) || newPassword.getText().toString().trim() == null) {
					Toast.makeText(InfomationChangePasswordActivity.this, "密码不能为空", 0).show();
					return;
				}
				RequestParams params=new RequestParams();
				params.put("oldPassword", oldPassword.getText().toString().trim());
				params.put("newPassword", newPassword.getText().toString().trim());
				NetworkHelper.doGet(BaseInfo.CHANGEPASSWORD, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							com.easymother.bean.Root root1=JsonUtils.getResult(response, com.easymother.bean.Root.class);
							if (root1.getIsSuccess()) {
								MyApplication.destoryActivity("infomationactivity");
								MyApplication.preferences.edit().clear().commit();
								EasyMotherUtils.goActivity(InfomationChangePasswordActivity.this, LoginOrRegisterActivity.class);
								InfomationChangePasswordActivity.this.finish();
								
								
							}
							Toast.makeText(InfomationChangePasswordActivity.this, root1.getMessage(), 0).show();
							
						}else {
							Toast.makeText(InfomationChangePasswordActivity.this, "修改密码失败！", 0).show();
						}
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Toast.makeText(InfomationChangePasswordActivity.this, "连接服务器失败", 0).show();
						Log.e("responseString--修改密码", responseString);
					}
				});
			}
		});
	}

}
