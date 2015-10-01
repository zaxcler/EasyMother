package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.TestBean;
import com.easymother.bean.TopicHelpDetailResult;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class TopicReplyListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_reply);
		EasyMotherUtils.initTitle(this, "话题回复", false);
		findView();
		init();
		
		
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
		// TODO Auto-generated method stub
		
	}
	private void init() {
		
		loadData();
		//测试数据
		TestBean bean=new TestBean();
		List<TestBean> list=new ArrayList<TestBean>();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		
//		TopicListAdapter adapter=new TopicListAdapter(this, list, R.layout.activity_mypage_reply_item);
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
//		listview.setAdapter(adapter);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(controller);
		listview.startLayoutAnimation();
		
	}
	private void loadData() {
		RequestParams params=new RequestParams();
		
		NetworkHelper.doGet(BaseInfo.CHECK_TOPIC_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TopicHelpDetailResult detailResult=JsonUtils.getResult(response, TopicHelpDetailResult.class);
					bindData(detailResult);
				}
			}
		});
	}
	
	protected void bindData(TopicHelpDetailResult detailResult) {
		if (detailResult!=null) {
			NurseBaseBean baseBean=detailResult.getNurseinfo();
			if (baseBean!=null) {
				
			}
		}
	}

}
