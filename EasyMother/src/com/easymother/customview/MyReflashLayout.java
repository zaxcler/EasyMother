package com.easymother.customview;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

//
//import com.easymother.main.R;
//
//import android.content.Context;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.ListView;
//
//
///** 
// * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能. 
// *  
// * @author zaxcler
// */ 
public class MyReflashLayout implements OnScrollListener{

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
//	public class MyReflashLayout extends SwipeRefreshLayout implements OnScrollListener{
//	
//	/** 
//     * 滑动到最下面时的上拉操作 
//     */  
//  
//    private int mTouchSlop;  
//    /** 
//     * listview实例 
//     */  
//    private ListView mListView;  
//  
//    /** 
//     * 上拉监听器, 到了最底部的上拉加载操作 
//     */  
//    private onLoadingLisenter mOnLoadListener;  
//  
//    /** 
//     * ListView的加载中footer 
//     */  
//    private View mListViewFooter;  
//  
//    /** 
//     * 按下时的y坐标 
//     */  
//    private int mYDown;  
//    /** 
//     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉 
//     */  
//    private int mLastY;  
//    /** 
//     * 是否在加载中 ( 上拉加载更多 ) 
//     */  
//    private boolean isLoading = false; 
//    
//    private Animation animation;//刷新时的动画
//
//	public MyReflashLayout(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		//getScaledTouchSlop getScaledTouchSlop是一个距离，表示滑动的时候，
//		//手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件，如viewpager就是
//		//用这个距离来判断用户是否翻页
//		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop(); 
//		//刷新的底部
//		mListViewFooter=LayoutInflater.from(context).inflate(R.layout.foot_loading,null);
////		mListViewFooter=inflate(context, R.layout.foot_loading, null);
//		animation=AnimationUtils.loadAnimation(context, R.anim.rotation);
//	}
//
//	public MyReflashLayout(Context context) {
//		super(context);
//		//getScaledTouchSlop getScaledTouchSlop是一个距离，表示滑动的时候，
//				//手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件，如viewpager就是
//				//用这个距离来判断用户是否翻页
//				mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop(); 
//				//刷新的底部
//				mListViewFooter=LayoutInflater.from(context).inflate(R.layout.foot_loading,null);
////				mListViewFooter=inflate(context, R.layout.foot_loading, null);
//				animation=AnimationUtils.loadAnimation(context, R.anim.rotation);
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	
//
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
//		//获取子控件
//		int childs=getChildCount();
//		if (childs>0) {
//			View childview=getChildAt(0);
//			if (childview instanceof ListView) {
//				mListView=(ListView) childview;
//				//给listview设置滚动监听
//				mListView.setOnScrollListener(this);
//			}
//		}
//	}
//
//	//分发触摸事件
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			//记录按下时的y值
//			mYDown=(int) ev.getRawY();
//			
//			break;
//		case MotionEvent.ACTION_MOVE:
//			//移动过程中记录y值
//			mLastY=(int) ev.getRawY();
//			
//			break;
//		case MotionEvent.ACTION_UP:
//				if (canload()) {
//					loadData();
//				}
//			break;
//
//		}
//		return super.dispatchTouchEvent(ev);
//		
//	}
//	private void loadData() {
//		if (mOnLoadListener!=null) {
//			if (!isLoading) {
//				setloadingState(true);
//				if (mOnLoadListener!=null) {
//					mOnLoadListener.onLoadingData();
//				}
//			
//			}
//			
//		}
//		
//	}
//
//	/*
//	 * 设置刷新状态
//	 */
//	private void setloadingState(boolean state) {
//		//
//		isLoading=state;
//		if (mListViewFooter!=null) {
//			View loading=mListViewFooter.findViewById(R.id.loading);
//			loading.setAnimation(animation);
//			loading.startAnimation(animation);
//			
//			if (isLoading) {
//				mListView.addFooterView(mListViewFooter);
//			}else {
//				mListView.removeFooterView(mListViewFooter);
//			}
//			
//		}
//	}
//
//	/**
//	 * 判断是否到达最底部
//	 * @return
//	 */
//	public boolean isBottom(){
//		if (mListView!=null && mListView.getAdapter()!=null) {
//			return mListView.getLastVisiblePosition()==mListView.getAdapter().getCount()-1;
//		}
//		return false;
//	}
//	/**
//	 * 判断是否是上啦操作
//	 * @return
//	 */
//	public boolean isPullUp(){
//		//mTouchSlop 是最小的滑动判断
//		return mLastY-mYDown>=mTouchSlop;
//		
//	}
//	/**
//	 * 是否能加载
//	 * @return
//	 */
//	public boolean canload(){
//		return isBottom()&& isPullUp()&& !isLoading;
//	}
//	
//	@Override
//	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//		if (canload()) {
//			loadData();
//		}
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView arg0, int arg1) {
//		
//	}
//	/*
//	 * 设置加载监听器
//	 */
//	public void setOnLoadingListener(onLoadingLisenter lisenter){
//		this.mOnLoadListener=lisenter;
//	}
//	
//	/*
//	 * 刷新数据接口
//	 */
//	public interface onLoadingLisenter{
//		public void onLoadingData();
//	}
//	
//	
}
