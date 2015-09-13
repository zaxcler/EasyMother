package com.easymother.main.my;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymother.bean.NewsBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CollectionListAdapter extends CommonAdapter<NewsBean> {
	private List<NewsBean> list;
	private Context context;
	

	protected CollectionListAdapter(Context context, List<NewsBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		
	}

	@Override
	public void setDataToItem(ViewHolder holder, NewsBean t) {
		
		CircleImageView circleImageView1=holder.getView(R.id.circlepicture);
		circleImageView1.setVisibility(View.GONE);
		ImageView picture=holder.getView(R.id.picture);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getImages(), picture,MyApplication.options_image);
		TextView title=holder.getView(R.id.title);
		if (t.getNewsName()!=null) {
			title.setText(t.getNewsName());
		}else {
			title.setText("");
		}
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
			content.setText(t.getContent());
		}else {
			content.setText("");
		}
		TextView type=holder.getView(R.id.type);
		if (t.getNewsType()!=null) {
			if ("A".equals(type)) {
					content.setText(t.getContent());
			}
			if ("B".equals(type)) {
				content.setText(t.getContent());
			}
			if ("C".equals(type)) {
				content.setText(t.getContent());
			}
			if ("D".equals(type)) {
				content.setText(t.getContent());
			}
			
		}else {
			content.setText("");
		}
	}

}
