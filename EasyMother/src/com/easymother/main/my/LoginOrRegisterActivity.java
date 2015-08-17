package com.easymother.main.my;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class LoginOrRegisterActivity extends Activity implements OnClickListener{
	private TextView register;//注册
	private TextView forget_password;//忘记密码
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_or_login);
		findView();
		init();
	}

	private void findView() {
		register=(TextView) findViewById(R.id.textView2);
		forget_password=(TextView) findViewById(R.id.forget_password);
	}

	private void init() {
		register.setOnClickListener(this);
		forget_password.setOnClickListener(this);
		
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
		}
	}

}
