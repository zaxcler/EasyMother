package com.easymother.main.babytime;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.BabyInfoBean;
import com.easymother.bean.BabyTimeBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class BabyTimeDetail extends Activity {
	private TextView content;
	private MyGridView gridview;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.baby_time_detail_activity);
		EasyMotherUtils.initTitle(this, "囡囡日记", false);
		intent=getIntent();
		findView();
		init();
		
	}

	private void findView() {
		gridview=(MyGridView) findViewById(R.id.gridview);
		content=(TextView) findViewById(R.id.content);
		
	}
	private void init() {
		int id=intent.getIntExtra("id", 0);
		RequestParams params=new RequestParams();
		if (id==0) {
			return;
		}
		params.put("id", id);
		NetworkHelper.doGet(BaseInfo.BABYTIME_TODETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					BabyTimeBean infoBean=JsonUtils.getResult(response, BabyTimeBean.class);
					
					bindData(infoBean);
					
				}
			}
		});
	}

	protected void bindData(BabyTimeBean timeBean) {
		if (timeBean.getContent()!=null) {
			content.setText(timeBean.getContent());
		}else {
			content.setText("");
		}
		if (timeBean.getImages()!=null) {
			List<String> list=JSON.parseArray(timeBean.getImages(), String.class);
			ImageAdapter adapter=new ImageAdapter(this, list, R.layout.image_item);
			gridview.setAdapter(adapter);
		}
	}

}
