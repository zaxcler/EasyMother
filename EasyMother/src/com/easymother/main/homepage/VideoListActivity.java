package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.NurseJobMediaBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyReflashLayout;
import com.easymother.customview.MyReflashLayout.onLoadingLisenter;
import com.easymother.main.R;
import com.easymother.main.WebViewActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class VideoListActivity extends Activity {
	private PullToRefreshListView listView;//视频列表
//	private MyReflashLayout reflash;
//	private ListView listView;//视频列表
	private Intent intent;
	private List<NurseJobMediaBean> list;
	private int pageNO=1;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private ImageView imageView1;//背景
	private TextView video_name;
	private TextView upload_time;
	private TextView notice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_list);
		EasyMotherUtils.initTitle(this, "视频列表", false);
		intent=getIntent();
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		findView();
		init();
	}

	private void findView() {
//		listView=(ListView) findViewById(R.id.pulltoreflash);
		listView=(PullToRefreshListView) findViewById(R.id.pulltoreflash);
//		reflash=(ReflashLayout) findViewById(R.id.reflash);
		
	}

	private void init() {
		
		
		loadData();
//		reflash.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		reflash.setOnLoadingListener(new onLoadingLisenter() {
//			
//			@Override
//			public void onLoadingData() {
//				
//			}
//		});
		listView.setMode(Mode.BOTH);//设置上拉加载和下来刷新
		listView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				loadData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				loadData();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				intent.putExtra("url", list.get(arg2).getUrl());
				intent.setClass(VideoListActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
		
	}
	private void loadData(){
		RequestParams params=new RequestParams();
		params.put("pageNo", pageNO);
//		params.put("nurseJobId", nursejob.getId());
		NetworkHelper.doGet(BaseInfo.CHECK_VIDEO, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					list=JsonUtils.getResultList(response, NurseJobMediaBean.class);
					VideoListAdapter adapter=new VideoListAdapter(VideoListActivity.this, list, R.layout.video_list_item);
					listView.setAdapter(adapter);
					pageNO++;
					if (pageNO==1&&list.size()==0) {
						if (notice==null) {
							notice=new TextView(VideoListActivity.this);
							notice.setText("没有匹配的结果");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							listView.addView(notice);
						}
					}
				}else {
					pageNO=1;
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				pageNO=1;
			}
		});
	}

}
