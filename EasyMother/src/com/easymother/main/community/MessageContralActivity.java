package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class MessageContralActivity extends Activity {
	private ListView listView;//消息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_contral);
		EasyMotherUtils.initTitle(this, "消息管路", false);
		findView();
		init();
	}

	private void findView() {
		listView=(ListView) findViewById(R.id.listview);
		
	}

	private void init() {
		List<TestBean> list=new ArrayList<TestBean>();
		TestBean bean=new TestBean();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		MessageAdapter adapter=new MessageAdapter(this, list, R.layout.message_contral_item);
		listView.setAdapter(adapter);
	}
}
