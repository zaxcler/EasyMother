package com.easymother.main.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseSpaceDetailBean;
import com.easymother.bean.TestBean;
import com.easymother.bean.TopicItemBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.homepage.CommonListActivity;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.example.demobyimage.DensityUtil;
import com.example.demobyimage.GalleryUrlActivity;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HuLiShiZoneDetailActivity extends Activity {
	private PullToRefreshScrollView scrollView;//下拉刷新控件
	private MyListview listview;//
//	private Gallery gallery;
	private LinearLayout images;//画廊
	private Intent intent;
	private int nurseId;
	private TextView nurse_name;//护理师姓名
	private TextView work_experience;//护理师工龄
	private TextView age;//护理师年龄
	private TextView hometown;//护理师籍贯
	private TextView star;//护理师星座
	private TextView nurse_sx;//护理师生肖
	private TextView article;//文章数量
	private TextView like;//喜欢数量
	private ImageView imageView1;//背景
	private TextView notice;//提示信息
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hulishi_zone);
		EasyMotherUtils.initTitle(this, "护理师空间", false);
		intent=getIntent();
		nurseId=intent.getIntExtra("id", 0);
		findView();
		init();
	}
	private void findView() {
		scrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview=(MyListview) findViewById(R.id.listview);
//		gallery=(Gallery) findViewById(R.id.gallery);
		images=(LinearLayout) findViewById(R.id.images);
		nurse_name=(TextView) findViewById(R.id.nurse_name);
		work_experience=(TextView) findViewById(R.id.work_experience);
		age=(TextView) findViewById(R.id.age);
		hometown=(TextView) findViewById(R.id.hometown);
		star=(TextView) findViewById(R.id.star);
		nurse_sx=(TextView) findViewById(R.id.nurse_sx);
		article=(TextView) findViewById(R.id.textView1);
		like=(TextView) findViewById(R.id.like);
		imageView1=(ImageView) findViewById(R.id.imageView1);
	}
	private void init() {
		loadData();
//		HuLiShiAdapter adapter=new HuLiShiAdapter(this, list, R.layout.activity_mypage_topic_item);
//		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				EasyMotherUtils.goActivity(HuLiShiZoneDetailActivity.this, HuLiShiReplyListActivity.class);
			}
		});
		
	}
	/**
	 * 加载数据
	 */
	private void loadData() {
		RequestParams params=new RequestParams();
		params.put("userId", nurseId);
		params.put("type", "KJ");
		NetworkHelper.doGet(BaseInfo.NURSE_ZOME_DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					NurseSpaceDetailBean detail=JsonUtils.getResult(response, NurseSpaceDetailBean.class);
					bindData(detail);
				}
			}
		});
	}
	/**
	 * 绑定数据
	 * @param detail
	 */
	protected void bindData(NurseSpaceDetailBean detail) {
		NurseBaseBean nursebase=detail.getNurseinfo();
		if (nursebase!=null) {
			if (nursebase.getRealName()!=null) {
				nurse_name.setText(nursebase.getRealName());
			}else {
				nurse_name.setText("");
			}
			if (nursebase.getSeniority()!=null) {
				work_experience.setText("工龄："+nursebase.getSeniority()+"年");
			}else {
				work_experience.setText("工龄："+0+"年");
			}
			if (nursebase.getAge()!=null) {
				age.setText("年龄："+nursebase.getAge()+"岁");
			}else {
				age.setText("年龄："+0+"岁");
			}
			if (nursebase.getHometown()!=null) {
				hometown.setText("籍贯："+nursebase.getHometown());
			}else {
				hometown.setText("籍贯：");
			}
			if (nursebase.getConstellation()!=null) {
				star.setText("星座："+nursebase.getConstellation());
			}else {
				star.setText("星座：");
			}
			if (nursebase.getYearLunar()!=null) {
				nurse_sx.setText("生肖："+nursebase.getYearLunar());
			}else {
				nurse_sx.setText("生肖：");
			}
			
//			if (nursebase.getWorkImageArrays()!=null) {
				final ArrayList<String> picture=(ArrayList<String>) JSON.parseArray(nursebase.getLifeImages(), String.class);
//				final ArrayList<String> picture=(ArrayList<String>) nursebase.getWorkImageArrays();
				android.widget.LinearLayout.LayoutParams layoutParams = null;
				DensityUtil densityUtil=new DensityUtil(this);
				for (int i = 0; i < picture.size(); i++) {
					final ImageView imageview = new ImageView(this);
					int space = densityUtil.convertDipsToPixels(15);// 间距
					int width = (densityUtil.getDisplayMetrics().widthPixels - 3 * space) / 2;
					int height = width * 800 / 480;
					layoutParams = new LinearLayout.LayoutParams(width,height);
					layoutParams.leftMargin = space;
					if (i == picture.size() - 1) {
						layoutParams.rightMargin = space;
					}
					// 显示图片
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+picture.get(i), imageview,MyApplication.options_image);
					images.addView(imageview, layoutParams);
					imageview.setTag(i);
					imageview.setScaleType(ScaleType.FIT_XY);
					imageview.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							Intent intent = new Intent(HuLiShiZoneDetailActivity.this,
									GalleryUrlActivity.class);
//							System.out.println(Integer.parseInt(arg0.getTag().toString())+"---Integer.parseInt(arg0.getTag().toString())");
							intent.putExtra("tag", Integer.parseInt(arg0.getTag().toString()));
							Bundle b = new Bundle();
							b.putSerializable("list_img", picture);
							intent.putExtras(b);
//							int[] location = new int[2];
//							imageview.getLocationOnScreen(location);
//							intent.putExtra("locationX", location[0]);
//							intent.putExtra("locationY", location[1]);
//							intent.putExtra("width", imageview.getWidth());
//							intent.putExtra("height", imageview.getHeight());
							startActivity(intent);
							overridePendingTransition(0, 0);
						}
					});
				}
//			}
			ArrayList<String> list=(ArrayList<String>) JSON.parseArray(nursebase.getLifeImages(), String.class);
			if (list!=null) {
				if (list.size()>0) {
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursebase.getImage(), imageView1, MyApplication.options_image);
				}
				
			}
			
		}
		List<TopicItemBean> beans=detail.getPosts();
		if (beans!=null) {
			HuLiShiAdapter adapter=new HuLiShiAdapter(this, beans, R.layout.activity_mypage_topic_item);
			if (beans.size()==0) {
				if (notice==null) {
					notice=new TextView(HuLiShiZoneDetailActivity.this);
					notice.setText("没有发布过信息哟！");
					notice.setGravity(Gravity.CENTER_HORIZONTAL);
					AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					notice.setLayoutParams(params);
					notice.setBackgroundColor(getResources().getColor(R.color.background));
					notice.setTextColor(getResources().getColor(R.color.boroblacktext));
					listview.addFooterView(notice);
				}
			}
			listview.setAdapter(adapter);
			
		}
	}

}
