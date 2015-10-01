package com.easymother.main.babytime;

import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.BabyInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChanegBabyInfo extends Activity  {
	private EditText text;//要修改的信息
	private TextView title;//标题
	private ImageView back;//返回
	private ImageView save;//保存
	 Intent intent;
	 private String type;//类型
	 private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_infomation_chengetext);
		intent=getIntent();
		type=intent.getStringExtra("type");
		id=intent.getIntExtra("id", 0);
		findView();
		init();
		
	}
	private void findView() {
		text=(EditText) findViewById(R.id.changetext);
		back=(ImageView) findViewById(R.id.back);
		save=(ImageView) findViewById(R.id.more);
		title=(TextView) findViewById(R.id.title);
		
	}
	private void init() {
		save.setImageDrawable(getResources().getDrawable(R.drawable.save));
		title.setText("修改信息");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ChanegBabyInfo.this.finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (text.getText().toString().trim()!=null&&!"".equals(text.getText().toString().trim())) {
					
					RequestParams params=new RequestParams();
					if (id!=0) {
						params.put("id", id);
					}else {
						params.put("id", "");
					}
					if (MyApplication.preferences.getInt("id", 0)!=0) {
						params.put("userId", MyApplication.preferences.getInt("id", 0));
					}else {
						EasyMotherUtils.goActivity(ChanegBabyInfo.this, LoginOrRegisterActivity.class);
					}
					if ("nannan_name".equals(intent.getStringExtra("type"))){
						params.put("babyName", text.getText().toString().trim());
					}
					if ("nannan_sex".equals(intent.getStringExtra("type"))) {
						params.put("gender", text.getText().toString().trim());
					}
					
					NetworkHelper.doGet(BaseInfo.BABYINFO_SAVEINFO, params, new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							super.onSuccess(statusCode, headers, response);
							Log.e("修改信息response---->", response.toString());
							if (JsonUtils.getRootResult(response).getIsSuccess()) {
								BabyInfoBean babyinfo=JsonUtils.getResult(response, BabyInfoBean.class);
								if (babyinfo!=null) {
									if (babyinfo.getId()!=null) {
										MyApplication.editor.putInt("baby_id",babyinfo.getId());
									}
									if (babyinfo.getBabyImage()!=null) {
										MyApplication.editor.putString("baby_image",babyinfo.getBabyImage());
									}
									if (babyinfo.getBackground()!=null) {
										MyApplication.editor.putString("baby_background",babyinfo.getBackground());
									}
									if (babyinfo.getGender()!=null) {
										MyApplication.editor.putString("nannan_sex",babyinfo.getGender());
									}
									if (babyinfo.getBabyName()!=null) {
										MyApplication.editor.putString("nannan_name",babyinfo.getBabyName());
									}
									if (babyinfo.getBirthday()!=null) {
									SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String birthy=dateFormat.format(babyinfo.getBirthday());
										MyApplication.editor.putString("nannan_birthday",birthy);
									}
									MyApplication.editor.commit();
								}
								MyApplication.editor.commit();
								Toast.makeText(ChanegBabyInfo.this, "信息修改成功", 0).show();
								ChanegBabyInfo.this.finish();
							}
							
							
						}
						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString,
								Throwable throwable) {
							super.onFailure(statusCode, headers, responseString, throwable);
							Toast.makeText(ChanegBabyInfo.this, responseString, 0).show();
						}
					});
					}else {
						Toast.makeText(ChanegBabyInfo.this, "修改信息不能为空", 0).show();
					}
					
			}
		});
		
		
	}
}
