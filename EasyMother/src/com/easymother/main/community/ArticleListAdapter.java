package com.easymother.main.community;

import java.util.List;

import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class ArticleListAdapter extends CommonAdapter<TestBean> {

	protected ArticleListAdapter(Context context, List<TestBean> list, int resource) {
		super(context, list, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDataToItem(ViewHolder holder, TestBean t) {
		// TODO Auto-generated method stub
		
	}
	
}