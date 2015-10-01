package com.easymother.main.my;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.alidao.mama.R;
import com.easymother.bean.NewsBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionListAdapter extends CommonAdapter<NewsBean> {
	private List<NewsBean> list;
	private Context context;
	

	protected CollectionListAdapter(Context context, List<NewsBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
	}
	
	@Override
	public void setDataToItem(ViewHolder holder, NewsBean t) {
		
		CircleImageView circleImageView1=holder.getView(R.id.circlepicture);
		circleImageView1.setVisibility(View.GONE);
		ImageView picture=holder.getView(R.id.picture);
		if (t.getImages()!=null&&!"".equals(t.getImages())) {
			try {
				JSONArray array=new JSONArray(t.getImages());
				if (array.length()>0) {
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+array.getInt(0), picture,MyApplication.options_photo);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TextView time_tv=holder.getView(R.id.time);
		if (t.getCreateTime()!=null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
			String time=dateFormat.format(t.getCreateTime());
			time_tv.setText(time);
		}else {
			time_tv.setText("");
		}
		TextView title=holder.getView(R.id.title);
		if (t.getNewsName()!=null&&!"".equals(t.getNewsName())) {
			title.setText(t.getNewsName());
		}else {
			title.setText("");
		}
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null&&!"".equals(t.getContent())){
			String temp=Html.fromHtml(t.getContent()).toString();
			String msg;
			if (temp.length()>15) {
				msg=temp.substring(0, 15);
			}else {
				msg=temp;
			}
			content.setText(msg);
		}else {
			content.setText("");
		}
		TextView type_tv=holder.getView(R.id.type);
		String type=t.getNewsType();
		if (type!=null&&!"".equals(type)) {
			if ("A".equals(type)) {
				type_tv.setText("医");
			}
			if ("B".equals(type)) {
				type_tv.setText("食");
			}
			if ("C".equals(type)) {
				type_tv.setText("衣");
			}
			if ("D".equals(type)) {
				type_tv.setText("趣");
			}
			
		}else {
			type_tv.setText("");
		}
	}

}
