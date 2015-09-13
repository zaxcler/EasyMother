package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyGridView;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.my.CollectionListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class HuLiShiReplyListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	private TextView user_name;
	private TextView time;
	private TextView content;
	private TextView share;
	private TextView reply;
	private TextView star;
	private TextView reply_nums;
	private CircleImageView circleImageView1;
	private MyGridView pictures;
	
	private Intent intent;
	private int id;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hulishi_zone_reply);
		EasyMotherUtils.initTitle(this, "话题详情", false);
		intent=getIntent();
		id=intent.getIntExtra("id", 0);
		type=intent.getStringExtra("type");
		findView();
		init();
		
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
		user_name=(TextView) findViewById(R.id.user_name);
		time=(TextView) findViewById(R.id.time);
		content=(TextView) findViewById(R.id.content);
		share=(TextView) findViewById(R.id.share);
		reply=(TextView) findViewById(R.id.reply);
		star=(TextView) findViewById(R.id.star);
		reply_nums=(TextView) findViewById(R.id.reply_nums);
		circleImageView1=(CircleImageView) findViewById(R.id.circleImageView1);
		pictures=(MyGridView) findViewById(R.id.pictures);
		
	}
	private void init() {
		loadData();
		int resources[]=new int[]{R.layout.activity_hulishi_reply_item,R.layout.activity_hulishi_reply_item1};
//		HuLiShiReplyAdapter adapter=new HuLiShiReplyAdapter(this, list, resources);
//		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				EasyMotherUtils.goActivity(HuLiShiReplyListActivity.this, HuLiShiZoneDetailActivity.class);
			}
		});
		
	}
	/*
	 * 加载数据
	 */
	private void loadData() {
		RequestParams params=new RequestParams();
		if (id!=0) {
			params.put("userId", id);
		}
		if (type!=null && !"".equals(type)) {
			params.put("type", type);
		}
		NetworkHelper.doGet(BaseInfo.CHECK_TOPIC_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					
				}
			}
		});
	}

}
