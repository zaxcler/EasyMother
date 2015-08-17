package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

/**
 * 时间表的gridview的适配器
 * @author zaxcler
 *
 */
public class OrderCRSPreocessGridViewAdapter extends CommonAdapter<String> {
	List<String> list;
	protected OrderCRSPreocessGridViewAdapter(Context context, List<String> list,
			int resource) {
		super(context, list, resource);
		
	}

	@Override
	public void setDataToItem(ViewHolder holder, String s) {
		
		TextView textView=(TextView)holder.getView(R.id.time);
		textView.setText(s);
	}

}
