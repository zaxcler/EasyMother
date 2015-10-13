package com.easymother.main.my;

import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alidao.mama.R;
import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.ImageZoom;
import com.easymother.customview.MyGridView;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.main.community.HuLiShiReplyListActivity;
import com.easymother.main.community.HuLiShiZoneDetailActivity;
import com.easymother.main.community.HuLiShiZoneListActivity;
import com.easymother.main.community.TopicAndAskListActivity;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyTopicAdapter extends CommonAdapter<TopicItemBean>{
	private Intent intent;
	private String flag = "";
	private List<TopicItemBean> list;

	public MyTopicAdapter(Context context, List<TopicItemBean> list, int resource) {
		super(context, list, resource);
		intent = new Intent();
		this.list = list;
		if (context instanceof HuLiShiZoneDetailActivity) {
			flag = "hulishi_reply";
			intent.setClass((Activity) context, HuLiShiReplyListActivity.class);
		} else if (context instanceof HuLiShiZoneListActivity) {
			flag = "zone_list";
			intent.setClass((Activity) context, HuLiShiZoneDetailActivity.class);
		} else if (context instanceof TopicAndAskListActivity) {
			flag = "topic_help";
			intent.setClass((Activity) context, HuLiShiReplyListActivity.class);
		} else if (context instanceof TopicListActivity) {
			flag = "user_topic";
			intent.setClass((Activity) context, HuLiShiReplyListActivity.class);
		}
	}

	public void addList(List<TopicItemBean> list) {
		this.list.addAll(list);
	}
	public void clearList(){
		this.list.clear();
	}
	

	@Override
	public void setDataToItem(ViewHolder holder, final TopicItemBean t) {
		holder.getConvertView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int s = t.getId();
				// userId实际上是nurseId 因为后台懒 返回的数据是这个所以。。你懂的
				if ("hulishi_reply".equals(flag) || "topic_help".equals(flag) || "user_topic".equals(flag)) {
					intent.putExtra("id", t.getId());
				} else {
					intent.putExtra("id", t.getUserId());
				}
				intent.putExtra("type", t.getType());
				context.startActivity(intent);

			}
		});
		holder.getConvertView().setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder=new AlertDialog.Builder(context);
				builder.setTitle("是否删除");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						RequestParams params=new RequestParams();
						params.put("id", t.getId());
						NetworkHelper.doGet(BaseInfo.DELETE_TOPIC_HELP,params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (JsonUtils.getRootResult(response).getIsSuccess()) {
									Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
									((TopicListActivity)context).loadData();
//									activity.loadData();
								}else {
									Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
								}
							}
						});
						dialog.dismiss();
					}
				
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alertDialog=builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();
				return true;
			}
		});
		CircleImageView circleImageView = holder.getView(R.id.circleImageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + t.getUserImage(),
				circleImageView, MyApplication.options_image);

		TextView nurse_type = holder.getView(R.id.nurse_type);
		if (t.getJob() != null && !"topic_help".equals(flag) && !"user_topic".equals(flag)) {
			if ("YS".equals(t.getJob())) {
				nurse_type.setText("月嫂");
			}
			if ("YYS".equals(t.getJob())) {
				nurse_type.setText("育婴师");
			}
			if ("CRS".equals(t.getJob())) {
				nurse_type.setText("催乳师");
			}
			if ("detail".equals(flag)) {
				nurse_type.setVisibility(View.GONE);
			}

		} else {
			nurse_type.setVisibility(View.GONE);
		}

		TextView name = holder.getView(R.id.name);
		if (t.getUserNickname() != null) {
			name.setText(t.getUserNickname());
		} else {
			name.setText("");
		}
		TextView time = holder.getView(R.id.time);
		//
		if (t.getCreateTime() != null) {

			String showTime = TimeCounter.CountTime(t.getCreateTime(), new Date(System.currentTimeMillis()));
			time.setText(showTime);

		} else {
			time.setText("");
		}

		TextView content = holder.getView(R.id.content);
		if (t.getContent() != null) {
			content.setText(NetworkHelper.showFWBText(t.getContent()));
		} else {
			content.setText("");
		}
		MyGridView gridView = holder.getView(R.id.pictures);
		if (t.getImages() != null && !"".equals(t.getImages())) {
//			Log.e("tupian", t.getImages());
			List<String> list = JSON.parseArray(t.getImages().toString(), String.class);
			if (list != null) {
				gridView.setVisibility(View.VISIBLE);
				// RelativeLayout.LayoutParams params=new
				// RelativeLayout.LayoutParams(MyApplication.getScreen_width(),ViewGroup.LayoutParams.WRAP_CONTENT);
				// gridView.setLayoutParams(params);
				ImageAdapter adapter = new ImageAdapter(context, list, R.layout.comment_image);
				gridView.setAdapter(adapter);
			}
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					ImageZoom.showBigImgaes(context, t.getImages(),arg2);
				}
			});

		} else {
			gridView.setVisibility(View.GONE);
		}

		
	}

}
