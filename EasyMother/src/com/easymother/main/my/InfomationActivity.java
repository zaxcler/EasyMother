package com.easymother.main.my;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.main.babytime.BabyTimeInfomationActivity;
import com.easymother.utils.EasyMotherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfomationActivity extends Activity implements OnClickListener {
	private CircleImageView user_photo;
	private TextView nick_name;
	private TextView phone_num;
	private TextView hospital;
	private TextView address;
	private TextView time;
	private LinearLayout chenge_pssword;
	private LinearLayout nick_name_Layout;
	private LinearLayout phone_num_Layout;
	private LinearLayout hospital_Layout;
	private LinearLayout address_Layout;
	private LinearLayout time_Layout;
	
	private final int TAKE_PHOTO = 1;// 照相取图
	private final int CHOOSE_PHOTO = 0;// 相册取图
	private final int CROP_PHOTO = 2;// 剪切图片
	private final String URI = "file:///sdcard/temp.jpg";// 保存拍照后图片的URI
	private final Uri uri2 = Uri.parse(URI);// //保存拍照后图片的URI
	private final String URI1 = "file:///sdcard/temp1.jpg";// 保存剪切图片后的URI
	private final Uri uri1 = Uri.parse(URI1);// //保存剪切图片后的URI

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_infomation);
		EasyMotherUtils.initTitle(this, "个人信息", false);
		findView();
		init();
	}

	private void findView() {
		user_photo = (CircleImageView) findViewById(R.id.circleImageView1);
		nick_name = (TextView) findViewById(R.id.nick_name);
		phone_num = (TextView) findViewById(R.id.phone_num);
		hospital = (TextView) findViewById(R.id.hospital);
		address = (TextView) findViewById(R.id.address);
		time = (TextView) findViewById(R.id.time);
		chenge_pssword = (LinearLayout) findViewById(R.id.chenge_pssword);
		nick_name_Layout = (LinearLayout) findViewById(R.id.name_layout);
		hospital_Layout = (LinearLayout) findViewById(R.id.hospital_layout);
		address_Layout = (LinearLayout) findViewById(R.id.address_Layout);
		time_Layout = (LinearLayout) findViewById(R.id.time_Layout);
	}

	private void init() {
		SharedPreferences preferences = MyApplication.preferences;
		ImageLoader.getInstance().displayImage(
				BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + preferences.getString("image", ""), user_photo);
		nick_name.setText(preferences.getString("nickname", ""));
		phone_num.setText(preferences.getString("mobile", ""));
		hospital.setText(preferences.getString("hospitalName", ""));
		address.setText(preferences.getString("address", ""));
		time.setText(preferences.getString("preDate", ""));

		user_photo.setOnClickListener(this);
		nick_name_Layout.setOnClickListener(this);
		hospital_Layout.setOnClickListener(this);
		address_Layout.setOnClickListener(this);
		time_Layout.setOnClickListener(this);
		chenge_pssword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.circleImageView1:
			final MyPopupWindow popupWindow = new MyPopupWindow(this,
					R.layout.chenge_photo);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {

				@Override
				public void onClick(View view) {
					TextView takephoto = (TextView) view
							.findViewById(R.id.take_photo);
					TextView choosephot = (TextView) view
							.findViewById(R.id.choose_photo);
					TextView cancle = (TextView) view.findViewById(R.id.cancle);
					ChengeImageClickLisenter lisenter = new ChengeImageClickLisenter(popupWindow);
					takephoto.setOnClickListener(lisenter);
					choosephot.setOnClickListener(lisenter);
					cancle.setOnClickListener(lisenter);

				}
			});
			popupWindow.showAtLocation(v, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.setWindowsAlpha(0.5f);
			break;

		case R.id.name_layout:
			
			intent.setClass(InfomationActivity.this, InfomationChangeTextActivity.class);
			intent.putExtra("type", "nick_name");
			startActivity(intent);
			break;
		
		case R.id.hospital_layout:
			intent.setClass(InfomationActivity.this, InfomationChangeTextActivity.class);
			intent.putExtra("type", "hospital");
			startActivity(intent);
			break;
		case R.id.address_Layout:
			intent.setClass(InfomationActivity.this, InfomationChangeTextActivity.class);
			intent.putExtra("type", "address");
			startActivity(intent);
			break;
		case R.id.time_Layout:
			intent.setClass(InfomationActivity.this, InfomationChangeTextActivity.class);
			intent.putExtra("type", "time");
			startActivity(intent);
			break;
		case R.id.chenge_pssword:
			EasyMotherUtils.goActivity(this, InfomationChangePasswordActivity.class);
			break;
		}
	}
	private class ChengeImageClickLisenter implements OnClickListener {
		MyPopupWindow window;
		public ChengeImageClickLisenter(MyPopupWindow window) {
			this.window=window;
		}
		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.take_photo:
				// 打开相机
				EasyMotherUtils.takePhoto(InfomationActivity.this,
						TAKE_PHOTO, uri1);

				break;

			case R.id.choose_photo:
				EasyMotherUtils.chosePhoto(InfomationActivity.this, CHOOSE_PHOTO, null);
				break;
			case R.id.cancle:
				window.dismiss();
				break;
			}
		}

	}
	// 监听返回数据
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			Uri uri;
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case TAKE_PHOTO:
					EasyMotherUtils.cropPhoto(this, 100, 100, CROP_PHOTO, uri1,uri2);
					
					break;
				case CHOOSE_PHOTO:
					 uri=data.getData();
					EasyMotherUtils.cropPhoto(this, 100, 100, CROP_PHOTO, uri,uri2);

					break;
				case CROP_PHOTO:
					try {
						BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
						bitmapOptions.inJustDecodeBounds = true;
					      bitmapOptions.inSampleSize = 4;  
					      bitmapOptions.inJustDecodeBounds = false;
					    Bitmap  bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri2), null , bitmapOptions);  
						user_photo.setImageBitmap(bitmap);
						String type="user_image";
						EasyMotherUtils.uploadPhoto(bitmap, BaseInfo.UPLOADPHTO,type);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					break;

				}
			}

		}
		
		@Override
		protected void onResume() {
			super.onResume();
			nick_name.setText(MyApplication.preferences.getString("nickname", ""));
			hospital.setText(MyApplication.preferences.getString("hospitalName", ""));
			address.setText(MyApplication.preferences.getString("address", ""));
			time.setText(MyApplication.preferences.getString("preDate", ""));
			
		}


}
