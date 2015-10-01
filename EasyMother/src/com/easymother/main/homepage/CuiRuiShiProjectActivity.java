package com.easymother.main.homepage;

import com.alidao.mama.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class CuiRuiShiProjectActivity extends Activity implements OnClickListener{
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout4;
	private LinearLayout layout5;
	private LinearLayout layout6;
	private LinearLayout layout7;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cuirushi_project);
		EasyMotherUtils.initTitle(this, "项目介绍", false);
		intent=getIntent();
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
			intent.putExtra("CRS_Project", 1);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project1.html",R.drawable.baby_photo,"产前开奶");
			break;
		case R.id.layout2:
			intent.putExtra("CRS_Project", 2);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project2.html",R.drawable.baby_photo,"产后无痛点穴开奶");
			break;
		case R.id.layout3:
			intent.putExtra("CRS_Project", 3);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project3.html",R.drawable.baby_photo,"催乳");
			break;
		case R.id.layout4:
			intent.putExtra("CRS_Project", 4);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project4.html",R.drawable.baby_photo,"消淤");
			break;
		case R.id.layout5:
			intent.putExtra("CRS_Project", 5);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project5.html",R.drawable.baby_photo,"乳腺小叶增生护养");
			break;
		case R.id.layout6:
			intent.putExtra("CRS_Project", 6);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project6.html",R.drawable.baby_photo,"产后满月汗蒸排毒");
			break;
		case R.id.layout7:
			intent.putExtra("CRS_Project", 7);
			EasyMotherUtils.showDialog(this,"file:///android_asset/project7.html",R.drawable.baby_photo,"产后经络调理消月子病");
			break;
			

		}
		
	}
	
	
	

}
