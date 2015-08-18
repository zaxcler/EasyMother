package com.easymother.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	public MyViewPager(Context context) {
		this(context, null);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// 返回false 作为父控件 对触摸事件放行 不处理触摸事件
		return false;
	}


}
