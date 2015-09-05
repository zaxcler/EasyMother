package com.easymother.main.babytime;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.BabyTimeBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.homepage.CommonListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class BabyTimeActivity extends Activity {
//	private MyListview listview;//囡囡记列表
	private ListView listview;//囡囡记列表
	private View headview;//listview的头
	private List<BabyTimeBean> list;
	private TextView notice;//没有数据的时候，提示信息
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime);
		EasyMotherUtils.initTitle(this, "囡囡记", false);
		findView();
		init();
	}

	private void findView() {
//		listview=(MyListview) findViewById(R.id.reflashlistview);
		listview=(ListView) findViewById(R.id.reflashlistview);
		headview=LayoutInflater.from(this).inflate(R.layout.activity_babytime_head, null);
	}

	private void init() {
		
//		List<TestBean> list=new ArrayList<TestBean>();
//		TestBean bean=new TestBean();
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
//		list.add(bean);
//		BabyTimeListAdapter adapter=new BabyTimeListAdapter(this, list, R.layout.activity_babytime_item);
		
		NetworkHelper.doGet(BaseInfo.BABYTIME_LIST, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					list=JsonUtils.getResultList(response, BabyTimeBean.class);
					bindData(list);
					
				}
			}
		});
		
		
	}

	protected void bindData(List<BabyTimeBean> list2) {
		if (list2.size()==0) {
			notice=new TextView(BabyTimeActivity.this);
			notice.setText("你还没有记录过囡囡信息哦！快去记录吧！");
			notice.setGravity(Gravity.CENTER_HORIZONTAL);
			AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			notice.setLayoutParams(params);
			notice.setBackgroundColor(getResources().getColor(R.color.background));
			notice.setTextColor(getResources().getColor(R.color.boroblacktext));
			listview.addFooterView(notice);
			
		}
		BabyTimeListAdapter adapter=new BabyTimeListAdapter(this, list, R.layout.activity_babytime_item);
		listview.addHeaderView(headview);
		listview.setAdapter(adapter);
	}

}
