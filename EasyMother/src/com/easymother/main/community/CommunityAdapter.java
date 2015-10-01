package com.easymother.main.community;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.Blocks;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityAdapter extends CommonAdapter<Blocks> {

	public CommunityAdapter(Context context, List<Blocks> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Blocks t) {
		ImageView imageView1=holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getImage(), imageView1, MyApplication.options_image);
		TextView title=holder.getView(R.id.title);
		if (t.getName()!=null) {
			title.setText(t.getName());
		}else {
			title.setText("");
		}
		TextView today=holder.getView(R.id.today);
		today.setText("今天："+t.getDayAmount());
		TextView topic=holder.getView(R.id.topic);
		topic.setText("话题："+t.getAllAmount());
	}

}
