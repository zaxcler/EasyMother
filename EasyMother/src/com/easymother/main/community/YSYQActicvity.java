package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class YSYQActicvity extends Activity {
	
	private RelativeLayout photos1;
	private RelativeLayout photos2;
	private RelativeLayout photos3;
	private RelativeLayout photos4;
	
	private ListView listview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commnuity_yishiyiqu);
		findView();
		init();
	}


	private void findView() {
		photos1=(RelativeLayout) findViewById(R.id.layout1);
		photos2=(RelativeLayout) findViewById(R.id.layout2);
		photos3=(RelativeLayout) findViewById(R.id.layout3);
		photos4=(RelativeLayout) findViewById(R.id.layout4);
		
		listview=(ListView) findViewById(R.id.listview);
	}


	private void init() {
		int height= MyApplication.getScreen_width()/9*4;
		LayoutParams params=new LayoutParams(height, height);
		params.setMargins(5, 5, 5, 5);
		photos1.setLayoutParams(params);
		photos2.setLayoutParams(params);
		photos3.setLayoutParams(params);
		photos4.setLayoutParams(params);
		
		List<TestBean> list=new ArrayList<>();
		TestBean bean=new TestBean();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		YSYQAdapter adapter=new YSYQAdapter(this, list, R.layout.activity_commnuity_yishiyiqu_item);
		listview.setAdapter(adapter);
		
		
	}

}
