package com.easymother.main.homepage;

import java.util.List;

import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.widget.TextView;

public class WeekAdapter extends CommonAdapter<String> {

	protected WeekAdapter(Context context, List<String> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, String t) {
		TextView week=holder.getView(R.id.time);
		week.setTextSize(12);
		week.setText(t);
	}

}
