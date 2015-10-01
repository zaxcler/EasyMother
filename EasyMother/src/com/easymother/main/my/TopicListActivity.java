package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.ForumBean;
import com.easymother.bean.NewsInfoBean;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
import com.easymother.main.community.ArticleListActivity;
import com.easymother.main.community.HuLiShiAdapter;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class TopicListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	protected int pageNo = 2;
	private TextView notice;
	private MyTopicAdapter adapter;
	private boolean canload = true;

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
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(TopicListActivity.this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(controller);
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

	public void loadData() {
		RequestParams params = new RequestParams();
		params.put("pageNo", 1);
		params.put("userId", MyApplication.preferences.getInt("id", -1));
		params.put("pageSize", 10);
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TopicHelpResult result = JsonUtils.getResult(response, TopicHelpResult.class);
					bindData(result);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				TopicListActivity.this.pageNo = 2;
				canload = false;
			}
		});

	}

	/*
	 * 绑定数据
	 */
	public void bindData(TopicHelpResult result) {

		List<TopicItemBean> list = new ArrayList<>();
		if (result.getTopics() != null) {
			list.addAll(result.getTopics());
		}
		if (result.getHelps() != null) {
			list.addAll(result.getHelps());
		}

		if (list != null) {

			if (adapter == null) {
				adapter = new MyTopicAdapter(this, list, R.layout.my_topic_item);
			}
			if (list.size() == 0) {
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
		}
		listview.startLayoutAnimation();
		listview.setAdapter(adapter);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final TopicItemBean bean=(TopicItemBean) arg0.getAdapter().getItem(arg2);
				AlertDialog.Builder builder=new AlertDialog.Builder(TopicListActivity.this);
				builder.setTitle("是否删除");
				builder.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						RequestParams params=new RequestParams();
						params.put("id", bean.getId());
						NetworkHelper.doGet(BaseInfo.COLLECTION_DELETE,params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (JsonUtils.getRootResult(response).getIsSuccess()) {
									Toast.makeText(TopicListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
									loadData();
								}else {
									Toast.makeText(TopicListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
								}
							}
						});
						dialog.dismiss();
					}
				
				});
				builder.setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alertDialog=builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();
				return true;
			}
		});
	}
	
	public void loadMore(int pageNo) {
		Log.e("pageNo", pageNo+"-----");
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		params.put("userId", MyApplication.preferences.getInt("id", -1));
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TopicHelpResult result = JsonUtils.getResult(response, TopicHelpResult.class);
					List<TopicItemBean> list = new ArrayList<>();
					if (result.getTopics() != null) {
						list.addAll(result.getTopics());
					}
					if (result.getHelps() != null) {
						list.addAll(result.getHelps());
					}
					if (list != null) {
						adapter.addList(list);
						if (list.size() > 0) {
							if (notice != null) {
								listview.removeFooterView(notice);
							}
							canload = true;
							TopicListActivity.this.pageNo++;
						} else {
							TopicListActivity.this.pageNo = 2;
							canload = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				TopicListActivity.this.pageNo = 2;
				canload = false;
			}
		});

	}

}
