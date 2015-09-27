package com.easymother.main.babytime;

import java.util.List;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class ImageAdapter extends CommonAdapter<String> {

	public ImageAdapter(Context context, List<String> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, String t) {
		ImageView imageView=holder.getView(R.id.imageView1);
		LayoutParams params=new LayoutParams(MyApplication.getScreen_width()/3-20,MyApplication.getScreen_width()/3-20);
		imageView.setLayoutParams(params);
		if (t!=null&&!"".equals(t)){
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t, imageView,MyApplication.options_image);
		}
		
	}

}
