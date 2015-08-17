package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class TuiJianAdapter extends FragmentPagerAdapter {
	private List<TuiJianFragment> list;
	private Activity activity;

	public TuiJianAdapter(FragmentManager fm, JSONArray array) {
		this(null, fm, null);

	}

	public TuiJianAdapter(Activity activity, FragmentManager fm, JSONArray array) {
		super(fm);
		list = new ArrayList<TuiJianFragment>();
		this.activity = activity;

	}

	@Override
	public Fragment getItem(int arg0) {

		Log.e("viewpageadapter----", arg0 + "");
		return new TuiJianItemFragment();
	}

	@Override
	public int getCount() {
		return 3;
	}
	
	


}
