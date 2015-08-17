package com.easymother.main.babytime;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class BabyTimeEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_edit);
		
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				
				
			}
		});
		EasyMotherUtils.initTitle(this, "囡囡", true);
	}
}

