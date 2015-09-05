package com.easymother.main;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.BabyTimeResult;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.babytime.BabyTimeActivity;
import com.easymother.main.babytime.BabyTimeEditActivity;
import com.easymother.main.babytime.BabyTimeInfomationActivity;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
	public static ImageView background;//背景
	private TextView baby_name;
	private TextView days;
	
	public final static int CHOOSE_PHOTO=1;//请求代码
//	private TextView baby_name;
//	private TextView baby_name;
	
	
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
		baby_name=(TextView) viewGroup.findViewById(R.id.baby_name);
		days=(TextView) viewGroup.findViewById(R.id.days);
		
	}
	private void init() {
		move.setOnClickListener(this);
		addbabytime.setOnClickListener(this);
		circleImageView.setOnClickListener(this);
		background.setOnClickListener(this);
		
		NetworkHelper.doGet(BaseInfo.BABYTIME_INDEX, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					BabyTimeResult result=JsonUtils.getResult(response, BabyTimeResult.class);
					bindData(result);
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("baby_time失败", responseString);
			}
		});
		
	}
	/**
	 * b=绑定数据到界面
	 * @param result
	 */
	protected void bindData(BabyTimeResult result) {
		if (result==null) {
			return;
		}
		if (result.getBabyInfo()==null) {
			if (MyApplication.preferences.getInt("id", 0)==0) {
				baby_name.setText("请登录");
			}else {
				baby_name.setText("请完善囡囡记信息");
			}
			return;
		}
		if (result.getBabyInfo().getId()!=null) {
			MyApplication.editor.putInt("baby_id",result.getBabyInfo().getId());
		}
		if (result.getBabyInfo().getBabyImage()!=null) {
			MyApplication.editor.putString("baby_image",result.getBabyInfo().getBabyImage());
		}
		if (result.getBabyInfo().getBackground()!=null) {
			MyApplication.editor.putString("baby_background",result.getBabyInfo().getBackground());
		}
		if (result.getBabyInfo().getGender()!=null) {
			MyApplication.editor.putString("nannan_sex",result.getBabyInfo().getGender());
		}
		if (result.getBabyInfo().getBabyName()!=null) {
			MyApplication.editor.putString("nannan_name",result.getBabyInfo().getBabyName());
		}
		if (result.getBabyInfo().getBirthday()!=null) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String birthy=dateFormat.format(result.getBabyInfo().getBirthday());
			MyApplication.editor.putString("nannan_birthday",birthy);
		}
		MyApplication.editor.commit();
		String backgroundimage=result.getBabyInfo().getBackground();
		if (backgroundimage!=null&&!"".equals(backgroundimage)) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+backgroundimage, background);
		}
		String babyimage=result.getBabyInfo().getBabyImage();
		if (babyimage!=null&&!"".equals(babyimage)) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+babyimage, circleImageView);
		}
		if (result.getBabyInfo().getBabyName()!=null&&!"".equals(result.getBabyInfo().getBabyName())) {
			baby_name.setText(result.getBabyInfo().getBabyName());
		}
		if (result.getDays()!=null) {
			days.setText("出生"+result.getDays()+"天");
		}
		
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
			if (MyApplication.preferences.getInt("baby_id", 0)==0) {
				EasyMotherUtils.goActivity(getActivity(), LoginOrRegisterActivity.class);
				return;
			}
			EasyMotherUtils.goActivity(getActivity(), BabyTimeInfomationActivity.class);
			break;
		case R.id.baby_image:
			final MyPopupWindow popupWindow=new MyPopupWindow(getActivity(), R.layout.chenge_background);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {
				
				@Override
				public void onClick(View view) {
					TextView change_backgroung=(TextView) view.findViewById(R.id.change_background);
					TextView cancle=(TextView) view.findViewById(R.id.cancle);
					change_backgroung.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							EasyMotherUtils.chosePhoto(getActivity(), CHOOSE_PHOTO, null);
							popupWindow.dismiss();
						}
					});
					cancle.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							popupWindow.dismiss();
						}
					});
				}
			});
			popupWindow.showAtLocation(getActivity().findViewById(R.id.babytime_page), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.setWindowsAlpha(0.5f);			
			break;
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
//		String backgroundimgaename=MyApplication.preferences.getString("baby_background", "");
//		Log.e("baby_background", backgroundimgaename);
//		if (backgroundimgaename!=null&&!"".equals(backgroundimgaename)) {
//			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+backgroundimgaename, background);
//		}
		String babyimgaename=MyApplication.preferences.getString("baby_image", "");
		if (babyimgaename!=null&&!"".equals(babyimgaename)) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+babyimgaename, circleImageView);
		}
		
	}
	
	
}
