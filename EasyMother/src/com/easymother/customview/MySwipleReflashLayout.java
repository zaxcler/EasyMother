package com.easymother.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MySwipleReflashLayout extends SwipeRefreshLayout {
	private int mTouchSlop;
	// 上一次触摸时的X坐标
	private float mPrevX;
	// 上一次触摸时的y坐标
	private float mPrevY;

	public MySwipleReflashLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public MySwipleReflashLayout(Context context) {
		super(context);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

//	private int lastX, lastY, oldX, oldY;
//
//	@Override
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//
//		lastX = l;
//		oldX = oldl;
//		lastY = t;
//		oldY = oldt;
//		super.onScrollChanged(l, t, oldl, oldt);
//
//	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
//		if (Math.abs(oldY - lastY) < Math.abs(lastX - oldX)) {
//			return false;
//		}
		
		  switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
              mPrevX = event.getX();
              break;

          case MotionEvent.ACTION_MOVE:
              final float eventX = event.getX();
              float xDiff = Math.abs(eventX - mPrevX);
              // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
              // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
              if (xDiff > mTouchSlop + 60) {
                  return false;
              }
      }
		return super.onInterceptTouchEvent(event);
	}

}
