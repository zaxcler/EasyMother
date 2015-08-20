package com.easymother.main.homepage;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.easymother.bean.TestBean;
import com.easymother.bean.YueSao;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

public class YueSaoListAdapter extends CommonAdapter<YueSao> {
	private List<YueSao> list;
	private Context context;
	

	protected YueSaoListAdapter(Context context, List<YueSao> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		
	}
	
	/*
	 * 添加一个数据
	 */
	public void addAll(YueSao yuesao){
		this.list.add(yuesao);
	}
	
	/*
	 * 添加一个集合
	 */
	public void addAll(List<YueSao> list){
		this.list.addAll(list);
	}
	@Override
	public void setDataToItem(ViewHolder holder, YueSao yuesao) {
		holder.getView(R.id.delete).setVisibility(View.GONE);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		
	}

}
