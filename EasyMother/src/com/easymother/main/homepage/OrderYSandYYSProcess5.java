package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderYSandYYSProcess5 extends Activity {
	private TextView complete;//
	private WebView hetong;
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_ysandyys_order_next5);
		EasyMotherUtils.initTitle(this, "合同预览", false);
		MyApplication.addActivityToMap(this, "YSprocess");
		intent=getIntent();
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		findView();
		init();
	}

	private void findView() {
		complete=(TextView) findViewById(R.id.button);
		hetong=(WebView) findViewById(R.id.hetong);
		
	}

	private void init() {
		hetong.loadUrl("file:///android_asset/demo.html");
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				boolean saveContractStatu=saveContract();
				//判断是否保存成功
				boolean saveOrderstatu=saveOrder();
				if (saveContractStatu && saveOrderstatu) {
					intent.setClass(OrderYSandYYSProcess5.this, OrderYSandYYSProcess6.class);
					startActivity(intent);
				}
				
				
			}
		});
	}
	/**
	 * 保存合同信息
	 */
	public boolean saveContract(){
		
		RequestParams params=new RequestParams();
		int id=MyApplication.preferences.getInt("id", 0);
		if (id==0) {
			Toast.makeText(OrderYSandYYSProcess5.this, "请登录", 0).show();
			EasyMotherUtils.goActivity(OrderYSandYYSProcess5.this, LoginOrRegisterActivity.class);
			return false;
		}
		params.put("userId",id);
		if (!"".equals(MyApplication.preferences.getString("order_user_name", ""))) {
			params.put("userName",MyApplication.preferences.getString("order_user_name", ""));
		}
		if (!"".equals(MyApplication.preferences.getString("order_user_phone", ""))) {
			params.put("mobile",MyApplication.preferences.getString("order_user_phone", ""));
		}
		if (!"".equals(MyApplication.preferences.getString("order_user_card_id", ""))) {
			params.put("identificationCode",MyApplication.preferences.getString("order_user_card_id", ""));
		}
		if (!"".equals(MyApplication.preferences.getString("order_user_address", ""))) {
			params.put("address",MyApplication.preferences.getString("order_user_card_id", ""));
		}
		
		if (nursejob.getNurseId()!=null) {
			params.put("nurseId", nursejob.getNurseId());
		}
		if (nursejob.getNurseName()!=null) {
			params.put("nurseName", nursejob.getNurseName());
		}
		
		if (nursebase.getMobile()!=null) {
			params.put("nurseMobile", nursebase.getMobile());
		}
		if (nursebase.getCurrentAddress()!=null) {
			params.put("nurseAddress", nursebase.getCurrentAddress());
		}
		
		NetworkHelper.doGet(BaseInfo.SAVE_CONTRACT, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				Log.e("onSuccess","错误"+ arg2);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e("onFailure","错误"+ arg2);
			}
		});
		return true;
	}
	/*
	 * 提交订单信息
	 */
	public boolean saveOrder(){
		RequestParams params=new RequestParams();
		int id=MyApplication.preferences.getInt("id", 0);
		if (id==0) {
			Toast.makeText(OrderYSandYYSProcess5.this, "请登录", 0).show();
			EasyMotherUtils.goActivity(OrderYSandYYSProcess5.this, LoginOrRegisterActivity.class);
			return false;
		}
		params.put("userId",id);
		if (!"".equals(MyApplication.preferences.getString("order_user_name", ""))) {
			params.put("userName",MyApplication.preferences.getString("order_user_name", ""));
		}
		if (!"".equals(MyApplication.preferences.getString("order_user_phone", ""))) {
			params.put("userMobile",MyApplication.preferences.getString("order_user_phone", ""));
		}
//		if (!"".equals(MyApplication.preferences.getString("order_user_card_id", ""))) {
//			params.put("identificationCode",MyApplication.preferences.getString("order_user_card_id", ""));
//		}
//		if (!"".equals(MyApplication.preferences.getString("order_user_address", ""))) {
//			params.put("address",MyApplication.preferences.getString("order_user_card_id", ""));
//		}
		if (nursejob.getNurseId()!=null) {
			params.put("nurseId", nursejob.getNurseId());
		}
		if (nursejob.getNurseName()!=null) {
			params.put("nurseName", nursejob.getNurseName());
		}
		if (nursejob.getJob()!=null) {
			params.put("job", nursejob.getJob());
		}
//		if (nursebase.getMobile()!=null) {
//			params.put("nurseMobile", nursebase.getMobile());
//		}
//		
//		if (nursebase.getCurrentAddress()!=null) {
//			params.put("nurseAddress", nursebase.getCurrentAddress());
//		}
		//雇佣时间
		if (nursebase.getEmploymentStartTime()!=null) {
			params.put("hireStartTime", nursebase.getEmploymentStartTime());
		}
		//雇佣结束时间
		if (nursebase.getEmploymentEndTime()!=null) {
			params.put("hireEndTime", nursebase.getEmploymentEndTime());
		}
		if (nursebase.getEmploymentStartTime()!=null) {
			params.put("realHireStartTime", nursebase.getEmploymentStartTime());
		}
		//雇佣结束时间
		if (nursebase.getEmploymentEndTime()!=null) {
			params.put("realHireEndTime", nursebase.getEmploymentEndTime());
		}
			params.put("status","未付款");
			params.put("realAllAmount", 0);
			params.put("nurseJobId", nursejob.getId());
			params.put("isSee", intent.getByteExtra("isSee", (byte) 0));
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				if (intent.getStringExtra("seeTime")!=null&&!"".equals(intent.getStringExtra("seeTime"))) {
					date = format.parse(intent.getStringExtra("seeTime"));
					params.put("seeTime", date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			NetworkHelper.doGet(BaseInfo.SAVE_ORDER, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					Log.e("success", response.toString());
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("success", responseString);
				}
			});
		return true;
		
		
		
	}

}
