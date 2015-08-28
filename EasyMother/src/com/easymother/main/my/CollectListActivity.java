 package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class CollectListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
		EasyMotherUtils.initTitle(this,"我的收藏", false);
		findView();
		init();
		
		
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
		// TODO Auto-generated method stub
		
	}
	private void init() {
		//测试数据
		TestBean bean=new TestBean();
		List<TestBean> list=new ArrayList<TestBean>();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		
		TopicListAdapter adapter=new TopicListAdapter(this, list, R.layout.activity_mypage_collection_item);
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		listview.setAdapter(adapter);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(controller);
		listview.startLayoutAnimation();
		
	}

}
