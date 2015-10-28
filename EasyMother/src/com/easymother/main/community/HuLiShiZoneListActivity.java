package com.easymother.main.community;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.OrderComments;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyLoadingProgress;
import com.easymother.main.my.CommentListActivity;
import com.easymother.utils.EasyMotherUtils;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.TextView;

public class HuLiShiZoneListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	private int pageNo = 2;
	private HuLiShiAdapter adapter;
	private TextView notice;
	private boolean canload = true;

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
		scrollView = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview = (MyListview) findViewById(R.id.listview);
		// TODO Auto-generated method stub
		
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					loadData();
				refreshView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					loadMore(pageNo++);
				}
				refreshView.onRefreshComplete();
			}
		});

	}

	private void init() {
		loadData();
	}

	private void loadData() {
		MyLoadingProgress.showLoadingDialog(this);
		RequestParams params = new RequestParams();
		params.put("type", "KJ");
		params.put("pageNo", 1);
		NetworkHelper.doGet(BaseInfo.NURSE_ZOME_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<TopicItemBean> list = JsonUtils.getResultList(response, TopicItemBean.class);
					bindData(list);
					MyLoadingProgress.closeLoadingDialog();
				}
			}
		});
	}

	protected void bindData(List<TopicItemBean> list) {
		if (list != null) {
			if (adapter == null) {
				adapter = new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
			}
			if (list.size() == 0) {
					if (notice == null) {
						notice = new TextView(HuLiShiZoneListActivity.this);
						notice.setText("没有信息哟！");
						notice.setGravity(Gravity.CENTER_HORIZONTAL);
						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						notice.setLayoutParams(params);
						notice.setBackgroundColor(getResources().getColor(R.color.background));
						notice.setTextColor(getResources().getColor(R.color.boroblacktext));
						listview.addFooterView(notice);
				}
			}
			listview.startLayoutAnimation();
			listview.setAdapter(adapter);
		}

	}
	
	public void loadMore(int pageNo) {
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		params.put("type", "KJ");
		NetworkHelper.doGet(BaseInfo.NURSE_ZOME_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<TopicItemBean> list = JsonUtils.getResultList(response, TopicItemBean.class);
				
					if (list != null) {
						
						if (list.size()>0) {
							if (notice != null) {
								listview.removeFooterView(notice);
							}
							adapter.addList(list);
							canload = true;
							HuLiShiZoneListActivity.this.pageNo++;
						} else {
							HuLiShiZoneListActivity.this.pageNo = 2;
							canload = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				HuLiShiZoneListActivity.this.pageNo = 2;
				canload = false;
			}
		});

	}

}
