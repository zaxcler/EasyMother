package com.easymother.main.my;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class ChangeNurseActivity extends Activity {
	private Intent intent;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_change);
		intent=getIntent();
		String type=intent.getStringExtra("type");
		id=intent.getIntExtra("id", 0);
		if ("CHANGENURSE".equals(type)) {
			EasyMotherUtils.initTitle(this, "申请更换", false);
		}
		if ("UNORDER".equals(type)) {
			EasyMotherUtils.initTitle(this, "申请退订", false);
		}
		
	}

}
