package com.easymother.main.community;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.main.R.id;
import com.easymother.main.babytime.BabyTimeActivity;
import com.easymother.main.babytime.BabyTimeEditActivity;
import com.easymother.main.homepage.CommentImageAdapter;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class TopicOrHelpEditActivity extends Activity {

	private EditText content_tv;
	private ImageView add_photo;
	private GridView gridview;
	
	private String desc;//描述
	private final int CHOOSE_PHOTOS=1;
	private List<Bitmap> images;//要上传的图片集合
	private List<String> imagesname;//保存图片的名字上传到服务器
	private CommentImageAdapter adapter;//图片适配器
	
	private Intent intent;
	private String flag;//判断是topic 还是 help
	private Integer blockId;//话题id
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime_edit);
		intent=getIntent();
		flag=intent.getStringExtra("flag");
		blockId=intent.getIntExtra("blockId", 0);
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
						if (blockId!=0) {
							params.put("blockId", blockId);
						}
						params.put("type", flag);
						desc=content_tv.getText().toString().trim();
						if (!"".equals(desc)&& desc!=null) {
							params.put("content", desc);
						}
						imagesname=EasyMotherUtils.photosname;
						if (imagesname!=null && imagesname.size()!=0) {
							params.put("images", imagesname.toString());
						}
						//名字上传后清空
						EasyMotherUtils.photosname.clear();
						params.put("parentId", 0);
						NetworkHelper.doGet(BaseInfo.SUBMIT_TOPIC_HELP, params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (JsonUtils.getRootResult(response).getIsSuccess()) {
									Toast.makeText(TopicOrHelpEditActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
									TopicOrHelpEditActivity.this.setResult(Activity.RESULT_OK);//返回状态给上一级
									TopicOrHelpEditActivity.this.finish();
								}else {
									Toast.makeText(TopicOrHelpEditActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
								}
							}
							@Override
							public void onFailure(int statusCode, Header[] headers, String responseString,
									Throwable throwable) {
								super.onFailure(statusCode, headers, responseString, throwable);
								Log.e("发布话题", responseString);
							}
						});
						
						
					}
				});
				
			}
		});
		if ("topic".equals(flag)) {
			EasyMotherUtils.initTitle(this, "发布话题", true);
		}
		if ("help".equals(flag)) {
			EasyMotherUtils.initTitle(this, "发布求助", true);
		}
		
		
	}

	private void findView() {
		content_tv=(EditText) findViewById(R.id.baby_edit);
		add_photo=(ImageView) findViewById(R.id.add_photo);
		gridview=(GridView) findViewById(R.id.gridview);
	}

	private void init() {
		images=new ArrayList<>();
		imagesname=new ArrayList<>();
		desc=content_tv.getText().toString().trim();
		add_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EasyMotherUtils.chosePhoto(TopicOrHelpEditActivity.this, CHOOSE_PHOTOS, null);
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
						adapter=new CommentImageAdapter(TopicOrHelpEditActivity.this, images, R.layout.comment_image);
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
