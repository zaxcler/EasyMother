package com.easymother.main.homepage;

import java.util.List;



import android.content.Context;
import android.view.View;

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;


/**
 * 
 * 
 * 此类已经弃用
 * 
 * 
 * 
 */

public class YuYingShiListAdapter extends CommonAdapter<TestBean> {
	private List<TestBean> list;
	private Context context;
	

	protected YuYingShiListAdapter(Context context, List<TestBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		
	}

	@Override
	public void setDataToItem(ViewHolder holder, TestBean t) {
		holder.getView(R.id.delete).setVisibility(View.GONE);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		
	}

}
