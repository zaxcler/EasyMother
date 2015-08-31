package com.easymother.main.homepage;

import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OrderYSandYYSProcess6 extends Activity {
	private Button complete;//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next6);
		EasyMotherUtils.initTitle(this, "签订成功", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		findView();
		init();
	}

	private void findView() {
		complete=(Button) findViewById(R.id.begain_next6);
		
		
	}

	private void init() {
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EasyMotherUtils.goActivity(OrderYSandYYSProcess6.this, OrderYSandYYSProcess7.class);
				
			}
		});
	}

}
