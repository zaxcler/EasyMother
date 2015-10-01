 package com.easymother.main.my;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.CollectionListResult;
import com.easymother.bean.ForumBean;
import com.easymother.bean.NewsBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.community.ArticleActivity;
import com.easymother.main.community.HuLiShiReplyListActivity;
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class CollectListActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
	private MyListview listview1;//
	protected int pageNo=1;
	private TextView notice;
//	private SwipeRefreshLayout swipelayout;//下拉刷新
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
//		swipelayout=(SwipeRefreshLayout) findViewById(R.id.pulltoreflash);
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
//		swipelayout.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				pageNo++;
//				loadData();
//				scrollView.onRefreshComplete();
//			}
//		});
//		
		
		
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
			listview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					final NewsBean bean=(NewsBean) arg0.getAdapter().getItem(arg2);
					AlertDialog.Builder builder=new AlertDialog.Builder(CollectListActivity.this);
					builder.setTitle("是否删除");
					builder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							RequestParams params=new RequestParams();
							params.put("id", bean.getId());
							NetworkHelper.doGet(BaseInfo.YSYQ_LIST_DELETE,params,  new JsonHttpResponseHandler(){
								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									super.onSuccess(statusCode, headers, response);
									Log.e("删除", "-----response"+response.toString());
									if (JsonUtils.getRootResult(response).getIsSuccess()) {
										Toast.makeText(CollectListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
										loadData();
									}else {
										Toast.makeText(CollectListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
									}
								}
								@Override
								public void onFailure(int statusCode, Header[] headers, String responseString,
										Throwable throwable) {
									super.onFailure(statusCode, headers, responseString, throwable);
									Log.e("删除", "-----response"+responseString.toString());
								}
							});
							dialog.dismiss();
						}
					
					});
					builder.setNegativeButton("取消", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					AlertDialog alertDialog=builder.create();
					alertDialog.setCancelable(false);
					alertDialog.show();
					return true;
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
			listview1.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					final ForumBean bean=(ForumBean) arg0.getAdapter().getItem(arg2);
					AlertDialog.Builder builder=new AlertDialog.Builder(CollectListActivity.this);
					builder.setTitle("是否删除");
					builder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							RequestParams params=new RequestParams();
							params.put("id", bean.getId());
							NetworkHelper.doGet(BaseInfo.COLLECTION_DELETE ,params,new JsonHttpResponseHandler(){
								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									super.onSuccess(statusCode, headers, response);
									if (JsonUtils.getRootResult(response).getIsSuccess()) {
										Toast.makeText(CollectListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
										loadData();
									}else {
										Toast.makeText(CollectListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
									}
								}
								@Override
								public void onFailure(int statusCode, Header[] headers, String responseString,
										Throwable throwable) {
									super.onFailure(statusCode, headers, responseString, throwable);
									Log.e("删除", "-----response"+responseString.toString());
								}
							});
							dialog.dismiss();
						}
					
					});
					builder.setNegativeButton("取消", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					AlertDialog alertDialog=builder.create();
					alertDialog.setCancelable(false);
					alertDialog.show();
					return true;
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
