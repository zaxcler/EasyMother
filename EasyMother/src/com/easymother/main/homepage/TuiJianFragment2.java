package com.easymother.main.homepage;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.CuiRuShi;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
import com.easymother.customview.MyGridView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TuiJianFragment2<T> extends Fragment {
	private View tuijianView;
	private String type;//职业类型
	private MyGridView gridView;
	private List<YueSao> yuesao;
	private List<YuYingShi> yuyingshi;
	private List<CuiRuShi> cuirushi;
	private List<T> t;
	
	public  <T> TuiJianFragment2(String type,List<T> t) {
		
		this.type=type;
		if ("yuesao".equals(type)) {
			 yuesao=(List<YueSao>) t;
		}
		if ("yuyingshi".equals(type)) {
			 yuyingshi=(List<YuYingShi>) t;
		}
		if ("cuirushi".equals(type)) {
			 cuirushi=(List<CuiRuShi>) t;
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tuijianView=inflater.inflate(R.layout.fragment_tuijian2, null);
		Log.e("log大法", "fragment已建立");
		findView();
		init();
		
		return tuijianView;
	}
	
	private void findView() {
		gridView=(MyGridView) tuijianView.findViewById(R.id.gridview);
	}
	
	private void init() {
	
		
			if ("yuesao".equals(type)) {
				GridViewAdapter<YueSao> adapter=new GridViewAdapter<>(getActivity(), yuesao, R.layout.fragment_tuijian_item);
				gridView.setAdapter(adapter);
			}
			if ("yuyingshi".equals(type)) {
				GridViewAdapter<YuYingShi> adapter=new GridViewAdapter<>(getActivity(), yuyingshi, R.layout.fragment_tuijian_item);
				gridView.setAdapter(adapter);
			}
			if ("cuirushi".equals(type)) {
				GridViewAdapter<CuiRuShi> adapter=new GridViewAdapter<CuiRuShi>(getActivity(), cuirushi, R.layout.fragment_tuijian_item);
				gridView.setAdapter(adapter);
			}
		
		
	}
	

	
}
