package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import com.easymother.bean.CuiRuShi;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
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
	private String type;//职业类型
	private TuiJianAdapter adapter;//fragment适配器
	private LinearLayout viewpager_container;
	private List<TuiJianItemFragment> list;
	
	/**
	 * 传入职业和数据集合(因为首页三个职业字段相同所以使用其中一个bean)
	 * @param <T>
	 * @param type
	 * 
	 */
	public  <T> TuiJianFragment(String type,List<T> t) {
		this.type=type;
		list=new ArrayList<>();
		
		for (T t1 : t) {
			/*
			 * 根据不同的type实例化不同的fragment 
			 */
			if ("yuesao".equals(type)) {
				YueSao yuesao=(YueSao) t1;
				TuiJianItemFragment fragment=new TuiJianItemFragment(type,yuesao);
				list.add(fragment);
			}
			if ("yuyingshi".equals(type)) {
				YuYingShi yuyingshi=(YuYingShi) t1;
				TuiJianItemFragment fragment=new TuiJianItemFragment(type,yuyingshi);
				list.add(fragment);
			}
			if ("cuirushi".equals(type)) {
				CuiRuShi cuirushi=(CuiRuShi) t1;
				TuiJianItemFragment fragment=new TuiJianItemFragment(type,cuirushi);
				list.add(fragment);
			}
			
		}
		
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
		if ("yuyingshi".equals(type)) {
		TuiJianYYSAdapter	adapter=new TuiJianYYSAdapter(getActivity(),getChildFragmentManager(), list);
			tuijian_content.setAdapter(adapter);
		}
		if ("yuesao".equals(type)) {
		TuiJianYSAdapter	adapter=new TuiJianYSAdapter(getActivity(),getChildFragmentManager(), list);
			tuijian_content.setAdapter(adapter);
		}if ("cuirushi".equals(type)) {
			adapter=new TuiJianAdapter(getActivity(),getChildFragmentManager(), list);
			tuijian_content.setAdapter(adapter);
		}
		
		
		//使viewpager整个页面都可以滑动，否则只有其中一个能滑动（配合viewpager的onpagerchengelistener使用）
		viewpager_container.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return tuijian_content.dispatchTouchEvent(event);
			}
		});
		
		Log.e("数据条数", list.size()+"");
		
		//如果数据大于3条则缓存3三条若小于3条则缓存list.size()条
		if (list.size()>=3) {
			tuijian_content.setOffscreenPageLimit(3); 
		}
		else {
			tuijian_content.setOffscreenPageLimit(list.size()); 
		}
		 
		tuijian_content.setPageMargin(20);
		tuijian_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				viewpager_container.invalidate();//在滑动过程中进行重绘 使显示流畅
				
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
