package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.OrderComments;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
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

public class CommentListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private int pageNo = 2;
	private TextView notice;
	protected boolean canload=true;
	private CommentListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
		EasyMotherUtils.initTitle(this, "评论列表", false);
		intent = getIntent();
		nursebase = (NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob = (NurseJobBean) intent.getSerializableExtra("nursejob");
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
				AnimationUtils.loadAnimation(CommentListActivity.this, R.anim.zoom_right_in));
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


	private void loadData(){
		RequestParams params = new RequestParams();
		params.put("pageNo", 1);
		if (nursejob.getId() != null) {
			params.put("nurseId", nursebase.getId());
		}
		if (nursejob.getJob() != null) {
			params.put("job", nursejob.getJob());
		}
		NetworkHelper.doGet(BaseInfo.CHCLK_ALL_COMMENTS, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<OrderComments> comments = JsonUtils.getResultList(response, OrderComments.class);
					adapter = new CommentListAdapter(CommentListActivity.this, comments,
							R.layout.comment_item);
					if (comments!=null) {
						if (comments.size() == 0) {
							if (notice==null) {
								notice=new TextView(CommentListActivity.this);
								notice.setText("还没有评价！");
								notice.setGravity(Gravity.CENTER_HORIZONTAL);
								AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
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
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}
	public void loadMore(int pageNo) {
		Log.e("pageNo", pageNo+"");
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		if (nursejob.getId() != null) {
			params.put("nurseId", nursebase.getId());
		}
		if (nursejob.getJob() != null) {
			params.put("job", nursejob.getJob());
		}
		NetworkHelper.doGet(BaseInfo.CHCLK_ALL_COMMENTS, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<OrderComments> comments = JsonUtils.getResultList(response, OrderComments.class);
				
					if (comments != null) {
						
						if (comments.size()>0) {
							if (notice != null) {
								listview.removeFooterView(notice);
							}
							adapter.addList(comments);
							canload = true;
							CommentListActivity.this.pageNo++;
						} else {
							CommentListActivity.this.pageNo = 2;
							canload = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				CommentListActivity.this.pageNo = 2;
				canload = false;
			}
		});

	}

}
