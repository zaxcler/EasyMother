package com.easymother.utils;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class CommonAdapter2<T> extends BaseAdapter {
	
	protected Context context;
	protected List<T> list;
	protected int resource;
	protected CommonAdapter2(Context context,List<T> list,int resource){
		this.context = context;
		this.list = list;
		this.resource=resource;
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public T getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public  View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.getInstance(context, position,
				convertView, parent, resource);
		/*
		 * 过滤掉预加载时，重复测量 使得position=0出现3次
		 */
		if (parent.getChildCount()== position) {
					setDataToItem(holder,getItem(position));
		}
		convertView=holder.getConvertView();
	

		return convertView;
	}
	public abstract void setDataToItem(ViewHolder holder, T t);

}
