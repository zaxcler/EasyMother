package com.easymother.main;

import java.util.ArrayList;

import com.easymother.configure.BaseInfo;
import com.easymother.customview.ImageCycleView;
import com.easymother.customview.ImageCycleView.ImageCycleViewListener;
import com.easymother.main.community.HuLiShiZoneListActivity;
import com.easymother.main.community.MessageContralActivity;
import com.easymother.main.community.YSYQActicvity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommunityPageFragment extends Fragment implements OnClickListener {
	private TextView hulishizoom;// 护理师空间
	private TextView more;// 医食衣趣
	private TextView babyfood_today;// 今天
	private TextView babyfood_topic;// 话题
	private TextView motherfood_today;// 今天
	private TextView motherfood_topic;// 话题
	private TextView motherhuli_today;//
	private TextView motherhuli_topic;//
	private TextView babyhuli_today;//
	private TextView babyhuli_topic;//

	private RelativeLayout babyfood;// 宝贝辅食
	private RelativeLayout motherfood;// 月子餐
	private RelativeLayout motherhuli;// 妈妈护理
	private RelativeLayout babyhuli;// 宝贝护理

	private ImageView message;//消息管理
	// 测试数据
	private ArrayList<String> mImageUrl = null;
	private String imageUrl1 = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
	private String imageUrl2 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl3 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl4 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl5 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl6 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl7 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";

	private ImageCycleView cycleView;// 广告栏

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
		cycleView = (ImageCycleView) view.findViewById(R.id.imageCycleView2);
		babyfood = (RelativeLayout) view.findViewById(R.id.babyfood);
		motherfood = (RelativeLayout) view.findViewById(R.id.motherfood);
		motherhuli = (RelativeLayout) view.findViewById(R.id.mmhuli);
		babyhuli = (RelativeLayout) view.findViewById(R.id.babyhuli);

		hulishizoom = (TextView) view.findViewById(R.id.hulishizone);
		more = (TextView) view.findViewById(R.id.other);

		babyfood_today = (TextView) view.findViewById(R.id.babyfood_today);
		babyfood_topic = (TextView) view.findViewById(R.id.babyfood_topic);
		motherfood_today = (TextView) view.findViewById(R.id.mother_food_today);
		motherfood_topic = (TextView) view.findViewById(R.id.mother_food_topic);
		motherhuli_today = (TextView) view.findViewById(R.id.mmyhuli_today);
		motherhuli_topic = (TextView) view.findViewById(R.id.mmhuli_topic);
		babyhuli_today = (TextView) view.findViewById(R.id.babyhuli_today);
		babyhuli_topic = (TextView) view.findViewById(R.id.babyhuli_topic);
		
		message=(ImageView) view.findViewById(R.id.message);

	}

	private void init() {
		// 测试
		mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
		mImageUrl.add(imageUrl4);
		mImageUrl.add(imageUrl5);
		mImageUrl.add(imageUrl6);
		mImageUrl.add(imageUrl7);

		cycleView.setImageResources(mImageUrl, new ImageCycleViewListener() {

			@Override
			public void onImageClick(int position, View imageView) {
				// TODO Auto-generated method stub

			}
		});

		babyfood.setOnClickListener(this);
		motherfood.setOnClickListener(this);
		motherhuli.setOnClickListener(this);
		babyhuli.setOnClickListener(this);
		hulishizoom.setOnClickListener(this);
		more.setOnClickListener(this);
		message.setOnClickListener(this);
	}
	/**
	 * 加载数据
	 */
	private void loadData(){
		NetworkHelper.doGet(BaseInfo.COMMNUTITY, new JsonHttpResponseHandler(){
			
		});
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
		case R.id.babyfood:

			break;
		case R.id.motherfood:

			break;
		case R.id.babyhuli:

			break;
		case R.id.mmhuli:

			break;
		case R.id.message:
			EasyMotherUtils.goActivity(getActivity(), MessageContralActivity.class);

			break;


		}

	}

	

}
