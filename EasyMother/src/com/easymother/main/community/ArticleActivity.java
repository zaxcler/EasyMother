package com.easymother.main.community;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.NewsInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow1;
import com.easymother.customview.MyPopupWindow1.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleActivity extends Activity {
	private Intent intent;
	private ImageView imageView1;
	private TextView title;
	private TextView content;
	private int id;
	private MyPopupWindow1 popupwindow;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.article_details);
		intent=getIntent();
		id=intent.getIntExtra("id", 0);
		findView();
		init();
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.meun));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						popupwindow.showAsDropDown(v);
					}
				});
			}
		});
		EasyMotherUtils.initTitle(this, "文章详情", true);
		
		
	}
	private void findView() {
		imageView1=(ImageView) findViewById(R.id.imageView1);
		title=(TextView) findViewById(R.id.article_title);
		content=(TextView) findViewById(R.id.content);
	}
	private void init() {
		RequestParams params=new RequestParams();
		if (id!=0) {
			params.put("id", id);
		}
		NetworkHelper.doGet(BaseInfo.YSYQ_TYPE_NEWS_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					NewsInfoBean bean=JsonUtils.getResult(response, NewsInfoBean.class);
					bindData(bean);
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("ArticleActivity", responseString);
			}
		});
		popupwindow=new MyPopupWindow1(ArticleActivity.this, R.layout.popupwindow_community);
		popupwindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {
			
			@Override
			public void onClick(View view) {
				/*
				 * 打开分享
				 */
				view.findViewById(R.id.share).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						WeiXinUtils.shareDownloadUrl(ArticleActivity.this, "www.qsmam.com", R.drawable.app, SendMessageToWX.Req.WXSceneSession);
						popupwindow.dismiss();
					}
				});
				view.findViewById(R.id.collection).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						RequestParams params=new RequestParams();
						params.put("newsId", id);
						int userId=MyApplication.preferences.getInt("id", 0);
						if (userId!=0) {
							params.put("userId", userId);
							NetworkHelper.doGet(BaseInfo.SAVE_YSYQ_TO_COLLECTION, params, new JsonHttpResponseHandler(){
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									if (JsonUtils.getRootResult(response).getIsSuccess()) {
										popupwindow.dismiss();
										Toast.makeText(ArticleActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
									}else {
										Toast.makeText(ArticleActivity.this, "收藏失败 ", Toast.LENGTH_SHORT).show();
										Log.e("收藏失败", response.toString());
									}
								};
								public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
									Toast.makeText(ArticleActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
									Log.e("连接服务器失败", responseString);
								};
							});
						}else {
							EasyMotherUtils.goActivity(ArticleActivity.this, LoginOrRegisterActivity.class);
						}
					}
				});
			}
			
		});
		
	}
	/**
	 * 绑定数据
	 * @param bean
	 */
	protected void bindData(NewsInfoBean bean) {
		if (bean!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getLogo(), imageView1, MyApplication.options_image);
			if (bean.getTitle()!=null) {
				title.setText(bean.getTitle());
			}else {
				title.setText(bean.getTitle());
			}
			if (bean.getContent()!=null) {
				content.setText(NetworkHelper.showFWBText(bean.getContent()));
			}else {
				content.setText("");
			}
		}
		
	}

}
