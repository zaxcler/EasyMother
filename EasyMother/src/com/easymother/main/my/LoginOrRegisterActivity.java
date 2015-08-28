package com.easymother.main.my;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.easymother.bean.LoginResult;
import com.easymother.bean.UserInfo;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginOrRegisterActivity extends Activity implements OnClickListener{
	private TextView register;//注册
	private TextView forget_password;//忘记密码
	private ImageView back;
	private EditText phone_num;//电话号码
	private EditText password;//密码
	
	private Button login;//登录
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_or_login);
		MyApplication.addActivityToMap(this, "login_process");
		findView();
		init();
	}

	private void findView() {
		register=(TextView) findViewById(R.id.textView2);
		back=(ImageView) findViewById(R.id.imageView1);
		forget_password=(TextView) findViewById(R.id.forget_password);
		phone_num=(EditText) findViewById(R.id.phone_num);
		password=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);
	}

	private void init() {
		register.setOnClickListener(this);
		forget_password.setOnClickListener(this);
		back.setOnClickListener(this);
		phone_num.setOnClickListener(this);
		password.setOnClickListener(this);
		login.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.textView2:
			EasyMotherUtils.goActivity(this, RegisterActivity.class);
			break;

		case R.id.forget_password:
			EasyMotherUtils.goActivity(this, ForgetPasswordActivity1.class);
			break;
		case R.id.imageView1:
			this.finish();
			break;
		case R.id.login:
			RequestParams params=new RequestParams();
			if ("".equals(phone_num.getText().toString().trim())||phone_num.getText().toString().trim()==null) {
				Toast.makeText(LoginOrRegisterActivity.this, "请输入账号", 0).show();
				break;
			}
			if ("".equals(password.getText().toString().trim())||password.getText().toString().trim()==null) {
				Toast.makeText(LoginOrRegisterActivity.this, "请输入密码", 0).show();
				break;
			}
			params.put("mobile", phone_num.getText().toString().trim());
			params.put("password", password.getText().toString().trim());
			
			NetworkHelper.doGet(BaseInfo.LOGIN, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (JsonUtils.getRootResult(response).getIsSuccess()) {
						LoginResult result=JsonUtils.getLoginResult(response);
						//保存用户信息到本地
						MyApplication.saveUserInfo(result.getUserInfo());
						Log.e("LoginResult", result.getUserInfo().toString());
						Toast.makeText(LoginOrRegisterActivity.this, "登录成功", 0).show();
						//保存登录状态
						MyApplication.editor.putBoolean("isLogin", true).commit();
						Log.e("登录后的appToken", result.getAppToken());
						//保存登录后的appToken
						MyApplication.editor.putString("appToken", result.getAppToken()).commit();
						LoginOrRegisterActivity.this.finish();
						
					}else {
						password.setText("");
						Toast.makeText(LoginOrRegisterActivity.this, "账号或密码输入错误", 0).show();
					}
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
					Toast.makeText(LoginOrRegisterActivity.this, "连接服务器失败", 0).show();
				}
			});
			break;
		}
		
		
	}
	
}
