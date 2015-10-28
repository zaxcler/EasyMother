package com.easymother.main.homepage;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class CommentImageAdapter extends CommonAdapter<Bitmap> {
	private List<Bitmap> images;

	public CommentImageAdapter(Context context, List<Bitmap> list, int resource) {
		super(context, list, resource);
		this.images=list;
	}

	@Override
	public void setDataToItem(ViewHolder holder, Bitmap bitmap) {
		ImageView imageView=holder.getView(R.id.imageView1);
		LayoutParams params=new LayoutParams(MyApplication.getScreen_width()/3-20,MyApplication.getScreen_width()/3-20);
		imageView.setLayoutParams(params);
		imageView.setImageBitmap(bitmap);
	}
	public void addDate(Bitmap bitmap){
		list.add(bitmap);
	}

}
