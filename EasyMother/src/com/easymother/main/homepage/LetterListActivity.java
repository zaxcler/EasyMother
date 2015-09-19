package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alipay.sdk.util.i;
import com.easymother.bean.Letter;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class LetterListActivity extends Activity {
	private PullToRefreshListView listView;//视频列表
	private Intent intent;
	
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
		listView=(PullToRefreshListView) findViewById(R.id.pulltoreflash);
		
		
	}

	private void init() {
		RequestParams params=new RequestParams();
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
					listView.setAdapter(adapter);
				}
			}
		});
		
		
	}

}
