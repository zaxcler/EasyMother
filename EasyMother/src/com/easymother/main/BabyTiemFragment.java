package com.easymother.main;

import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.babytime.BabyTimeActivity;
import com.easymother.main.babytime.BabyTimeEditActivity;
import com.easymother.main.babytime.BabyTimeInfomationActivity;
import com.easymother.utils.EasyMotherUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyTiemFragment extends Fragment implements OnClickListener{
	private TextView move;//转到囡囡记列表
	private ImageView addbabytime;//转到编辑框
	private CircleImageView circleImageView;//跳转到囡囡个人信息
	private ImageView background;//背景
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup babytime=(ViewGroup) inflater.inflate(R.layout.fragment_babytimepage, null);
		findView(babytime);
		init();
		
		return babytime;
		
	}
	private void findView(ViewGroup viewGroup) {
		move=(TextView) viewGroup.findViewById(R.id.move);
		addbabytime=(ImageView) viewGroup.findViewById(R.id.add_babytime);
		circleImageView=(CircleImageView) viewGroup.findViewById(R.id.circleImageView1);
		background=(ImageView) viewGroup.findViewById(R.id.baby_image);
		
		
	}
	private void init() {
		move.setOnClickListener(this);
		addbabytime.setOnClickListener(this);
		circleImageView.setOnClickListener(this);
		background.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {

		int id=arg0.getId();
		switch (id) {
		case R.id.move:
			EasyMotherUtils.goActivity(getActivity(), BabyTimeActivity.class);
			break;

		case R.id.add_babytime:
			EasyMotherUtils.goActivity(getActivity(), BabyTimeEditActivity.class);
			break;
		case R.id.circleImageView1:
			EasyMotherUtils.goActivity(getActivity(), BabyTimeInfomationActivity.class);
			break;
		case R.id.baby_image:
			MyPopupWindow popupWindow=new MyPopupWindow(getActivity(), R.layout.chenge_background);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {
				
				@Override
				public void onClick(View view) {
					
				}
			});
			popupWindow.showAtLocation(getActivity().findViewById(R.id.babytime_page), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.setWindowsAlpha(0.5f);			
			
			break;
		}
	}
	
	
}
