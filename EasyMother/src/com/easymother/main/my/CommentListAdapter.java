package com.easymother.main.my;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.alidao.mama.R;
import com.easymother.bean.OrderComments;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class CommentListAdapter extends CommonAdapter<OrderComments> {
	private List<OrderComments> list;
	private Context context;
	

	public CommentListAdapter(Context context, List<OrderComments> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
		
	}
	
	public  void addList(List<OrderComments> list){
		this.list.addAll(list);
	}
	public void clear(){
		this.list.clear();
	}

	@Override
	public void setDataToItem(ViewHolder holder, OrderComments t) {
		CircleImageView employer_photo=holder.getView(R.id.employer_photo);
		TextView employer_name=holder.getView(R.id.employer_name);
		TextView employer_num=holder.getView(R.id.employer_num);
		TextView employer_content=holder.getView(R.id.employer_content);
		TextView comment_time=holder.getView(R.id.comment_time);
		GridView gridView=holder.getView(R.id.photos);
		if (t.getContent()!=null) {
			employer_content.setText(t.getContent());
		}else {
			employer_content.setText("");
		}
		if (t.getUpdateTime()!=null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=dateFormat.format(t.getUpdateTime());
			comment_time.setText(time);
		}
		if (t.getImages()!=null) {
			try {
				gridView.setVisibility(View.VISIBLE);
				List<String> iamges=new ArrayList<>();
				JSONArray array=new JSONArray(t.getImages());
				if (array.length()>0) {
					for (int i = 0; i < array.length(); i++) {
						iamges.add(array.getString(i));
					}
				}
				
//				List<String> iamges=JSON.parseArray(t.getImages(), String.class);
				ImageAdapter adapter=new ImageAdapter(context, iamges, R.layout.comment_image);
				gridView.setAdapter(adapter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			gridView.setVisibility(View.GONE);
		}
		
//		if (t.getMore1()!=null) {
////			employer_name.setText(t.getMore1());
//			employer_name.setText("");
//		}else {
//			employer_name.setText("");
//		}
		
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getMore2(), employer_photo,MyApplication.options_photo);
		if (t.getMore3()!=null) {
			String phone=t.getMore3();
			String newphone=phone.substring(0, 3)+"****"+phone.substring(phone.length()-4,phone.length());
			employer_name.setText(newphone);
		}
		
		
	}

}
