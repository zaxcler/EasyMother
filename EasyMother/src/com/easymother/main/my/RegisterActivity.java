package com.easymother.main.my;

import com.easymother.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class RegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
	}

}
