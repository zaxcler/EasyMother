package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderYSandYYSProcess1 extends Activity {
	private Button begain_sign;// 开始签约
	private TextView startTime_tv;// 开始时间
	private TextView endTime_tv;// 结束时间
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
	private String startTime;
	private String endTime;

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
		startTime_tv = (TextView) findViewById(R.id.startTime);
		endTime_tv = (TextView) findViewById(R.id.endTime);
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
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			startTime=dateFormat.format(startdate);
			endTime=dateFormat.format(enddate);
			String start = format.format(startdate);
			String end = format.format(enddate);
			startTime_tv.setText(start);
			endTime_tv.setText(end);
			int countday = TimeCounter.countTimeOfDay(startdate, enddate);
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
				RequestParams params=new RequestParams();
				params.put("realHireStartTime", startTime);
				params.put("realHireEndTime", endTime);
				params.put("nurseJobId", nursejob.getId());
				NetworkHelper.doGet(BaseInfo.CHECK_TIME, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							intent.setClass(OrderYSandYYSProcess1.this, OrderYSandYYSProcess2.class);
							intent.putExtra("nursebase", nursebase);
							intent.putExtra("nursejob", nursejob);
							startActivity(intent);
						}else {
							Toast.makeText(OrderYSandYYSProcess1.this, "我靠，这段时间已被预定", Toast.LENGTH_SHORT).show();
						}
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Toast.makeText(OrderYSandYYSProcess1.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
						Log.e("连接服务器失败", responseString);
					}
				});
				

			}
		});
	}

}
