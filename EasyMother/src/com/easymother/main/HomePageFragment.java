package com.easymother.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.w3c.dom.Text;

import com.easymother.configure.MyApplication;
import com.easymother.customview.AutoTextView;
import com.easymother.customview.ImageCycleView;
import com.easymother.customview.ImageCycleView.ImageCycleViewListener;
import com.easymother.customview.MyLinearlayout;
import com.easymother.main.R;
import com.easymother.main.R.id;
import com.easymother.main.R.layout;
import com.easymother.main.homepage.CuiRuShiListActivity;
import com.easymother.main.homepage.CuiRuiShiProjectActivity;
import com.easymother.main.homepage.MyWishListActivity;
import com.easymother.main.homepage.ShortOrderListActivity;
import com.easymother.main.homepage.TuiJianFragment;
import com.easymother.main.homepage.YuYingShiListActivity;
import com.easymother.main.homepage.YueSaoListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.FrameLayout;
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
	
//	private LinearLayout tuijian_content;//推荐左右滑动的容器
	private ViewPager tuijian_content;//推荐左右滑动的容器
	
	private FragmentTransaction transaction;//推荐的fragment的管理器
	
	private TuiJianFragment tuijian_ysf;//推荐月嫂fragment
	private TuiJianFragment tuijian_yysf;//推荐育婴师fragment
	private TuiJianFragment tuijian_crsf;//推荐催乳师fragment

	private int lastPosition=MyApplication.getScreen_width()/12*1;// scorllbar上次滑动到的位置
	
	//------网咯获取数据----
	private String URL="app/index/toIndex";
	
	// 测试数据--以后要改成网络访问获取图片-------------------
	private ArrayList<String> mImageUrl = null;
	private String imageUrl1 = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
	private String imageUrl2 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl3 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl4 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl5 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl6 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	private String imageUrl7 = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";

	// ------------------------------------

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

		homepage_ad.setAutoCycle(true);// 设置自动循环
		
//		NetworkHelper.doGet(URL, new () {
//			
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				
//			}
//			
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		} );

		// 测试——————————————————————————
		mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
		mImageUrl.add(imageUrl4);
		mImageUrl.add(imageUrl5);
		mImageUrl.add(imageUrl6);
		mImageUrl.add(imageUrl7);

		homepage_ad.setImageResources(mImageUrl,
				new ImageCycleViewClickLisenter(), stype);
		// ————————————————————————————————

		
		//初始化话水平的红色滑动条
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scorllbar
				.getLayoutParams();
		int scrollbarLength = MyApplication.getScreen_width() / 6;
		params.width = scrollbarLength;
		scorllbar.setLayoutParams(params);
		scrollbarMove(0);
		//测试数据
//		List<TextView> textViews=new ArrayList<TextView>();
		TextView textView=new TextView(getActivity());
		textView.setText("轻松妈妈开发");
		TextView textView1=new TextView(getActivity());
		textView1.setText("我是阿斯顿卡萨丁");
		TextView textView2=new TextView(getActivity());
		textView2.setText("轻按时大大撒妈妈开发");
		TextView textView3=new TextView(getActivity());
		textView3.setText("轻松撒大声地妈开发");
		
		homepage_notic.addView(textView);
		homepage_notic.addView(textView1);
		homepage_notic.addView(textView2);
		homepage_notic.addView(textView3);
		homepage_notic.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_bottom_in));
		homepage_notic.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_bottom_in));
		homepage_notic.setFlipInterval(3000);
		homepage_notic.startFlipping();
		
		
		tuijian_cuirushi.setOnClickListener(this);
		tuijian_yuesao.setOnClickListener(this);
		tuijian_yuyingshi.setOnClickListener(this);
		yuesao.setOnClickListener(this);
		yuyingshi.setOnClickListener(this);
		cuirushi.setOnClickListener(this);
		duanqihuli.setOnClickListener(this);
		
		homepage_wish.setOnClickListener(this);
		
		
//		transaction=getFragmentManager().beginTransaction();
//		tuijian_ysf=new TuiJianFragment();
//		transaction.add(R.id.tuijian_content, tuijian_ysf);
//		transaction.show(tuijian_ysf);
//		transaction.commit();
		List<TuiJianFragment> fragments=new ArrayList<>();
		TuiJianFragment fragment=new TuiJianFragment();
		TuiJianFragment fragment1=new TuiJianFragment();
		TuiJianFragment fragment2=new TuiJianFragment();
		fragments.add(fragment);
		fragments.add(fragment1);
		fragments.add(fragment2);
		HomePageFragmentAdapter arg0=new HomePageFragmentAdapter(getFragmentManager(),fragments);
		tuijian_content.setAdapter(arg0);
		
		scrollView.setMode(Mode.PULL_FROM_START);//设置只能下拉刷新
		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				//在这里设置刷新最后要调用 onRefreshComplete()方法
				scrollView.onRefreshComplete();
				
			}
		});

	}

	private void findView() {

		scrollView=(PullToRefreshScrollView) homepage.findViewById(R.id.pulltoreflashscrollview);
		homepage_area = (TextView) homepage.findViewById(R.id.home_page_area);
		homepage_wish =  (ImageView) homepage.findViewById(R.id.home_page_wish);
		homepage_ad = (ImageCycleView) homepage.findViewById(R.id.home_page_ad);
		
		tuijian_yuesao=(TextView) homepage.findViewById(R.id.tuijian_yuesao);
		tuijian_yuyingshi=(TextView) homepage.findViewById(R.id.tuijian_yuyingshi);
		tuijian_cuirushi=(TextView) homepage.findViewById(R.id.tuijian_cuirushi);
		
//		tuijian_content= (LinearLayout) homepage.findViewById(R.id.tuijian_content);
		tuijian_content= (ViewPager) homepage.findViewById(R.id.tuijian_content);
		
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

	@Override
	public void onStart() {
		super.onStart();
		homepage_ad.startImageTimerTask();// 开启轮播图滚动
	}

	@Override
	public void onStop() {
		super.onStop();
		homepage_ad.stopImageTimerTask();// 关闭轮播图滚动
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
//			if (tuijian_ysf==null) {
//				tuijian_ysf=new TuiJianFragment();
//				transaction.add(R.id.tuijian_content, tuijian_ysf);
//			}
//			if (tuijian_crsf!=null) {
//				transaction.hide(tuijian_crsf);
//			}
//			if (tuijian_yysf!=null) {
//				transaction.hide(tuijian_yysf);
//			}
//			transaction.show(tuijian_ysf);
			tuijian_content.setCurrentItem(0);
			break;

		case R.id.tuijian_yuyingshi:
			scrollbarMove(1);
//			if (tuijian_yysf==null) {
//				tuijian_yysf=new TuiJianFragment();
//				transaction.add(R.id.tuijian_content, tuijian_yysf);
//				
//			}
//			if (tuijian_crsf!=null) {
//				transaction.hide(tuijian_crsf);
//			}
//			if (tuijian_ysf!=null) {
//				transaction.hide(tuijian_ysf);
//			}
//			transaction.show(tuijian_yysf);
			tuijian_content.setCurrentItem(1);
			break;
		case R.id.tuijian_cuirushi:
			scrollbarMove(2);
//			if (tuijian_crsf==null) {
//				tuijian_crsf=new TuiJianFragment();
//				transaction.add(R.id.tuijian_content, tuijian_crsf);
//				
//			}
//			if (tuijian_yysf!=null) {
//				transaction.hide(tuijian_yysf);
//			}
//			if (tuijian_ysf!=null) {
//				transaction.hide(tuijian_ysf);
//			}
//			transaction.show(tuijian_crsf);
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
