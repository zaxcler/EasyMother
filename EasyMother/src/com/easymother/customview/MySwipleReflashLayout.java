package com.easymother.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MySwipleReflashLayout extends SwipeRefreshLayout {

	public MySwipleReflashLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MySwipleReflashLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int lastX, lastY, oldX, oldY;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		lastX = l;
		oldX = oldl;
		lastY = t;
		oldY = oldt;
		super.onScrollChanged(l, t, oldl, oldt);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (Math.abs(oldY - lastY) < Math.abs(lastX - oldX)) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	

}
