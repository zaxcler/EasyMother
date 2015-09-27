package com.easymother.main.my;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
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

public class InfomationChangeTextActivity extends Activity {
	private EditText text;// 要修改的信息
	private TextView title;// 标题
	private ImageView back;// 返回
	private ImageView save;// 保存
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_infomation_chengetext);
		intent = getIntent();
		findView();

		init();

	}

	private void findView() {
		text = (EditText) findViewById(R.id.changetext);
		back = (ImageView) findViewById(R.id.back);
		save = (ImageView) findViewById(R.id.more);
		title = (TextView) findViewById(R.id.title);

	}

	private void init() {
		save.setImageDrawable(getResources().getDrawable(R.drawable.save));
		title.setText("修改信息");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InfomationChangeTextActivity.this.finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (text.getText().toString().trim() != null && !"".equals(text.getText().toString().trim())) {
					RequestParams params = new RequestParams();
					params.put("id", MyApplication.preferences.getInt("id", 0));
					if ("nick_name".equals(intent.getStringExtra("type"))) {
						params.put("nickname", text.getText().toString().trim());
					}
					if ("hospital".equals(intent.getStringExtra("type"))) {
						params.put("hospitalName", text.getText().toString().trim());
					}
					if ("address".equals(intent.getStringExtra("type"))) {
						params.put("address", text.getText().toString().trim());
					}
					if ("phone".equals(intent.getStringExtra("type"))) {
						params.put("mobile", text.getText().toString().trim());
					}

					NetworkHelper.doGet(BaseInfo.CHANGEINFO, params, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							super.onSuccess(statusCode, headers, response);

							Log.e("修改信息response---->", response.toString());
							if (JsonUtils.getRootResult(response).getIsSuccess()) {
								if ("nick_name".equals(intent.getStringExtra("type"))) {
									MyApplication.editor.putString("nickname", text.getText().toString().trim());
								}
								if ("hospital".equals(intent.getStringExtra("type"))) {
									MyApplication.editor.putString("hospitalName", text.getText().toString().trim());
								}
								if ("address".equals(intent.getStringExtra("type"))) {
									MyApplication.editor.putString("address", text.getText().toString().trim());
								}
								if ("time".equals(intent.getStringExtra("type"))) {
									MyApplication.editor.putString("preDate", text.getText().toString().trim());
								}
								if ("phone".equals(intent.getStringExtra("type"))) {
									MyApplication.editor.putString("mobile", text.getText().toString().trim());
								}
								MyApplication.editor.commit();
								Toast.makeText(InfomationChangeTextActivity.this, "信息修改成功", 0).show();
								InfomationChangeTextActivity.this.finish();
							}

						}

						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString,
								Throwable throwable) {
							super.onFailure(statusCode, headers, responseString, throwable);
							Toast.makeText(InfomationChangeTextActivity.this, responseString, 0).show();
						}
					});
				} else {
					Toast.makeText(InfomationChangeTextActivity.this, "修改信息不能为空", 0).show();
				}

			}
		});

	}

}
