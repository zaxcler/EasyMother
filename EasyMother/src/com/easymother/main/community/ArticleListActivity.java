package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class ArticleListActivity extends Activity {
	private PullToRefreshScrollView pulltoreflash;
	private MyListview listview; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_collection);
		findView();
		init();
		
	}

	private void findView() {
		listview=(MyListview) findViewById(R.id.listview);
	}

	private void init() {
		
		List<TestBean> list=new ArrayList<>();
		TestBean bean=new TestBean();
		list.add(bean);
		list.add(bean);
		list.add(bean);
		
		ArticleListAdapter adapter=new ArticleListAdapter(this, list, R.layout.article_details_item);
		listview.setAdapter(adapter);
		
	}

}
