package com.easymother.main.babytime;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.BabyTimeBean;
import com.easymother.bean.NewsInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyListview;
import com.easymother.main.community.ArticleListActivity;
import com.easymother.main.my.LoginOrRegisterActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class BabyTimeActivity extends Activity {
	private MyListview listview;//囡囡记列表
//	private ListView listview;//囡囡记列表
	private PullToRefreshScrollView pulltoreflash;
	private View headview;//listview的头
	private List<BabyTimeBean> list;
	private TextView notice;//没有数据的时候，提示信息
	private CircleImageView circleImageView1;//囡囡头像
	private TextView nannan_name;//囡囡姓名
	private TextView days;//囡囡出生多少天
	private Intent intent;
	private boolean isFirstTime=true;
	private int pageNo=2;
	private boolean canLoad=true;
	public final static int LOGIN_CODE=2;//请求登陆代码
	private BabyTimeListAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_babytime);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {
			
			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.add_yuan));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if (MyApplication.preferences.getInt("id", 0)==0) {
							EasyMotherUtils.goActivityForResult(BabyTimeActivity.this, LoginOrRegisterActivity.class, LOGIN_CODE);
							return;
						}
						EasyMotherUtils.goActivity(BabyTimeActivity.this, BabyTimeEditActivity.class);
					}
				});
				
			}
		});
		EasyMotherUtils.initTitle(this, "囡囡记", false);
		findView();
		init();
	}

	private void findView() {
		pulltoreflash=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
//		listview=(MyListview) findViewById(R.id.reflashlistview);
		listview=(MyListview) findViewById(R.id.reflashlistview);
		headview=LayoutInflater.from(this).inflate(R.layout.activity_babytime_head, null);
		nannan_name=(TextView) headview.findViewById(R.id.nannan_name);
		days=(TextView)headview. findViewById(R.id.days);
		circleImageView1=(CircleImageView) headview.findViewById(R.id.circleImageView1);
		
	}

	private void init() {
		loadData();
		pulltoreflash.setMode(Mode.PULL_FROM_END);
		pulltoreflash.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				refreshView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (canLoad) {
					loadMore(pageNo);
				}
				refreshView.onRefreshComplete();
				
			}
		});
	}
	public void loadData(){
		RequestParams params=new RequestParams();
		params.put("pageNo", 1);
		NetworkHelper.doGet(BaseInfo.BABYTIME_LIST,params,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					list=JsonUtils.getResultList(response, BabyTimeBean.class);
					bindData(list);
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString);
			}
		});
		
	}

	protected void bindData(List<BabyTimeBean> list2) {
		String baby_image=MyApplication.preferences.getString("baby_image", "");
		if (!"".equals(baby_image)&&baby_image!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+baby_image, circleImageView1, MyApplication.options_photo);
		}
		String baby_name=MyApplication.preferences.getString("nannan_name", "");
		if (!"".equals(baby_name)&&baby_name!=null) {
			nannan_name.setText(baby_name);
		}
		days.setVisibility(View.GONE);
		
		if (list2.size()==0) {
			notice=new TextView(BabyTimeActivity.this);
			notice.setText("你还没有记录过囡囡信息哦！快去记录吧！");
			notice.setGravity(Gravity.CENTER_HORIZONTAL);
			AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			notice.setLayoutParams(params);
			notice.setBackgroundColor(getResources().getColor(R.color.background));
			notice.setTextColor(getResources().getColor(R.color.boroblacktext));
			listview.addFooterView(notice);
		}
		if (adapter==null) {
			adapter=new BabyTimeListAdapter(this, list, R.layout.activity_babytime_item);
		}
		if (isFirstTime) {
			isFirstTime=false;
			listview.addHeaderView(headview);
		}
		listview.setAdapter(adapter);
		
	}
	
	public void loadMore(int pageNo) {
		Log.e("pageNo", pageNo+"----S");
		RequestParams params = new RequestParams();
		params.put("pageNo", pageNo);
		NetworkHelper.doGet(BaseInfo.BABYTIME_LIST, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<BabyTimeBean>	list=JsonUtils.getResultList(response, BabyTimeBean.class);
					if (list != null) {
						adapter.addList(list);
						if (list.size() > 0) {
							if (notice != null) {
								listview.removeHeaderView(notice);
							}
							canLoad = true;
							BabyTimeActivity.this.pageNo++;
						} else {
							BabyTimeActivity.this.pageNo = 2;
							canLoad = false;
						}
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				BabyTimeActivity.this.pageNo = 2;
				canLoad = false;
			}
		});

	}

}
