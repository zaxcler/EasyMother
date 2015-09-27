package com.alidao.mama;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alidao.mama.ScrollLayout.PageListener2;
import com.easymother.main.MainActivity;
import com.easymother.main.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
public class WelActivity extends Activity {
	LinearLayout layoutBottom;
	Context theContext;
	LinearLayout start_ll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		theContext = this;
		train_sc = (ScrollLayout)findViewById(R.id.wel_sc);
		layoutBottom = (LinearLayout)findViewById(R.id.layout_scr_bottom);
		boolean isfirst = DataUtil.getSettingBoolValueByKey(this, "isfirst");
		if (isfirst) {
			goMain();
		}
		initViews();
	}
	public void setCurPage(int page) {
		layoutBottom.removeAllViews();
		for (int i = 0; i < 4; i++) {
			ImageView imgCur = new ImageView(theContext);
			imgCur.setBackgroundResource(R.drawable.dot11);
			imgCur.setId(i);
			if (imgCur.getId() == page) {
				imgCur.setBackgroundResource(R.drawable.dot22);
			}
			layoutBottom.addView(imgCur);
		}
	}
	ScrollLayout train_sc; 
	LayoutInflater  layoutInflater;
	void initViews(){
		layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view1 = layoutInflater.inflate(R.layout.page1, null);
		View view2 = layoutInflater.inflate(R.layout.page2, null);
		View view3 = layoutInflater.inflate(R.layout.page3, null);
		View view4 = layoutInflater.inflate(R.layout.page4, null);
		start_ll = (LinearLayout) view2.findViewById(R.id.start_ll);
		start_ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				goMain();
			}
		});
		train_sc.addView(view4);
		train_sc.addView(view1);
		train_sc.addView(view3);
		train_sc.addView(view2);
		train_sc.setPageListener2(new PageListener2() {
			@Override
			public void page(int page) {
				// TODO Auto-generated method stub
				setCurPage(page);
			}
		});
		setCurPage(0);
	}
	public void goMain(){
		DataUtil.setSettingBoolValueByKey(this, "isfirst", true);
		Intent mIntent = new Intent(WelActivity.this, MainActivity.class);
		startActivity(mIntent);
		finish();
		
	}



}
