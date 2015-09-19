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
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

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
	private RelativeLayout time;

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
		time=(RelativeLayout) findViewById(R.id.time);
	}

	private void init() {
		intent = getIntent();
		nursebase = (NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob = (NurseJobBean) intent.getSerializableExtra("nursejob");
		startTime=intent.getStringExtra("startTime");
		endTime=intent.getStringExtra("endTime");
//		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
//		// 开始时间
//		Date startdate = nursebase.getEmploymentStartTime();
//		// 结束时间
//		Date enddate = nursebase.getEmploymentEndTime();

		time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDateDialog(v);
			}
		});
		if (startTime != null && endTime != null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date startdate;
			Date enddate;
			try {
				startdate = dateFormat.parse(startTime);
				enddate = dateFormat.parse(endTime);
				SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
				String start=format.format(startdate);
				String end=format.format(enddate);
				startTime_tv.setText(start);
				endTime_tv.setText(end);
				int countday = TimeCounter.countTimeOfDay(startdate, enddate);
				countTime.setText("共计" + countday + "天");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}else {
			Date currentdate=new Date(System.currentTimeMillis());
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(currentdate);
			startTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+ calendar.get(Calendar.DAY_OF_MONTH);
			startTime_tv.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
			endTime_tv.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
			countTime.setText("共计" + 1 + "天");
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

		if (nursejob.getPrice()!= null) {
			price.setText("￥" + nursejob.getPrice() + "元");
		}
		prePrice.setText("￥500元");
		begain_sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RequestParams params=new RequestParams();
				if (startTime==null || endTime==null) {
					Toast.makeText(OrderYSandYYSProcess1.this, "请选择时间", 0).show();
					return;
				}
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
	
	/**
	 * 显示时间选择框
	 * @param v
	 */
	protected void showDateDialog(View v) {
		final Dialog dialog=new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view=LayoutInflater.from(this).inflate(R.layout.dialog_chosedate, null);
		dialog.setContentView(view);
		dialog.getWindow().setLayout(MyApplication.getScreen_width()/5*4, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
		DatePicker datePicker1=(DatePicker) view.findViewById(R.id.start_time);
		DatePicker datePicker2=(DatePicker) view.findViewById(R.id.end_time);
		final Date currentdate=new Date(System.currentTimeMillis());
		final Calendar calendar=Calendar.getInstance();
		calendar.setTime(currentdate);
		//设置时间 防止未选择时间时，出现空指针异常
		startTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+ calendar.get(Calendar.DAY_OF_MONTH);
		endTime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+ calendar.get(Calendar.DAY_OF_MONTH);
		startTime_tv.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
		datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				startTime=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
				startTime_tv.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
			}
		});
        datePicker2.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				endTime=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
		        endTime_tv.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
			}
		});
        
		view.findViewById(R.id.comfire).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
					Date startDate=dateFormat.parse(startTime);
					Date endDate=dateFormat.parse(endTime);
					Date current=dateFormat.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
					if (TimeCounter.countTimeOfDay(startDate, endDate)>0 && TimeCounter.countTimeOfDay(current , startDate)>=0) {
						intent.putExtra("startTime", startTime);
						intent.putExtra("endTime", endTime);
						int countday = TimeCounter.countTimeOfDay(startDate, endDate);
						countTime.setText("共计" + countday + "天");
						dialog.dismiss();
					}else {
						Toast.makeText(OrderYSandYYSProcess1.this,"请检查时间选择是否正确", Toast.LENGTH_SHORT).show();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		dialog.show();
	}

}
