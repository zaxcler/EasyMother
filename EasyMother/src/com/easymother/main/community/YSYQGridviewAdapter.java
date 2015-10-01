package com.easymother.main.community;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.YSYQItemBean;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

public class YSYQGridviewAdapter extends CommonAdapter<YSYQItemBean> {

	private Context context;
	protected YSYQGridviewAdapter(Context context, List<YSYQItemBean> list, int resource) {
		super(context, list, resource);
		this.context=context;
	}

	@Override
	public void setDataToItem(ViewHolder holder, YSYQItemBean t) {
		int height= MyApplication.getScreen_width()/2-40;
		AbsListView.LayoutParams params=new AbsListView.LayoutParams(height, height);
		View view=holder.getConvertView();
		view.setLayoutParams(params);
		ImageView imageView=holder.getView(R.id.image);
		imageView.setImageDrawable(context.getResources().getDrawable(t.getImage()));
		TextView textView1=holder.getView(R.id.textView1);
		TextView textView2=holder.getView(R.id.textView2);
		textView1.setText(t.getTitle());
		textView2.setText(t.getDesc());
		textView1.setVisibility(View.GONE);
		textView2.setVisibility(View.GONE);
	}
	
	
}
