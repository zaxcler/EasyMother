package com.easymother.main.community;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.ForumPostBean;
import com.easymother.bean.TopicHelpResult;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyGridView;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyPopupWindow1;
import com.easymother.customview.MyPopupWindow1.OnMyPopupWindowsClick;
import com.easymother.main.R;
import com.easymother.main.my.TopicListActivity;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TopicAndAskListActivity extends Activity implements OnClickListener {
	private ImageView back;
	private TextView topic;
	private TextView ask;
	private ImageView more;
	private PullToRefreshScrollView pulltoreflash;
	private MyListview mylistview;
	private MyListview mylistview1;

	private Intent intent;
	private int blockId;

	private HuLiShiAdapter adapter;
	private HuLiShiAdapter adapter1;
	private TopicHelpResult result;// 访问的结果
	private TextView notice;// 提示
	private String flag = "topic";// 是话题还是求助的标记
	private final int TOPIC = 0;// 话题
	private final int HELP = 1;// 帮助
	private int pageNo = 1;
	boolean canLoad=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.topic_ask_activity);
		intent = getIntent();
		blockId = intent.getIntExtra("blockId", 0);
		findView();
		init();

	}

	private void findView() {
		back = (ImageView) findViewById(R.id.back);
		topic = (TextView) findViewById(R.id.topic);
		ask = (TextView) findViewById(R.id.ask);
		more = (ImageView) findViewById(R.id.more);
		pulltoreflash = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		mylistview = (MyListview) findViewById(R.id.mylistview);
//		mylistview1 = (MyListview) findViewById(R.id.mylistview1);
	}

	private void init() {
		loadData();
		back.setOnClickListener(this);
		topic.setOnClickListener(this);
		ask.setOnClickListener(this);
		more.setOnClickListener(this);
		pulltoreflash.setMode(Mode.BOTH);
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(TopicAndAskListActivity.this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		mylistview.setLayoutAnimation(controller);
		pulltoreflash.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (canLoad) {
//					pageNo++;
					loadData();
				}
				refreshView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canLoad) {
//					pageNo++;
					loadData();
				}
				refreshView.onRefreshComplete();
			}
		});
	}

	private void loadData() {
		Log.e("pageNo", pageNo+"--------");
		RequestParams params = new RequestParams();
		params.put("blockId", blockId);
		params.put("pageNo", pageNo);
		
		NetworkHelper.doGet(BaseInfo.TOPIC_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					result = JsonUtils.getResult(response, TopicHelpResult.class);
					bindDate(result);
				} else {
					Toast.makeText(TopicAndAskListActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
					pageNo = 1;
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				pageNo = 1;
				Toast.makeText(TopicAndAskListActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 绑定数据
	 * 
	 * @param result
	 */
	protected void bindDate(TopicHelpResult result1) {
		
		Log.e("haha", ""+"--------");
		List<TopicItemBean> list = null;
		
		if ("topic".equals(flag)) {
			list = result1.getTopics();
			if (list != null) {
				if (adapter == null) {
					adapter = new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
				}
				
				if (list.size()==0 ) {
					if (pageNo==1) {
						if (notice == null) {
							notice = new TextView(TopicAndAskListActivity.this);
							notice.setText("没有信息哟！");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							mylistview.addFooterView(notice);
						}
					}
					canLoad=false;
					pageNo=1;
				}
				if (list.size() > 0) {
					
					if (pageNo>1) {
						adapter.addList(list);
					}
					if (notice != null) {
						mylistview.removeFooterView(notice);
					}
//					if (pageNo==1) {
//						adapter.clearList();
//						adapter.addList(list);
//					}
//					adapter.notifyDataSetChanged();
					canLoad=true;
					pageNo++;
					
				}
				mylistview.startLayoutAnimation();
				mylistview.setAdapter(adapter);
			}
		
		} 
		else if ("help".equals(flag)) {
			list = result1.getHelps();
			if (list != null) {
				if (adapter1 == null) {
					adapter1 = new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
				}
				
				if (list.size()==0 ) {
					if (pageNo==1) {
						if (notice == null) {
							notice = new TextView(TopicAndAskListActivity.this);
							notice.setText("没有信息哟！");
							notice.setGravity(Gravity.CENTER_HORIZONTAL);
							AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT);
							notice.setLayoutParams(params);
							notice.setBackgroundColor(getResources().getColor(R.color.background));
							notice.setTextColor(getResources().getColor(R.color.boroblacktext));
							mylistview.addFooterView(notice);
						}
					}
					canLoad=false;
					pageNo=1;
				}
				if (list.size() > 0) {
					
					if (pageNo>1) {
						adapter1.addList(list);
//						adapter1.notifyDataSetChanged();
					}
					if (notice != null) {
						mylistview.removeFooterView(notice);
					}
					if (pageNo==1) {
//						adapter.clearList();
//						adapter.addList(list);
					}
					canLoad=true;
					pageNo++;
					
				}
				mylistview.startLayoutAnimation();
				mylistview.setAdapter(adapter1);
			}
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.topic:
			clearState();
			topic.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_half_solid1));
			topic.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightredwine_solid2));
			flag = "topic";
			pageNo=0;
			topic.setTextColor(getResources().getColor(R.color.white));
			// adapter.clearList();
			loadData();
			break;
		case R.id.ask:
			clearState();
			ask.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_half_solid2));
			ask.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightredwine_solid3));
			flag = "help";
			pageNo=0;
			ask.setTextColor(getResources().getColor(R.color.white));
			// adapter.clearList();
			loadData();
			break;
		case R.id.more:
			final MyPopupWindow1 popupWindow1 = new MyPopupWindow1(this, R.layout.popupwindow_community2);
			popupWindow1.setOnMyPopupClidk(new OnMyPopupWindowsClick() {

				@Override
				public void onClick(View view) {
					view.findViewById(R.id.topic).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							intent.setClass(TopicAndAskListActivity.this, TopicOrHelpEditActivity.class);
							intent.putExtra("flag", "topic");
							startActivityForResult(intent, TOPIC);
							popupWindow1.dismiss();
						}
					});
					view.findViewById(R.id.help).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							intent.setClass(TopicAndAskListActivity.this, TopicOrHelpEditActivity.class);
							intent.putExtra("flag", "help");
							startActivityForResult(intent, HELP);
							popupWindow1.dismiss();
						}
					});
				}
			});
			popupWindow1.showAsDropDown(v);
			break;
		}
	}

	/*
	 * 清楚选中状态
	 */
	private void clearState() {
		topic.setTextColor(getResources().getColor(R.color.blacktext));
		topic.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_half_solid1));
		ask.setTextColor(getResources().getColor(R.color.blacktext));
		ask.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_half_solid2));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TOPIC:
				flag = "topic";
				pageNo = 1;
				loadData();
				break;
			case HELP:
				flag = "help";
				pageNo = 1;
				loadData();
				break;

			}
		}
	}

}
