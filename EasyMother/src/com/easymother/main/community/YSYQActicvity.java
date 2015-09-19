package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.CommunityHotBean;
import com.easymother.bean.YSYQItemBean;
import com.easymother.bean.YSYQResult;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyGridView;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class YSYQActicvity extends Activity {
	
	private MyGridView gridview;
	private MyListview listview;
	private List<YSYQItemBean> beans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commnuity_yishiyiqu);
		EasyMotherUtils.initTitle(this, "医食衣趣", false);
		findView();
		init();
	}


	private void findView() {
		gridview=(MyGridView) findViewById(R.id.gridview);
		listview=(MyListview) findViewById(R.id.listview);
	}


	private void init() {
		beans=new ArrayList<>();
		YSYQItemBean bean1=new YSYQItemBean();
		bean1.setImage(R.drawable.yi);
		bean1.setTitle("医");
		bean1.setDesc("宝贝健康随我来");
		YSYQItemBean bean2=new YSYQItemBean();
		bean2.setImage(R.drawable.shi);
		bean2.setTitle("食");
		bean2.setDesc("白白胖胖笑嘻嘻");
		YSYQItemBean bean3=new YSYQItemBean();
		bean3.setImage(R.drawable.yi2);
		bean3.setTitle("衣");
		bean3.setDesc("打扮宝宝我拿手");
		YSYQItemBean bean4=new YSYQItemBean();
		bean4.setImage(R.drawable.qu);
		bean4.setTitle("趣");
		bean4.setDesc("宝贝健康随我来");
		beans.add(bean1);
		beans.add(bean2);
		beans.add(bean3);
		beans.add(bean4);
		YSYQGridviewAdapter adapter=new YSYQGridviewAdapter(this, beans, R.layout.yishiyiqu_item);
		gridview.setAdapter(adapter);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		NetworkHelper.doGet(BaseInfo.YSYQ_INFO, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					YSYQResult result=JsonUtils.getResult(response, YSYQResult.class);
					bindData(result);
				}
			}
		});
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Intent intent=new Intent();
				intent.setClass(YSYQActicvity.this, ArticleListActivity.class);
				if ("医".equals(beans.get(arg2).getTitle())) {
					intent.putExtra("typeName", "A");
				}
				if ("食".equals(beans.get(arg2).getTitle())) {
					intent.putExtra("typeName", "B");
				}
				if ("衣".equals(beans.get(arg2).getTitle())) {
					intent.putExtra("typeName", "C");
				}
				if ("趣".equals(beans.get(arg2).getTitle())) {
					intent.putExtra("typeName", "D");
				}
				startActivity(intent);
			}
		});
		
		
	}

	/**
	 * 绑定数据
	 * @param result
	 */

	protected void bindData(YSYQResult result) {
		
		if (result!=null) {
			if (result.getNewsinfos()!=null) {
				final List<CommunityHotBean> beans=result.getNewsinfos();
				YSYQAdapter adapter1=new YSYQAdapter(this, beans, R.layout.activity_commnuity_yishiyiqu_item);
				listview.setAdapter(adapter1);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						Intent intent=new Intent();
						intent.setClass(YSYQActicvity.this, ArticleActivity.class);
						intent.putExtra("id", beans.get(arg2).getId());
						YSYQActicvity.this.startActivity(intent);
					}
				});
			}
		}
		
		
		
		
	}

}
