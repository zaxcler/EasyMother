package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alipay.sdk.auth.h;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.NurseService;
import com.easymother.bean.Order;
import com.easymother.bean.TaocanBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class OrderCRSProcess extends Activity implements OnClickListener {
	private Button begain_sign;// 开始签约
	private MyGridView gridView;// 时间表
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private ArrayList<Order> orders;
	
	private TextView nurse_name;
	private TextView nurse_phone;
	private TextView nurse_price;
	private TextView price;
	private TextView project_name;
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	
	private int clicktimes=1;//点击次数
	private boolean isChoose=false;//是否已选择
	
	private LinearLayout linearLayout1;//套餐

	private RatingBar ratingBar1;
	private CircleImageView imageView1;
	OrderCRSPreocessGridViewAdapter adapter ;
	private String projectname;
	
	private String startTime;
	private String endTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_crs_order);
		EasyMotherUtils.initTitle(this, "护理师预约", false);
		MyApplication.addActivityToMap(this, "CRSprocess");
		intent = getIntent();
		nursebase = (NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob = (NurseJobBean) intent.getSerializableExtra("nursejob");
		orders=intent.getParcelableArrayListExtra("orders");
		findView();
		init();
	}

	private void findView() {
		begain_sign = (Button) findViewById(R.id.begain_sign);
		gridView = (MyGridView) findViewById(R.id.gridView1);
		nurse_name = (TextView) findViewById(R.id.nurse_name);
		nurse_phone = (TextView) findViewById(R.id.nurse_phone);
		nurse_price = (TextView) findViewById(R.id.nurse_price);
		project_name = (TextView) findViewById(R.id.project_name);
		price = (TextView) findViewById(R.id.price);
		day1 = (TextView) findViewById(R.id.day1);
		day2 = (TextView) findViewById(R.id.day2);
		day3 = (TextView) findViewById(R.id.day3);
		day4 = (TextView) findViewById(R.id.day4);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
		linearLayout1=(LinearLayout) findViewById(R.id.linearLayout1);
	}

	private void init() {
		loadData();
		if (nursebase.getRealName() != null) {
			nurse_name.setText(nursebase.getRealName());
		}
		if (nursebase.getMobile() != null) {
			nurse_phone.setText(nursebase.getMobile());
		}
		if (nursejob.getPrice() != null) {
			nurse_price.setText("¥" + nursejob.getPrice() + "/次");
		}
		if (nursejob.getPrice() != null) {
			price.setText("¥" + nursejob.getPrice() + "/次");
		}
		if (nursejob.getLevel() != null) {
			ratingBar1.setProgress(nursejob.getLevel());
		} else {
			ratingBar1.setProgress(0);
		}
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + nursejob.getWorkImageArrays()[0],
					imageView1, MyApplication.options_photo);
		if (intent.getIntExtra("CRS_Project", 0) != 0) {
			switch (intent.getIntExtra("CRS_Project", 0)) {
			case 1:
				project_name.setText("产前开奶");
				projectname="产前开奶";
				break;
			case 2:
				project_name.setText("产后无痛点穴开奶");
				projectname="产后无痛点穴开奶";
				break;
			case 3:
				project_name.setText("催乳");
				projectname="催乳";
				break;
			case 4:
				project_name.setText("消淤");
				projectname="消淤";
				break;
			case 5:
				project_name.setText("乳腺小叶增生养护");
				projectname="乳腺小叶增生养护";
				break;

			case 6:
				project_name.setText("产后满月汗蒸排毒");
				projectname="产后满月汗蒸排毒";
				break;
			case 7:
				project_name.setText("产后经络调理消月子病");
				projectname="产后经络调理消月子病";
				break;

			}
		}
		begain_sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startTime=adapter.getStarttime();
				endTime=adapter.getEndtime();
				Log.e("startTime", startTime);
				Log.e("endTime", endTime);
				if ("".equals(startTime)||"".equals(endTime)||startTime==null||endTime==null) {
					Toast.makeText(OrderCRSProcess.this, "请选择时间", Toast.LENGTH_SHORT).show();
					return;
				}
				intent.putExtra("startTime", startTime);
				intent.putExtra("endTime", endTime);
				intent.setClass(OrderCRSProcess.this, OrderCRSProcess1.class);
				startActivity(intent);
			}
		});

		adapter = new OrderCRSPreocessGridViewAdapter(this, EasyMotherUtils.getTime(),
				R.layout.crs_gridview_item,orders);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				OrderCRSPreocessGridViewAdapter adapter=(OrderCRSPreocessGridViewAdapter) arg0.getAdapter();
				ViewHolder holder=(ViewHolder) arg1.getTag();
				TextView textView=holder.getView(R.id.time);
				if (isChoose) {
					return;
				}
				arg1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
				String tag=textView.getTag().toString();
				if (clicktimes%2==1) {
					adapter.setStarttime(tag);
				}
				if (clicktimes%2==0) {
					isChoose=true;
					adapter.setEndtime(textView.getTag().toString());
					adapter.notifyDataSetChanged();
				}
				clicktimes++;
			}
		});
		
		day1.setOnClickListener(this);
		day2.setOnClickListener(this);
		day3.setOnClickListener(this);
		day4.setOnClickListener(this);

	}
	/**
	 * 加载套餐类型
	 */
	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("name", projectname);
		if (nursejob.getLevel()!=null) {
			params.put("level", nursejob.getJobTitle());
		}else {
			params.put("level", "");
		}
		NetworkHelper.doGet(BaseInfo.GET_TAOCAN, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					TaocanBean taocan=JsonUtils.getResult(response, TaocanBean.class);
					bindData(taocan);
				}
			}
		});
		
		
	}

	protected void bindData(TaocanBean taocan) {
		if (taocan!=null) {
			List<NurseService> list=taocan.getNurseservice();
			if (list!=null) {
				for (int i = 0; i < list.size(); i++) {
					TextView view=new TextView(this);
					view.setText(list.get(i).getFixedPrice()+"元"+"/"+list.get(i).getNums()+"次");
					view.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_border));
					linearLayout1.addView(view);
				}
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		clearState();
		switch (v.getId()) {
		case R.id.day1:
			isChoose=false;
			day1.setTextColor(getResources().getColor(R.color.white));
			day1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.setCurrentDay();
			adapter.notifyDataSetChanged();
			break;

		case R.id.day2:
			isChoose=false;
			day2.setTextColor(getResources().getColor(R.color.white));
			day2.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.setTomorrow();
			adapter.notifyDataSetChanged();
			break;
		case R.id.day3:
			isChoose=false;
			day3.setTextColor(getResources().getColor(R.color.white));
			day3.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.setTheDayAffterTomorrow();
			adapter.notifyDataSetChanged();
			break;
		case R.id.day4:
			isChoose=false;
			day4.setTextColor(getResources().getColor(R.color.white));
			day4.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.setForthDay();
			adapter.notifyDataSetChanged();
			break;

		}
	}
	private void clearState(){
		day1.setTextColor(getResources().getColor(R.color.blacktext));
		day2.setTextColor(getResources().getColor(R.color.blacktext));
		day3.setTextColor(getResources().getColor(R.color.blacktext));
		day4.setTextColor(getResources().getColor(R.color.blacktext));
		day1.setBackgroundColor(getResources().getColor(R.color.white));
		day2.setBackgroundColor(getResources().getColor(R.color.white));
		day3.setBackgroundColor(getResources().getColor(R.color.white));
		day4.setBackgroundColor(getResources().getColor(R.color.white));
		
	}

}
