package com.easymother.main;


import com.easymother.customview.ImageCycleView;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private ViewPager mPager;// 
	private LinearLayout home_page;// tab首页
	private LinearLayout community_page;// tab社区
	private LinearLayout babytime_page;// tab宝贝时光
	private LinearLayout my_page;// tab个人
	
	private ImageView homeImageView;//首页tab图标
	private ImageView communityImageView;//社区tabͼ图标
	private ImageView babytimeImageView;//宝贝时光tabͼ图标
	private ImageView mypageImageView;//个人tabͼ图标
	
	private TextView homeText;//tab首页text
	private TextView communityText;//tab社区text
	private TextView babytimeText;//tab宝贝时光text
	private TextView mytText;//tab图标text
	
	private HomePageFragment homePageFragment;//首页fragment
	private CommunityPageFragment communityPageFragment;//社区fragment
	private BabyTiemFragment babyTiemFragment;//宝贝时光fragment
	private MyPageFragment myPageFragment;//个人fragment
	private FragmentTransaction transaction;
	
	private FrameLayout frameLayout;//主容器
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		
		
		findView();
		init();
		transaction=getSupportFragmentManager().beginTransaction();//开启fragment管理器
		homePageFragment=new HomePageFragment();
		transaction.add(R.id.main_content, homePageFragment);//添加fragment
		transaction.show(homePageFragment);//显示fragment
		transaction.commit();//提交
		homeImageView.setImageDrawable(getResources().getDrawable(R.drawable.community_choose));
		homeText.setTextColor(getResources().getColor(R.color.lightredwine));
		
		
	}

	private void init() {
		home_page.setOnClickListener(this);
		community_page.setOnClickListener(this);
		babytime_page.setOnClickListener(this);
		my_page.setOnClickListener(this);
	}

	private void findView() {
		frameLayout = (FrameLayout) findViewById(R.id.main_content);
//		mPager = (ViewPager) findViewById(R.id.main_content);
		home_page = (LinearLayout) findViewById(R.id.home_page);
		community_page = (LinearLayout) findViewById(R.id.community_page);
		babytime_page = (LinearLayout) findViewById(R.id.babytime_page);
		my_page = (LinearLayout) findViewById(R.id.my_page);
		
		homeImageView=(ImageView) findViewById(R.id.home_page_icon);
		communityImageView=(ImageView) findViewById(R.id.community_page_icon);
		babytimeImageView=(ImageView) findViewById(R.id.babytime_page_icon);
		mypageImageView=(ImageView) findViewById(R.id.my_page_icon);
		
		homeText=(TextView) findViewById(R.id.home_page_text);
		communityText=(TextView) findViewById(R.id.community_page_text);
		babytimeText=(TextView) findViewById(R.id.babytime_page_text);
		mytText=(TextView) findViewById(R.id.my_page_text);
		
	}

	
	//清楚选中状态
	private void clearChooseStatus(){
		homeImageView.setImageDrawable(getResources().getDrawable(R.drawable.home));
		communityImageView.setImageDrawable(getResources().getDrawable(R.drawable.community));
		babytimeImageView.setImageDrawable(getResources().getDrawable(R.drawable.babytime));
		mypageImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
		
		homeText.setTextColor(getResources().getColor(R.color.boroblacktext));
		communityText.setTextColor(getResources().getColor(R.color.boroblacktext));
		babytimeText.setTextColor(getResources().getColor(R.color.boroblacktext));
		mytText.setTextColor(getResources().getColor(R.color.boroblacktext));
		
	}

	@Override
	public void onClick(View view) {
		transaction=getSupportFragmentManager().beginTransaction();//开启fragment管理器
		switch (view.getId()) {
		case R.id.home_page:
			clearChooseStatus();
			homeImageView.setImageDrawable(getResources().getDrawable(R.drawable.home_choose));
			homeText.setTextColor(getResources().getColor(R.color.lightredwine));
			Log.e("click", "click----R.id.home_page");
			if (homePageFragment==null) {
				homePageFragment=new HomePageFragment();
				transaction.add(R.id.main_content, homePageFragment);
			}
			if (communityPageFragment!=null) {
				transaction.hide(communityPageFragment);
			}
			if (babyTiemFragment!=null) {
				transaction.hide(babyTiemFragment);
			}
			if (myPageFragment!=null) {
				transaction.hide(myPageFragment);
			}
			transaction.show(homePageFragment);
			transaction.commit();
			break;

		case R.id.community_page:
			clearChooseStatus();
			communityImageView.setImageDrawable(getResources().getDrawable(R.drawable.community_choose));
			communityText.setTextColor(getResources().getColor(R.color.lightredwine));
			Log.e("click", "click----R.id.community_page");
			if (communityPageFragment==null) {
				communityPageFragment=new CommunityPageFragment();
				transaction.add(R.id.main_content, communityPageFragment);
			}
			if (homePageFragment!=null) {
				transaction.hide(homePageFragment);
			}
			if (babyTiemFragment!=null) {
				transaction.hide(babyTiemFragment);
			}
			if (myPageFragment!=null) {
				transaction.hide(myPageFragment);
			}
			transaction.show(communityPageFragment);
			transaction.commit();
			break;
		case R.id.babytime_page:
			clearChooseStatus();
			babytimeImageView.setImageDrawable(getResources().getDrawable(R.drawable.babytime_choose));
			babytimeText.setTextColor(getResources().getColor(R.color.lightredwine));
			Log.e("click", "click----R.id.babytime_page");
			if ( babyTiemFragment==null) {
				babyTiemFragment=new BabyTiemFragment();
				transaction.add(R.id.main_content, babyTiemFragment);
			}
			if (homePageFragment!=null) {
				transaction.hide(homePageFragment);
			}
			if (communityPageFragment!=null) {
				transaction.hide(communityPageFragment);
			}
			if (myPageFragment!=null) {
				transaction.hide(myPageFragment);
			}
			transaction.show(babyTiemFragment);
			transaction.commit();
			break;
		case R.id.my_page:
			clearChooseStatus();
			mypageImageView.setImageDrawable(getResources().getDrawable(R.drawable.user_choosepng));
			mytText.setTextColor(getResources().getColor(R.color.lightredwine));
			Log.e("click", "click----R.id.my_page");
			if ( myPageFragment ==null) {
				myPageFragment=new MyPageFragment();
				transaction.add(R.id.main_content, myPageFragment);
			}
			if (homePageFragment!=null) {
				transaction.hide(homePageFragment);
			}
			if (babyTiemFragment!=null) {
				transaction.hide(babyTiemFragment);
			}
			if (communityPageFragment!=null) {
				transaction.hide(communityPageFragment);
			}
			transaction.show(myPageFragment);
			transaction.commit();
			break;

		}
		
	}
	

}
