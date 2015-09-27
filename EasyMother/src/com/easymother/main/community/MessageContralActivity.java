package com.easymother.main.community;

import java.util.List;
import org.apache.http.Header;
import org.json.JSONObject;
import com.easymother.bean.ForumPost;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.homepage.LetterListActivity;
import com.easymother.main.my.CollectListActivity;
import com.easymother.main.my.TopicListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageContralActivity extends Activity {
	private MyListview listView;//消息
	private PullToRefreshScrollView scrollView;
	private Intent intent;
	private String flag;
	private String url;//访问连接
	protected int pageNo=1;
	private TextView notice;
	private MessageAdapter adapter;
	private boolean canload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_contral);
		intent=getIntent();
		flag=intent.getStringExtra("flag");
		if ("unread".equals(flag)) {
			EasyMotherUtils.initTitle(this, "消息管理", false);
			url=BaseInfo.UNREAD;
		}else if ("reply_list".equals(flag)){
			EasyMotherUtils.initTitle(this, "我的回复", false);
			url=BaseInfo.REPLY_LIST;
		}
		findView();
		init();
	}

	private void findView() {
		listView=(MyListview) findViewById(R.id.listview);
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
	}

	private void init() {
		loadData();
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					pageNo++;
					loadData();
				}
				scrollView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canload) {
					pageNo++;
					loadData();
				}
				scrollView.onRefreshComplete();
			}
		});
	}

	//加载数据
	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("pageNo", pageNo);
		NetworkHelper.doGet(url, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<ForumPost> forumPosts=JsonUtils.getResultList(response, ForumPost.class);
					bindDate(forumPosts);
				}else {
					pageNo=1;
					Toast.makeText(MessageContralActivity.this, "未登录！", 0).show();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				pageNo=1;
			}
		});
		
	}


	protected void bindDate(List<ForumPost> forumPosts) {
		if (forumPosts!=null) {
			if (adapter == null) {
				adapter = adapter=new MessageAdapter(this, forumPosts, R.layout.message_control_item);
			}
			
			if (forumPosts.size()==0 ) {
				if (pageNo==1) {
					if (notice == null) {
						notice = new TextView(MessageContralActivity.this);
						notice.setText("没有消息哦！");
						notice.setGravity(Gravity.CENTER_HORIZONTAL);
						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						notice.setLayoutParams(params);
						notice.setBackgroundColor(getResources().getColor(R.color.background));
						notice.setTextColor(getResources().getColor(R.color.boroblacktext));
						listView.addFooterView(notice);
					}
				}
				canload=false;
				pageNo=1;
			}
			if (forumPosts.size() > 0) {
				
				if (pageNo>1) {
					adapter.addList(forumPosts);
					adapter.notifyDataSetChanged();
					
				}
				if (notice != null) {
					listView.removeFooterView(notice);
				}
				canload=true;
				pageNo++;
				
			}
			LayoutAnimationController controller = new LayoutAnimationController(
					AnimationUtils.loadAnimation(MessageContralActivity.this, R.anim.zoom_right_in));
			controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
			listView.setLayoutAnimation(controller);
			listView.startLayoutAnimation();
			listView.setAdapter(adapter);
		
		
		}
//			if (adapter==null) {
//				adapter=new MessageAdapter(this, forumPosts, R.layout.message_control_item);
//			}
//			LayoutAnimationController controller = new LayoutAnimationController(
//					AnimationUtils.loadAnimation(MessageContralActivity.this, R.anim.zoom_right_in));
//			controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//			listView.setLayoutAnimation(controller);
//			listView.startLayoutAnimation();
//			if (forumPosts.size()>0) {
//				if (notice!=null) {
//					listView.removeFooterView(notice);
//				}
//				//表示第一次刷新
//				if (pageNo==1) {
//					listView.setAdapter(adapter);
//				}
//				//pageNo大于1表示刷新
//				if (pageNo > 1) {
//					adapter.addList(forumPosts);
//					adapter.notifyDataSetChanged();
//				}
//				pageNo++;
//			}else {
//				if (pageNo==1) {
//					if (notice == null) {
//						notice = new TextView(MessageContralActivity.this);
//						notice.setText("你没有发布过话题！");
//						notice.setGravity(Gravity.CENTER_HORIZONTAL);
//						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
//								LayoutParams.WRAP_CONTENT);
//						notice.setLayoutParams(params);
//						notice.setBackgroundColor(getResources().getColor(R.color.background));
//						notice.setTextColor(getResources().getColor(R.color.boroblacktext));
//						listView.addFooterView(notice);
//					}
//				}
//				pageNo = 1;
//			}
//			
////			if (forumPosts.size()==0 && pageNo==1 ) {
////				if (notice==null) {
////					notice=new TextView(MessageContralActivity.this);
////					notice.setText("没有消息哦！");
////					notice.setGravity(Gravity.CENTER_HORIZONTAL);
////					AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
////					notice.setLayoutParams(params);
////					notice.setBackgroundColor(getResources().getColor(R.color.background));
////					notice.setTextColor(getResources().getColor(R.color.boroblacktext));
////					listView.addFooterView(notice);
////					pageNo=1;
////				}
////			}else {
////				if (notice!=null) {
////					listView.removeFooterView(notice);
////				}
////				adapter.addList(forumPosts);
////				pageNo++;
////			}
////			listView.setAdapter(adapter);
//		}
		
	}
	
	
	
}
