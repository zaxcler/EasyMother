package com.easymother.main.homepage;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CalenderFragmentAdapter extends PagerAdapter {
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}
}
