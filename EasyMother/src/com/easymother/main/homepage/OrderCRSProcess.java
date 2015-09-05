package com.easymother.main.homepage;

import java.util.ArrayList;

import com.alipay.sdk.auth.h;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.Order;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyGridView;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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

	private RatingBar ratingBar1;
	private CircleImageView imageView1;
	OrderCRSPreocessGridViewAdapter adapter ;

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

	}

	private void init() {
		
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
		if (nursebase.getImage() != null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + nursebase.getImage(),
					imageView1, MyApplication.options_photo);
		}
		if (intent.getIntExtra("CRS_Project", 0) != 0) {
			switch (intent.getIntExtra("CRS_Project", 0)) {
			case 1:
				project_name.setText("产前开奶");
				break;
			case 2:
				project_name.setText("产后无痛点穴开奶");
				break;
			case 3:
				project_name.setText("催乳");
				break;
			case 4:
				project_name.setText("消淤");
				break;
			case 5:
				project_name.setText("乳腺小叶增生养护");
				break;

			case 6:
				project_name.setText("产后满月汗蒸排毒");
				break;
			case 7:
				project_name.setText("产后经络调理消月子病");
				break;

			}
		}
		begain_sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EasyMotherUtils.goActivity(OrderCRSProcess.this, OrderCRSProcess1.class);

			}
		});

		adapter = new OrderCRSPreocessGridViewAdapter(this, EasyMotherUtils.getTime(0),
				R.layout.crs_gridview_item,orders);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ViewHolder holder=(ViewHolder) arg1.getTag();
				TextView textView=holder.getView(R.id.time);
				if ("flag".equals(textView.getTag())) {
					textView.setTag(null);
					isChoose=false;
					arg1.setBackgroundColor(getResources().getColor(R.color.white));
					if (clicktimes%2==0) {
						adapter.notifyDataSetChanged();
					}
					return;
				}else {
					if (!isChoose) {
						textView.setTag("flag");
						//如果不是选择状态并且点击了两次，那么选择状态变为true
						if (!isChoose&&clicktimes%2==0) {
							isChoose=true;
						}
					}
					
				}
				arg1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
				if (clicktimes%2==0) {
					adapter.notifyDataSetChanged();
				}
				clicktimes++;
			}
		});

	}

	@Override
	public void onClick(View v) {
		clearState();
		switch (v.getId()) {
		case R.id.day1:
			day1.setTextColor(getResources().getColor(R.color.white));
			day1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.notifyDataSetChanged();
			break;

		case R.id.day2:
			day2.setTextColor(getResources().getColor(R.color.white));
			day2.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.notifyDataSetChanged();
			break;
		case R.id.day3:
			day3.setTextColor(getResources().getColor(R.color.white));
			day3.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			adapter.notifyDataSetChanged();
			break;
		case R.id.day4:
			day4.setTextColor(getResources().getColor(R.color.white));
			day4.setBackgroundColor(getResources().getColor(R.color.lightredwine));
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
