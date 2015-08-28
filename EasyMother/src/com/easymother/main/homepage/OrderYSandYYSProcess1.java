package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.TimeCounter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderYSandYYSProcess1 extends Activity {
	private Button begain_sign;// 开始签约
	private TextView startTime;// 开始时间
	private TextView endTime;// 结束时间
	private TextView countTime;// 共计时间
	private TextView price;// 价格
	private TextView prePrice;// 定金
	private LinearLayout meetlayout;// 是否预约见面
	private TextView meettime;// 见面时间
	private boolean isMeet = false;// 是否见面
	private ImageView meetImage;//

	private Intent intent;
	private NurseBaseBean nursebase;// 传入的bean
	private NurseJobBean nursejob;// 传入的bean

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next1);
		EasyMotherUtils.initTitle(this, "信息填写", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		findView();
		init();
	}

	private void findView() {
		begain_sign = (Button) findViewById(R.id.begain_next1);
		startTime = (TextView) findViewById(R.id.startTime);
		endTime = (TextView) findViewById(R.id.endTime);
		countTime = (TextView) findViewById(R.id.countTime);
		price = (TextView) findViewById(R.id.price);
		prePrice = (TextView) findViewById(R.id.prePrice);
		meetlayout = (LinearLayout) findViewById(R.id.meet_layout);
		meetImage = (ImageView) findViewById(R.id.meet);
		meettime = (TextView) findViewById(R.id.meet_time);
	}

	private void init() {
		intent = getIntent();
		nursebase = (NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob = (NurseJobBean) intent.getSerializableExtra("nursejob");
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		// 开始时间
		Date startdate = nursebase.getEmploymentStartTime();

		// 结束时间
		Date enddate = nursebase.getEmploymentEndTime();

		if (startdate != null && enddate != null) {
			String start = format.format(startdate);
			String end = format.format(enddate);
			startTime.setText(start);
			endTime.setText(end);
			int countday = TimeCounter.countTime(startdate, enddate);
			countTime.setText("共计" + countday + "天");
		}
		if (!isMeet) {
			meetImage.setVisibility(View.GONE);
		}
		meetlayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog datePicker = new DatePickerDialog(OrderYSandYYSProcess1.this, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						meetImage.setVisibility(View.VISIBLE);

						meettime.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
						intent.putExtra("isSee", 1);
						intent.putExtra("seeTime", year + "-" + monthOfYear + "-" + dayOfMonth);
					}
				}, 0, 0, 0);

				datePicker.show();
			}

		});

		//

		if (nursejob.getPrice() != null) {
			price.setText("￥" + nursejob.getPrice() + "元");
		}
		prePrice.setText("￥500元");

		begain_sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent.setClass(OrderYSandYYSProcess1.this, OrderYSandYYSProcess2.class);
				intent.putExtra("nursebase", nursebase);
				intent.putExtra("nursejob", nursejob);
				startActivity(intent);

			}
		});
	}

}
