package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.Order;
import com.easymother.bean.OrderListBean;
import com.easymother.bean.OrderListResult;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.my.CollectListActivity;
import com.easymother.main.my.OrderDetailActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderListActivity extends Activity {
	private MyListview ysListview;// 月嫂列表
	private MyListview yysListview;// 育婴师列表
	private MyListview crsListview;// 催乳师列表
	private PullToRefreshScrollView scrollView;

	private TextView title;// 标题
	private ImageView back;// 返回键

	private TextView ys_tv;// 没有月嫂时显示
	private TextView yys_tv;// 没有育婴师时显示
	private TextView crs_tv;// 没有催乳师时显示

	private List<OrderListBean> yuesaoList;
	private List<OrderListBean> yuyingshiList;
	private List<OrderListBean> cuirushiList;
	protected int pageNo=1;
	private TextView notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mywishlist);
		EasyMotherUtils.initTitle(this, "我的订单", false);
		findView();
		init();

	}

	private void findView() {
		scrollView = (PullToRefreshScrollView) findViewById(R.id.wish_pulltoreflash);
		title = (TextView) findViewById(R.id.title);
		back = (ImageView) findViewById(R.id.back);

		ys_tv = (TextView) findViewById(R.id.ys_tv);
		yys_tv = (TextView) findViewById(R.id.yys_tv);
		crs_tv = (TextView) findViewById(R.id.crs_tv);

		ysListview = (MyListview) findViewById(R.id.wish_ys_list);
		yysListview = (MyListview) findViewById(R.id.wish_yys_list);
		crsListview = (MyListview) findViewById(R.id.wish_crs_list);
	}

	private void init() {
		loadData();
		

	}

	private void loadData() {
		RequestParams params = new RequestParams();

		NetworkHelper.doGet(BaseInfo.ORDER_LIST, params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					OrderListResult listResult = JsonUtils.getResult(response, OrderListResult.class);
					bindData(listResult);// 绑定数据
				} else {
					Toast.makeText(OrderListActivity.this, JsonUtils.getRootResult(response).getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("订单查询失败", responseString);
			}
		});

	}

	protected void bindData(OrderListResult listResult) {
		yuesaoList = listResult.getYS();
		yuyingshiList = listResult.getYYS();
		cuirushiList = listResult.getCRS();
		
		

		// 短期月嫂和月嫂算在一起 先判断是否为空，然后判断是否有数据，若为空则不会继续判断
		if (listResult.getSHORT_YS() != null && listResult.getSHORT_YS().size() > 0 && yuesaoList != null) {
			yuesaoList.addAll(listResult.getSHORT_YS());
		}
		// 短期育婴师和育婴师算在一起
		if (listResult.getSHORT_YYS() != null && listResult.getSHORT_YYS().size() > 0 && yuyingshiList != null) {
			yuyingshiList.addAll(listResult.getSHORT_YYS());
		}

		// 如果有月嫂则不显示
		if (yuesaoList != null && yuesaoList.size() > 0) {
			ys_tv.setVisibility(View.GONE);
		}
		// 如果有育婴师则不显示
		if (yuyingshiList != null && yuyingshiList.size() > 0) {
			yys_tv.setVisibility(View.GONE);
		}
		// 如果有催乳师则不显示
		if (cuirushiList != null && cuirushiList.size() > 0) {
			crs_tv.setVisibility(View.GONE);
		}

		if (yuesaoList != null && yuesaoList.size() > 0) {
			OrderListAdapter<OrderListBean> adapter = new OrderListAdapter<OrderListBean>(this, yuesaoList, "yuesao",
					R.layout.activity_yuesao_item);
			ysListview.setAdapter(adapter);
			ysListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					OrderListAdapter<OrderListBean> ysAdapter = (OrderListAdapter<OrderListBean>) arg0.getAdapter();
					List<OrderListBean> orders = ysAdapter.getList();
					int id = orders.get(arg2).getOrderId();
					Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
					// 传递数据
					intent.putExtra("id", id);
					//// intent.putExtra("order", orders.get(arg2));
					// intent.putExtra("", orders.get(arg2));
					startActivity(intent);
				}
			});
		}

		if (yuyingshiList != null && yuyingshiList.size() > 0) {
			OrderListAdapter<OrderListBean> adapter1 = new OrderListAdapter<OrderListBean>(this, yuyingshiList,
					"yuyingshi", R.layout.activity_yuesao_item);
			yysListview.setAdapter(adapter1);

			yysListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					OrderListAdapter<OrderListBean> ysAdapter = (OrderListAdapter<OrderListBean>) arg0.getAdapter();
					List<OrderListBean> orders = ysAdapter.getList();
					int id = orders.get(arg2).getOrderId();

					Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
					// 传递数据
					intent.putExtra("id", id);
					intent.putExtra("order", orders.get(arg2));
					startActivity(intent);
				}
			});
		}

		if (cuirushiList != null && cuirushiList.size() > 0) {
			OrderListAdapter<OrderListBean> adapter2 = new OrderListAdapter<OrderListBean>(this, cuirushiList,
					"cuirushi", R.layout.activity_yuesao_item);
			crsListview.setAdapter(adapter2);
			crsListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					OrderListAdapter<OrderListBean> ysAdapter = (OrderListAdapter<OrderListBean>) arg0.getAdapter();
					List<OrderListBean> orders = ysAdapter.getList();
					int id = orders.get(arg2).getOrderId();

					Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
					// 传递数据
					intent.putExtra("id", id);
					// intent.putExtra("order", orders.get(arg2));
					startActivity(intent);
				}
			});
		}

		// OrderListAdapter adapter=new OrderListAdapter(this, list,
		// R.layout.activity_yuesao_item);
		// ysListview.setAdapter(adapter);
		// yysListview.setAdapter(adapter);
		// crsListview.setAdapter(adapter);
		// 设置listview加载动画
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		ysListview.setLayoutAnimation(controller);
		ysListview.setLayoutAnimation(controller);
		yysListview.setLayoutAnimation(controller);
		yysListview.startLayoutAnimation();
		crsListview.setLayoutAnimation(controller);
		crsListview.startLayoutAnimation();
		// OnItemClickLisenter lisenter=new OnItemClickLisenter();
		// ysListview.setOnItemClickListener(lisenter);
		// crsListview.setOnItemClickListener(lisenter);
		// yysListview.setOnItemClickListener(lisenter);

	}
	// private class OnItemClickLisenter implements OnItemClickListener{
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// EasyMotherUtils.goActivity(OrderListActivity.this,
	// OrderDetailActivity.class);
	//
	// }
	//
	//
	//
	// }

}
