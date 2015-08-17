package com.easymother.customview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class MyLinearlayout extends LinearLayout{

	public MyLinearlayout(Context context) {
		super(context);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	                MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }

}
