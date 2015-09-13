package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.TestBean;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HuLiShiZoneListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
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
		loadData();
	}
	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("type", "KJ");
		NetworkHelper.doGet(BaseInfo.NURSE_ZOME_LIST,params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<TopicItemBean> list=JsonUtils.getResultList(response, TopicItemBean.class);
					bindData(list);
				}
			}
		});
	}
	protected void bindData(List<TopicItemBean> list) {
		if (list!=null) {
			HuLiShiAdapter adapter=new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
			listview.setAdapter(adapter);
//			listview.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//					Log.e("点击", arg2+"");
//					EasyMotherUtils.goActivity(HuLiShiZoneListActivity.this, HuLiShiZoneDetailActivity.class);
//				}
//			});
		}
	}

}
