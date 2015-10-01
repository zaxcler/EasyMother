package com.easymother.main.my;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.RegularUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity1 extends Activity {
	private Button confirm;//确认
	private EditText phone_num;//电话号码
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_password);
		EasyMotherUtils.initTitle(this, "找回密码", false);
		MyApplication.addActivityToMap(this, "forgetpassword");
		findView();
		init();
	}

	private void findView() {
		confirm=(Button) findViewById(R.id.comfirm);
		phone_num=(EditText) findViewById(R.id.phone_num);
	}

	private void init() {
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if ("".equals(phone_num.getText().toString().trim())||(phone_num.getText().toString().trim()).equals(null)) {
					Toast.makeText(ForgetPasswordActivity1.this, "请输入注册时的电话号码", 0).show();
					return;
				}
				if (!RegularUtils.isPhoneNumber(phone_num.getText().toString().trim())||(phone_num.getText().toString().trim()).equals(null)) {
					Toast.makeText(ForgetPasswordActivity1.this, "请输入正确的电话号码", 0).show();
					return;
				}
				Intent intent=new Intent(ForgetPasswordActivity1.this, ForgetPasswordActivity2.class);
				intent.putExtra("phone", phone_num.getText().toString().trim());
				startActivity(intent);
			}
		});
		
	}
}
