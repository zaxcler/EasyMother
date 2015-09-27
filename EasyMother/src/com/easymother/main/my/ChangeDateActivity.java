package com.easymother.main.my;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.main.homepage.OrderCRSPreocessGridViewAdapter;
import com.easymother.main.homepage.OrderYSandYYSProcess1;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;

public class ChangeDateActivity extends Activity implements View.OnClickListener {
	private EditText beizhu;// 备注
	private Button complete;// 完成
	private Intent intent;
	private int id;
	private String content;
	private MyGridView gridView;
	private OrderCRSPreocessGridViewAdapter adapter;
	protected boolean isChoose = false;
	protected int clicktimes = 1;
	private ImageView addtime;
	private ImageView deletetime;
	private TextView currenttime;
	private TextView time1;
	private TextView time3;
	private LinearLayout time2;
	private String job;
	protected String startTime;
	protected String endTime;
	private LinearLayout timedialog;
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_changetime);
		EasyMotherUtils.initTitle(this, "申请改期", false);
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		job = intent.getStringExtra("job");
		findView();
		init();

	}

	private void findView() {
		beizhu = (EditText) findViewById(R.id.beizhu);
		complete = (Button) findViewById(R.id.complete);
		addtime = (ImageView) findViewById(R.id.addtime);
		deletetime = (ImageView) findViewById(R.id.deletetime);
		currenttime = (TextView) findViewById(R.id.currenttime);
		time1 = (TextView) findViewById(R.id.time1);
		time3 = (TextView) findViewById(R.id.time3);
		time2 = (LinearLayout) findViewById(R.id.time2);
		gridView = (MyGridView) findViewById(R.id.gridView1);
		timedialog = (LinearLayout) findViewById(R.id.timedialog);
	}

	private void init() {

		addtime.setOnClickListener(this);
		deletetime.setOnClickListener(this);
		time1.setOnClickListener(this);

		/**
		 * 时间更改
		 */
		if ("YS".equals(job) || "YYS".equals(job) || "SHORT_YS".equals(job) || "SHORT_YYS".equals(job)) {
			time1.setVisibility(View.VISIBLE);
			time3.setVisibility(View.VISIBLE);
			Date currentdate = new Date(System.currentTimeMillis());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentdate);
			startTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
			endTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
			time1.setText("开始时间:        " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
			time3.setText("结束时间:        " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
			timedialog.setVisibility(View.VISIBLE);
			timedialog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showDateDialog(v);
				}
			});
			complete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					content = beizhu.getText().toString().trim();
					params = new RequestParams();

					if (id != 0) {
						params.put("orderId", id);
					}
					if (content == null && !"".equals(content)) {
						params.put("descrition", content);
					}

					if (startTime != null && !"".equals(startTime)) {
						params.put("realHireStartTime", startTime);
					} else {
						params.put("realHireStartTime", currenttime);
					}

					if (endTime != null && !"".equals(endTime)) {
						params.put("realHireEndTime", endTime);
					} else {
						params.put("realHireEndTime", currenttime);
					}
					if (startTime != null && !"".equals(startTime)) {
						params.put("hireStartTime", startTime);
					} else {
						params.put("hireStartTime", currenttime);
					}

					if (endTime != null && !"".equals(endTime)) {
						params.put("hireEndTime", endTime);
					} else {
						params.put("hireEndTime", currenttime);
					}

					params.put("type", "申请改期");
					submit();
				}
			});

		} else {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd");
			String tiem = dateFormat.format(date);
			currenttime.setText(tiem);
			time2.setVisibility(View.VISIBLE);
			gridView.setVisibility(View.VISIBLE);
			adapter = new OrderCRSPreocessGridViewAdapter(this, EasyMotherUtils.getTime(), R.layout.crs_gridview_item,
					null);
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					OrderCRSPreocessGridViewAdapter adapter = (OrderCRSPreocessGridViewAdapter) arg0.getAdapter();
					ViewHolder holder = (ViewHolder) arg1.getTag();
					TextView textView = holder.getView(R.id.time);
					if (isChoose) {
						return;
					}
					arg1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
					String tag = textView.getTag().toString();
					if (clicktimes % 2 == 1) {
						adapter.setStarttime(tag);
					}
					if (clicktimes % 2 == 0) {
						isChoose = true;
						adapter.setEndtime(textView.getTag().toString());
						adapter.notifyDataSetChanged();
					}
					clicktimes++;
				}
			});
			complete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					content = beizhu.getText().toString().trim();
					params = new RequestParams();

					if (id != 0) {
						params.put("orderId", id);
					}
					if (content == null && !"".equals(content)) {
						params.put("descrition", content);
					}
					adapter.getStarttime();
					if (adapter.getStarttime() == null && "".equals(adapter.getStarttime())) {
						params.put("realHireStartTime", currenttime);
					} else {
						params.put("realHireStartTime", adapter.getStarttime());
					}

					if (adapter.getEndtime() == null && "".equals(adapter.getEndtime())) {
						params.put("realHireEndTime", currenttime);
					} else {
						params.put("realHireEndTime", adapter.getEndtime());
					}

					if (adapter.getStarttime() == null && "".equals(adapter.getStarttime())) {
						params.put("hireStartTime", currenttime);
					} else {
						params.put("hireStartTime", adapter.getStarttime());
					}

					if (adapter.getStarttime() == null && "".equals(adapter.getEndtime())) {
						params.put("hireEndTime", currenttime);
					} else {
						params.put("hireEndTime", adapter.getEndtime());
					}
					params.put("type", "申请改期");
					submit();
				}
			});
		}

	}

	public void submit() {
		NetworkHelper.doGet(BaseInfo.CHANGE_ORDER_MSG, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					Toast.makeText(ChangeDateActivity.this, "提交成功，等待审核", 0).show();
					ChangeDateActivity.this.finish();
				}
				// Log.e("修改订单", response.toString());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("修改订单连接服务器失败", responseString);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addtime:
			currenttime.setText(adapter.deleteTime());
			adapter.notifyDataSetChanged();
			isChoose = false;
			break;

		case R.id.deletetime:
			currenttime.setText(adapter.addTime());
			adapter.notifyDataSetChanged();
			isChoose = false;
			break;

		}
	}

	/**
	 * 显示时间选择框
	 * 
	 * @param v
	 */
	protected void showDateDialog(View v) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_chosedate, null);
		dialog.setContentView(view);
		dialog.getWindow().setLayout(MyApplication.getScreen_width() / 5 * 4,
				android.view.WindowManager.LayoutParams.WRAP_CONTENT);
		DatePicker datePicker1 = (DatePicker) view.findViewById(R.id.start_time);
		DatePicker datePicker2 = (DatePicker) view.findViewById(R.id.end_time);
		final Date currentdate = new Date(System.currentTimeMillis());
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentdate);
		startTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		endTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		time1.setText("开始时间:        " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
		time3.setText("结束时间:        " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日");
		datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						time1.setText("开始时间:        " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
					}
				});
		datePicker2.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						endTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						time3.setText("结束时间:        " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
					}
				});

		view.findViewById(R.id.comfire).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		view.findViewById(R.id.dismisse).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

}
