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
import com.easymother.main.community.ArticleActivity;
import com.easymother.main.community.HuLiShiReplyListActivity;
import com.easymother.main.homepage.LetterListActivity;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CollectListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	private MyListview listview1;//
	protected int pageNo=1;
	private TextView notice;
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
		
		loadData();
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollView.onRefreshComplete();
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageNo++;
				loadData();
				scrollView.onRefreshComplete();
			}
		});
		
		
		
		
	}
	/**
	 * 加载数据
	 */
	private void loadData() {
		NetworkHelper.doGet(BaseInfo.COLLECTION_LIST, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					CollectionListResult result=JsonUtils.getResult(response, CollectionListResult.class);
					bindData(result);
				}else {
					Toast.makeText(CollectListActivity.this, "未登录！", 0).show();
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
		if (result!=null) {
			
			final List<NewsBean> news=result.getNews();
			final List<ForumBean> forumBeans=result.getForum();
			CollectionListAdapter adapter=new CollectionListAdapter(this, news, R.layout.activity_mypage_collection_item);
			CollectionListAdapter2 adapter2=new CollectionListAdapter2(this, forumBeans, R.layout.activity_mypage_collection_item);
			LayoutAnimationController controller=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
			//刷新模块
			
			if (news.size()>0 || forumBeans.size()>0 ) {
				if (notice!=null) {
					listview1.removeFooterView(notice);
				}
				pageNo++;
			}else {
				if (notice==null) {
					notice=new TextView(CollectListActivity.this);
					notice.setText("没有收藏过内容哦！");
					notice.setGravity(Gravity.CENTER_HORIZONTAL);
					AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					notice.setLayoutParams(params);
					notice.setBackgroundColor(getResources().getColor(R.color.background));
					notice.setTextColor(getResources().getColor(R.color.boroblacktext));
					listview1.addFooterView(notice);
					pageNo=1;
				}
			}
				
			
			
			listview.setAdapter(adapter);
			listview1.setAdapter(adapter2);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent=new Intent();
					intent.putExtra("id", news.get(arg2).getNewsId());
					intent.setClass(CollectListActivity.this, ArticleActivity.class);
					startActivity(intent);
				}
			});
			listview1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent=new Intent();
					intent.putExtra("id", forumBeans.get(arg2).getPostId());
					intent.setClass(CollectListActivity.this, HuLiShiReplyListActivity.class);
					startActivity(intent);
				}
			});
			controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
			listview.setLayoutAnimation(controller);
			listview.startLayoutAnimation();
			listview1.setLayoutAnimation(controller);
			listview1.startLayoutAnimation();
		}
		
		
	}

}
