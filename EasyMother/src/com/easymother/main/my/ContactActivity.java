package com.easymother.main.my;

import com.alidao.mama.R;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactActivity extends Activity {
	private LinearLayout contact;//确认
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_contact);
		EasyMotherUtils.initTitle(this, "联系我们", false);
		findView();
		init();
	}

	private void findView() {
		contact=(LinearLayout) findViewById(R.id.contact);
	}

	private void init() {
		contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Dialog dialog=new Dialog(ContactActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog_mypage_contact);
				int width=MyApplication.getScreen_width()/3*2;
				
				dialog.getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.show();
				final TextView phone_num=(TextView) dialog.findViewById(R.id.phone_num);
				
				
				TextView cancle=(TextView) dialog.findViewById(R.id.cancle);
				cancle.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				TextView call=(TextView) dialog.findViewById(R.id.call);
				call.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone_num.getText().toString()));
						startActivity(intent);
						dialog.dismiss();
					}
				});
			}
		});
		
	}
}
