package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NewsInfoBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyLoadingProgress;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ArticleListActivity extends Activity {
	private PullToRefreshScrollView pulltoreflash;
	private MyListview listview; 
	private Intent intent;
	private String type;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_collection);
		intent=getIntent();
		type=intent.getStringExtra("typeName");
		EasyMotherUtils.initTitle(this,"文章列表" , false);
		findView();
		init();
		
	}

	private void findView() {
		listview=(MyListview) findViewById(R.id.listview);
	}

	private void init() {
		loadData();
		MyLoadingProgress.showLoadingDialog(this);
//		List<TestBean> list=new ArrayList<>();
//		TestBean bean=new TestBean();
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
		
//		ArticleListAdapter adapter=new ArticleListAdapter(this, list, R.layout.article_details_item);
//		listview.setAdapter(adapter);
		
	}

	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("type", type);
		NetworkHelper.doGet(BaseInfo.YSYQ_TYPE_NEWS, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<NewsInfoBean> list=JsonUtils.getResultList(response, NewsInfoBean.class);
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
		if (list!=null) {
			ArticleListAdapter adapter=new ArticleListAdapter(this, list, R.layout.article_details_item);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent=new Intent();
					intent.setClass(ArticleListActivity.this, ArticleActivity.class);
					intent.putExtra("id", list.get(arg2).getId());
					startActivity(intent);
				}
			});
		}
	}

}
