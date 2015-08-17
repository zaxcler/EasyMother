package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.my.OrderDetailActivity;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListActivity extends Activity {
	private MyListview ysListview;// 月嫂列表
	private MyListview yysListview;// 育婴师列表
	private MyListview crsListview;// 催乳师列表

	private TextView title;// 标题
	private ImageView back;// 返回键
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mywishlist);
		EasyMotherUtils.initTitle(this, "我的订单", false);
		findView();
		init();

	}

	private void findView() {
		title = (TextView) findViewById(R.id.title);
		back = (ImageView) findViewById(R.id.back);
		ysListview = (MyListview) findViewById(R.id.wish_ys_list);
		yysListview = (MyListview) findViewById(R.id.wish_yys_list);
		crsListview = (MyListview) findViewById(R.id.wish_crs_list);
	}

	private void init() {
		//测试数据------------
		List<TestBean> list=new ArrayList<TestBean>();
		TestBean bean=new TestBean();
		TestBean bean1=new TestBean();
		TestBean bean2=new TestBean();
		list.add(bean);
		list.add(bean1);
		list.add(bean2);
		
		OrderListAdapter adapter=new OrderListAdapter(this, list, R.layout.activity_yuesao_item);
		ysListview.setAdapter(adapter);
		yysListview.setAdapter(adapter);
		crsListview.setAdapter(adapter);
		
		//设置listview加载动画
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		ysListview.setLayoutAnimation(controller);
		ysListview.setLayoutAnimation(controller);
		yysListview.setLayoutAnimation(controller);
		yysListview.startLayoutAnimation();
		crsListview.setLayoutAnimation(controller);
		crsListview.startLayoutAnimation();
		OnItemClickLisenter lisenter=new OnItemClickLisenter();
		ysListview.setOnItemClickListener(lisenter);
		crsListview.setOnItemClickListener(lisenter);
		yysListview.setOnItemClickListener(lisenter);
		

	}
	private class OnItemClickLisenter implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			EasyMotherUtils.goActivity(OrderListActivity.this, OrderDetailActivity.class);
			
		}

		
		
	}

}
