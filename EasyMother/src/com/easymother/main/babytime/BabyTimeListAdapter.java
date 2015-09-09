package com.easymother.main.babytime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.l;
import com.easymother.bean.BabyTimeBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.main.homepage.CommentImageAdapter;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BabyTimeListAdapter extends CommonAdapter<BabyTimeBean> {
	
	protected BabyTimeListAdapter(Context context, List<BabyTimeBean> list,
			int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, BabyTimeBean t) {
		TextView days=holder.getView(R.id.days);
		String birthdayString=MyApplication.preferences.getString("nannan_birthday", "");
		
		if (!"".equals(birthdayString)||birthdayString!=null) {
			Date currentdate=new Date(System.currentTimeMillis());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date birthday=format.parse(birthdayString);
				Log.e("birthday", birthday.toString());
				Log.e("currentdate", currentdate.toString());
				int day=TimeCounter.countTimeOfDay(birthday, currentdate);
				days.setText(""+day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			days.setText("0");
		}
			
		TextView createdate=holder.getView(R.id.createdate);
		if (t.getCreateTime()!=null) {
			Date date=t.getCreateTime();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			createdate.setText(""+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.YEAR));
		}
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
			content.setText(t.getContent());
		}else {
			content.setText("");
		}
		
		holder.getView(R.id.photos).setVisibility(View.VISIBLE);
		holder.getView(R.id.photo).setVisibility(View.VISIBLE);
		
		//图片先不管，因为返回的数据不是数组形式
		if (t.getImages()!=null&&!"".equals(t.getImages())) {
//			String json= "['150907112001easymother20150907_111958.jpg','150907112001easymother20150907_111958.jpg']";
//			List<String> list=JSON.parseArray(json, String.class);
			Log.e("tupian", t.getImages());
			List<String> list=JSON.parseArray(t.getImages(), String.class);
			
				Log.e("图片数组", list.toString());
				if (list==null) {
					holder.getView(R.id.photos).setVisibility(View.GONE);
					holder.getView(R.id.photo).setVisibility(View.GONE);
				}else if (list.size()==1) {
					holder.getView(R.id.photos).setVisibility(View.GONE);
					ImageView imageView=holder.getView(R.id.photo);
//					if (imageView.getTag()!=String.valueOf(t.hashCode())) {
						ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+list.get(0), imageView,MyApplication.options_image);
//						imageView.setTag(String.valueOf(t.hashCode()));
//					}
				}else if(list.size()>1){
					holder.getView(R.id.photo).setVisibility(View.GONE);
					MyGridView gridView=holder.getView(R.id.photos);
//					if (gridView.getTag()!=String.valueOf(t.hashCode())) {
						ImageAdapter adapter=new ImageAdapter(context, list, R.layout.comment_image);
						gridView.setAdapter(adapter);
//						gridView.setTag(String.valueOf(t.hashCode()));
//					}
					
				
			}else {
				holder.getView(R.id.photos).setVisibility(View.GONE);
				holder.getView(R.id.photo).setVisibility(View.GONE);
			}
			
			
		}
		
//		List<String> list=JSON.parseArray(t.getImages(), String.class);
//		List<String> list=t.getBabyImages();
		
		
	}

}
