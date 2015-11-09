package com.easymother.main.my;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.PayResult;
import com.alipay.sdk.pay.demo.SignUtils;
import com.easymother.bean.OrderListBean;
import com.easymother.bean.OrderPayBean;
import com.easymother.configure.BaseInfo;
import com.easymother.main.homepage.OrderListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PayListActivity extends Activity {
	
	private List<OrderListBean> list;
	private OrderPayBean orderPayBean;//传递的订单
	private final int COMMENT=0;//评价 
	
	//支付部分
	
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
				
//				Intent intent=new Intent(PayListActivity.this,CommentActivity.class);
//				intent.putExtra("order", orderPayBean);
//				intent.putExtra("type", "order");
//				startActivityForResult(intent, COMMENT);
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(PayListActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
						if (!"10".equals(orderPayBean.getStatus())) {
							Intent intent=new Intent(PayListActivity.this,CommentActivity.class);
							intent.putExtra("order", orderPayBean);
							intent.putExtra("type", "order");
							startActivityForResult(intent, COMMENT);
						}else {
							loadData();
						}
						
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(PayListActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(PayListActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(PayListActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
				}
			};
		};
	
	//-----------
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_list);
		EasyMotherUtils.initTitle(this,"支付订单", false);
		
		findView();
		init();
		
		
	}
	private void findView() {
		listview=(ListView) findViewById(R.id.listview);
	}
	private void init() {
		loadData();
	}
	
	
	public void loadData() {
		NetworkHelper.doGet(BaseInfo.PAY_ORDER, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					list=JsonUtils.getResultList(response, OrderListBean.class);
					bindData(list);
				}
			}
		});
		
	}
	/**
	 * 绑定数据
	 * @param list
	 */
	protected void bindData(final List<OrderListBean> list) {
		orderPayBean=new OrderPayBean();
		if (list!=null) {
			OrderListAdapter<OrderListBean> adapter=new OrderListAdapter<>(this, list, null, R.layout.activity_yuesao_item);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					pay(list.get(arg2),arg2);
					orderPayBean.setAge(PayListActivity.this.list.get(arg2).getAge());
					orderPayBean.setRealName(PayListActivity.this.list.get(arg2).getRealName());
					orderPayBean.setJob(PayListActivity.this.list.get(arg2).getJob());
					orderPayBean.setSeniority(PayListActivity.this.list.get(arg2).getSeniority());
					orderPayBean.setHometown(PayListActivity.this.list.get(arg2).getHometown());
					orderPayBean.setPayMoney(PayListActivity.this.list.get(arg2).getPayMoney());
					orderPayBean.setMarketPrice(PayListActivity.this.list.get(arg2).getMarketPrice());
					orderPayBean.setNurseId(PayListActivity.this.list.get(arg2).getNurseId());
					orderPayBean.setStatus(PayListActivity.this.list.get(arg2).getStatus());
				}
			});
		}
		
		
	}
	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(OrderListBean order,final int postion) {
		
		// 订单
		String orderInfo = getOrderInfo("本次支付费用", "护理师费用每周支付一次", ""+order.getPayMoney(),order.getOrderCode());

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
				PayTask alipay = new PayTask(PayListActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				msg.arg1=postion;
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
				PayTask payTask = new PayTask(PayListActivity.this);
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
	public String getOrderInfo(String subject, String body, String price,String orderCode) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderCode + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + "http://test1.hzlianhai.com/easyMother/app/ordernotifyurl" + "\"";
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==RESULT_OK) {
			switch (requestCode) {
			case COMMENT:
				loadData();
				break;

			default:
				break;
			}
		}
	}

	

}
