package com.easymother.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.BannerTexts;
import com.easymother.bean.Banners;
import com.easymother.bean.HomePageResult;
import com.easymother.configure.MyApplication;
import com.easymother.customview.ImageCycleView;
import com.easymother.customview.ImageCycleView.ImageCycleViewListener;
import com.easymother.customview.MyViewPager;
import com.easymother.main.homepage.CuiRuShiListActivity;
import com.easymother.main.homepage.MyWishListActivity;
import com.easymother.main.homepage.ShortOrderListActivity;
import com.easymother.main.homepage.TuiJianFragment;
import com.easymother.main.homepage.YuYingShiListActivity;
import com.easymother.main.homepage.YueSaoListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HomePageFragment extends Fragment implements OnClickListener {
	private View homepage;// 首页的对象
	private TextView homepage_area;// 地区
	private ImageView homepage_wish;// 心愿单
	private ImageCycleView homepage_ad;// 广告栏
	private int stype = 0;// 0轮播图下面的是小圆点
	private View scorllbar;// 滑动条
	private PullToRefreshScrollView scrollView;//下拉刷新的scrollview

	private ViewFlipper homepage_notic;//公告
	private LinearLayout yuesao;
	private LinearLayout yuyingshi;
	private LinearLayout cuirushi;
	private LinearLayout duanqihuli;

	private TextView tuijian_yuesao;
	private TextView tuijian_yuyingshi;
	private TextView tuijian_cuirushi;

	private TextView private_more;// 私人定制
	
//	private ViewPager tuijian_content;//推荐左右滑动的容器
	private MyViewPager tuijian_content;//推荐左右滑动的容器
	
	private FragmentTransaction transaction;//推荐的fragment的管理器
	
	private TuiJianFragment tuijian_ysf;//推荐月嫂fragment
	private TuiJianFragment tuijian_yysf;//推荐育婴师fragment
	private TuiJianFragment tuijian_crsf;//推荐催乳师fragment

	private int lastPosition=MyApplication.getScreen_width()/12*1;// scorllbar上次滑动到的位置
	
	//------网咯获取数据----
//	private String URL="app/index/toIndex";
	
	private String URL="http://zaxcler.oss-cn-beijing.aliyuncs.com/test.txt";
	private ArrayList<String> mImageUrl = null;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		homepage = inflater.inflate(R.layout.fragment_homepage, null, false);
		findView();
		init();
		return homepage;
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void init() {

		
		
		//获取网路数据
		NetworkHelper.doGet(URL,new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response-------->", response.toString());
				HomePageResult pageResult=JsonUtils.getHomePageResult(response);
				Log.e("pageResult-------->", pageResult.getBanners().toString());
				BindData(pageResult);//绑定数据到界面
			}
			
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});


		
		//初始化话水平的红色滑动条
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scorllbar
				.getLayoutParams();
		int scrollbarLength = MyApplication.getScreen_width() / 6;
		params.width = scrollbarLength;
		scorllbar.setLayoutParams(params);
		scrollbarMove(0);
		
		
		tuijian_cuirushi.setOnClickListener(this);
		tuijian_yuesao.setOnClickListener(this);
		tuijian_yuyingshi.setOnClickListener(this);
		yuesao.setOnClickListener(this);
		yuyingshi.setOnClickListener(this);
		cuirushi.setOnClickListener(this);
		duanqihuli.setOnClickListener(this);
		
		homepage_wish.setOnClickListener(this);
		
		
		
		
		scrollView.setMode(Mode.PULL_FROM_START);//设置只能下拉刷新
		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				//在这里设置刷新最后要调用 onRefreshComplete()方法
				scrollView.onRefreshComplete();
				
			}
		});

	}
	/**
	 * 绑定数据到界面
	 * @param pageResult
	 */
	protected void BindData(HomePageResult pageResult) {
		/*
		 * 绑定banner数据
		 */
		List<Banners> banners=pageResult.getBanners();
		
		mImageUrl=new ArrayList<>();
		for (int i = 0; i < banners.size(); i++) {
//			mImageUrl.add(MyApplication.BASE_PICTURE+banners.get(i).getLogo());
			mImageUrl.add(banners.get(i).getImages());
		}
		
		
		
		
				homepage_ad.setImageResources(mImageUrl,
						new ImageCycleViewClickLisenter());
		
		
		/*
		 * 绑定bannertexts数据
		 */
		List<BannerTexts> bannerTexts=pageResult.getBannerTexts();
		for (BannerTexts bannerText : bannerTexts) {
			TextView textView=new TextView(getActivity());
			Log.e("textView", bannerText.getTitle());
			textView.setText(bannerText.getTitle());
			textView.setSingleLine();
			homepage_notic.addView(textView);
		}
		//开启flip的动画
		homepage_notic.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_bottom_in));
		homepage_notic.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_bottom_in));
		homepage_notic.setFlipInterval(3000);
		homepage_notic.startFlipping();
		
		/*
		 * 绑定推荐的数据
		 */
		
		List<TuiJianFragment> fragments=new ArrayList<>();
		
		TuiJianFragment fragment=new TuiJianFragment("yuesao",pageResult.getYuesao());
		TuiJianFragment fragment1=new TuiJianFragment("yuyingshi",pageResult.getYuyingshi());
		TuiJianFragment fragment2=new TuiJianFragment("cuirushi",pageResult.getCuirushi());
		fragments.add(fragment);
		fragments.add(fragment1);
		fragments.add(fragment2);
		HomePageFragmentAdapter arg0=new HomePageFragmentAdapter(getFragmentManager(),fragments);
		tuijian_content.setAdapter(arg0);
		
		
		
		
				
		
		
	}

	private void findView() {

		scrollView=(PullToRefreshScrollView) homepage.findViewById(R.id.pulltoreflashscrollview);
		homepage_area = (TextView) homepage.findViewById(R.id.home_page_area);
		homepage_wish =  (ImageView) homepage.findViewById(R.id.home_page_wish);
		homepage_ad = (ImageCycleView) homepage.findViewById(R.id.home_page_ad);
		
		tuijian_yuesao=(TextView) homepage.findViewById(R.id.tuijian_yuesao);
		tuijian_yuyingshi=(TextView) homepage.findViewById(R.id.tuijian_yuyingshi);
		tuijian_cuirushi=(TextView) homepage.findViewById(R.id.tuijian_cuirushi);
		
		tuijian_content= (MyViewPager) homepage.findViewById(R.id.tuijian_content);
		
		yuesao= (LinearLayout) homepage.findViewById(R.id.yuesao);
		yuyingshi= (LinearLayout) homepage.findViewById(R.id.yuyingshi);
		cuirushi= (LinearLayout) homepage.findViewById(R.id.cuirushi);
		duanqihuli= (LinearLayout) homepage.findViewById(R.id.duanqihuli);
		
		
		homepage_notic=(ViewFlipper) homepage.findViewById(R.id.homepage_notice);
		scorllbar = homepage.findViewById(R.id.scorllbar);

	}
	
	
/**
 * 控制推荐下面的滑动条
 * @param postion
 */
	private void scrollbarMove(int postion) {
		clearChooseStatus();
		int fromPostion = lastPosition;
		int newPostion=0;
		Animation animation;
		switch (postion) {
		case 0:
			tuijian_yuesao.setTextColor(getResources().getColor(R.color.deeppink));
			newPostion=MyApplication.getScreen_width()/12*1;
			break;
		case 1:
			tuijian_yuyingshi.setTextColor(getResources().getColor(R.color.deeppink));
			newPostion=MyApplication.getScreen_width()/12*5;
			break;
		case 2:
			tuijian_cuirushi.setTextColor(getResources().getColor(R.color.deeppink));
			newPostion=MyApplication.getScreen_width()/12*9;
			
			break;
		}
		animation = new TranslateAnimation(fromPostion, newPostion, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(500);
		animation.setInterpolator(new DecelerateInterpolator());
		scorllbar.setAnimation(animation);
		scorllbar.startAnimation(animation);
		lastPosition=newPostion;
		
		
		
		
	}


	private class ImageCycleViewClickLisenter implements ImageCycleViewListener {

		@Override
		public void onImageClick(int position, View imageView) {
			

		}

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		transaction=getFragmentManager().beginTransaction();
		switch (id) {

		case R.id.home_page_area:

			break;
		case R.id.home_page_wish:
			EasyMotherUtils.goActivity(getActivity(), MyWishListActivity.class);
			break;
		case R.id.homepage_notice:

			break;
		case R.id.yuesao:
			EasyMotherUtils.goActivity(getActivity(), YueSaoListActivity.class);
			break;

		case R.id.yuyingshi:
			EasyMotherUtils.goActivity(getActivity(), YuYingShiListActivity.class);
			break;
		case R.id.cuirushi:
			EasyMotherUtils.goActivity(getActivity(), CuiRuShiListActivity.class);
			break;

		case R.id.duanqihuli:
			EasyMotherUtils.goActivity(getActivity(), ShortOrderListActivity.class);
			break;

		case R.id.tuijian_yuesao:
			scrollbarMove(0);
			tuijian_content.setCurrentItem(0);
			break;

		case R.id.tuijian_yuyingshi:
			scrollbarMove(1);
			tuijian_content.setCurrentItem(1);
			break;
		case R.id.tuijian_cuirushi:
			scrollbarMove(2);
			tuijian_content.setCurrentItem(2);
			break;
		}
		transaction.commit();
	}
	
	private void clearChooseStatus(){
		
		tuijian_yuesao.setTextColor(getResources().getColor(R.color.boroblacktext));
		tuijian_yuyingshi.setTextColor(getResources().getColor(R.color.boroblacktext));
		tuijian_cuirushi.setTextColor(getResources().getColor(R.color.boroblacktext));
	}

}
