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

public class OrderYSandYYSProcess extends Activity {
	private Button begain_sign;//开始签约
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order);
		EasyMotherUtils.initTitle(this, "护理师预约", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		findView();
		init();
	}

	private void findView() {
		begain_sign=(Button) findViewById(R.id.begain_sign);
		
		
	}

	private void init() {
		begain_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderYSandYYSProcess.this, OrderYSandYYSProcess1.class);
				
			}
		});
	}

}
