package com.easymother.main.my;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.ForumBean;
import com.easymother.bean.NewsBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CollectionListAdapter2 extends CommonAdapter<ForumBean> {
	private List<ForumBean> list;
	private Context context;
	

	protected CollectionListAdapter2(Context context, List<ForumBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		
	}

	@Override
	public void setDataToItem(ViewHolder holder, ForumBean t) {
		CircleImageView circlepicture=holder.getView(R.id.circlepicture);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getUserImage(), circlepicture,MyApplication.options_image);
		
		ImageView picture=holder.getView(R.id.picture);
		String photo="";
		if (t.getPostImage()!=null&&!"".equals(t.getPostImage())) {
			List<String> photos=JSON.parseArray(photo, String.class);
			photo=photos.get(0);
		}
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+photo, picture,MyApplication.options_photo);
		
		TextView title=holder.getView(R.id.title);
		if (t.getUserName()!=null) {
			title.setText(t.getUserName());
		}else {
			title.setText("");
		}
		TextView content=holder.getView(R.id.content);
		if (t.getPostTitle()!=null) {
			content.setText(t.getPostTitle());
		}else {
			content.setText("");
		}
		TextView type=holder.getView(R.id.type);
		if (t.getPostType()!=null) {
			type.setText(t.getPostType());
		}else {
			type.setText("");
		}
	}

}
