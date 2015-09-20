package com.easymother.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private onReflashLisenter reflashListener;

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
//		滚动到顶部判断：getScrollY() == 0
//		滚动到底部判断
//        View contentView = getChildAt(0);
//		contentView.getMeasuredHeight() <= getScrollY() + getHeight();
//		View contentView = getChildAt(0);
//		contentView.getMeasuredHeight() <= getScrollY() + getHeight();
//		其中getChildAt表示得到ScrollView的child View，因为ScrollView只允许一个child view，所以contentView.getMeasuredHeight()表示得到子View的高度,
//		getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
		View childView=getChildAt(0);
		//如果Y等于0  则调用顶部刷新
		if (childView!=null&&getY()==0) {
			reflashListener.onHeadReflash();
		}
		else if(childView!=null&& getScrollY() + getHeight() >= computeVerticalScrollRange()){
			reflashListener.onFootReflash();
		}
		
		
	}
	
	
	//底部刷新监听
	public interface onReflashLisenter{
		
		public void onFootReflash();
		public void onHeadReflash();
	}
	//设置底部刷洗监听器
	public void setOnReflashListener(onReflashLisenter reflashListener){
		this.reflashListener=reflashListener;
	}
		
	
	

}
