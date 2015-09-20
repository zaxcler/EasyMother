package com.easymother.main.community;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
import com.easymother.utils.NetworkHelper;
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
	public void add(ForumPost forumPost){
		list.add(forumPost);
	}
	/*
	 * 返回布局类型
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		//根据回复的分级来判断view的类型
		return list.get(position).getGrade()<=1?0:1;
	}
	/**
	 *返回布局数
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ForumPost forumPost=(ForumPost) getItem(position);
		ViewHolder holder=null;
		if (forumPost!=null) {
			
			switch (getItemViewType(position)) {
			case 0:
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
					content.setText(NetworkHelper.showFWBText(forumPost.getContent()));
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
				TextView reply=holder.getView(R.id.replay);
				reply.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						HuLiShiReplyListActivity  activity=	(HuLiShiReplyListActivity)context;
						activity.id=forumPost.getId();
//						activity.editText1.setFocusable(true);
//						activity.editText1.setFocusableInTouchMode(true);
						//强制获取焦点
						activity.editText1.requestFocus();
						activity.parentContent=forumPost.getContent();
						activity.editText1.setHint("回复"+forumPost.getUserNickname());
						activity.parentUserNickName=forumPost.getParentUserNickname();
						//调用输入法
						InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
//						imm.showSoftInput(m_receiverView(接受软键盘输入的视图(View)),InputMethodManager.SHOW_FORCED(提供当前操作的标记，SHOW_FORCED表示强制显示)); 
						imm.showSoftInput(activity.editText1,InputMethodManager.SHOW_FORCED); 
						
						
					}
				});
				convertView=holder.getConvertView();
				break;
			case 1:
				holder = ViewHolder.getInstance(context, position, convertView, parent, resources[1]);
				 TextView user_name1=holder.getView(R.id.user_name);
					if (forumPost.getUserNickname()!=null&&!"".equals(forumPost.getUserNickname())) {
						user_name1.setText(forumPost.getUserNickname());
					}else {
						user_name1.setText("");
					}
					
					CircleImageView circleImageView=holder.getView(R.id.circleImageView1);
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+forumPost.getUserImage(), circleImageView, MyApplication.options_photo);
					TextView floor1=holder.getView(R.id.floor);
					floor1.setText((position+1)+"楼");
					TextView show_time1=holder.getView(R.id.show_time);
					if (forumPost.getCreateTime()!=null&&!"".equals(forumPost.getCreateTime())) {
						show_time1.setText(TimeCounter.CountTime(forumPost.getCreateTime(),currentDate));
					}else {
						show_time1.setText("");
					}
					
					TextView parent_name=holder.getView(R.id.parent_name);
					if (forumPost.getParentUserNickname()!=null&&!"".equals(forumPost.getParentUserNickname())) {
						parent_name.setText(forumPost.getParentUserNickname());
					}else {
						parent_name.setText("");
					}
					
					
					TextView child_content=holder.getView(R.id.child_content);
					if (forumPost.getContent()!=null&&!"".equals(forumPost.getContent())) {
						child_content.setText(":"+NetworkHelper.showFWBText(forumPost.getParentCountent()));
					}else {
						child_content.setText("");
					}
					//要后台添加父文本
					TextView parent_conten=holder.getView(R.id.parent_conten);
					if (forumPost.getParentCountent()!=null&&!"".equals(forumPost.getParentCountent())) {
						parent_conten.setText(NetworkHelper.showFWBText(forumPost.getParentCountent()));
					}else {
						parent_conten.setText("");
					}
					TextView reply1=holder.getView(R.id.reply);
					reply1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							HuLiShiReplyListActivity  activity=	(HuLiShiReplyListActivity)context;
							activity.id=forumPost.getId();
//							activity.editText1.setFocusable(true);
//							activity.editText1.setFocusableInTouchMode(true);
							activity.editText1.requestFocus();
							if (forumPost.getParentUserNickname()!=null&&!"".equals(forumPost.getParentUserNickname())) {
								activity.editText1.setHint("回复"+forumPost.getUserNickname());
							}
							if (forumPost.getContent()!=null&&!"".equals(forumPost.getContent())) {
								activity.parentContent=forumPost.getContent();
							}
							if (forumPost.getUserNickname()!=null&&!"".equals(forumPost.getUserNickname())) {
								activity.parentUserNickName=forumPost.getUserNickname();
							}
							InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
//							imm.showSoftInput(m_receiverView(接受软键盘输入的视图(View)),InputMethodManager.SHOW_FORCED(提供当前操作的标记，SHOW_FORCED表示强制显示)); 
							imm.showSoftInput(activity.editText1,InputMethodManager.SHOW_FORCED); 
						}
					});
					convertView=holder.getConvertView();
				break;

			
			}
			
				
			
				 
			 return convertView;
		}else {
			return null;
		}
		
		
	}

}
