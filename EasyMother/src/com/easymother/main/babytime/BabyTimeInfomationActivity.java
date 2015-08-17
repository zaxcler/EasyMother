package com.easymother.main.babytime;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyPopupWindow;
import com.easymother.customview.MyPopupWindow.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyTimeInfomationActivity extends Activity implements
		OnClickListener {

	private CircleImageView circleImageView;// 囡囡图片更改

	private final int TAKE_PHOTO = 1;// 照相取图
	private final int CHOOSE_PHOTO = 0;// 相册取图
	private final int CROP_PHOTO = 2;// 剪切图片
	private final String URI = "file:///sdcard/temp.jpg";// 保存拍照后图片的URI
	private final Uri uri = Uri.parse(URI);// //保存拍照后图片的URI
	private final String URI1 = "file:///sdcard/temp1.jpg";// 保存剪切图片后的URI
	private final Uri uri1 = Uri.parse(URI);// //保存剪切图片后的URI

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_infomation);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {

				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.save));
			}

		});
		EasyMotherUtils.initTitle(this, "囡囡信息", true);
		findView();
		inti();

	}

	private void findView() {
		circleImageView = (CircleImageView) findViewById(R.id.circleImageView1);

	}

	private void inti() {

		circleImageView.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.circleImageView1:
			MyPopupWindow popupWindow = new MyPopupWindow(this,
					R.layout.chenge_photo);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {

				@Override
				public void onClick(View view) {
					TextView takephoto = (TextView) view
							.findViewById(R.id.take_photo);
					TextView choosephot = (TextView) view
							.findViewById(R.id.choose_photo);
					TextView cancle = (TextView) view.findViewById(R.id.cancle);
					ChengeImageClickLisenter lisenter = new ChengeImageClickLisenter();
					takephoto.setOnClickListener(lisenter);
					choosephot.setOnClickListener(lisenter);
					cancle.setOnClickListener(lisenter);

				}
			});
			popupWindow.showAtLocation(arg0, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			popupWindow.setWindowsAlpha(0.5f);

			break;

		default:
			break;
		}

	}

	private class ChengeImageClickLisenter implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.take_photo:
				// 打开相机
				EasyMotherUtils.takePhoto(BabyTimeInfomationActivity.this,
						TAKE_PHOTO, uri);

				break;

			case R.id.choose_photo:

				break;
			case R.id.cancle:

				break;
			}
		}

	}

	// 监听返回数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PHOTO:
				EasyMotherUtils.cropPhoto(this, 100, 100, CROP_PHOTO, uri1);
				
				break;
			case CHOOSE_PHOTO:

				break;
			case CROP_PHOTO:
				try {
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
					bitmapOptions.inJustDecodeBounds = true;
				      bitmapOptions.inSampleSize = 4;  
				      bitmapOptions.inJustDecodeBounds = false;
				    Bitmap  bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri1), null , bitmapOptions);  
					circleImageView.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			}
		}

	}

}
