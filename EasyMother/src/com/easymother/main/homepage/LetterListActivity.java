package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LetterListActivity extends Activity {
	private PullToRefreshListView listView;//视频列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_list);
		EasyMotherUtils.initTitle(this, "所有信件", false);
		findView();
		init();
	}

	private void findView() {
		listView=(PullToRefreshListView) findViewById(R.id.pulltoreflash);
		
	}

	private void init() {
		//----------测试数据
		List<TestBean> list = new ArrayList<TestBean>();
		TestBean bean1 = new TestBean();
		TestBean bean2 = new TestBean();
		TestBean bean3 = new TestBean();
		TestBean bean4 = new TestBean();
		TestBean bean5 = new TestBean();
		TestBean bean6 = new TestBean();
		TestBean bean7 = new TestBean();
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		list.add(bean4);
		list.add(bean5);
		list.add(bean6);
		list.add(bean7);
//		VideoListAdapter adapter=new VideoListAdapter(this, list, R.layout.video_list_item);
//		listView.setAdapter(adapter);
		
	}

}
