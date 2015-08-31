package com.easymother.main.homepage;

import java.util.List;

import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class CommentImageAdapter extends CommonAdapter<Bitmap> {
	private List<Bitmap> images;

	public CommentImageAdapter(Context context, List<Bitmap> list, int resource) {
		super(context, list, resource);
		this.images=list;
	}

	@Override
	public void setDataToItem(ViewHolder holder, Bitmap bitmap) {
		ImageView imageView=holder.getView(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
	}
	public void addDate(Bitmap bitmap){
		list.add(bitmap);
	}

}
