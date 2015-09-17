package com.easymother.main.community;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.auth.h;
import com.easymother.bean.ForumPost;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HuLiShiReplyAdapter extends BaseAdapter {
	private Context context;
	private List<ForumPost> list;
	private int resources[];
	private int flag;
	int i;
	private Date currentDate;

	public HuLiShiReplyAdapter(Context context, List<ForumPost> list, int resources[]) {
		this.context = context;
		this.list = list;
		this.resources = resources;
		currentDate=new Date(System.currentTimeMillis());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ForumPost forumPost=(ForumPost) getItem(position);
		ViewHolder holder=null;
		ViewHolder holder1=null;
		if (forumPost!=null) {
			//如果是1级回复
			if (forumPost.getGrade()==1) {
				holder = ViewHolder.getInstance(context, position, convertView, parent, resources[0]);
				TextView user_name=holder.getView(R.id.user_name);
				if (forumPost.getUserNickname()!=null) {
					user_name.setText(forumPost.getUserNickname());
				}else {
					user_name.setText("");
				}
				CircleImageView circleImageView1=holder.getView(R.id.circleImageView1);
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+forumPost.getUserImage(), circleImageView1, MyApplication.options_photo);
				TextView floor=holder.getView(R.id.floor);
				floor.setText((position+1)+"楼");
				TextView show_time=holder.getView(R.id.show_time);
				if (forumPost.getCreateTime()!=null) {
				
					show_time.setText(TimeCounter.CountTime(forumPost.getCreateTime(), currentDate));
				}else {
					show_time.setText("");
				}
				TextView content=holder.getView(R.id.content);
				if (forumPost.getContent()!=null) {
					content.setText(forumPost.getContent());
				}else {
					content.setText("");
				}
				MyGridView pictures=holder.getView(R.id.pictures);
				if (forumPost.getImages()!=null) {
					List<String> images=JSON.parseArray(forumPost.getImages(), String.class);
					
					ImageAdapter adapter=new ImageAdapter(context, images, R.layout.comment_image);
					pictures.setAdapter(adapter);
				}else {
					pictures.setVisibility(View.GONE);
				}
				convertView=holder.getConvertView();
			}
			//如果是2级及以上的回复
			else {
				 holder1 = ViewHolder.getInstance(context, position, convertView, parent, resources[1]);
				 TextView user_name=holder1.getView(R.id.user_name);
					if (forumPost.getUserNickname()!=null) {
						user_name.setText(forumPost.getUserNickname());
					}else {
						user_name.setText("");
					}
					
					CircleImageView circleImageView1=holder1.getView(R.id.circleImageView1);
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+forumPost.getUserImage(), circleImageView1, MyApplication.options_photo);
					TextView floor=holder1.getView(R.id.floor);
					floor.setText((position+1)+"楼");
					
					TextView show_time=holder1.getView(R.id.show_time);
					if (forumPost.getCreateTime()!=null) {
						
						show_time.setText(TimeCounter.CountTime(forumPost.getCreateTime(),currentDate));
					}else {
						show_time.setText("");
					}
					
					TextView parent_name=holder1.getView(R.id.parent_name);
					if (forumPost.getParentUserNickname()!=null) {
						parent_name.setText(forumPost.getParentUserNickname());
					}else {
						parent_name.setText("");
					}
					
					
					TextView child_content=holder1.getView(R.id.child_content);
					if (forumPost.getContent()!=null) {
						child_content.setText(":"+forumPost.getContent());
					}else {
						child_content.setText("");
					}
					//要后台添加父文本
					TextView parent_conten=holder1.getView(R.id.parent_conten);
					if (forumPost.getContent()!=null) {
						parent_conten.setText(":"+forumPost.getContent());
					}else {
						parent_conten.setText("");
					}
					convertView=holder1.getConvertView();
					//2级回复及以上暂时不要图片
//					MyGridView pictures=holder.getView(R.id.pictures);
//					if (forumPost.getImages()!=null) {
//						List<String> images=JSON.parseArray(forumPost.getImages(), String.class);
//						ImageAdapter adapter=new ImageAdapter(context, images, R.layout.comment_image);
//						pictures.setAdapter(adapter);
//					}else {
//						pictures.setVisibility(View.GONE);
//					}
			}
			 return convertView;
		}else {
			return null;
		}
		
		
	}

}
