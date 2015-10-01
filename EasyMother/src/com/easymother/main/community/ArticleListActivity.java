package com.easymother.main.community;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NewsInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyLoadingProgress;
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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleListActivity extends Activity {
	private PullToRefreshScrollView pulltoreflash;
	private MyListview listview;
	private Intent intent;
	private String type;
	private ArticleListAdapter adapter;
	private int pageNo = 1;
	private TextView notice;
	private boolean canLoad = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_collection);
		intent = getIntent();
		type = intent.getStringExtra("typeName");
		EasyMotherUtils.initTitle(this, "文章列表", false);
		findView();
		init();

	}

	private void findView() {
		pulltoreflash = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview = (MyListview) findViewById(R.id.listview);
	}

	private void init() {
		loadData();
		MyLoadingProgress.showLoadingDialog(this);
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(ArticleListActivity.this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(controller);
		pulltoreflash.setMode(Mode.BOTH);
		pulltoreflash.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				loadData();
				refreshView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canLoad) {
					loadMore(pageNo);
				}
				refreshView.onRefreshComplete();
			}
		});

	}

	private void loadData() {
		RequestParams params = new RequestParams();
		params.put("type", type);
		NetworkHelper.doGet(BaseInfo.YSYQ_TYPE_NEWS, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<NewsInfoBean> list = JsonUtils.getResultList(response, NewsInfoBean.class);
					bindData(list);
					MyLoadingProgress.closeLoadingDialog();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("ArticleListActivity", responseString);
			}
		});
	}

	/**
	 * 绑定数据
	 */
	protected void bindData(final List<NewsInfoBean> list) {
		if (list != null) {

			if (adapter == null) {
				adapter = new ArticleListAdapter(this, list, R.layout.article_details_item);
			} else {
				adapter.clearList();
				adapter.addList(list);
			}

			if (list.size() == 0) {
				if (notice == null) {
					notice = new TextView(ArticleListActivity.this);
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
			adapter.addList(list);
			listview.startLayoutAnimation();
			listview.setAdapter(adapter);
		}

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.setClass(ArticleListActivity.this, ArticleActivity.class);
				intent.putExtra("id", list.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	public void loadMore(int pageNo) {
		RequestParams params = new RequestParams();
		params.put("type", type);
		params.put("pageNo", pageNo);
		NetworkHelper.doGet(BaseInfo.YSYQ_TYPE_NEWS, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<NewsInfoBean> list = JsonUtils.getResultList(response, NewsInfoBean.class);
					if (list != null) {
						adapter.addList(list);
						if (list.size() > 0) {
							if (notice != null) {
								listview.removeFooterView(notice);
							}
							canLoad = true;
							ArticleListActivity.this.pageNo++;
						} else {
							ArticleListActivity.this.pageNo = 2;
							canLoad = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				ArticleListActivity.this.pageNo = 2;
				canLoad = false;
			}
		});

	}

}
