package com.Library.ToolsClass;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GalleryItemAdapter extends BaseAdapter
{
	private ArrayList<ImageView> arrListItem = null;
	private Context mContext;
	private String kind;
	LayoutInflater vi;

	public GalleryItemAdapter(Context c, String kind, ArrayList<ImageView> arrItem)
	{
		mContext = c;
		if (arrItem == null)
			arrItem = new ArrayList<ImageView>();
		this.arrListItem = arrItem;
		this.kind = kind;
		vi = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount()
	{
		return arrListItem.size();
	}

	public Object getItem(int position)
	{
		return null;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView iv;

		if (convertView == null)
		{
			// iv = new ImageView(mContext);
			iv = arrListItem.get(position);
			Gallery.LayoutParams params = new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			iv.setAdjustViewBounds(true);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(params);
		}
		else
		{
			iv = (ImageView) convertView;
		}
		return iv;
	}
}