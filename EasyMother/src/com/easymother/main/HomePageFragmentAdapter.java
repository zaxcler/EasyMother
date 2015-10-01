package com.easymother.main;

import java.util.List;

import com.easymother.main.homepage.TuiJianFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePageFragmentAdapter extends FragmentPagerAdapter{
	private List<TuiJianFragment> list;

	public HomePageFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public  HomePageFragmentAdapter(FragmentManager fm,List<TuiJianFragment> list) {
		this(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
}