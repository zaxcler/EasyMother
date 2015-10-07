package com.easymother.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
//	private onReflashLisenter reflashListener;
	private int mTouchSlop;
	// 上一次触摸时的X坐标
	private float mPrevX;
	// 上一次触摸时的y坐标
	private float mPrevY;
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public MyScrollView(Context context) {
		super(context);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
//	@Override
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		super.onScrollChanged(l, t, oldl, oldt);
////		滚动到顶部判断：getScrollY() == 0
////		滚动到底部判断
////        View contentView = getChildAt(0);
////		contentView.getMeasuredHeight() <= getScrollY() + getHeight();
////		View contentView = getChildAt(0);
////		contentView.getMeasuredHeight() <= getScrollY() + getHeight();
////		其中getChildAt表示得到ScrollView的child View，因为ScrollView只允许一个child view，所以contentView.getMeasuredHeight()表示得到子View的高度,
////		getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
//		View childView=getChildAt(0);
//		//如果Y等于0  则调用顶部刷新
//		if (childView!=null&&getY()==0) {
//			reflashListener.onHeadReflash();
//		}
//		else if(childView!=null&& getScrollY() + getHeight() >= computeVerticalScrollRange()){
//			reflashListener.onFootReflash();
//		}
//		
//		
//	}
	
	
//	//底部刷新监听
//	public interface onReflashLisenter{
//		
//		public void onFootReflash();
//		public void onHeadReflash();
//	}
//	//设置底部刷洗监听器
//	public void setOnReflashListener(onReflashLisenter reflashListener){
//		this.reflashListener=reflashListener;
//	}
		
//	private int lastX,lastY,oldX,oldY;
//	
//	@Override
//		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		
//		
//		lastX=l;
//		oldX=oldl;
//		lastY=t;
//		oldY=oldt;
//			super.onScrollChanged(l, t, oldl, oldt);
//			
//		}
//	
//	@Override
//		public boolean onInterceptTouchEvent(MotionEvent ev) {
//		if (Math.abs(oldY-lastY)>2*Math.abs(lastX-oldX)) {
//			return true;
//		}
//		return false;
//		}
//	
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
