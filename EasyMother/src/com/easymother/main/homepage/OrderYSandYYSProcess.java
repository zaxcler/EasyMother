package com.easymother.main.homepage;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.MyApplication;
import com.easymother.utils.EasyMotherUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class OrderYSandYYSProcess extends Activity {
	private Button begain_sign;//开始签约
	private TextView name;
	private TextView phone;
	private CheckBox checkbox;//是否同意协议
	private NurseBaseBean nurseBase;
	private NurseJobBean nurseJob;
	private TextView nursetype;//合同类型
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order);
		EasyMotherUtils.initTitle(this, "护理师预约", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		Intent intent=getIntent();
		nurseBase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nurseJob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		findView();
		init();
	}

	private void findView() {
		begain_sign=(Button) findViewById(R.id.begain_sign);
		name=(TextView) findViewById(R.id.name);
		phone=(TextView) findViewById(R.id.phone);
		nursetype=(TextView) findViewById(R.id.type);
		checkbox=(CheckBox) findViewById(R.id.checkBox1);
	}

	private void init() {
		if (nurseBase!=null) {
			if (nurseBase.getRealName()!=null||!"".equals(nurseBase.getRealName())) {
				name.setText(nurseBase.getRealName());
			}
//			if (nurseBase.getMobile()!=null||!"".equals(nurseBase.getMobile())) {
//				phone.setText(nurseBase.getMobile());
//			}
			if ("YS".equals(nurseBase.getJob())) {
				nursetype.setText("月嫂合同");
			}
			if ("YYS".equals(nurseBase.getJob())) {
				nursetype.setText("育婴师合同");
			}
			
			begain_sign.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (checkbox.isChecked()) {
						Intent intent=getIntent();
						intent.setClass(OrderYSandYYSProcess.this, OrderYSandYYSProcess1.class);
						intent.putExtra("nursebase", nurseBase);
						intent.putExtra("nursejob", nurseJob);
						startActivity(intent);
					}else {
						Toast.makeText(OrderYSandYYSProcess.this, "请同意网签协议", 0).show();
					}
					
				}
			});
		}
		
	}

}
