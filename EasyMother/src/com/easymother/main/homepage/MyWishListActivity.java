package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.CuiRuShi;
import com.easymother.bean.WishListResult;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyWishListActivity extends Activity {
	private MyListview ysListview;// 月嫂列表
	private MyListview yysListview;// 育婴师列表
	private MyListview crsListview;// 催乳师列表

	private TextView title;// 标题
	
	private TextView ys_tv;// 没有月嫂时显示
	private TextView yys_tv;// 没有育婴师时显示
	private TextView crs_tv;// 没有催乳师时显示
	
	private ImageView back;// 返回键
	
	private List<YueSao> yuesaoList;
	private List<YuYingShi> yuyingshiList;
	private List<CuiRuShi> cuirushiList;
	
//	private final String WISH_LIST="app/nursecollection/collectionlist";
//	private final String WISH_LIST="http://zaxcler.oss-cn-beijing.aliyuncs.com/testwish.txt";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mywishlist);
		EasyMotherUtils.initTitle(this, "我的心愿单", false);
		MyApplication.addActivityToMap(this, "wish");//添加到myapplication到退出的时候关闭
		findView();
		init();

	}

	private void findView() {
		title = (TextView) findViewById(R.id.title);
		ys_tv = (TextView) findViewById(R.id.ys_tv);
		yys_tv = (TextView) findViewById(R.id.yys_tv);
		crs_tv = (TextView) findViewById(R.id.crs_tv);
		
		back = (ImageView) findViewById(R.id.back);
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
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					WishListResult listResult=JsonUtils.getWishListResult(response);
					 Log.e("WishListResult", listResult.toString());
					 bindData(listResult);//绑定数据
				}else {
					Toast.makeText(MyWishListActivity.this, "未登录！", Toast.LENGTH_SHORT).show();
				}
				 
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("获取心愿单列表失败", responseString);
				Toast.makeText(MyWishListActivity.this, "连接服务器失败", 0).show();
			}
		});
		

	}

	protected void bindData(WishListResult listResult) {
		yuesaoList=listResult.getYS();
		yuyingshiList=listResult.getYYS();
		cuirushiList=listResult.getCRS();
		Log.e("月嫂数量", yuesaoList.size()+"个");
		Log.e("育婴师数量", yuyingshiList.size()+"个");
		Log.e("催乳师数量", cuirushiList.size()+"个");
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
			MyWishListAdapter<YueSao> adapter=new MyWishListAdapter<YueSao>(this, yuesaoList, "yuesao", R.layout.activity_yuesao_item);
			ysListview.setAdapter(adapter);
			ysListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					MyWishListAdapter<YueSao> ysAdapter=(MyWishListAdapter<YueSao>) arg0.getAdapter();
					List<YueSao> baseBeans=ysAdapter.getList();
					int id=baseBeans.get(arg2).getNurseId();
					
					Intent intent=new Intent(MyWishListActivity.this,YueSaoDetailActivity.class);
					//传递数据
					intent.putExtra("id", id);
					intent.putExtra("job", baseBeans.get(arg2).getJob());
					intent.putExtra("type", "wish");//打开详情时判断是从哪儿打开的
					startActivity(intent);
				}
			});
		}
		
		if (yuyingshiList!=null) {
			MyWishListAdapter<YuYingShi> adapter1=new MyWishListAdapter<YuYingShi>(this, yuyingshiList,"yuyingshi", R.layout.activity_yuesao_item);
			yysListview.setAdapter(adapter1);
			
			yysListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					MyWishListAdapter<YuYingShi> ysAdapter=(MyWishListAdapter<YuYingShi>) arg0.getAdapter();
					List<YuYingShi> baseBeans=ysAdapter.getList();
					int id=baseBeans.get(arg2).getNurseId();
					
					Intent intent=new Intent(MyWishListActivity.this,YuYingShiDetailActivity.class);
					//传递数据
					
					startActivity(intent);
				}
			});
		}
		
		if (cuirushiList!=null) {
			MyWishListAdapter<CuiRuShi> adapter2=new MyWishListAdapter<CuiRuShi>(this, cuirushiList,"cuirushi", R.layout.activity_yuesao_item);
			crsListview.setAdapter(adapter2);
			crsListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					@SuppressWarnings("unchecked")
					MyWishListAdapter<CuiRuShi> ysAdapter=(MyWishListAdapter<CuiRuShi>) arg0.getAdapter();
					List<CuiRuShi> baseBeans=ysAdapter.getList();
					int id=baseBeans.get(arg2).getNurseId();
					
					Intent intent=new Intent(MyWishListActivity.this,CuiRuShiDetailActivity.class);
					//传递数据
					
					startActivity(intent);
				}
			});
		}
		
	
		
		//listview 的item进入时的动画
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		ysListview.setLayoutAnimation(controller);
		ysListview.startLayoutAnimation();
		yysListview.setLayoutAnimation(controller);
		yysListview.startLayoutAnimation();
		crsListview.setLayoutAnimation(controller);
		crsListview.startLayoutAnimation();
		
	}

	

}
