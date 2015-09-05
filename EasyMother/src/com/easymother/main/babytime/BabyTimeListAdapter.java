package com.easymother.main.babytime;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.sdk.app.l;
import com.easymother.bean.BabyTimeBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
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
		if (t.getBirthday()!=null) {
			Date currentdate=new Date(System.currentTimeMillis());
			int day=TimeCounter.countTimeOfDay(t.getBirthday(), currentdate);
			days.setText(""+day);
		}
		TextView createdate=holder.getView(R.id.createdate);
		if (t.getCreateTime()!=null) {
			Date date=t.getCreateTime();
			createdate.setText(""+date.getMonth()+"/"+date.getDay()+" "+date.getYear());
		}
		TextView content=holder.getView(R.id.createdate);
		if (t.getContent()!=null) {
			content.setText(t.getContent());
		}
		
		//图片先不管，因为返回的数据不是数组形式
		List<String> list=JSON.parseArray(t.getImages(), String.class);
		if (list.size()==0) {
			holder.getView(R.id.photos).setVisibility(View.GONE);
			holder.getView(R.id.photo).setVisibility(View.GONE);
		}else if (list.size()==1) {
			holder.getView(R.id.photos).setVisibility(View.GONE);
			ImageView imageView=holder.getView(R.id.photo);
			if (imageView.getTag()!=String.valueOf(list.hashCode())) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+list.get(0), imageView);
				imageView.setTag(String.valueOf(list.hashCode()));
			}
		}else if(list.size()>1){
			holder.getView(R.id.photo).setVisibility(View.GONE);
			GridView gridView=holder.getView(R.id.gridview);
			if (gridView.getTag()!=String.valueOf(list.hashCode())) {
				ImageAdapter adapter=new ImageAdapter(context, list, R.layout.comment_image);
				gridView.setAdapter(adapter);
				gridView.setTag(String.valueOf(list.hashCode()));
			}
			
		}
	}

}
