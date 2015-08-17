package com.easymother.main.my;

import com.easymother.customview.CircleImageView;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class InfomationActivity extends Activity {
	private CircleImageView user_photo;
	private TextView nick_name;
	private TextView phone_num;
	private TextView hospital;
	private TextView address;
	private TextView time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_infomation);
		EasyMotherUtils.initTitle(this, "个人信息", false);
		findView();
		init();
	}

	private void findView() {
		// TODO Auto-generated method stub
		
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}

}
