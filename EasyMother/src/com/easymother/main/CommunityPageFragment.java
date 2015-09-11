package com.easymother.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.Banners;
import com.easymother.bean.Blocks;
import com.easymother.bean.CommunityResult;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.ImageCycleView;
import com.easymother.customview.ImageCycleView.ImageCycleViewListener;
import com.easymother.customview.MyListview;
import com.easymother.main.community.CommunityAdapter;
import com.easymother.main.community.HuLiShiZoneListActivity;
import com.easymother.main.community.MessageContralActivity;
import com.easymother.main.community.TopicAndAskListActivity;
import com.easymother.main.community.YSYQActicvity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CommunityPageFragment extends Fragment implements OnClickListener {
	private TextView hulishizoom;// 护理师空间
	private TextView more;// 医食衣趣
	
	private MyListview mylistview;
	private ImageView message;//消息管理
	// 测试数据
	private ArrayList<String> mImageUrl = null;
	private ImageCycleView cycleView;// 广告栏
	
	private PullToRefreshScrollView pulltoreflash;

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
		pulltoreflash=(PullToRefreshScrollView) view.findViewById(R.id.pulltoreflash);
		cycleView = (ImageCycleView) view.findViewById(R.id.imageCycleView2);
		mylistview=(MyListview) view.findViewById(R.id.mylistview);
		hulishizoom = (TextView) view.findViewById(R.id.hulishizone);
		more = (TextView) view.findViewById(R.id.other);
		message=(ImageView) view.findViewById(R.id.message);

	}

	private void init() {
		loadData();
		pulltoreflash.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				loadData();
				refreshView.onRefreshComplete();
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
	protected void bindData(CommunityResult result) {
		/*
		 * 绑定banner
		 */
		if (result.getBanners()!=null) {
			mImageUrl = new ArrayList<String>();
			for (Banners banner : result.getBanners()) {
				mImageUrl.add(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+banner.getLogo());
			}
			cycleView.setImageResources(mImageUrl, new ImageCycleViewListener() {
				@Override
				public void onImageClick(int position, View imageView) {
					
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
			EasyMotherUtils.goActivity(getActivity(), MessageContralActivity.class);

			break;


		}

	}

	

}
