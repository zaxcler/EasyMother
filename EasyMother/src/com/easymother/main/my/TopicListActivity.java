package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.TestBean;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.community.HuLiShiAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

public class TopicListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
		EasyMotherUtils.initTitle(this, "我的话题", false);
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
//		TestBean bean=new TestBean();
//		List<TestBean> list=new ArrayList<TestBean>();
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
		
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TopicHelpResult result=JsonUtils.getResult(response, TopicHelpResult.class);
					bindData(result);
				}
			}
		});
		
//		TopicListAdapter adapter=new TopicListAdapter(this, list, R.layout.activity_mypage_topic_item);
//		listview.setAdapter(adapter);
		
	}
	/*
	 * 绑定数据
	 */
	protected void bindData(TopicHelpResult result) {
		List<TopicItemBean> list=new ArrayList<>();
			if (result.getTopics()!=null) {
				list.addAll(result.getTopics());
			}
			if (result.getHelps()!=null) {
				list.addAll(result.getHelps());
			}
			HuLiShiAdapter adapter=new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
			listview.setAdapter(adapter);
			
		
	}

}
