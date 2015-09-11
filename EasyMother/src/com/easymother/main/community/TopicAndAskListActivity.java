package com.easymother.main.community;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.ForumPostBean;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyPopupWindow1;
import com.easymother.customview.MyPopupWindow1.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class TopicAndAskListActivity extends Activity implements OnClickListener{
	private ImageView back;
	private TextView topic;
	private TextView ask;
	private ImageView more;
	private PullToRefreshScrollView pulltoreflash;
	private MyListview mylistview;
	
	private Intent intent;
	private int blockId;
	
	private HuLiShiAdapter adapter;
	private TopicHelpResult result;//访问的结果
	private TextView notice;//提示
	private String flag="topic";//是话题还是求助的标记
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.topic_ask_activity);
		intent=getIntent();
		blockId=intent.getIntExtra("blockId", 0);
		findView();
		init();
		
		
	}

	private void findView() {
		back=(ImageView) findViewById(R.id.back);
		topic=(TextView) findViewById(R.id.topic);
		ask=(TextView) findViewById(R.id.ask);
		more=(ImageView) findViewById(R.id.more);
		pulltoreflash=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		mylistview=(MyListview) findViewById(R.id.mylistview);
	}

	private void init() {
		loadData();
		back.setOnClickListener(this);
		topic.setOnClickListener(this);
		ask.setOnClickListener(this);
		more.setOnClickListener(this);
		pulltoreflash.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadData();
				refreshView.onRefreshComplete();
			}
		});
	}

	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("blockId", blockId);
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					result=JsonUtils.getResult(response, TopicHelpResult.class);
					bindDate(result);
				}
			}
		});
	}
	/**
	 * 绑定数据
	 * @param result 
	 */
	protected void bindDate(TopicHelpResult result1) {
		List<TopicItemBean> list=null;
		if ("topic".equals(flag)) {
			list=result1.getTopics();
		}else if ("help".equals(flag)){
			list=result1.getHelps();
		}
		
		if (list!=null && list.size()>0) {
			adapter=new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
			mylistview.setAdapter(adapter);
		}else{
			if (notice==null) {
				notice=new TextView(TopicAndAskListActivity.this);
				notice.setText("没有信息哟！");
				notice.setGravity(Gravity.CENTER_HORIZONTAL);
				AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				notice.setLayoutParams(params);
				notice.setBackgroundColor(getResources().getColor(R.color.background));
				notice.setTextColor(getResources().getColor(R.color.boroblacktext));
				mylistview.addFooterView(notice);
			}
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case  R.id.back:
			this.finish();
			break;
		case  R.id.topic:
			clearState();
			topic.setTextColor(getResources().getColor(R.color.white));
			topic.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightredwine_solid2));
			flag="topic";
			bindDate(result);
			break;
		case  R.id.ask:
			clearState();
			ask.setTextColor(getResources().getColor(R.color.white));
			ask.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightredwine_solid3));
			flag="help";
			bindDate(result);
			break;
		case  R.id.more:
			final MyPopupWindow1 popupWindow1=new MyPopupWindow1(this, R.layout.popupwindow_community2);
			popupWindow1.setOnMyPopupClidk(new OnMyPopupWindowsClick() {
				
				@Override
				public void onClick(View view) {
					view.findViewById(R.id.topic).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							intent.setClass(TopicAndAskListActivity.this,TopicOrHelpEditActivity.class );
							intent.putExtra("flag", "topic");
							startActivity(intent);
							popupWindow1.dismiss();
						}
					});
         view.findViewById(R.id.help).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							intent.setClass(TopicAndAskListActivity.this,TopicOrHelpEditActivity.class );
							intent.putExtra("flag", "help");
							startActivity(intent);
							popupWindow1.dismiss();
						}
					});
				}
			});
			popupWindow1.showAsDropDown(v);
			break;
		
		}
	}
	/*
	 * 清楚选中状态
	 */
	private void clearState(){
		topic.setTextColor(getResources().getColor(R.color.blacktext));
		topic.setBackgroundColor(getResources().getColor(R.color.white));
		ask.setTextColor(getResources().getColor(R.color.blacktext));
		ask.setBackgroundColor(getResources().getColor(R.color.white));
	}

}
