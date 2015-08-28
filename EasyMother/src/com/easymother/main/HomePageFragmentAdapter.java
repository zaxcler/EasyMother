package com.easymother.main;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

import com.easymother.configure.MyApplication;
import com.easymother.customview.AutoTextView;
import com.easymother.customview.ImageCycleView;
import com.easymother.customview.ImageCycleView.ImageCycleViewListener;
import com.easymother.customview.MyLinearlayout;
import com.easymother.main.R;
import com.easymother.main.R.id;
import com.easymother.main.R.layout;
import com.easymother.main.homepage.CuiRuShiListActivity;
import com.easymother.main.homepage.CuiRuiShiProjectActivity;
import com.easymother.main.homepage.MyWishListActivity;
import com.easymother.main.homepage.ShortOrderListActivity;
import com.easymother.main.homepage.TuiJianFragment;
import com.easymother.main.homepage.TuiJianItemFragment;
import com.easymother.main.homepage.YuYingShiListActivity;
import com.easymother.main.homepage.CommonListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HomePageFragmentAdapter extends FragmentPagerAdapter{
	private List<TuiJianFragment> list;

	public HomePageFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public  HomePageFragmentAdapter(FragmentManager fm,List<TuiJianFragment> list) {
		this(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
}