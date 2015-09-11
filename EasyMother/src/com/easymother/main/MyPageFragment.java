package com.easymother.main;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.homepage.MyWishListActivity;
import com.easymother.main.homepage.OrderListActivity;
import com.easymother.main.my.CollectListActivity;
import com.easymother.main.my.ContactActivity;
import com.easymother.main.my.InfomationActivity;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.main.my.OrderDetailActivity;
import com.easymother.main.my.SettingActivity;
import com.easymother.main.my.TopicListActivity;
import com.easymother.main.my.TopicReplyListActivity;
import com.easymother.main.my.VersionActivity;
import com.easymother.utils.EasyMotherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.SharedPreferences;
import android.os.Bundle;
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
		vip = (TextView) view.findViewById(R.id.vip);
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
		
		showUserMessage();
		circleImageView.setOnClickListener(this);
		user_name.setOnClickListener(this);
		topic_num.setOnClickListener(this);
		reply_num.setOnClickListener(this);
		pay.setOnClickListener(this);
		vip.setOnClickListener(this);
		order.setOnClickListener(this);
		contact.setOnClickListener(this);
		wish.setOnClickListener(this);
		version.setOnClickListener(this);
		setting.setOnClickListener(this);
		topiceLayout.setOnClickListener(this);
		replyLayout.setOnClickListener(this);
		collectionLayout.setOnClickListener(this);

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
			EasyMotherUtils.goActivity(getActivity(), TopicReplyListActivity.class);
			break;
		case R.id.collection:
			EasyMotherUtils.goActivity(getActivity(), CollectListActivity.class);
			break;
		case R.id.pay:
			EasyMotherUtils.goActivity(getActivity(), CollectListActivity.class);
			break;
		case R.id.vip:

			break;
		case R.id.order:
			EasyMotherUtils.goActivity(getActivity(), OrderListActivity.class);
			break;
		case R.id.contact:
			EasyMotherUtils.goActivity(getActivity(), ContactActivity.class);
			break;
		case R.id.wish:
			EasyMotherUtils.goActivity(getActivity(), MyWishListActivity.class);
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
			circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.user_defult));
			user_name.setText("请登录");
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		showUserMessage();
	}

}
