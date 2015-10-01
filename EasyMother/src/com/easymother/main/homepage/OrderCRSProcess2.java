package com.easymother.main.homepage;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class OrderCRSProcess2 extends Activity {
	private Button begain_sign;//开始签约
	private MyGridView gridView;//时间表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_duanqi_order_next3);
		EasyMotherUtils.initTitle(this, "信息确认", false);
		MyApplication.addActivityToMap(this, "CRSprocess");
		findView();
		init();
	}

	private void findView() {
		begain_sign=(Button) findViewById(R.id.begain_next1);
		
		
	}

	private void init() {
		begain_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderCRSProcess2.this, OrderCRSProcess3.class);
				
			}
		});
		
		
	}

}
