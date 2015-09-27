package com.easymother.main.my;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.text.Html;
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
import com.easymother.utils.NetworkHelper;
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
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getUserImage(), circlepicture,MyApplication.options_photo);
		ImageView picture=holder.getView(R.id.picture);
		String photo="";
		if (t.getPostImage()!=null&&!"".equals(t.getPostImage())&&!"[".equals(t.getPostImage())) {
			try {
				JSONArray array=new JSONArray(photo);
				photo=array.getString(0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			List<String> photos=JSON.parseArray(photo.toString(), String.class);
//			photo=photos.get(0);
		}
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+photo, picture,MyApplication.options_image);
		
		TextView title=holder.getView(R.id.title);
		if (t.getUserName()!=null) {
			title.setText(t.getUserName());
		}else {
			title.setText("");
		}
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
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
		
		TextView time_tv=holder.getView(R.id.time);
		if (t.getCreateTime()!=null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
			String time=dateFormat.format(t.getCreateTime());
			time_tv.setText(time);
		}else {
			time_tv.setText("");
		}
		TextView type=holder.getView(R.id.type);
		if (t.getPostType()!=null) {
			type.setText(t.getPostType());
		}else {
			type.setText("");
		}
	}

}
