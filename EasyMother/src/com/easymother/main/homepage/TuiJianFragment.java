package com.easymother.main.homepage;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TuiJianFragment extends Fragment {
	private View tuijianView;
	private ViewPager tuijian_content;//推荐的左右华东的容器
	private String name;
	private TuiJianAdapter adapter;//fragment适配器
	
	private LinearLayout viewpager_container;
	
	public void setName(String name){
		this.name=name;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tuijianView=inflater.inflate(R.layout.fragment_tuijian, null);
		Log.e("log大法", "fragment已建立");
		findView();
		init();
		
		return tuijianView;
	}
	
	private void init() {
		adapter=new TuiJianAdapter(getActivity(),getChildFragmentManager(), null);
		tuijian_content.setAdapter(adapter);
		//使viewpager整个页面都可以滑动，否则只有其中一个能滑动（配合viewpager的onpagerchengelistener使用）
		viewpager_container.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return tuijian_content.dispatchTouchEvent(event);
			}
		});
		tuijian_content.setOffscreenPageLimit(3);  
		tuijian_content.setPageMargin(10);
		tuijian_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				viewpager_container.invalidate();
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void findView() {
		tuijian_content=(ViewPager) tuijianView.findViewById(R.id.tuijian_viewpager);
		viewpager_container=(LinearLayout) tuijianView.findViewById(R.id.viewpager_container);
		
	}

	
}
