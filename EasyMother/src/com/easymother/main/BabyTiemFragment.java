package com.easymother.main;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
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
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BabyTiemFragment extends Fragment implements OnClickListener{
	private TextView move;//转到囡囡记列表
	private ImageView addbabytime;//转到编辑框
	private CircleImageView circleImageView;//跳转到囡囡个人信息
	public static ImageView background;//背景
	private TextView baby_name;
	private TextView days;
	
	public final static int CHOOSE_PHOTO=1;//请求选择照片代码
	public final static int LOGIN_CODE=2;//请求登陆代码
	
	public  Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				loadData();
				break;
			case 2:
				Bitmap bitmap=(Bitmap) msg.obj;
				background.setImageBitmap(bitmap);
				break;

			
			}
			
		};
	};
	
	public BabyTiemFragment() {
	}
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
		loadData();
	}
	/**
	 * 加载数据
	 */
	public void loadData(){
		NetworkHelper.doGet(BaseInfo.BABYTIME_INDEX, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					BabyTimeResult result=JsonUtils.getResult(response, BabyTimeResult.class);
					bindData(result);
				}else {
					baby_name.setText("");
					days.setText("");
					Toast.makeText(getActivity(),"未登录！", Toast.LENGTH_SHORT).show();
					background.setImageDrawable(getResources().getDrawable(R.drawable.baby_image3));
					circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.photo));
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
//				return;
			}
			//没有囡囡信息就加载一个错误的图片，会有默认图片
			baby_name.setText("");
			days.setText("");
			background.setImageDrawable(getResources().getDrawable(R.drawable.baby_image3));
			circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.photo));
		}else {
			int baby_id=MyApplication.preferences.getInt("baby_id", 0);
			String baby_image=MyApplication.preferences.getString("baby_image", "");
			String nannan_sex=MyApplication.preferences.getString("nannan_sex", "");
			String nannan_name=MyApplication.preferences.getString("nannan_name", "");
			String nannan_birthday=MyApplication.preferences.getString("nannan_birthday", "");
			if ("".equals(baby_id)||"".equals(baby_image) || "".equals(nannan_sex)||"".equals(nannan_name)||"".equals(nannan_birthday)) {
				baby_name.setText("请完善囡囡记信息");
			}else {
				baby_name.setText(nannan_name);
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
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String birthy=dateFormat.format(result.getBabyInfo().getBirthday());
				MyApplication.editor.putString("nannan_birthday",birthy);
			}
			MyApplication.editor.commit();
			String backgroundimage=result.getBabyInfo().getBackground();
			if (backgroundimage!=null&&!"".equals(backgroundimage)) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+backgroundimage, background,MyApplication.options_image);
			}
			String babyimage=result.getBabyInfo().getBabyImage();
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+babyimage, circleImageView,MyApplication.options_photo);
			if (result.getBabyInfo().getBabyName()!=null&&!"".equals(result.getBabyInfo().getBabyName())) {
				baby_name.setText(result.getBabyInfo().getBabyName());
			}
			if (result.getDays()!=null) {
				days.setText("出生"+result.getDays()+"天");
			}
				
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
			if (MyApplication.preferences.getInt("id", 0)==0) {
				EasyMotherUtils.goActivityForResult(getActivity(), LoginOrRegisterActivity.class, LOGIN_CODE);
				return;
			}
			//如果宝贝信息没有就必须先去添加宝贝信息
			if (MyApplication.preferences.getInt("baby_id", 0)==0) {
				Toast.makeText(getActivity(), "请先添加宝贝信息", Toast.LENGTH_SHORT).show();
				return;
			}
			EasyMotherUtils.goActivity(getActivity(), BabyTimeEditActivity.class);
			break;
		case R.id.circleImageView1:
			//没有登陆不能添加宝贝信息
			if (MyApplication.preferences.getInt("id", 0)==0) {
				EasyMotherUtils.goActivityForResult(getActivity(), LoginOrRegisterActivity.class, LOGIN_CODE);
				return;
			}
			EasyMotherUtils.goActivity(getActivity(), BabyTimeInfomationActivity.class);
			break;
		case R.id.baby_image:
			//没有登陆不能添加宝贝信息
			if (MyApplication.preferences.getInt("id", 0)==0) {
				EasyMotherUtils.goActivityForResult(getActivity(), LoginOrRegisterActivity.class, LOGIN_CODE);
				return;
			}
			if (MyApplication.preferences.getInt("baby_id", 0)==0) {
				Toast.makeText(getActivity(), "请先添加宝贝信息", Toast.LENGTH_SHORT).show();
				return;
			}
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
	public void onResume(){
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
		String name=MyApplication.preferences.getString("nannan_name", "");
		if (!"".equals(name)&&name!=null) {
			baby_name.setText(name);
		}
		Log.e("onresume", "=+++++++++++++++++++=====");
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==Activity.RESULT_OK) {
			Log.e("调用l", "-----------");
			switch (requestCode) {
			case BabyTiemFragment.CHOOSE_PHOTO:
				try {
					Uri uri=data.getData();
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
					bitmapOptions.inJustDecodeBounds = true;
					  bitmapOptions.inSampleSize = 4;  
					  bitmapOptions.inJustDecodeBounds = false;
					Bitmap  bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null , bitmapOptions);
					EasyMotherUtils.uploadPhoto(bitmap,BaseInfo.UPLOADPHTO, "baby_background");
					background.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
				//如果登陆成功 则刷新一下BabyTiemFragment的数据
			case BabyTiemFragment.LOGIN_CODE:
				handler.sendEmptyMessage(1);
				break;
			}
		}
	}
	
}
