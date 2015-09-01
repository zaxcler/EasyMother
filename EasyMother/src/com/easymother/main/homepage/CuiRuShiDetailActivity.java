package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

public class CuiRuShiDetailActivity extends Activity implements OnClickListener{
	private GridView gridView;//证书列表
	private PullToRefreshScrollView pullToRefreshScrollView;//下拉刷新空间
	
	private MyListview mListview;//雇主评价
	private TextView buy_now;//立即预约
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cuirushi_detail);
		EasyMotherUtils.initTitle(this, "催乳师详情", false);
		findView();
		init();
	}

	
	private void findView() {
		gridView=(GridView) findViewById(R.id.gridView1);
		mListview=(MyListview) findViewById(R.id.comment);
		buy_now=(TextView) findViewById(R.id.buy_now);
		
		
		
	}
	private void init() {
		
		//-------测试数据
		List<TestBean> beans=new ArrayList<TestBean>();
		TestBean bean1=new TestBean();
		
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		//----------后期改为网络访问	
//		YueSaoGridViewAdapter adapter=new YueSaoGridViewAdapter(this, beans, R.layout.activity_yuesao_gridview_item);
//		gridView.setAdapter(adapter);
		//-------测试数据
				List<TestBean> beans1=new ArrayList<TestBean>();
				beans1.add(bean1);
				beans1.add(bean1);
		EmployerCommentAdapter commentAdapter=new EmployerCommentAdapter(this, beans1, R.layout.comment_item);
		mListview.setAdapter(commentAdapter);
		buy_now.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.buy_now:
			EasyMotherUtils.goActivity(this, CuiRuiShiProjectActivity.class);
			break;

		default:
			break;
		}
	}


}
