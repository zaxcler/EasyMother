package com.easymother.main.my;

import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

public class SettingSueegstionActivity extends Activity {
	private EditText suggestion;//意见
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_setting_suggestion);
		EditText suggestion = (EditText) findViewById(R.id.suggestion);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.save));
				RequestParams params=new RequestParams();
//				params.put(key, file);
//				NetworkHelper.doGet(BaseInfo.SAVESUGGESTION, params, handler);
			}
		});
		EasyMotherUtils.initTitle(this, "设置", false);
		
	}
	

}
