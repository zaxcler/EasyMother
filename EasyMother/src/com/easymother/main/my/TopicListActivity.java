package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.OrderComments;
import com.easymother.bean.TestBean;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.community.HuLiShiAdapter;
import com.easymother.main.community.TopicAndAskListActivity;
import com.easymother.main.homepage.LetterListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	protected int pageNo = 1;
	private TextView notice;
	private HuLiShiAdapter adapter ;
	private boolean canload=true;

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
		scrollView = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview = (MyListview) findViewById(R.id.listview);

	}

	private void init() {
		loadData();
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					pageNo++;
					loadData();
				}
				scrollView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					pageNo++;
					loadData();
				}
				scrollView.onRefreshComplete();
			}
		});

	}

	private void loadData() {
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		params.put("userId", MyApplication.preferences.getInt("id", -1));
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TopicHelpResult result = JsonUtils.getResult(response, TopicHelpResult.class);
					bindData(result);
				}else {
					pageNo=1;
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				pageNo=1;
			}
		});

	}

	/*
	 * 绑定数据
	 */
	protected void bindData(TopicHelpResult result) {

		List<TopicItemBean> list = new ArrayList<>();
		if (result.getTopics() != null) {
			list.addAll(result.getTopics());
		}
		if (result.getHelps() != null) {
			list.addAll(result.getHelps());
		}
		
		if (list!=null) {

			if (adapter == null) {
				adapter = new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
				
			}
			
			if (list.size()==0 ) {
				if (pageNo==1) {
					if (notice == null) {
						notice = new TextView(TopicListActivity.this);
						notice.setText("你没有发布过话题！");
						notice.setGravity(Gravity.CENTER_HORIZONTAL);
						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						notice.setLayoutParams(params);
						notice.setBackgroundColor(getResources().getColor(R.color.background));
						notice.setTextColor(getResources().getColor(R.color.boroblacktext));
						listview.addFooterView(notice);
					}
				}
				canload=false;
				pageNo=1;
			}
			if (list.size() > 0) {
				
				if (pageNo>1) {
					adapter.addList(list);
					adapter.notifyDataSetChanged();
					
				}
				if (notice != null) {
					listview.removeFooterView(notice);
				}
				canload=true;
				pageNo++;
				
			}
			LayoutAnimationController controller = new LayoutAnimationController(
					AnimationUtils.loadAnimation(TopicListActivity.this, R.anim.zoom_right_in));
			controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
			listview.setLayoutAnimation(controller);
			listview.startLayoutAnimation();
			listview.setAdapter(adapter);
		
		}

//		if (adapter==null) {
//			adapter= new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
//		}
		
		
//		if (list.size()>0) {
//			if (notice!=null) {
//				listview.removeFooterView(notice);
//			}
//			//表示第一次刷新
//			if (pageNo==1) {
//				listview.setAdapter(adapter);
//			}
//			//pageNo大于1表示刷新
//			if (pageNo > 1) {
//				adapter.addList(list);
//				adapter.notifyDataSetChanged();
//			}
//			pageNo++;
//		}else {
//			if (pageNo==1) {
//				if (notice == null) {
//					notice = new TextView(TopicListActivity.this);
//					notice.setText("你没有发布过话题！");
//					notice.setGravity(Gravity.CENTER_HORIZONTAL);
//					AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
//							LayoutParams.WRAP_CONTENT);
//					notice.setLayoutParams(params);
//					notice.setBackgroundColor(getResources().getColor(R.color.background));
//					notice.setTextColor(getResources().getColor(R.color.boroblacktext));
//					listview.addFooterView(notice);
//				}
//			}
//			pageNo = 1;
//		}
	}
		
		

}
