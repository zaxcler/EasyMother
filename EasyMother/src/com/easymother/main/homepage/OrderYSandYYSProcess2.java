package com.easymother.main.homepage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.RegularUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderYSandYYSProcess2 extends Activity {
	private Button begain_sign;//开始签约
	
	private TextView nursename;//护理师姓名
	private TextView nursephone;//护理师电话
	private TextView nurseaddress;//护理师地址
	private ImageView nurseimage;
	
	private EditText user_name;
	private EditText user_phone;
	private EditText card_id;
	private EditText user_address;
	
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next2);
		EasyMotherUtils.initTitle(this, "合同填写", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		intent=getIntent();
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		
		findView();
		init();
	}

	private void findView() {
		begain_sign=(Button) findViewById(R.id.begain_next2);
		nursename=(TextView) findViewById(R.id.nursename);
		nursephone=(TextView) findViewById(R.id.nursephone);
		nurseaddress=(TextView) findViewById(R.id.nurseaddress);
		nurseimage=(ImageView) findViewById(R.id.nurseimage);
		
		user_name=(EditText) findViewById(R.id.user_name);
		user_phone=(EditText) findViewById(R.id.user_phone);
		card_id=(EditText) findViewById(R.id.card_id);
		user_address=(EditText) findViewById(R.id.user_address);
	}

	private void init() {
		if (nursebase.getRealName()!=null) {
			nursename.setText(nursebase.getRealName());
		}
//		if (nursebase.getMobile()!=null) {
//			nursephone.setText(nursebase.getMobile());
//		}
		if (nursebase.getCurrentAddress()!=null) {
			nurseaddress.setText(nursebase.getCurrentAddress());
		}
		if (nursebase.getImage()!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursebase.getImage(), nurseimage);
		}
		
		
		begain_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				String test=user_name.getText().toString().trim();
				Log.e("user_name.getText().toString().trim()", user_name.getText().toString().trim());
				if (user_name.getText().toString().trim()==null||"".equals(user_name.getText().toString().trim())) {
					Toast.makeText(OrderYSandYYSProcess2.this, "姓名不能为空", 0).show();
					return;
				}
				if (user_phone.getText().toString().trim()==null||"".equals(user_phone.getText().toString().trim())) {
					Toast.makeText(OrderYSandYYSProcess2.this, "电话不能为空", 0).show();
					return;
				}else if(!RegularUtils.isPhoneNumber(user_phone.getText().toString().trim())){
					
					Toast.makeText(OrderYSandYYSProcess2.this, "请输入正确电话号码！", 0).show();
					return;
				}
				if (card_id.getText().toString().trim()==null||"".equals(card_id.getText().toString().trim())) {
					Toast.makeText(OrderYSandYYSProcess2.this, "身份证号码不能为空", 0).show();
					return;
				}
				if (user_address.getText().toString().trim()==null||"".equals(user_address.getText().toString().trim())) {
					Toast.makeText(OrderYSandYYSProcess2.this, "地址不能为空", 0).show();
					return;
				}
				
				MyApplication.editor.putString("order_user_name", user_name.getText().toString().trim());
				MyApplication.editor.putString("order_user_phone", user_phone.getText().toString().trim());
				MyApplication.editor.putString("order_user_address", user_address.getText().toString().trim());
				//判断是否是身份证
				Pattern pattern=Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
				Matcher matcher=pattern.matcher(card_id.getText().toString().trim());
				if (matcher.matches()) {
					MyApplication.editor.putString("order_user_card_id", card_id.getText().toString().trim());
				}else {
					Toast.makeText(OrderYSandYYSProcess2.this, "请输入正确的身份证", 0).show();
					return;
				}
				MyApplication.editor.commit();
				intent.setClass(OrderYSandYYSProcess2.this, OrderYSandYYSProcess3.class);
				intent.putExtra("nursebase", nursebase);
				intent.putExtra("nursejob", nursejob);
				startActivity(intent);
				
			}
		});
	}

}
