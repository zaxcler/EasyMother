package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.ForumPost;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
import com.easymother.main.my.TopicListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageContralActivity extends Activity {
	private MyListview listView;// 消息
	private PullToRefreshScrollView scrollView;
	private Intent intent;
	private String flag;
	private String url;// 访问连接
	protected int pageNo = 2;
	private TextView notice;
	private MessageAdapter adapter;
	private boolean canload=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_contral);
		intent = getIntent();
		flag = intent.getStringExtra("flag");
		if ("unread".equals(flag)) {
			EasyMotherUtils.initTitle(this, "消息管理", false);
			url = BaseInfo.UNREAD;
		} else if ("reply_list".equals(flag)) {
			EasyMotherUtils.initTitle(this, "我的回复", false);
			url = BaseInfo.REPLY_LIST;
		}
		findView();
		init();
	}

	private void findView() {
		listView = (MyListview) findViewById(R.id.listview);
		scrollView = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
	}

	private void init() {
		loadData();
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(MessageContralActivity.this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listView.setLayoutAnimation(controller);
		scrollView.setMode(Mode.PULL_FROM_END);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					loadMore(pageNo);
				}
				scrollView.onRefreshComplete();
			}
		});
	}

	// 加载数据
	private void loadData() {
		NetworkHelper.doGet(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<ForumPost> forumPosts = JsonUtils.getResultList(response, ForumPost.class);
					bindDate(forumPosts);
				} else {
					Toast.makeText(MessageContralActivity.this, "未登录！", 0).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});

	}

	protected void bindDate(List<ForumPost> forumPosts) {
		if (forumPosts != null) {
			if (adapter == null) {
				adapter = new MessageAdapter(this, forumPosts, R.layout.message_control_item);
			}
			if (forumPosts.size() == 0) {
				if (notice == null) {
					notice = new TextView(MessageContralActivity.this);
					notice.setText("没有消息哦！");
					notice.setGravity(Gravity.CENTER_HORIZONTAL);
					AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					notice.setLayoutParams(params);
					notice.setBackgroundColor(getResources().getColor(R.color.background));
					notice.setTextColor(getResources().getColor(R.color.boroblacktext));
					listView.addFooterView(notice);
				}
			}
			listView.startLayoutAnimation();
			listView.setAdapter(adapter);

		}

	}
	
	public void loadMore(int pageNo) {
		Log.e("pageNo", pageNo+"");
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		NetworkHelper.doGet(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<ForumPost> forumPosts = JsonUtils.getResultList(response, ForumPost.class);
					if (forumPosts != null) {
						adapter.addList(forumPosts);
						if (forumPosts.size() > 0) {
							if (notice != null) {
								listView.removeFooterView(notice);
							}
							canload = true;
							MessageContralActivity.this.pageNo++;
						} else {
							MessageContralActivity.this.pageNo = 2;
							canload = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				MessageContralActivity.this.pageNo = 2;
				canload = false;
			}
		});

	}

}
