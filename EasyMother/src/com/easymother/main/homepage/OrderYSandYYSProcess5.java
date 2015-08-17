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

public class OrderYSandYYSProcess5 extends Activity {
	private TextView complete;//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next5);
		EasyMotherUtils.initTitle(this, "合同预览", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		findView();
		init();
	}

	private void findView() {
		complete=(TextView) findViewById(R.id.button);
		
		
	}

	private void init() {
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderYSandYYSProcess5.this, OrderYSandYYSProcess6.class);
				
			}
		});
	}

}
