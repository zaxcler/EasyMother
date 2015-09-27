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

public class OrderCRSProcess3 extends Activity {
	private Button comfirm;//开始签约
	private MyGridView gridView;//时间表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_crs_order_success);
		EasyMotherUtils.initTitle(this, "付款成功", false);
		MyApplication.addActivityToMap(this, "CRSprocess1");
		findView();
		init();
	}

	private void findView() {
		comfirm=(Button) findViewById(R.id.comfirm);
		
		
	}

	private void init() {
		comfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderCRSProcess3.this, OrderListActivity.class);
				
			}
		});
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.destoryActivity("CRSprocess");
	}

}
