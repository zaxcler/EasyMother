package com.easymother.main.babytime;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.BabyInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class BabyTimeInfomationActivity extends Activity implements OnClickListener {

	private CircleImageView circleImageView;// 囡囡图片更改
	private TextView nannan_name;
	private TextView nannan_sex;
	private TextView nannan_birthday;
	

	private final int TAKE_PHOTO = 1;// 照相取图
	private final int CHOOSE_PHOTO = 0;// 相册取图
	private final int CROP_PHOTO = 2;// 剪切图片
	private final String URI2 = "file:///sdcard/temp.jpg";// 保存拍照后图片的URI
	private final Uri uri2 = Uri.parse(URI2);// //保存拍照后图片的URI
	private final String URI1 = "file:///sdcard/temp1.jpg";// 保存剪切图片后的URI
	private final Uri uri1 = Uri.parse(URI1);// //保存剪切图片后的URI
	private boolean uploadstatu;// 上传是否成功
	private BabyInfoBean info;//宝贝信息
	private String birthday;
	private int  baby_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_infomation);
		
		EasyMotherUtils.initTitle(this, "囡囡信息", false);
		findView();
		inti();

	}

	private void findView() {
		circleImageView = (CircleImageView) findViewById(R.id.circleImageView1);
		nannan_name=(TextView) findViewById(R.id.nannan_name);
		nannan_sex=(TextView) findViewById(R.id.nannan_sex);
		nannan_birthday=(TextView) findViewById(R.id.nannan_birthday);
	}

	private void inti() {
		circleImageView.setOnClickListener(this);
		nannan_name.setOnClickListener(this);
		nannan_sex.setOnClickListener(this);
		nannan_birthday.setOnClickListener(this);
		RequestParams params=new RequestParams();
		int id=MyApplication.preferences.getInt("baby_id", 0);
		if (id==0) {
			params.put("id", "");
		}else {
			params.put("id", id);
		}
		NetworkHelper.doGet(BaseInfo.BABYINFO_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					BabyInfoBean infoBean=null;
					if (JsonUtils.getRootResult(response).getResult()!=null) {
						infoBean=JsonUtils.getResult(response, BabyInfoBean.class);
					}
					bindData(infoBean);
					
				}
			}
		});
	}
	/**
	 * 绑定数据
	 * @param infoBean
	 */
	protected void bindData(BabyInfoBean infoBean) {
		if (infoBean!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+infoBean.getBabyImage(), circleImageView, MyApplication.options_photo);
			info=infoBean;
			if (infoBean.getBabyName()!=null) {
				nannan_name.setText(infoBean.getBabyName());
			}else {
				nannan_name.setText("");
			}
			if (infoBean.getGender()!=null) {
				nannan_sex.setText(infoBean.getGender());
			}else {
				nannan_sex.setText("");
			}
			if (infoBean.getBirthday()!=null) {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				String birthday=format.format(infoBean.getBirthday());
				nannan_birthday.setText(birthday);
			}else {
				nannan_birthday.setText("");
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		int view_id = arg0.getId();
		Intent intent=new Intent(this,ChanegBabyInfo.class);
		//如果获取到数据就从数据中获取id
		if (info!=null) {
			intent.putExtra("id", info.getId());
		}else {
			//当还在这个页面的时候更改其他的信息，则不会再次获取数据，就从本地获取数据
			if (MyApplication.preferences.getInt("baby_id", 0)!=0) {
				intent.putExtra("id", MyApplication.preferences.getInt("baby_id", 0));
			}else {
				intent.putExtra("id", "");
			}
			
		}
		switch (view_id) {
		case R.id.circleImageView1:
			final MyPopupWindow popupWindow = new MyPopupWindow(this, R.layout.chenge_photo);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {

				@Override
				public void onClick(View view) {
					TextView takephoto = (TextView) view.findViewById(R.id.take_photo);
					TextView choosephot = (TextView) view.findViewById(R.id.choose_photo);
					TextView cancle = (TextView) view.findViewById(R.id.cancle);
					ChengeImageClickLisenter lisenter = new ChengeImageClickLisenter(popupWindow);
					takephoto.setOnClickListener(lisenter);
					choosephot.setOnClickListener(lisenter);
					cancle.setOnClickListener(lisenter);
				}
			});
			popupWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.setWindowsAlpha(0.5f);

			break;
		case R.id.nannan_name:
			intent.putExtra("type", "nannan_name");
			startActivity(intent);
			break;
		case R.id.nannan_sex:
			intent.putExtra("type", "nannan_sex");
			startActivity(intent);
			break;
		case R.id.nannan_birthday:
			Date currentdate=new Date(System.currentTimeMillis());
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(currentdate);
			DatePickerDialog picker=new DatePickerDialog(this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, final int year, final int monthOfYear,final int dayOfMonth) {
					birthday=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
					nannan_birthday.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
				}
				
			
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			picker.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					save();
					Log.e("setOnDismissListener", "setOnDismissListener========");
				}
			});
			
			picker.show();
			break;

		}
		

	}
	/*
	 * 保存年龄信息
	 */
	public void save(){
		
		RequestParams params=new RequestParams();
		if (MyApplication.preferences.getInt("id", 0)!=0) {
			params.put("userId", MyApplication.preferences.getInt("id", 0));
		}else {
			EasyMotherUtils.goActivity(BabyTimeInfomationActivity.this, LoginOrRegisterActivity.class);
			return;
		}
		if (info!=null) {
			params.put("id", info.getId());
		}else {
			//当还在这个页面的时候更改其他的信息，则不会再次获取数据，就从本地获取数据
			if (MyApplication.preferences.getInt("baby_id", 0)!=0) {
				params.put("id", MyApplication.preferences.getInt("baby_id", 0));
			}else {
				params.put("id", "");
			}
			
		}
		
		params.put("birthday", birthday);
		NetworkHelper.doGet(BaseInfo.BABYINFO_SAVEINFO, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					MyApplication.editor.putString("nannan_birthday", birthday).commit();
					Toast.makeText(BabyTimeInfomationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(BabyTimeInfomationActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString,
					Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(BabyTimeInfomationActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
				Log.e("连接服务器失败", responseString);
			}
		});
	}
	private class ChengeImageClickLisenter implements OnClickListener {
		MyPopupWindow window;

		public ChengeImageClickLisenter(MyPopupWindow window) {
			this.window = window;
		}

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.take_photo:
				// 打开相机
				EasyMotherUtils.takePhoto(BabyTimeInfomationActivity.this, TAKE_PHOTO, uri1);
				window.dismiss();
				break;

			case R.id.choose_photo:
				EasyMotherUtils.chosePhoto(BabyTimeInfomationActivity.this, CHOOSE_PHOTO, null);
				window.dismiss();
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
				EasyMotherUtils.cropPhoto(this, 100, 100, CROP_PHOTO, uri1, uri2);

				break;
			case CHOOSE_PHOTO:
				uri = data.getData();
				EasyMotherUtils.cropPhoto(this, 100, 100, CROP_PHOTO, uri, uri2);

				break;
			case CROP_PHOTO:
				try {
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmapOptions.inJustDecodeBounds = true;
					bitmapOptions.inSampleSize = 4;
					bitmapOptions.inJustDecodeBounds = false;
					Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri2), null,
							bitmapOptions);
					circleImageView.setImageBitmap(bitmap);
					String type = "baby_image";
					EasyMotherUtils.uploadPhoto(bitmap, BaseInfo.UPLOADPHTO, type);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;

			}
		}

	}

	@Override
	public void onResume() {
		super.onResume();
//		String babyimgaename = MyApplication.preferences.getString("baby_image", "");
//		if (babyimgaename != null && !"".equals(babyimgaename)) {
//			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + babyimgaename,
//					circleImageView);
//		}
		String name=MyApplication.preferences.getString("nannan_name", "");
		if (!"".equals(name)&&name!=null) {
			nannan_name.setText(name);
		}
		String sex=MyApplication.preferences.getString("nannan_sex", "");
		if (!"".equals(sex)&&sex!=null) {
			nannan_sex.setText(sex);
		}

	}

}
