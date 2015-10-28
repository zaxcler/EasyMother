package com.easymother.main.babytime;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

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
//			BitmapFactory.Options decodingOptions=new BitmapFactory.Options();
//			decodingOptions.inJustDecodeBounds=true;
//			decodingOptions.inSampleSize=4;
//			decodingOptions.inJustDecodeBounds=false;
//			DisplayImageOptions options=new DisplayImageOptions.Builder().decodingOptions(decodingOptions).cacheInMemory(true).cacheOnDisc(true)
//					.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//					.showImageOnFail(R.drawable.photo).showImageOnLoading(R.drawable.photo)
//					.showImageForEmptyUri(R.drawable.photo)
//					.showImageOnFail(R.drawable.photo).build();
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t, imageView,MyApplication.options_image);
		}
		
	}

}
