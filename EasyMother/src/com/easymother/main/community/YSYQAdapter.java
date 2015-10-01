package com.easymother.main.community;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.CommunityHotBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class YSYQAdapter extends CommonAdapter<CommunityHotBean> {

	protected YSYQAdapter(Context context, List<CommunityHotBean> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, CommunityHotBean t) {
		ImageView imageView=holder.getView(R.id.imageView1);
		DisplayImageOptions options=new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(10)).cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.showImageOnFail(R.drawable.picture).showImageOnLoading(R.drawable.picture)
				.showImageForEmptyUri(R.drawable.picture)
				.showImageOnFail(R.drawable.picture).build();
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getLogo(), imageView, options);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getLogo(), imageView, MyApplication.options_image);
		TextView textView1=holder.getView(R.id.textView1);
		if (t.getTitle()!=null) {
			
			textView1.setText(Html.fromHtml(t.getTitle()));
		}else {
			textView1.setText("");
		}
		TextView textView2=holder.getView(R.id.textView2);
		if (t.getContent()!=null) {
			String temp=Html.fromHtml(t.getContent()).toString();
			String msg;
			if (temp.length()>20) {
				msg=temp.substring(0, 20);
			}else {
				msg=temp;
			}
			textView2.setText(msg);
		}else {
			textView2.setText("");
		}
		
	}
	
	
}
