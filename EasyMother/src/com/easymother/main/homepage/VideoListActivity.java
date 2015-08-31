package com.easymother.main.homepage;

import java.util.List;
import org.apache.http.Header;
import org.json.JSONObject;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.NurseJobMediaBean;
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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListActivity extends Activity {
	private PullToRefreshListView listView;//视频列表
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private ImageView imageView1;//背景
	private TextView video_name;
	private TextView upload_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_list);
		EasyMotherUtils.initTitle(this, "视频列表", false);
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		findView();
		init();
	}

	private void findView() {
		listView=(PullToRefreshListView) findViewById(R.id.pulltoreflash);
		
		
	}

	private void init() {
		
		RequestParams params=new RequestParams();
//		params.put("nurseJobId", nursejob.getId());
		NetworkHelper.doGet(BaseInfo.CHECK_VIDEO, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				List<NurseJobMediaBean> list=JsonUtils.getResultList(response, NurseJobMediaBean.class);
				VideoListAdapter adapter=new VideoListAdapter(VideoListActivity.this, list, R.layout.video_list_item);
				listView.setAdapter(adapter);
			}
		});
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}
		});
		
	}

}
