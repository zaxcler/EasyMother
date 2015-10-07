package com.easymother.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.Blocks;
import com.easymother.bean.CommunityResult;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.ImageCycleView1;
import com.easymother.customview.ImageCycleView1.ImageCycleViewListener;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyScrollView;
import com.easymother.customview.MySwipleReflashLayout;
import com.easymother.main.community.CommunityAdapter;
import com.easymother.main.community.HuLiShiZoneListActivity;
import com.easymother.main.community.MessageContralActivity;
import com.easymother.main.community.TopicAndAskListActivity;
import com.easymother.main.community.YSYQActicvity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CommunityPageFragment extends Fragment implements OnClickListener {
	private TextView hulishizoom;// 护理师空间
	private TextView more;// 医食衣趣
	
	private MyListview mylistview;
	private ImageView message;//消息管理
	// 测试数据
	private ArrayList<String> mImageUrl = null;
	private ImageCycleView1 cycleView;// 广告栏
	
//	public PullToRefreshScrollView pulltoreflash;
//	public SwipeRefreshLayout pulltoreflash;
	public MySwipleReflashLayout pulltoreflash;
//	private ScrollView scrollview;
	private MyScrollView scrollview;
	private LinearLayout child;
	public CommunityPageFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View community = inflater
				.inflate(R.layout.fragment_communitypage, null);
		findView(community);
		init();
		return community;
	}

	private void findView(View view) {
		pulltoreflash=(MySwipleReflashLayout) view.findViewById(R.id.pulltoreflashscrollview);
		cycleView = (ImageCycleView1) view.findViewById(R.id.imageCycleView2);
		mylistview=(MyListview) view.findViewById(R.id.mylistview);
		hulishizoom = (TextView) view.findViewById(R.id.hulishizone);
		more = (TextView) view.findViewById(R.id.other);
		message=(ImageView) view.findViewById(R.id.message);
		scrollview=(MyScrollView) view.findViewById(R.id.scrollview);
		child=(LinearLayout) view.findViewById(R.id.child);

	}

	private void init() {
//		pulltoreflash.scrollTo(0, 0);
		loadData();
//		pulltoreflash.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//				loadData();
//				refreshView.onRefreshComplete();
//			}
//		});
		scrollview.requestChildFocus(child, cycleView);
		pulltoreflash.setColorSchemeColors(R.color.lightredwine);
		pulltoreflash.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				loadData();
				pulltoreflash.setRefreshing(false);
			}
		});
		hulishizoom.setOnClickListener(this);
		more.setOnClickListener(this);
		message.setOnClickListener(this);
	}
	/**
	 * 加载数据
	 */
	private void loadData(){
		NetworkHelper.doGet(BaseInfo.COMMNUTITY, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					CommunityResult result=JsonUtils.getResult(response, CommunityResult.class);
					bindData(result);
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("社区", responseString);
			}
		});
	}
	/**
	 * 绑定数据
	 * @param result
	 */
	protected void bindData(final CommunityResult result) {
		/*
		 * 绑定banner
		 */
		if (result.getBanners()!=null) {
			mImageUrl = new ArrayList<String>();
			for (int i = 0; i < result.getBanners().size(); i++) {
				mImageUrl.add(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+result.getBanners().get(i).getLogo());
			}
//			for (Banners banner : result.getBanners()) {
//				mImageUrl.add(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+banner.getLogo());
//			}
			cycleView.setImageResources(mImageUrl, new ImageCycleViewListener() {
				@Override
				public void onImageClick(int position, View imageView) {
					Intent intent=new Intent(getActivity(), WebViewActivity.class);
					intent.putExtra("url", result.getBanners().get(position).getUrlValue());
					startActivity(intent);
				}
			});
		}
		/**
		 * 绑定社区下面的部分
		 */
		if (result.getBlocks()!=null) {
			final List<Blocks> list=result.getBlocks();
			CommunityAdapter adapter=new CommunityAdapter(getActivity(), list, R.layout.community_item);
			mylistview.setAdapter(adapter);
			mylistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent=new Intent();
					intent.setClass(getActivity(), TopicAndAskListActivity.class);
					intent.putExtra("blockId", list.get(arg2).getId());
					startActivity(intent);
				}
			});
		}
		
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.hulishizone:
			EasyMotherUtils.goActivity(getActivity(), HuLiShiZoneListActivity.class);
			break;
		case R.id.other:
			EasyMotherUtils.goActivity(getActivity(), YSYQActicvity.class);
			break;
		case R.id.message:
			Intent intent=new Intent();
			intent.putExtra("flag", "unread");
			intent.setClass(getActivity(), MessageContralActivity.class);
			getActivity().startActivity(intent);

			break;


		}

	}

	

}
