package com.easymother.main.homepage;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OrderYSandYYSProcess3 extends Activity {
	private TextView begain_sign;//开始签约
	private WebView hetong;//合同
	
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next3);
		EasyMotherUtils.initTitle(this, "合同填写", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		findView();
		init();
	}

	private void findView() {
		begain_sign=(TextView) findViewById(R.id.begain_next1);
		hetong=(WebView) findViewById(R.id.hetong);
		
		
	}

	private void init() {
		intent=getIntent();
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		hetong.loadUrl("file:///android_asset/demo.html");
		begain_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				intent.setClass(OrderYSandYYSProcess3.this, OrderYSandYYSProcess4.class);
				startActivity(intent);
				
			}
		});
	}

}
