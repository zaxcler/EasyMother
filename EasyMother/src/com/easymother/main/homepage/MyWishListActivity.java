package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

public class MyWishListActivity extends Activity {
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
		EasyMotherUtils.initTitle(this, "我的心愿单", false);
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
		
		MyWishListAdapter adapter=new MyWishListAdapter(this, list, R.layout.activity_yuesao_item);
		ysListview.setAdapter(adapter);
		yysListview.setAdapter(adapter);
		crsListview.setAdapter(adapter);
		
		
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		ysListview.setLayoutAnimation(controller);
		ysListview.startLayoutAnimation();
		

	}

}
