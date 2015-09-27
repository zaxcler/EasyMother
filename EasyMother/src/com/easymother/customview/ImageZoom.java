package com.easymother.customview;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.MainActivity;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class ImageZoom {

	public static void showBigImgae(Context context, String imageurl) {
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_item_layout);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView1);
		ImageLoader.getInstance().displayImage(imageurl, imageView, MyApplication.options_photo);
		dialog.show();
	}

	/*
	 * 图片数组
	 */
	public static void showBigImgaes(Context context, String imageurl) {
		try {
			final Dialog dialog = new Dialog(context, R.style.Transparent);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.image_item_layout2);
			dialog.getWindow().setLayout(MyApplication.getScreen_width(), MyApplication.getScreen_height());
			ViewPager pager = (ViewPager) dialog.findViewById(R.id.vPager);
			JSONArray array = new JSONArray(imageurl);
			LayoutInflater inflater = LayoutInflater.from(context);
			List<ImageView> images = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				ImageView imageView = new ImageView(context);
				// imageView.setImageURI(Uri.create(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+array.getString(i)));
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.photo));
				imageView.setPadding(20, 20, 20, 20);
				images.add(imageView);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + array.getString(i),
						imageView, MyApplication.options_photo);
			}
			ImageviewAdapter adapter = new ImageviewAdapter(images);
			pager.setAdapter(adapter);
			dialog.show();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static class ImageviewAdapter extends PagerAdapter {
		public List<ImageView> mListViews;

		public ImageviewAdapter(List<ImageView> mListViews) {
			this.mListViews=mListViews;
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

	}

	

}
