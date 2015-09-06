package com.easymother.main.homepage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.Order;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class OrderCRSProcess1 extends Activity {
	private Button submit;//开始签约
	private MyGridView gridView;//时间表
	private EditText user_name;//用户姓名
	private EditText user_phone;//用户电话
	private EditText user_address;//用户地址
	private ImageView calender;//选择日期
	private TextView predate;//预产期
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioGroup radiogroup;
	String baby_type;
	private Intent intent;
	private NurseJobBean nursejob;
	private NurseBaseBean nursebase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_crs_order_next1);
		EasyMotherUtils.initTitle(this, "信息填写", false);
		MyApplication.addActivityToMap(this, "CRSprocess");
		intent=getIntent();
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		findView();
		init();
	}

	private void findView() {
		submit=(Button) findViewById(R.id.submit);
		user_name=(EditText) findViewById(R.id.user_name);
		user_phone=(EditText) findViewById(R.id.user_phone);
		user_address=(EditText) findViewById(R.id.user_address);
		calender=(ImageView) findViewById(R.id.calender);
		predate=(TextView) findViewById(R.id.predate);
		radioButton1=(RadioButton) findViewById(R.id.radioButton1);
		radioButton2=(RadioButton) findViewById(R.id.radioButton2);
		radiogroup=(RadioGroup) findViewById(R.id.radiogroup);
		
		
	}

	private void init() {
		calender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Date date=new Date(System.currentTimeMillis());
				Calendar c=Calendar.getInstance();
				c.setTime(date);
				DatePickerDialog dialog=new DatePickerDialog(OrderCRSProcess1.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						predate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}
			
		});
		String datestring=MyApplication.preferences.getString("preDate", "");
		if (datestring!=null&&!"".equals(datestring)) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//			Date date=format.parse(datestring);
			predate.setText(datestring);
		}
		
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioButton1:
					baby_type=radioButton1.getText().toString().trim();
					break;
                case R.id.radioButton2:
                	baby_type=radioButton1.getText().toString().trim();
					break;
				}
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final String name=user_name.getText().toString().trim();
				final String phone=user_phone.getText().toString().trim();
				final String address=user_address.getText().toString().trim();
				String time=predate.getText().toString().trim()+" 00:00:00";
				if (name==null||"".equals(name)) {
					Toast.makeText(OrderCRSProcess1.this, "姓名不能为空", 0).show();
					return;
				}
				if (phone==null||"".equals(phone)) {
					Toast.makeText(OrderCRSProcess1.this, "电话不能为空", 0).show();
					return;
				}
				if (address==null||"".equals(address)) {
					Toast.makeText(OrderCRSProcess1.this, "地址不能为空", 0).show();
					return;
				}
				if (time==null||"".equals(time)) {
					Toast.makeText(OrderCRSProcess1.this, "分娩日期不能为空", 0).show();
					return;
				}
				boolean saveinfo=saveUserInfo(time,baby_type);
				
				if (saveinfo) {
					//先保存合同信息
					RequestParams params=new RequestParams();
					params.put("nurseJobId", nursejob.getId());
					params.put("nurseId", nursebase.getId());
					int id=MyApplication.preferences.getInt("id", 0);
					params.put("userId", id);
					params.put("userName", name);
					params.put("mobile", phone);
					params.put("address", address);
					String startTime=intent.getStringExtra("startTime");
					String endTime=intent.getStringExtra("endTime");
					if (startTime!=null&&!"".equals(startTime)) {
						params.put("hireStartTime", startTime);
						params.put("realHireStartTime", startTime);
					}
					if (endTime!=null&&!"".equals(endTime)) {
						params.put("hireEndTime", endTime);
						params.put("realHireEndTime", endTime);
					}
					//保存合同
					NetworkHelper.doGet(BaseInfo.SAVE_CONTRACT, params, new JsonHttpResponseHandler(){
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							if (JsonUtils.getRootResult(response).getIsSuccess()) {
								Log.e("保存合同成功", response.toString());
							}else {
								Log.e("保存合同失败", response.toString());
							}
						};
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							Log.e("保存合同失败", responseString);
						};
					});
					//保存订单
					NetworkHelper.doGet(BaseInfo.SAVE_ORDER, params, new JsonHttpResponseHandler(){
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							if (JsonUtils.getRootResult(response).getIsSuccess()) {
							Order order=JsonUtils.getResult(response, Order.class);
							order.setPrice(0.01);
							intent.putExtra("order",order);
							intent.setClass(OrderCRSProcess1.this, OrderCRSProcess2.class);
							intent.putExtra("userName",name );
							intent.putExtra("mobile",phone );
							intent.putExtra("address",address );
							startActivity(intent);
							}else {
								Log.e("保存订单失败", response.toString());
							}
						};
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							Log.e("保存订单失败", responseString);
							Toast.makeText(OrderCRSProcess1.this, "连接服务器失败", 0).show();
						};
					});
					
					
				}
				
				
			}
		});
		
		
	}

	/**
	 * 保存用户信息
	 * @param time 分娩时间
	 * @param baby_type2 分娩方式
	 */
	protected boolean saveUserInfo(String time, String baby_type2) {
		RequestParams params=new RequestParams();
		int id=MyApplication.preferences.getInt("id", 0);
		if (id==0) {
			EasyMotherUtils.goActivity(this, LoginOrRegisterActivity.class);
			return false;
		}
		params.put("id", id);
		if (time!=null&&!"".equals(time)) {
			params.put("preDate", time);
		}
		if (baby_type2!=null&&!"".equals(baby_type2)) {
			params.put("childType", baby_type2);
		}
		NetworkHelper.doGet(BaseInfo.CHANGEINFO, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		return true;
	}

}
