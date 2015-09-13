 package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.CollectionListResult;
import com.easymother.bean.ForumBean;
import com.easymother.bean.NewsBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

public class CollectListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	private MyListview listview1;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
		EasyMotherUtils.initTitle(this,"我的收藏", false);
		findView();
		init();
		
		
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
		listview1=(MyListview) findViewById(R.id.listview1);
		
	}
	private void init() {
		
		loadDate();
		
		
		
		
	}
	/**
	 * 加载数据
	 */
	private void loadDate() {
		NetworkHelper.doGet(BaseInfo.COLLECTION_LIST, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					CollectionListResult result=JsonUtils.getResult(response, CollectionListResult.class);
					bindData(result);
				}else {
					Toast.makeText(CollectListActivity.this, "获取数据失败", 0).show();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(CollectListActivity.this, "连接服务器失败", 0).show();
			}
		});
	}
	protected void bindData(CollectionListResult result) {
		
		List<NewsBean> news=result.getNews();
		List<ForumBean> forumBeans=result.getForum();
		CollectionListAdapter adapter=new CollectionListAdapter(this, news, R.layout.activity_mypage_collection_item);
		CollectionListAdapter2 adapter2=new CollectionListAdapter2(this, forumBeans, R.layout.activity_mypage_collection_item);
		LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		listview.setAdapter(adapter);
		listview1.setAdapter(adapter2);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(controller);
		listview.startLayoutAnimation();
		listview1.setLayoutAnimation(controller);
		listview1.startLayoutAnimation();
	}

}
