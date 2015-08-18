package com.easymother.main.homepage;

import java.util.List;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class TuiJianYYSAdapter extends FragmentPagerAdapter {
	private List<TuiJianItemFragment> list;
	private Activity activity;

	public TuiJianYYSAdapter(FragmentManager fm, List<TuiJianItemFragment> list) {
		this(null, fm, null);

	}

	public TuiJianYYSAdapter(Activity activity, FragmentManager fm, List<TuiJianItemFragment> list) {
		super(fm);
		this.list = list;
		this.activity = activity;

	}

	@Override
	public Fragment getItem(int arg0) {

		Log.e("viewpageadapter----", arg0 + "");
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	


}
