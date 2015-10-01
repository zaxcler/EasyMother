package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.Certificate;
import com.easymother.configure.BaseInfo;
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
import android.view.Gravity;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.TextView;

public class ZhengshuListActivity extends Activity {
	private MyListview listView;// 视频列表
	private PullToRefreshScrollView scrollview;
	private Intent intent;
	private int id;
	protected int pageNo=1;
	private TextView notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_list);
		EasyMotherUtils.initTitle(this, "所有证书", false);
		intent = getIntent();
		id = intent.getIntExtra("jobId", 0);
		findView();
		init();
	}

	private void findView() {
		listView=(MyListview) findViewById(R.id.pulltoreflash);
		scrollview=(PullToRefreshScrollView) findViewById(R.id.scrollview);
	}

	private void init() {
		loadData();
		scrollview.setMode(Mode.BOTH);
		scrollview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollview.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollview.onRefreshComplete();
			}
		});

	}

	private void loadData() {
		RequestParams params = new RequestParams();
		params.put("jobId", id);
		NetworkHelper.doGet(BaseInfo.CHCLK_ZHENGSHU, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<Certificate> Certificates = JsonUtils.getResultList(response, Certificate.class);
					ZhengshuListAdapter adapter = new ZhengshuListAdapter(ZhengshuListActivity.this, Certificates,
							R.layout.letter_item);
					if (Certificates.size() == 0 && pageNo==1) {
						if (notice==null) {
							notice=new TextView(ZhengshuListActivity.this);
							notice.setText("还没有评价！");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							listView.addFooterView(notice);
							pageNo=1;
						}
					}else {
						pageNo++;
					}
					listView.setAdapter(adapter);
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

}
