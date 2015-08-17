package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.my.TopicListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HuLiShiReplyListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hulishi_zone_reply);
		EasyMotherUtils.initTitle(this, "护理师空间列表", false);
		findView();
		init();
		
		
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
		// TODO Auto-generated method stub
		
	}
	private void init() {
		//测试数据
		TestBean bean=new TestBean();
		List<TestBean> list=new ArrayList<TestBean>();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		int resources[]=new int[]{R.layout.activity_hulishi_reply_item,R.layout.activity_hulishi_reply_item1};
		HuLiShiReplyAdapter adapter=new HuLiShiReplyAdapter(this, list, resources);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				EasyMotherUtils.goActivity(HuLiShiReplyListActivity.this, HuLiShiZoneDetailActivity.class);
			}
		});
		
	}

}
