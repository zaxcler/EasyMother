package com.easymother.main.my;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.Order;
import com.easymother.bean.OrderDetailResult;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyPopupWindow1;
import com.easymother.customview.MyPopupWindow1.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {
	private MyPopupWindow1 popupWindow;
	private TextView order_no;// 订单号
	private TextView user_address;// 用户地址
	private TextView user_name;// 用户姓名
	private TextView user_phone;// 用户电话

	private TextView nurse_address;// 护理师地址
	private TextView nurse_name;// 护理师姓名
	private TextView pay_state;// 付款状态
	private TextView nurse_type;// 护理师类型
	private TextView work_express;// 工作经验
	private TextView nurse_age;// 护理师年龄
	private TextView nurse_area;// 护理师地区
	private TextView price;// 价格
	private TextView marketprice;// 市场价格
	private TextView startTime;// 开始时间
	private TextView endTime;// 开始时间
	private TextView paybyweek;// 每次支付
	private TextView order_time;// 下单时间
	private ImageView nurse_image;// 护理师图片

	private Intent intent;
	private int id;
	private String job;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_order_status);

		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(final ImageView imageView) {

				imageView.setImageDrawable(getResources().getDrawable(R.drawable.meun));
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// 根据传过来的状态初始化右边的按钮弹出框
						popupWindow.showAsDropDown(imageView, 0, 0);
					}
				});
			}
		});
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		EasyMotherUtils.initTitle(this, "订单状态", true);
		findView();
		init();
		// MyPopupWindow
	}

	private void findView() {
		order_no = (TextView) findViewById(R.id.order_no);
		user_address = (TextView) findViewById(R.id.user_address);
		user_name = (TextView) findViewById(R.id.user_name);
		user_phone = (TextView) findViewById(R.id.user_phone);
		nurse_address = (TextView) findViewById(R.id.nurse_address);
		nurse_name = (TextView) findViewById(R.id.nurse_name);
		pay_state = (TextView) findViewById(R.id.pay_state);
		work_express = (TextView) findViewById(R.id.work_express);
		nurse_age = (TextView) findViewById(R.id.nurse_age);
		nurse_area = (TextView) findViewById(R.id.nurse_area);
		price = (TextView) findViewById(R.id.price);
		marketprice = (TextView) findViewById(R.id.marketprice);
		startTime = (TextView) findViewById(R.id.startTime);
		endTime = (TextView) findViewById(R.id.endTime);
		paybyweek = (TextView) findViewById(R.id.paybyweek);
		order_time = (TextView) findViewById(R.id.order_time);

	}

	private void init() {
		RequestParams params = new RequestParams();
		params.put("id", id + "");
		// params.put("id", "10");
		NetworkHelper.doGet(BaseInfo.ORDER_DETAIL, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					OrderDetailResult order = JsonUtils.getOrder(response);
					// 绑定数据
					bindData(order);
					Log.e("订单详情onSuccess", response.toString());
				} else {
					Toast.makeText(OrderDetailActivity.this, JsonUtils.getRootResult(response).getMessage(), 0).show();
					Log.e("订单详情onSuccess", response.toString());
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("订单详情onFailure", responseString);
			}
		});

	}

	/*
	 * 绑定数据到界面
	 */
	protected void bindData(OrderDetailResult orderDetail) {
		if (orderDetail.getOrder().getOrderCode() != null) {
			order_no.setText(orderDetail.getOrder().getOrderCode());
		}
		if (orderDetail.getOrder().getUserAddress() != null) {
			user_address.setText(orderDetail.getOrder().getUserAddress());
		}
		if (orderDetail.getOrder().getUserName() != null) {
			user_name.setText(orderDetail.getOrder().getUserName());
		}
		if (orderDetail.getOrder().getUserMobile() != null) {
			user_phone.setText(orderDetail.getOrder().getUserMobile());
		}

		NurseBaseBean baseBean = orderDetail.getNurseInfo();
		if (baseBean != null) {
			if (baseBean.getRealName() != null) {
				nurse_name.setText(baseBean.getRealName());
			}
			if (baseBean.getJob() != null) {
				if ("YS".equals(baseBean.getJob())) {
					nurse_name.setText("月嫂");
					job="YS";
				}
			}
			if (baseBean.getJob() != null) {
				if ("YYS".equals(baseBean.getJob())) {
					nurse_name.setText("育婴师");
					job="YYS";
				}
			}
			if (baseBean.getJob() != null) {
				if ("CRS".equals(baseBean.getJob())) {
					nurse_name.setText("催乳师");
					job="CRS";
				}
			}
			if (baseBean.getJob() != null) {
				if ("SHORT_YS".equals(baseBean.getJob())) {
					nurse_name.setText("短期月嫂");
					job="SHORT_YS";
				}
			}
			if (baseBean.getJob() != null) {
				if ("SHORT_YYS".equals(baseBean.getJob())) {
					nurse_name.setText("短期育婴师");
					job="SHORT_YYS";
				}
			}
			if (baseBean.getSeniority() != null) {
				work_express.setText("从业" + baseBean.getSeniority() + "年");
			}
			if (baseBean.getAge() != null) {
				nurse_age.setText(+baseBean.getAge() + "岁");
			}
			if (baseBean.getHometown() != null) {
				nurse_area.setText(baseBean.getHometown());
			}
			if (baseBean.getCurrentAddress() != null) {
				nurse_address.setText(baseBean.getCurrentAddress());
			}
			
			if (baseBean.getImage() != null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + baseBean.getImage(),
						nurse_image,MyApplication.options_image);
			}

		}
		if (orderDetail.getNurseJob()!=null) {
			if (orderDetail.getNurseJob().getPrice() != null) {
				price.setText(baseBean.getPrice()+"元/26天");
			}else {
				price.setText("");
			}
			if (orderDetail.getNurseJob().getMarketPrice() != null) {
				marketprice.setText("市场价："+baseBean.getMarketPrice());
				marketprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}else {
				marketprice.setText("市场价：");
			}
		}

		if (orderDetail.getOrder().getStatus() != null) {
			if ("20".equals(orderDetail.getOrder().getStatus())) {
				pay_state.setText("等待服务");
			}else if ("10".equals(orderDetail.getOrder().getStatus())) {
				pay_state.setText("等待付款");
			}else if ("40".equals(orderDetail.getOrder().getStatus())) {
				pay_state.setText("服务结束");
			}else {
				pay_state.setText("服务中");
			}
		}
		

		if (orderDetail.getOrder().getRealHireStartTime() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String starttime = format.format(orderDetail.getOrder().getRealHireStartTime());
			startTime.setText(starttime);
		}
		if (orderDetail.getOrder().getRealHireEndTime() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String endtime = format.format(orderDetail.getOrder().getRealHireEndTime());
			endTime.setText(endtime);
		}

		if (orderDetail.getOrder().getCreateTime() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
			String createtime = format.format(orderDetail.getOrder().getCreateTime());
			order_time.setText("下单时间：" + createtime);
		}
		if (orderDetail.getOrder().getAllServerPrice() != null || orderDetail.getOrder().getRealHireStartTime() != null
				|| orderDetail.getOrder().getRealHireEndTime() != null) {
			Double allPrice = orderDetail.getOrder().getAllServerPrice();
			int day = TimeCounter.countTimeOfDay(orderDetail.getOrder().getRealHireStartTime(),
					orderDetail.getOrder().getRealHireEndTime());
			// 转换成两位小数点
			DecimalFormat format = new DecimalFormat(".##");
			String pay_week = format.format((allPrice / day));
			paybyweek.setText(pay_week + "元");
		}
		initPopupWindow(orderDetail.getOrder().getStatus(), orderDetail.getOrder());
//		initPopupWindow("WORKING", orderDetail.getOrder());

	}

	private void initPopupWindow(final String flag, Order order) {
		// 根据flag判断resource
		
			popupWindow = new MyPopupWindow1(this, R.layout.mypage_order_status_menu);
			popupWindow.setOnMyPopupClidk(new OnMyPopupWindowsClick() {
				
				@Override
				public void onClick(View view) {
					TextView changetime = (TextView) view.findViewById(R.id.chengetime);
					TextView change = (TextView) view.findViewById(R.id.chenge);
					TextView unorder = (TextView) view.findViewById(R.id.unorder);
					TextView contact = (TextView) view.findViewById(R.id.contact);
					OnClickListener listener = new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							intent.putExtra("job", job);
							switch (v.getId()) {
							case R.id.chengetime:
								intent.putExtra("id", id);
								intent.setClass(OrderDetailActivity.this, ChangeDateActivity.class);
								startActivity(intent);
								break;

							case R.id.chenge:
								intent.putExtra("id", id);
								intent.putExtra("type", "CHANGENURSE");
								intent.setClass(OrderDetailActivity.this, ChangeNurseActivity.class);
								startActivity(intent);
								break;
							case R.id.unorder:
								intent.putExtra("id", id);
								intent.putExtra("type", "UNORDER");
								intent.setClass(OrderDetailActivity.this, ChangeNurseActivity.class);
								startActivity(intent);
								break;
							case R.id.contact:
								EasyMotherUtils.goActivity(OrderDetailActivity.this, ContactActivity.class);
								break;
							}
						}
					};
					change.setOnClickListener(listener);
					changetime.setOnClickListener(listener);
					unorder.setOnClickListener(listener);
					contact.setOnClickListener(listener);
					if ("WORKING".equals(flag)) {
						unorder.setClickable(false);
						unorder.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
					}
					
				}
			});
		

	}
}
