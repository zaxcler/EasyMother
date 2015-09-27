package com.easymother.main.babytime;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.main.homepage.CommentImageAdapter;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class BabyTimeEditActivity extends Activity {
	private EditText baby_edit;
	private ImageView add_photo;
	private GridView gridview;
	
	private String desc;//描述
	private final int CHOOSE_PHOTOS=1;
	private List<Bitmap> images;//要上传的图片集合
	private List<String> imagesname;//保存图片的名字上传到服务器
	private CommentImageAdapter adapter;//图片适配器
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_edit);
		findView();
		init();
		
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.save));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						RequestParams params=new RequestParams();
						//判断本地是否保存之前从服务器获取的囡囡信息，如果有，表示服务器中有囡囡信息，服务器只修改，如果没有，服务器就新创建
						int baby_id=MyApplication.preferences.getInt("baby_id", 0);
						if (baby_id==0) {
							params.put("baby_id", "");
						}else {
							params.put("baby_id", baby_id);
						}
						if (MyApplication.preferences.getInt("id", 0)!=0) {
							params.put("userId", MyApplication.preferences.getInt("id", 0));
						}else {
							EasyMotherUtils.goActivity(BabyTimeEditActivity.this, LoginOrRegisterActivity.class);
							BabyTimeEditActivity.this.finish();
						}
						desc=baby_edit.getText().toString().trim();
						if (desc!=null&&!"".equals(desc)) {
							params.put("content", desc);
						}
						//上传之前添加的图片
//						for (Bitmap bitmap : images) {
//							EasyMotherUtils.uploadPhoto(bitmap, BaseInfo.UPLOADPHTO, "baby");
//							String name=MyApplication.preferences.getString("baby", "");
//							if (!"".equals(name)&&name!=null) {
//								name="\""+name+"\"";
//								imagesname.add(name);
//							}
//						}
//						for (Bitmap bitmap : images) {
//							EasyMotherUtils.uploadPhoto(bitmap, BaseInfo.UPLOADPHTO, "baby");
//						}
						
//						if (imagesname!=null && imagesname.size()>0) {
							String name=EasyMotherUtils.photosname.toString();
							if (name!=null&&!"".equals(name)) {
								params.put("images", name);
							}
							EasyMotherUtils.photosname.clear();
							Log.e("上传的图片名字", "图片名字"+imagesname.toString());
//						}
						NetworkHelper.doGet(BaseInfo.BABYTIME_SAVEINFO, params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (JsonUtils.getRootResult(response).getIsSuccess()) {
								Toast.makeText(BabyTimeEditActivity.this, "发布成功", 0).show();
								EasyMotherUtils.goActivity(BabyTimeEditActivity.this, BabyTimeActivity.class);
								BabyTimeEditActivity.this.finish();
								}else {
									Toast.makeText(BabyTimeEditActivity.this, response.toString(), 0).show();
								}
							}
							@Override
							public void onFailure(int statusCode, Header[] headers, String responseString,
									Throwable throwable) {
								super.onFailure(statusCode, headers, responseString, throwable);
								Toast.makeText(BabyTimeEditActivity.this, "连接服务器失败", 0).show();
								Log.e("发布囡囡记onFailure", responseString);
							}
						});
						
						
					}
				});
				
			}
		});
		EasyMotherUtils.initTitle(this, "囡囡", true);
		
	}

	private void findView() {
		baby_edit=(EditText) findViewById(R.id.baby_edit);
		add_photo=(ImageView) findViewById(R.id.add_photo);
		gridview=(GridView) findViewById(R.id.gridview);
	}

	private void init() {
		images=new ArrayList<>();
		imagesname=new ArrayList<>();
		desc=baby_edit.getText().toString().trim();
		add_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EasyMotherUtils.chosePhoto(BabyTimeEditActivity.this, CHOOSE_PHOTOS, null);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			switch (requestCode) {
			case CHOOSE_PHOTOS:
				Uri uri=data.getData();
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inJustDecodeBounds=true;
				options.inSampleSize=4;
				options.inJustDecodeBounds=false;
				try {
					Bitmap bitmap=BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null, options);
					EasyMotherUtils.uploadPhoto(bitmap, BaseInfo.UPLOADPHTO, null);
					images.add(bitmap);//保存图片后面上传
					if (adapter==null) {
						adapter=new CommentImageAdapter(BabyTimeEditActivity.this, images, R.layout.comment_image);
						gridview.setAdapter(adapter);
					}else {
						adapter.notifyDataSetChanged();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				break;

			}
		}
	}
}

