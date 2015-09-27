package com.easymother.main.community;

import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.ImageZoom;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.main.my.TopicListActivity;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class HuLiShiAdapter extends CommonAdapter<TopicItemBean> {

	private Intent intent;
	private String flag = "";
	private List<TopicItemBean> list;

	public HuLiShiAdapter(Context context, List<TopicItemBean> list, int resource) {
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
					ImageZoom.showBigImgaes(context, t.getImages());
				}
			});

		} else {
			gridView.setVisibility(View.GONE);
		}

		final TextView msgAmoount = holder.getView(R.id.msgAmoount);
		// if ("zone_list".equals(flag) || "topic_help".equals(flag) ||
		// "user_topic".equals(flag)) {
		if ("zone_list".equals(flag)) {

			if ("YES".equals(t.getMore1())) {
				Drawable img = context.getResources().getDrawable(R.drawable.collection_2);
				img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
				msgAmoount.setCompoundDrawables(img, null, null, null);
			}
			if (t.getInt2() != 0) {
				msgAmoount.setText(t.getInt2() + "");
			} else {
				msgAmoount.setText("收藏");
			}
			msgAmoount.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (MyApplication.preferences.getInt("id", 0) == 0) {
						Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
						return;
					}
					if ("YES".equals(t.getMore1())) {
						Toast.makeText(context, "你已经收藏过了", Toast.LENGTH_SHORT).show();
					} else {
						if (msgAmoount.getTag() == "true") {
							Toast.makeText(context, "你已经收藏过了", Toast.LENGTH_SHORT).show();
						} else {
							RequestParams params = new RequestParams();
							params.put("userId", MyApplication.preferences.getInt("id", 0));
							params.put("forumPostId", t.getId());
							params.put("type", t.getType());
							NetworkHelper.doGet(BaseInfo.SAVE_COLLECTION, params, new JsonHttpResponseHandler() {
								public void onSuccess(int statusCode, org.apache.http.Header[] headers,
										org.json.JSONObject response) {
									if (JsonUtils.getRootResult(response).getIsSuccess()) {
										msgAmoount.setTag("true");
										if (t.getCollectionAmount() != null) {
											msgAmoount.setText((t.getCollectionAmount() + 1) + "");
										} else {
											msgAmoount.setText("1");
										}
										Drawable img = context.getResources().getDrawable(R.drawable.collection_2);
										img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
										msgAmoount.setCompoundDrawables(img, null, null, null);
									}
								};

								public void onFailure(int statusCode, org.apache.http.Header[] headers,
										String responseString, Throwable throwable) {
									Toast.makeText(context, "连接服务器失败", 0).show();
								};
							});

						}
					}
				}
			});

		} else {
			if (t.getInt2() != 0) {
				msgAmoount.setText(t.getInt2() + "");
			} else {
				msgAmoount.setText("回复");
			}
			Drawable img = context.getResources().getDrawable(R.drawable.huifu_1);
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			msgAmoount.setCompoundDrawables(img, null, null, null);
		}
		/**
		 * 点赞功能
		 */
		final TextView upAmount = holder.getView(R.id.upAmount);

		if ("YES".equals(t.getMore3())) {
			Drawable img = context.getResources().getDrawable(R.drawable.xin_2);
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			upAmount.setCompoundDrawables(img, null, null, null);
		}
		if (t.getMore2() != null) {
			upAmount.setText(t.getMore2());
		} else {
			upAmount.setText("");
		}
		upAmount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("YES".equals(t.getMore3())) {
					Toast.makeText(context, "你已经点过赞咯", Toast.LENGTH_SHORT).show();
				} else {

					if (MyApplication.preferences.getInt("id", 0) == 0) {
						Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
						return;
					}
					if (upAmount.getTag() == "true") {
						Toast.makeText(context, "你已经点过赞咯", Toast.LENGTH_SHORT).show();
						return;
					} else {
						RequestParams params = new RequestParams();
						params.put("userId", MyApplication.preferences.getInt("id", 0));
						params.put("fromId", t.getId());
						params.put("fromType", t.getType());
						NetworkHelper.doGet(BaseInfo.STAR_NURSE, params, new JsonHttpResponseHandler() {
							public void onSuccess(int statusCode, org.apache.http.Header[] headers,
									org.json.JSONObject response) {
								if (t.getMore2() != null && !"".equals(t.getMore2()) && !"0".equals(t.getMore2())) {
									String s = t.getMore2();
									upAmount.setText((Integer.valueOf(t.getMore2()) + 1) + "");
								} else {
									upAmount.setText("1");
								}
								upAmount.setTag("true");
								Drawable img = context.getResources().getDrawable(R.drawable.xin_2);
								img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
								upAmount.setCompoundDrawables(img, null, null, null);
							}
						});
					}
				}
			}
		});

		TextView share = holder.getView(R.id.share);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WeiXinUtils.shareDownloadUrl((Activity) context);
			}
		});
		// 如果是从我的话题打开的，就影藏收藏和点赞等功能
		if ("user_topic".equals(flag)) {
			msgAmoount.setVisibility(View.GONE);
			upAmount.setVisibility(View.GONE);
			share.setVisibility(View.GONE);

		}
	}

}
