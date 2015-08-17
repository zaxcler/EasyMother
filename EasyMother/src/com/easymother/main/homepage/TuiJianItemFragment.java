package com.easymother.main.homepage;

import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class TuiJianItemFragment extends Fragment implements OnClickListener{
	private View tuijianItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tuijianItem=inflater.inflate(R.layout.fragment_tuijian_item, null);
		return tuijianItem;
	}

	@Override
	public void onClick(View arg0) {
		EasyMotherUtils.goActivity(getActivity(), YueSaoDetailActivity.class);		
	}
}
