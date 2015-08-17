package com.easymother.main.homepage;

import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;

public class OrderCRSProcess1 extends Activity {
	private Button submit;//开始签约
	private MyGridView gridView;//时间表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_crs_order_next1);
		EasyMotherUtils.initTitle(this, "信息填写", false);
		MyApplication.addActivityToMap(this, "CRSprocess");
		findView();
		init();
	}

	private void findView() {
		submit=(Button) findViewById(R.id.submit);
		
		
	}

	private void init() {
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderCRSProcess1.this, OrderCRSProcess2.class);
				
			}
		});
		
		
	}

}
