package com.easymother.main.my;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ForgetPasswordActivity1 extends Activity {
	private Button confirm;//确认
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget_password);
		findView();
		init();
	}

	private void findView() {
		confirm=(Button) findViewById(R.id.comfirm);
	}

	private void init() {
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(ForgetPasswordActivity1.this, ForgetPasswordActivity2.class);
				
			}
		});
		
	}
}
