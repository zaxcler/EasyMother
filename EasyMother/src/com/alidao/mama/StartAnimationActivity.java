package com.alidao.mama;

import com.easymother.main.MainActivity;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class StartAnimationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
			EasyMotherUtils.goActivity(this, MainActivity.class);
			this.finish();
	
	}
}
