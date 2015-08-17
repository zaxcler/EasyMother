package com.easymother.main.community;

import java.util.List;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easymother.bean.TestBean;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

public class HuLiShiReplyAdapter extends BaseAdapter {
	private Context context;
	private List<TestBean> list;
	private int resources[];
	private int flag;
	int i;

	public HuLiShiReplyAdapter(Context context, List<TestBean> list, int resources[]) {
		this.context = context;
		this.list = list;
		this.resources = resources;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		i++;
			flag=i%2;
//			if (convertView == null) {
				ViewHolder holder=null;
				if (flag == 1) {
//					 holder = ViewHolder.getInstance(context, position, convertView, parent, resources[0]);
//					 convertView= holder.getConvertView();
//					 convertView.setTag(holder);
//					 return convertView;
					convertView=LayoutInflater.from(context).inflate(resources[0], null);
				}
				if (flag == 0) {
//					 holder = ViewHolder.getInstance(context, position, convertView, parent, resources[1]);
//					 convertView= holder.getConvertView();
//					 convertView.setTag(holder);
//					 return convertView;
					 
					 convertView=LayoutInflater.from(context).inflate(resources[1], null);
				}
				
//			}else {
//				ViewHolder holder=(ViewHolder) convertView.getTag();
//			}
		
		
		return convertView;
	}

}
