package com.easymother.main.community;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.easymother.bean.ForumPostBean;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.R;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class TopicListAdapter extends CommonAdapter<TopicItemBean> {

	private Intent intent;
	private String flag="";
	protected TopicListAdapter(Context context, List<TopicItemBean> list, int resource) {
		super(context, list, resource);
		intent=new Intent();
		if (context instanceof HuLiShiZoneDetailActivity) {
			flag="detail";
			intent.setClass((Activity)context, HuLiShiReplyListActivity.class);
		}else {
			intent.setClass((Activity)context, HuLiShiZoneDetailActivity.class);
		}
	}

	@Override
	public void setDataToItem(ViewHolder holder, final TopicItemBean t) {
//		if (!"detail".equals(flag)) {
			holder.getConvertView().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//userId实际上是nurseId 因为后台懒 返回的数据是这个所以。。你懂的
					intent.putExtra("id", t.getUserId());
					context.startActivity(intent);
					
				}
			});
//		}
		
		CircleImageView circleImageView=holder.getView(R.id.circleImageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getUserImae(), circleImageView,MyApplication.options_image);
		
		TextView name=holder.getView(R.id.name);
		if (t.getUserNickname()!=null) {
			name.setText(t.getUserNickname());
		}else {
			name.setText("");
		}
		TextView time=holder.getView(R.id.time);
//		if (t.getUpdateTime()!=null) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			try {
//				Date date=format.parse(t.getUpdateTime());
//				Calendar calendar=Calendar.getInstance();
//				calendar.setTime(date);
//				time.setText(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
//				
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}else {
//			time.setText("");
//		}
		if (t.getShowTime()!=null) {
			time.setText(t.getShowTime());
			
		}else {
			time.setText("");
		}
		
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
			content.setText(NetworkHelper.showFWBText(t.getContent()));
		}else {
			content.setText("");
		}
		GridView gridView=holder.getView(R.id.pictures);
		if (t.getImages()!=null&&!"".equals(t.getImages())) {
//			String json= "[\"150907112001easymother20150907_111958.\",\"150907112001easymother20150907_111958.jpg\"]";
//			List<String> list=JSON.parseArray(t.getImages(), String.class);
			Log.e("tupian", t.getImages());
			List<String> list=JSON.parseArray(t.getImages().toString(), String.class);
			if (list!=null) {
				gridView.setVisibility(View.VISIBLE);
				ImageAdapter adapter=new ImageAdapter(context, list, R.layout.comment_image);
				gridView.setAdapter(adapter);
			}
			
		}else {
			gridView.setVisibility(View.GONE);
		}
		final TextView msgAmoount=holder.getView(R.id.msgAmoount);
		if (t.getCollectionAmount()!=null) {
			msgAmoount.setText(t.getCollectionAmount()+"");
		}else {
			msgAmoount.setText("收藏");
		}
		msgAmoount.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				if (msgAmoount.getTag()!="true") {
					if (MyApplication.preferences.getInt("id", 0)!=0) {
						RequestParams params=new RequestParams();
						params.put("userId", MyApplication.preferences.getInt("id", 0));
						params.put("fromPostId", t.getId());
						NetworkHelper.doGet(BaseInfo.SAVE_COLLECTION, params, new JsonHttpResponseHandler(){
							public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {
								if (t.getCollectionAmount()!=null) {
									msgAmoount.setText((t.getCollectionAmount()+1)+"");
								}else {
									msgAmoount.setText("1");
								}
								
							};
							public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
								Toast.makeText(context, "连接服务器失败", 0).show();
							};
						});
						
					}else {
						EasyMotherUtils.goActivity((Activity)context, LoginOrRegisterActivity.class);
					}
					
					
				}else {
					Toast.makeText(context, "你已经点过赞咯", Toast.LENGTH_SHORT).show();
				}
			
			}
		});
		/**
		 * 点赞功能
		 */
		final TextView upAmount=holder.getView(R.id.upAmount);
		upAmount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (upAmount.getTag()!="true") {
					if (MyApplication.preferences.getInt("id", 0)!=0) {
						RequestParams params=new RequestParams();
						params.put("userId", MyApplication.preferences.getInt("id", 0));
						params.put("fromId", t.getId());
						params.put("fromType", t.getType());
						NetworkHelper.doGet(BaseInfo.STAR_NURSE, params, new JsonHttpResponseHandler(){
							public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {
								if (t.getUpAmount()!=null) {
									upAmount.setText((t.getUpAmount()+1)+"");
								}else {
									upAmount.setText("1");
								}
								
								upAmount.setTag("true");
							};
							public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
								Toast.makeText(context, "连接服务器失败", 0).show();
							};
						});
						
					}else {
						EasyMotherUtils.goActivity((Activity)context, LoginOrRegisterActivity.class);
					}
					
					
				}else {
					Toast.makeText(context, "你已经点过赞咯", Toast.LENGTH_SHORT).show();
				}
			}
		});
		if (t.getUpAmount()!=null) {
			upAmount.setText(t.getUpAmount()+"");
		}else {
			upAmount.setText("");
		}

	}

}
