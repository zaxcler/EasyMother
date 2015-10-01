package com.easymother.main.homepage;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class OrderYSandYYSProcess8 extends Activity {
	private Button complete;//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next8);
		EasyMotherUtils.initTitle(this, "支付成功", false);
		MyApplication.addActivityToMap(this, "YSprocess1");
		findView();
		init();
	}

	private void findView() {
		complete=(Button) findViewById(R.id.comfirm);
		
		
	}

	private void init() {
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderYSandYYSProcess8.this, OrderListActivity.class);
				OrderYSandYYSProcess8.this.finish();
				
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.destoryActivity("YSprocess1");
	}

}
