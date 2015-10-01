package com.easymother.main.my;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.RegularUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	private LinearLayout country;//地区选择
	private EditText phone_num;//电话（账号）
	private EditText identity_editview;//填写验证码的窗口
	private TextView identity_code;//验证码
	private EditText password;
	private Button confirm;
	
	private boolean canGetIdentityCode=true;//能否获取验证码
	private TimerTask timerTask;//短信可以重新获取倒计时
	private Timer timer;//计时器
	private int time=180;//重新获取验证码的时间
	private String appTokenId;//注册时获得的tokenid
	
	//倒计时，通过handler显示在界面上
	private Handler handler=new Handler(){
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			if (msg.arg1!=0) {
				identity_code.setText(msg.arg1+"秒后重新获取");
				
			}else {
				canGetIdentityCode=true;
				identity_code.setText("重新获取验证码");
				timer.cancel();
				identity_code.setTextColor(getResources().getColor(R.color.boroblacktext));
				identity_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.boro_border));
				time=180;
			}
			
		};
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		EasyMotherUtils.initTitle(this, "注册", false);
		MyApplication.addActivityToMap(this, "login_process");
		findView();
		init();
	}


	private void findView() {
		country=(LinearLayout) findViewById(R.id.country);
		phone_num=(EditText) findViewById(R.id.phone_num);
		identity_editview=(EditText) findViewById(R.id.identity_editview);
		identity_code=(TextView) findViewById(R.id.identity_code);
		password=(EditText) findViewById(R.id.password);
		confirm=(Button) findViewById(R.id.confirm);
	}


	private void init() {
		country.setOnClickListener(this);
		identity_code.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}


	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.country:
			
			break;

        case R.id.identity_code:
        	if (canGetIdentityCode) {
        		canGetIdentityCode=false;
        		String phone=phone_num.getText().toString().trim();
        		RequestParams params=new RequestParams();
        		if ("".equals(phone)||phone.equals(null)) {
    				Toast.makeText(this, "电话号码不能为空", 0).show();
    				canGetIdentityCode=true;
    				return;
    			}
        		params.put("mobile", phone);
        		params.put("type", "regist");
        		NetworkHelper.doGetNoToken(BaseInfo.SEND_SMS_CODE, params, new JsonHttpResponseHandler(){
        			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        				if (JsonUtils.getRootResult(response).getIsSuccess()) {
        				identity_code.setTextColor(getResources().getColor(R.color.white));
        				identity_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.boro_solid));
        				timerTask=new TimerTask() {
        					
        					@Override
        					public void run() {
        						--time;
        						Message msg=Message.obtain();
        						msg.arg1=time;
        						handler.sendMessage(msg);
        					}
        				};
        				timer=new Timer(true);
        				timer.schedule(timerTask, 1000, 1000);
        				appTokenId=JsonUtils.getRootResult(response).getResult().toString();
        				Log.e("apptokenid", JsonUtils.getRootResult(response).getResult().toString());
        				}else {
        					Toast.makeText(RegisterActivity.this, JsonUtils.getRootResult(response).getMessage(), 0).show();
						}
        			};
        			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        				Toast.makeText(RegisterActivity.this, "连接服务器失败", 0).show();
        			};
        		});
        		
				
			}
        	
			
			break;
        case R.id.confirm:
        	String phone=phone_num.getText().toString().trim();
        	String identity_code_String=identity_editview.getText().toString().trim();
        	String passwordString=password.getText().toString().trim();
        	if ("".equals(phone)||phone.equals(null)) {
				Toast.makeText(this, "电话号码不能为空", 0).show();
				return;
			}
        	if ("".equals(identity_code_String)||identity_code_String.equals(null)) {
				Toast.makeText(this, "验证码不能为空", 0).show();
				return;
			}
        	if ("".equals(passwordString)||passwordString.equals(null)) {
				Toast.makeText(this, "密码不能为空", 0).show();
				return;
			}
        	if (passwordString.length()<6) {
        		Toast.makeText(this, "密码长度大于6", 0).show();
				return;
			}
        	if (passwordString.length()>20) {
        		Toast.makeText(this, "密码长度过大", 0).show();
				return;
			}
        	if (!RegularUtils.isPhoneNumber(phone)) {
        		Toast.makeText(this, "请输入正确的电话号码", 0).show();
				return;
			}
        	RequestParams params=new RequestParams();
        	params.put("mobile", phone);
        	params.put("password", passwordString);
        	params.put("yzm", identity_code_String);
        	params.put("appTokenId", appTokenId);
        	NetworkHelper.doGetNoToken(BaseInfo.REGIST, params, new JsonHttpResponseHandler(){
        		@Override
        		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        			super.onSuccess(statusCode, headers, response);
        			Log.e("注册信息", response.toString());
        			if (JsonUtils.getRootResult(response).getIsSuccess()) {
        				Toast.makeText(RegisterActivity.this, "注册成功", 0).show();
//        				EasyMotherUtils.goActivity(RegisterActivity.this, InfomationActivity.class);
        				Toast.makeText(RegisterActivity.this, "请前往完善信息", Toast.LENGTH_SHORT).show();
            			RegisterActivity.this.finish();
					}else {
						Toast.makeText(RegisterActivity.this, JsonUtils.getRootResult(response).getMessage(), 0).show();
					}
        			
        		}
        		@Override
        		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        			super.onFailure(statusCode, headers, responseString, throwable);
        			Log.e("responseString----->", responseString);
        			Toast.makeText(RegisterActivity.this, "连接服务器失败", 0).show();
        		}
        		
        	});
        	
			break;
		}
		
	}
	
	

}
