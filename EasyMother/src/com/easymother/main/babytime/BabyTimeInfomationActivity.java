package com.easymother.main.babytime;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.BabyInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_infomation);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {

				imageView.setImageDrawable(getResources().getDrawable(R.drawable.save));
			}

		});
		EasyMotherUtils.initTitle(this, "囡囡信息", true);
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
		params.put("id", MyApplication.preferences.getInt("baby_id", 0));
		NetworkHelper.doGet(BaseInfo.BABYTIME_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					BabyInfoBean infoBean=JsonUtils.getResult(response, BabyInfoBean.class);
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
			info=infoBean;
			if (infoBean.getBabyName()!=null) {
				nannan_name.setText(infoBean.getBabyName());
			}
			if (infoBean.getGender()!=null) {
				nannan_sex.setText(infoBean.getGender());
			}
			if (infoBean.getBirthday()!=null) {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				String birthday=format.format(infoBean.getBirthday());
				nannan_birthday.setText(birthday);
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		Intent intent=new Intent(this,ChanegBabyInfo.class);
		if (info!=null) {
			intent.putExtra("id", info.getId());
		}
		switch (id) {
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
			intent.putExtra("type", "nannan_birthday");
			startActivity(intent);
			break;

		}

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

				break;

			case R.id.choose_photo:
				EasyMotherUtils.chosePhoto(BabyTimeInfomationActivity.this, CHOOSE_PHOTO, null);
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
		String babyimgaename = MyApplication.preferences.getString("baby_image", "");
		if (babyimgaename != null && !"".equals(babyimgaename)) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + babyimgaename,
					circleImageView);
		}
		String name=MyApplication.preferences.getString("nannan_name", "");
		if (!"".equals(name)&&name!=null) {
			nannan_name.setText(name);
		}
		String sex=MyApplication.preferences.getString("nannan_sex", "");
		if (!"".equals(sex)&&sex!=null) {
			nannan_name.setText(sex);
		}

	}

}
