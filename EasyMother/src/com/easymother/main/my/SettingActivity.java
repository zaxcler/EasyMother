package com.easymother.main.my;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingActivity extends Activity implements OnClickListener {
	private LinearLayout about;
	private LinearLayout share;
	private LinearLayout suggestion;
	private LinearLayout delete_cache;
	private Button exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_setting);
		EasyMotherUtils.initTitle(this, "设置", false);
		findView();
		init();
	}

	private void findView() {
		about = (LinearLayout) findViewById(R.id.about);
		share = (LinearLayout) findViewById(R.id.share);
		suggestion = (LinearLayout) findViewById(R.id.suggestion);
		delete_cache = (LinearLayout) findViewById(R.id.delete_cache);
		exit = (Button) findViewById(R.id.exit);
	}

	private void init() {
		

		about.setOnClickListener(this);
		share.setOnClickListener(this);
		suggestion.setOnClickListener(this);
		delete_cache.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.about:
			EasyMotherUtils.goActivity(this, SettingAboutActivity.class);

			break;
		case R.id.share:
			
			break;
		case R.id.suggestion:
			EasyMotherUtils.goActivity(this, SettingSueegstionActivity.class);
			break;
		case R.id.delete_cache:

			break;
		case R.id.button:

			break;
		}

	}

}
