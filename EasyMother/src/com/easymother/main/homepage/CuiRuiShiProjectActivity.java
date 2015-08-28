package com.easymother.main.homepage;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CuiRuiShiProjectActivity extends Activity implements OnClickListener{
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout4;
	private LinearLayout layout5;
	private LinearLayout layout6;
	private LinearLayout layout7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cuirushi_project);
		
		findView();
		init();
		
	}

	private void findView() {
		layout1=(LinearLayout) findViewById(R.id.layout1);
		layout2=(LinearLayout) findViewById(R.id.layout2);
		layout3=(LinearLayout) findViewById(R.id.layout3);
		layout4=(LinearLayout) findViewById(R.id.layout4);
		layout5=(LinearLayout) findViewById(R.id.layout5);
		layout6=(LinearLayout) findViewById(R.id.layout6);
		layout7=(LinearLayout) findViewById(R.id.layout7);
		
	}

	private void init() {
		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
		layout6.setOnClickListener(this);
		layout7.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		
		switch (arg0.getId()) {
		case R.id.layout1:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout2:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout3:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout4:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout5:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout6:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.layout7:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
			

		}
		
	}
	
	
	

}
