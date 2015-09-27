package com.easymother.main;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.UserInfo;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.community.MessageContralActivity;
import com.easymother.main.homepage.MyWishListActivity;
import com.easymother.main.homepage.OrderListActivity;
import com.easymother.main.my.CollectListActivity;
import com.easymother.main.my.ContactActivity;
import com.easymother.main.my.InfomationActivity;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.main.my.OrderDetailActivity;
import com.easymother.main.my.PayListActivity;
import com.easymother.main.my.SettingActivity;
import com.easymother.main.my.TopicListActivity;
import com.easymother.main.my.TopicReplyListActivity;
import com.easymother.main.my.VersionActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyPageFragment extends Fragment implements OnClickListener {
	private CircleImageView circleImageView;// 用户头像点击登录（未登录状态）登录状态进入个人信息
	private LinearLayout topiceLayout;// 话题
	private LinearLayout replyLayout;// 回复
	private LinearLayout collectionLayout;// 收藏

	private TextView user_name;// 话题次数

	private TextView topic_num;// 话题次数
	private TextView reply_num;// 回复
	private TextView collection_num;// 收藏

	private TextView pay;// 付款
	private TextView vip;// vip
	private TextView order;// 订单
	private TextView contact;// 联系
	private TextView wish;// 心愿单
	private TextView version;// 版本
	private TextView setting;// 设置

	private boolean isOnLine = false;// 是否登录
	
	public  Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
//			退出登录后刷新
			case 1:
				loadData();
				break;

			default:
				break;
			}
			
		};
	};

	public MyPageFragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mypage = inflater.inflate(R.layout.fragment_mypage, null);
		findView(mypage);
		init();
		return mypage;

	}

	private void findView(View view) {
		circleImageView = (CircleImageView) view.findViewById(R.id.user_photo);
		user_name = (TextView) view.findViewById(R.id.textView1);
		topic_num = (TextView) view.findViewById(R.id.topic_num);
		reply_num = (TextView) view.findViewById(R.id.topic_reply_num);
		collection_num = (TextView) view.findViewById(R.id.collection_num);
		pay = (TextView) view.findViewById(R.id.pay);
		order = (TextView) view.findViewById(R.id.order);
		contact = (TextView) view.findViewById(R.id.contact);
		wish = (TextView) view.findViewById(R.id.wish);
		version = (TextView) view.findViewById(R.id.version);
		setting = (TextView) view.findViewById(R.id.setting);

		topiceLayout = (LinearLayout) view.findViewById(R.id.topic);
		replyLayout = (LinearLayout) view.findViewById(R.id.topic_reply);
		collectionLayout = (LinearLayout) view.findViewById(R.id.collection);

	}

	private void init() {
		/**
		 * 如果本地未保存用户信息则显示默认图片
		 */
//		SharedPreferences preferences = MyApplication.preferences;
//		String image = preferences.getString("image", "");
//		String nickname = preferences.getString("nickname", "");
//
//		String account = preferences.getString("account", "");
//		if ("".equals(account)) {
//			isOnLine = false;
//			circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.user_defult));
//			user_name.setText("请登录");
//		} else {
//			isOnLine = true;
//			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + image, circleImageView);
//			user_name.setText(nickname);
//		}
		loadData();
		showUserMessage();
		circleImageView.setOnClickListener(this);
		user_name.setOnClickListener(this);
		topic_num.setOnClickListener(this);
		reply_num.setOnClickListener(this);
		pay.setOnClickListener(this);
		order.setOnClickListener(this);
		contact.setOnClickListener(this);
		wish.setOnClickListener(this);
		version.setOnClickListener(this);
		setting.setOnClickListener(this);
		topiceLayout.setOnClickListener(this);
		replyLayout.setOnClickListener(this);
		collectionLayout.setOnClickListener(this);

	}

	public void loadData() {
		RequestParams params=new RequestParams();
		NetworkHelper.doGet(BaseInfo.TO_PERSONAL_CENT, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				
					UserInfo info=JsonUtils.getResult(response, UserInfo.class);
					bindData(info);
				
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				topic_num.setText("0");
				reply_num.setText("0");
				collection_num.setText("0");
//				Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	/**
	 * 清除数据，供maintivity调用
	 */
	public void clearData(){
		topic_num.setText("0");
		reply_num.setText("0");
		collection_num.setText("0");
	}
	/*
	 * 绑定数据
	 */
	protected void bindData(UserInfo info) {
		if (info!=null) {
			topic_num.setText(info.getMore1());
			reply_num.setText(info.getMore2());
			collection_num.setText(info.getMore3());
		}else {
			topic_num.setText("");
			reply_num.setText("");
			collection_num.setText("");
		}
		
	}
	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		
		
		case R.id.user_photo:
			if (isOnLine) {
				EasyMotherUtils.goActivity(getActivity(), InfomationActivity.class);
			} else {
				EasyMotherUtils.goActivity(getActivity(), LoginOrRegisterActivity.class);
			}
			break;
		case R.id.textView1:
			break;
		case R.id.topic:
			EasyMotherUtils.goActivity(getActivity(), TopicListActivity.class);
			break;
		case R.id.topic_reply:
			Intent intent=new Intent();
			intent.putExtra("flag", "reply_list");
			intent.setClass(getActivity(), MessageContralActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.collection:
			EasyMotherUtils.goActivity(getActivity(), CollectListActivity.class);
			break;
		case R.id.pay:
			if (!isOnLine) {
				EasyMotherUtils.goActivity(getActivity(), LoginOrRegisterActivity.class);
			}else {
				EasyMotherUtils.goActivity(getActivity(), PayListActivity.class);
			}
			
			break;
		case R.id.order:
			if (!isOnLine) {
				EasyMotherUtils.goActivity(getActivity(), LoginOrRegisterActivity.class);
			}else {
				EasyMotherUtils.goActivity(getActivity(), OrderListActivity.class);
			}
			
			break;
		case R.id.contact:
			EasyMotherUtils.goActivity(getActivity(), ContactActivity.class);
			break;
		case R.id.wish:
			if (!isOnLine) {
				EasyMotherUtils.goActivity(getActivity(), LoginOrRegisterActivity.class);
			}else {
				EasyMotherUtils.goActivity(getActivity(), MyWishListActivity.class);
			}
			
			break;
		case R.id.version:
			EasyMotherUtils.goActivity(getActivity(), VersionActivity.class);
			break;
		case R.id.setting:
			EasyMotherUtils.goActivity(getActivity(), SettingActivity.class);
			break;

		}

	}

	/**
	 * 显示页面信息
	 */
	public void showUserMessage() {
		SharedPreferences preferences = MyApplication.preferences;

		if (preferences.getBoolean("isLogin", false)) {

			isOnLine = true;
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + preferences.getString("image", ""), circleImageView);
			Log.e("用户图片", BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + preferences.getString("image", ""));
			user_name.setText(preferences.getString("nickname", "请前去完善信息"));
			
		}else {
			isOnLine = false;
			circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.photo));
			user_name.setText("请登录");
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		showUserMessage();
		loadData();
		Log.e("----", "--------");
	}

}
