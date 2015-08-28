package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.CuiRuShi;
import com.easymother.bean.WishListResult;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.my.OrderDetailActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListActivity extends Activity {
	private MyListview ysListview;// 月嫂列表
	private MyListview yysListview;// 育婴师列表
	private MyListview crsListview;// 催乳师列表

	private TextView title;// 标题
	private ImageView back;// 返回键
	
	private TextView ys_tv;// 没有月嫂时显示
	private TextView yys_tv;// 没有育婴师时显示
	private TextView crs_tv;// 没有催乳师时显示
	
	private List<YueSao> yuesaoList;
	private List<YuYingShi> yuyingshiList;
	private List<CuiRuShi> cuirushiList;
	
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
		
		
        RequestParams params=new RequestParams();
		
		NetworkHelper.doGet(BaseInfo.WISH_LIST, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				 WishListResult listResult=JsonUtils.getWishListResult(response);
				 bindData(listResult);//绑定数据
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
		
		protected void bindData(WishListResult listResult) {
			yuesaoList=listResult.getYS();
			yuyingshiList=listResult.getYYS();
			cuirushiList=listResult.getCRS();
			
			//短期月嫂和月嫂算在一起
			if (listResult.getSHORT_YS()!=null && yuesaoList!=null) {
				yuesaoList.addAll(listResult.getSHORT_YS());
			}
			//短期育婴师和育婴师算在一起
			if (listResult.getSHORT_YYS()!=null && yuyingshiList!=null) {
				yuyingshiList.addAll(listResult.getSHORT_YYS());
			}
			
			//如果有月嫂则不显示
			if (yuesaoList!=null) {
				ys_tv.setVisibility(View.GONE);
			}
			//如果有育婴师则不显示
			if (yuyingshiList!=null) {
				yys_tv.setVisibility(View.GONE);
			}
			//如果有催乳师则不显示
			if (cuirushiList!=null) {
				crs_tv.setVisibility(View.GONE);
			}
			
			if (yuesaoList!=null) {
				OrderListAdapter<YueSao> adapter=new OrderListAdapter<YueSao>(this, yuesaoList, "yuesao", R.layout.activity_yuesao_item);
				ysListview.setAdapter(adapter);
				ysListview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						@SuppressWarnings("unchecked")
						OrderListAdapter<YueSao> ysAdapter=(OrderListAdapter<YueSao>) arg0.getAdapter();
						List<YueSao> baseBeans=ysAdapter.getList();
						int id=baseBeans.get(arg2).getNurseId();
						Intent intent=new Intent(OrderListActivity.this,OrderDetailActivity.class);
						//传递数据
						
						startActivity(intent);
					}
				});
			}
			
			if (yuyingshiList!=null) {
				OrderListAdapter<YuYingShi> adapter1=new OrderListAdapter<YuYingShi>(this, yuyingshiList,"yuyingshi", R.layout.activity_yuesao_item);
				yysListview.setAdapter(adapter1);
				
				yysListview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						@SuppressWarnings("unchecked")
						OrderListAdapter<YuYingShi> ysAdapter=(OrderListAdapter<YuYingShi>) arg0.getAdapter();
						List<YuYingShi> baseBeans=ysAdapter.getList();
						int id=baseBeans.get(arg2).getNurseId();
						
						Intent intent=new Intent(OrderListActivity.this,OrderDetailActivity.class);
						//传递数据
						
						startActivity(intent);
					}
				});
			}
			
			if (cuirushiList!=null) {
				OrderListAdapter<CuiRuShi> adapter2=new OrderListAdapter<CuiRuShi>(this, cuirushiList,"cuirushi", R.layout.activity_yuesao_item);
				crsListview.setAdapter(adapter2);
				crsListview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						@SuppressWarnings("unchecked")
						OrderListAdapter<CuiRuShi> ysAdapter=(OrderListAdapter<CuiRuShi>) arg0.getAdapter();
						List<CuiRuShi> baseBeans=ysAdapter.getList();
						int id=baseBeans.get(arg2).getNurseId();
						
						Intent intent=new Intent(OrderListActivity.this,OrderDetailActivity.class);
						//传递数据
						
						startActivity(intent);
					}
				});
			}
		
//		OrderListAdapter adapter=new OrderListAdapter(this, list, R.layout.activity_yuesao_item);
//		ysListview.setAdapter(adapter);
//		yysListview.setAdapter(adapter);
//		crsListview.setAdapter(adapter);
		//设置listview加载动画
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		ysListview.setLayoutAnimation(controller);
		ysListview.setLayoutAnimation(controller);
		yysListview.setLayoutAnimation(controller);
		yysListview.startLayoutAnimation();
		crsListview.setLayoutAnimation(controller);
		crsListview.startLayoutAnimation();
		OnItemClickLisenter lisenter=new OnItemClickLisenter();
		ysListview.setOnItemClickListener(lisenter);
		crsListview.setOnItemClickListener(lisenter);
		yysListview.setOnItemClickListener(lisenter);
		

	}
	private class OnItemClickLisenter implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			EasyMotherUtils.goActivity(OrderListActivity.this, OrderDetailActivity.class);
			
		}

		
		
	}

}
