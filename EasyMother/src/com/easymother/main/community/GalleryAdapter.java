package com.easymother.main.community;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.configure.BaseInfo;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.widget.ImageView;

public class GalleryAdapter extends CommonAdapter<String>{
	

	protected GalleryAdapter(Context context, List<String> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, String imageurl) {
		ImageView imageView=holder.getView(R.id.imageView1);
		
		if (imageurl!=null&&!"".equals(imageurl)) {
			if (holder.getView(R.id.imageView1).getTag()!=imageurl) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+imageurl, imageView);
				holder.getView(R.id.imageView1).setTag(imageurl);
			}
			
		}
	}
}
