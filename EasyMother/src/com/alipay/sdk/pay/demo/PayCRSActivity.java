package com.alipay.sdk.pay.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alidao.mama.R;
import com.alipay.sdk.app.PayTask;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.Order;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.main.homepage.OrderCRSProcess3;
import com.easymother.main.homepage.OrderYSandYYSProcess5;
import com.easymother.main.homepage.OrderYSandYYSProcess8;
import com.easymother.utils.EasyMotherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PayCRSActivity extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = "2088812003704461";
	// 商户收款账号
	public static final String SELLER = "hzcq168@aliyun.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKtlqG1P7LANiHKhekiqyyuUXR9ieTlIYuzjeQ9qKTtY2JDG1PghhVYxfkPrZnOuFuzMa/4gM9C0M7lTEcvwGeXZpieaaHS2OXk9K2OW96TGhkhOOo1tIpzR/YVPCvtVWNbLi4yEFl+l+wcwS9k/bHDTEaUZRcAeA+tj1p7fIQtJAgMBAAECgYEApQwWnXT6gin1/BS9UZ6OWN/csK09+MhY4q7F0+8x10FeAkRFrs9wu6ibY0IafUjmMI9FawiNfOsaZ9qzZ4RFGnnSfSDXyOGP9ZRFWU5OzSEc6JauH+Flcqj2ALBoUXwwzGF28BOuz51kB17S3Su2s+aE5+Lny3Fn2WnoyMREs1ECQQDiVpYsPeEzmdqdfNsxY1YSTbUmJi5SNW09n++qblKLU7JToYIKDVTyvyQmQLeFeEssybaxt70VvmWDIoNO9EH9AkEAwdvacxdajD8BijkrJuslixWKT6GguHhyzDdqH53KoVcy08em6X7ZRGJVyQmcT4haFjANn7fItBYdnXyqRAI6PQJAUd0vg+D9y0QipkO6pCphv00L8DhvGJzqXhey/9nXUT18inMoLaqUxxgLYyqVNllEF3b99VmXsn2jCcM56xy9oQJAD2iDF2y+xLDpVWwv0tsT09c1dBjbpm3kafjplXO8PFCq+IxA5wQvDrFmMiFybBvhVSwimRRJfo7XccIL4nXu3QJAGbLfOhK/GPE1ajHpy05HOYNpbrfDXjxIR7dwOInyNEwlrcrB+6cYpl21lfjdid9bP4d2ia1WOJXSLitLV2NxBw==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayCRSActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					intent.setClass(PayCRSActivity.this, OrderCRSProcess3.class);
					startActivity(intent);
					PayCRSActivity.this.finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayCRSActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayCRSActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayCRSActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	private Order order;
	private TextView user_name;
	private TextView user_phone;
	private TextView user_address;
	private TextView time;
	private TextView nurse_name;
	private TextView type;
	private TextView money;
	private CircleImageView photo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.process_duanqi_order_next3);
		EasyMotherUtils.initTitle(this, "支付费用", false);
		MyApplication.destoryActivity("YSprocess");
		MyApplication.addActivityToMap(this, "YSprocess1");
		intent=getIntent();
		nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
		order=(Order) intent.getParcelableExtra("order");
		
		findView();
		init();
	}


	private void findView() {
		user_name=(TextView) findViewById(R.id.user_name);
		user_phone=(TextView) findViewById(R.id.user_phone);
		user_address=(TextView) findViewById(R.id.user_address);
		nurse_name=(TextView) findViewById(R.id.nurse_name);
		type=(TextView) findViewById(R.id.type);
		photo=(CircleImageView) findViewById(R.id.circleImageView1);
		time=(TextView) findViewById(R.id.time);
		type=(TextView) findViewById(R.id.type);
		money=(TextView) findViewById(R.id.money);
		photo=(CircleImageView) findViewById(R.id.photo);
		
	}
	private void init() {
		user_name.setText(intent.getStringExtra("userName"));
		user_phone.setText(intent.getStringExtra("mobile"));
		user_address.setText(intent.getStringExtra("address"));
		if (order!=null) {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startTime=order.getRealHireStartTime();
			String startTimeString=format.format(startTime);
			Date endTime=order.getRealHireEndTime();
			String endTimeString=format.format(endTime);
			time.setText(startTimeString+"至"+endTimeString);
			if (order.getPrice()!=null) {
				money.setText("￥"+order.getPrice()+"元");
			}
			
		}
		
		if (nursebase.getImage()!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursebase.getImage(), photo, MyApplication.options_photo);
		}
		if (nursejob.getNurseName()!=null) {
			nurse_name.setText(nursejob.getNurseName());
		}
		if (nursejob.getJob()!=null) {
			type.setText("催乳师");
		}
		
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		
		// 订单
		String orderInfo = getOrderInfo("催乳师定金", "催乳师定金", order.getPrice()+"");

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayCRSActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayCRSActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		orderInfo += "&out_trade_no=" + "\"" + order.getOrderCode() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + BaseInfo.BASE_URL+"app/ordernotifyurl"+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
