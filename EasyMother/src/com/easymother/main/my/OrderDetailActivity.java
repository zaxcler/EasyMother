package com.easymother.main.my;

import com.easymother.customview.MyPopupWindow1;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class OrderDetailActivity extends Activity {
	private MyPopupWindow1 popupWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_order_status);
		initPopupWindow("flag");
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(final ImageView imageView) {
				
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.meun));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						//根据传过来的状态初始化右边的按钮弹出框
						popupWindow.showAsDropDown(imageView, 0, 0);
					}
				});
				
				
			}
		});
		EasyMotherUtils.initTitle(this, "订单状态", true);
		findView();
		init();
//		MyPopupWindow
	}

	private void findView() {
	}

	private void init() {
		
	}
	
	private void initPopupWindow(String flag){
		//根据flag判断resource
		popupWindow=new MyPopupWindow1(this, R.layout.mypage_order_status_menu);
		
	}
}
