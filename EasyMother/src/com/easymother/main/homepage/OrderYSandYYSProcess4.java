package com.easymother.main.homepage;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class OrderYSandYYSProcess4 extends Activity {
	private Button complete;//
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next4);
		EasyMotherUtils.initTitle(this, "合同填写", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		intent=getIntent();
		findView();
		init();
	}

	private void findView() {
		complete=(Button) findViewById(R.id.complete);
		
		
	}

	private void init() {
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				intent.setClass(OrderYSandYYSProcess4.this, OrderYSandYYSProcess5.class);
				startActivity(intent);
				
			}
		});
	}

}
