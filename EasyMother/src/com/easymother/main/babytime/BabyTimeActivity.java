package com.easymother.main.babytime;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class BabyTimeActivity extends Activity {
	private MyListview listview;//囡囡记列表
	private View headview;//listview的头
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime);
		EasyMotherUtils.initTitle(this, "囡囡记", false);
		findView();
		init();
	}

	private void findView() {
		listview=(MyListview) findViewById(R.id.reflashlistview);
		headview=LayoutInflater.from(this).inflate(R.layout.activity_babytime_head, null);
	}

	private void init() {
		
		List<TestBean> list=new ArrayList<TestBean>();
		TestBean bean=new TestBean();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		BabyTimeListAdapter adapter=new BabyTimeListAdapter(this, list, R.layout.activity_babytime_item);
		
		listview.addHeaderView(headview);
		listview.setAdapter(adapter);
		
	}

}
