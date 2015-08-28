package com.easymother.utils;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
	public int position;
	private View mConvertView;
	private SparseArray<View> mView; 
	

	protected  ViewHolder(Context context,int position,  ViewGroup parent,int resourceId) {

		this.position=position;
		mView=new SparseArray<View>();
		LayoutInflater inflater=LayoutInflater.from(context);
		mConvertView=inflater.inflate(resourceId, parent,false);
		mConvertView.setTag(this);
		
		 
	}
	/**
	 * 通过viewid获取控件
	 * @param id
	 * @return
	 */
	
	public <T extends View> T getView(int id){
		View view=mView.get(id);
		if (view==null) {
			view=mConvertView.findViewById(id);
			mView.put(id, view);
		}
		return (T) view;
	}
	

	public View getConvertView() {
		return mConvertView;
	}

	public static ViewHolder getInstance(Context context,int position, View convertView,
			ViewGroup parent,int resourceId) {
		if (convertView==null) {
			return new ViewHolder(context, position, parent, resourceId);
		}else {
			ViewHolder holder=(ViewHolder) convertView.getTag();
			holder.position=position;
			return holder;
		}

		
	}
	

}
