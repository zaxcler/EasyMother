package com.easymother.customview;

import android.R;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 弹出popupwindows 
 * @author zaxcler
 *
 */

public class MyPopupWindow1 extends PopupWindow {
	
	private OnMyPopupWindowsClick click;
	private View view;

	private Activity activity;
	
	public MyPopupWindow1(Activity activity,int resource) {
		super(activity);
		this.activity=activity;
		
		view=LayoutInflater.from(activity).inflate(resource, null);
		
		setContentView(view);//设置布局
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);//设置窗口可点
		ColorDrawable drawable=new ColorDrawable(0x00000000);//设置背景全透明
		this.setBackgroundDrawable(drawable);
		
		//如果是弹窗外的点击 就取消
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				int height=view.getMeasuredHeight();
				int y=(int) arg1.getY();
				if (arg1.getAction()==MotionEvent.ACTION_UP) {
					if (y>height) {
						dismiss();
					}
				}
				return true;
			}
		});
		
		
		
	}

	
	public void setOnMyPopupClidk(OnMyPopupWindowsClick click){
		this.click=click;
		this.click.onClick(view);//将view返回 实现回调 用户根据view findviewbyid找到控件 并设置值
		
	}
	public interface OnMyPopupWindowsClick{
		  public void onClick(View view);
	}
	
	

	

}
