package com.easymother.main;

import java.util.List;

import com.easymother.main.homepage.TuiJianFragment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePageFragmentAdapter2 extends FragmentPagerAdapter{
	private List<TuiJianFragment2> list;

	public HomePageFragmentAdapter2(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public  HomePageFragmentAdapter2(FragmentManager fm,List<TuiJianFragment2> list) {
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