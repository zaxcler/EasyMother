package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.NurseJobMediaBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.WebViewActivity;
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
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListActivity extends Activity {
//	private PullToRefreshListView listView;//视频列表
//	private MyReflashLayout reflash;
	private PullToRefreshScrollView scrollview;
	private MyListview listView;//视频列表
	private Intent intent;
	private List<NurseJobMediaBean> list;
	private int pageNO=1;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private ImageView imageView1;//背景
	private TextView video_name;
	private TextView upload_time;
	private TextView notice;
	protected int pageNo=1;
	
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
		listView=(MyListview) findViewById(R.id.pulltoreflash);
		scrollview=(PullToRefreshScrollView) findViewById(R.id.scrollview);
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
		if (nursejob!=null && nursejob.getId()!=null) {
			params.put("nurseJobId", nursejob.getId());
		}
//		params.put("nurseJobId", nursejob.getId());
		NetworkHelper.doGet(BaseInfo.CHECK_VIDEO, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					list=JsonUtils.getResultList(response, NurseJobMediaBean.class);
					VideoListAdapter adapter=new VideoListAdapter(VideoListActivity.this, list, R.layout.video_list_item);
					if (pageNO==1&&list.size()==0) {
						if (notice==null) {
							notice=new TextView(VideoListActivity.this);
							notice.setText("没有护理视频！");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							listView.addFooterView(notice);
						}
					}else {
						if (notice!=null) {
							listView.removeView(notice);
						}
						pageNO++;
					}
					listView.setAdapter(adapter);
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
