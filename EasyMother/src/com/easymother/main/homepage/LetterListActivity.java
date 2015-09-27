package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.Letter;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
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

public class LetterListActivity extends Activity {
	private MyListview listView;//视频列表
	private PullToRefreshScrollView scrollView;
	private Intent intent;
	private int pageNo=1;
	private TextView notice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_list);
		EasyMotherUtils.initTitle(this, "所有信件", false);
		intent=getIntent();
		findView();
		init();
	}

	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.scrollview);
		listView=(MyListview) findViewById(R.id.pulltoreflash);
		
	}

	private void init() {
		loadData();
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollView.onRefreshComplete();
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollView.onRefreshComplete();
			}
		});
		
		
	}

	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("pageNo", pageNo);
		if (intent!=null) {
			if (intent.getIntExtra("nurseId", 0)!=0) {
				params.put("nurseId",intent.getIntExtra("nurseId", 0));
			}
			if (!"".equals(intent.getStringExtra("job"))&&intent.getStringExtra("job")!=null) {
			params.put("job", intent.getStringExtra("job"));	
			}
		}
		NetworkHelper.doGet(BaseInfo.CHCLK_LETTER, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<Letter> letters=JsonUtils.getResultList(response, Letter.class);
					LetterListAdapter adapter=new LetterListAdapter(LetterListActivity.this, letters, R.layout.letter_item);
					if (letters.size()==0 && pageNo==1 ) {
						if (notice==null) {
							notice=new TextView(LetterListActivity.this);
							notice.setText("没有推荐信！");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							listView.addFooterView(notice);
							pageNo=1;
						}
					}else {
						if (notice!=null) {
							listView.removeFooterView(notice);
						}
						adapter.add(letters);
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
