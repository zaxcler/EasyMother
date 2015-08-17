package com.easymother.main.homepage;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
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
			showDetail(R.layout.cuiruishi_project1_detail);
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 显示弹窗（详细信息）
	 * @param resource
	 */
	private void showDetail(int resource){

		View view=LayoutInflater.from(this).inflate(resource, null);
		final Dialog dialog=new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		view.findViewById(R.id.cancle).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		view.findViewById(R.id.yes).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				EasyMotherUtils.goActivity(CuiRuiShiProjectActivity.this,OrderCRSProcess.class);
				CuiRuiShiProjectActivity.this.finish();
			}
		});
		dialog.setContentView(view);
		dialog.show();
		
	}
	

}
