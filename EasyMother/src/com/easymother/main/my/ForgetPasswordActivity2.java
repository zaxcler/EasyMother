package com.easymother.main.my;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPasswordActivity2 extends Activity implements OnClickListener {

	private TextView notice;// 提示信息
	private EditText identity_edit;// 验证码填写框
	private EditText new_password;// 新密码
	private TextView identity_code;// 验证码
	private Button confirm;// 确认
	private ImageView showpassword;// 显示密码

	private TimerTask task;// 计时器任务
	private Timer timer;// 计时器
	private boolean canGetIdentity=true;
	private int time=60;
	private String phone;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if (msg.arg1==0) {
				identity_code.setText("重新获取验证码");
				time=60;
				timer.cancel();
				canGetIdentity=true;
				identity_code.setTextColor(getResources().getColor(R.color.boroblacktext));
				identity_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.boro_border));
			}else {
				identity_code.setText(msg.arg1+"秒后重新获取");
			}
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_password_confirm);
		EasyMotherUtils.initTitle(this, "找回密码", false);
		findView();
		init();
	}

	private void findView() {
		notice = (TextView) findViewById(R.id.textView1);
		identity_code = (TextView) findViewById(R.id.identity_code);
		identity_edit = (EditText) findViewById(R.id.identity_edit);
		new_password = (EditText) findViewById(R.id.new_password);
		confirm = (Button) findViewById(R.id.confirm);
		showpassword = (ImageView) findViewById(R.id.showpassword);
	}

	private void init() {
		Intent intent = getIntent();
		phone = intent.getStringExtra("phone");
		notice.setText("短信将发送至你的手机：" + phone);
		identity_code.setOnClickListener(this);
		confirm.setOnClickListener(this);
		showpassword.setOnClickListener(this);

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.identity_code:
			if (canGetIdentity) {
				canGetIdentity=false;
				//为了向下兼容，使用过时的api
				RequestParams params=new RequestParams();
				params.put("mobile", phone);
				NetworkHelper.doGetNoToken(BaseInfo.SEND_SMS_CODE, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							identity_code.setTextColor(getResources().getColor(R.color.white));
							identity_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.boro_solid));
							task=new TimerTask() {
								
								@Override
								public void run() {
									--time;
									Message message=Message.obtain();
									message.arg1=time;
									handler.sendMessage(message);
								}
							};
							timer=new Timer(true);
							timer.schedule(task, 1000, 1000);
						}else {
        					Toast.makeText(ForgetPasswordActivity2.this, JsonUtils.getRootResult(response).getMessage(), 0).show();
						}
					}
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        				Toast.makeText(ForgetPasswordActivity2.this, "连接服务器失败", 0).show();
        			};
				});
				
				
			}
			break;

		case R.id.confirm:

			break;
		case R.id.showpassword:

			break;
		}
	}

}
