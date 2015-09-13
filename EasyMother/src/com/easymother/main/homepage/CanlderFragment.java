package com.easymother.main.homepage;

import com.easymother.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class CanlderFragment extends Fragment {

	private GridView gridview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=(View) inflater.inflate(R.layout.calender_days, null);
		findView(view);
		init();
		return view;
		
	}
	private void findView(View view) {
		gridview=(GridView) view.findViewById(R.id.gridview);
	}
	private void init() {
//		CalenderAadpter adpter
		
	}
}
