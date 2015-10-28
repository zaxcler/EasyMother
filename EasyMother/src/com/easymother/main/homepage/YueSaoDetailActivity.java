package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alidao.mama.R;
import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.Certificate;
import com.easymother.bean.DetailResult;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.Order;
import com.easymother.bean.OrderComments;
import com.easymother.bean.Skill;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.ImageZoom;
import com.easymother.customview.MyGridView;
import com.easymother.customview.MyListview;
import com.easymother.customview.MyLoadingProgress;
import com.easymother.main.community.HuLiShiZoneDetailActivity;
import com.easymother.main.my.CommentActivity;
import com.easymother.main.my.CommentListActivity;
import com.easymother.main.my.CommentListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class YueSaoDetailActivity extends Activity implements OnClickListener {
	private GridView gridView;// 证书列表
	private PullToRefreshScrollView pullToRefreshScrollView;// 下拉刷新空间

	private MyListview mListview;// 雇主评价

	private RelativeLayout video;// 点击显示video列表
	private RelativeLayout kongjian;// 点击显示护理师空间

	private TextView ordertext;// 预约
	private TextView addtowish;// 添加到心愿单
	private TextView submit_comment;// 评价
	private int id;// 护理师id
	private String job;// 护理师职务
	private String type;// 护理师职务
	

	private ImageView backgroudPhoto;// 最上面的背景
	private CircleImageView nursePhoto;// 护理师头像
	private TextView nurseName;// 护理师姓名
	private TextView nurseJobNumber;// 护理师工号
	private TextView nurseType;// 护理师类别
	private TextView nurseAge;// 护理师年龄
	private TextView nurseWorkEx;// 护理师工龄
	private TextView nurseLevel;// 护理师等级
	private TextView nurseCurrentPrice;// 护理师实际价格
	private TextView nurseHometown;// 护理师籍贯
	private TextView nurseAddress;// 护理师Z住址
	private TextView nurseMarketPrice;// 护理师s市场价格
	private TextView like;// 喜欢数量
	private TextView comment_num;// 评论数量
	private TextView allcomment;// 点击进入评论列表
	private TextView check_all;// 查看所有信件
	private LinearLayout yuesaoskills;// 月嫂skills
	private LinearLayout yuyingshiskills;// 育婴师skills
	private LinearLayout cuirushiskills;// 催乳师skills
	private LinearLayout ysoryys;// 月嫂或者育婴师的价格
	private LinearLayout cuishi_stars;// 催乳师的星星等级

	private TextView check1;// 查看1
	private TextView check2;// 查看2
	private TextView check3;// 查看3
	private TextView check4;// 查看4
	private TextView text1;// 育婴师查看1
	private TextView text4;// 育婴师查看2
	private TextView text6;// 育婴师查看3
	private TextView text8;// 育婴师查看4
	private TextView text10;// 育婴师查看5
	private TextView text12;// 育婴师查看6
	private TextView text14;// 育婴师查看7
	private TextView text16;// 育婴师查看8

	private TextView message_height;
	private TextView message_edu;
	private TextView message_weight;
	private TextView message_pth;
	private TextView message_sx;
	private TextView message_address;
	private TextView message_xz;
	private TextView message_time;

	private NurseJobBean nurseJobBean;// nursejob
	private NurseBaseBean baseBean;//
	private ArrayList<Order> orders;// 订单 传递到后面的订单流程里
	private boolean hasVideo = false;// 是否有视频
	private boolean hasKongjian = false;// 是否有空间
	private Intent intent;
	
	private TextView check_zhengshu;//查看纸质证书

	private RatingBar ratingBar1;// 催乳师的等级

	private TextView letter_title;// 信件标题
	private TextView letter_content;// 信件内容
	private ImageView letter_image;// 信件图片

	private boolean show_all = false;// 是否显示全部的
	private TextView work_express_content;// 履历显示内容
	
	private ListView skilllistview;//技能列表

	// 日历部分
	// 删掉年份的加减
	private LinearLayout calendar;
	private LinearLayout crs_calendar;
	private TextView showdate;
	// private ImageView add_year;
	private ImageView add_mouth;
	private ImageView delete_mouth;
	// private ImageView delete_year;
	private GridView week;
	private MyGridView days;
	private ViewPager content;
	private CalenderAadpter aadpter;
	OrderCRSPreocessGridViewAdapter aadpter1;
	private MyGridView gridView2;
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView share;
	private TextView show_all_tv;//显示所有
	private List<String> weeks ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yuesao_detail);
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		job = intent.getStringExtra("job");
		type = intent.getStringExtra("type");
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if ("wish".equals(type)) {
							YueSaoDetailActivity.this.finish();
						}else {
							EasyMotherUtils.goActivity(YueSaoDetailActivity.this, MyWishListActivity.class);
						}
						
					}
				});
			}
		});

		
		
		if ("YS".equals(job)) {
			EasyMotherUtils.initTitle(this, "月嫂详情", true);
		}
		if ("YYS".equals(job)) {
			EasyMotherUtils.initTitle(this, "育婴师详情", true);
		}
		if ("CRS".equals(job)) {
			EasyMotherUtils.initTitle(this, "催乳师详情", true);
		}
		if ("SHORT_YS".equals(job)) {
			EasyMotherUtils.initTitle(this, "短期月嫂详情", true);
		}
		if ("SHORT_YYS".equals(job)) {
			EasyMotherUtils.initTitle(this, "短期育婴师详情", true);
		}
		findView();
		init();
	}

	private void findView() {
		pullToRefreshScrollView=(PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		gridView = (GridView) findViewById(R.id.gridView1);
		mListview = (MyListview) findViewById(R.id.comment);
		video = (RelativeLayout) findViewById(R.id.video);
		kongjian = (RelativeLayout) findViewById(R.id.kongjian);
		ordertext = (TextView) findViewById(R.id.buy_now);
		submit_comment = (TextView) findViewById(R.id.submit_comment);
		addtowish = (TextView) findViewById(R.id.add);
		like = (TextView) findViewById(R.id.like);
		allcomment = (TextView) findViewById(R.id.allcomment);
		check_all = (TextView) findViewById(R.id.check_all);
		show_all_tv = (TextView) findViewById(R.id.show_all);
		check1 = (TextView) findViewById(R.id.check1);
		check2 = (TextView) findViewById(R.id.check2);
		check3 = (TextView) findViewById(R.id.check3);
		check4 = (TextView) findViewById(R.id.check4);

		work_express_content = (TextView) findViewById(R.id.work_express_content);

		day1 = (TextView) findViewById(R.id.day1);
		day2 = (TextView) findViewById(R.id.day2);
		day3 = (TextView) findViewById(R.id.day3);
		day4 = (TextView) findViewById(R.id.day4);

		letter_title = (TextView) findViewById(R.id.letter_title);
		letter_content = (TextView) findViewById(R.id.letter_content);
		letter_image = (ImageView) findViewById(R.id.letter_image);

		message_height = (TextView) findViewById(R.id.message_height);
		message_edu = (TextView) findViewById(R.id.message_edu);
		message_weight = (TextView) findViewById(R.id.message_weight);
		message_pth = (TextView) findViewById(R.id.message_pth);
		message_sx = (TextView) findViewById(R.id.message_sx);
		message_address = (TextView) findViewById(R.id.message_address);
		message_xz = (TextView) findViewById(R.id.message_xz);
		message_time = (TextView) findViewById(R.id.message_time);

		backgroudPhoto = (ImageView) findViewById(R.id.background_photo);
		nursePhoto = (CircleImageView) findViewById(R.id.photo2);
		nurseName = (TextView) findViewById(R.id.name);
		nurseJobNumber = (TextView) findViewById(R.id.num);
		nurseType = (TextView) findViewById(R.id.label);
		nurseAge = (TextView) findViewById(R.id.age);
		nurseWorkEx = (TextView) findViewById(R.id.workyear);
		nurseLevel = (TextView) findViewById(R.id.label_level);
		nurseCurrentPrice = (TextView) findViewById(R.id.price);
		nurseHometown = (TextView) findViewById(R.id.area);
		nurseAddress = (TextView) findViewById(R.id.adrress);
		nurseMarketPrice = (TextView) findViewById(R.id.market_price);
		comment_num = (TextView) findViewById(R.id.comment_num);
		 check_zhengshu=(TextView) findViewById(R.id.check_zhengshu);
		
		yuesaoskills = (LinearLayout) findViewById(R.id.yuesaoskills);
		yuyingshiskills = (LinearLayout) findViewById(R.id.yuyingshiskills);
		cuirushiskills = (LinearLayout) findViewById(R.id.cuirushiskills);
		ysoryys = (LinearLayout) findViewById(R.id.ysoryys);
		cuishi_stars = (LinearLayout) findViewById(R.id.cuishi_stars);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);

		// 育婴师的技能
		text1 = (TextView) findViewById(R.id.text1);
		text4 = (TextView) findViewById(R.id.text4);
		text6 = (TextView) findViewById(R.id.text6);
		text8 = (TextView) findViewById(R.id.text8);
		text10 = (TextView) findViewById(R.id.text10);
		text12 = (TextView) findViewById(R.id.text12);
		text14 = (TextView) findViewById(R.id.text14);
		// 日历
		// content=(ViewPager)findViewById(R.id.content);

		// add_year=(ImageView) findViewById(R.id.add_year);
		add_mouth = (ImageView) findViewById(R.id.add_mouth);
		delete_mouth = (ImageView) findViewById(R.id.delete_mouth);
		// delete_year=(ImageView) findViewById(R.id.delete_year);
		week = (GridView) findViewById(R.id.week);
		days = (MyGridView) findViewById(R.id.days);
		showdate = (TextView) findViewById(R.id.showdate);
		calendar = (LinearLayout) findViewById(R.id.calendar);
		crs_calendar = (LinearLayout) findViewById(R.id.crs_calendar);
		gridView2 = (MyGridView) findViewById(R.id.gridView2);
		
		
		skilllistview=(ListView) findViewById(R.id.cuirushiskillslist);
		
		share=(TextView) findViewById(R.id.share);

	}

	private void init() {

		MyLoadingProgress.showLoadingDialog(this);//显示加载dialog
		//强制获取焦点防止pulltoreflash滑动到listview的第一条
		pullToRefreshScrollView.requestChildFocus(pullToRefreshScrollView.getChildAt(0), backgroudPhoto);
		share.setOnClickListener(this);
		RequestParams params = new RequestParams();
		params.put("job", job);
		params.put("nurseId", id);
		NetworkHelper.doGet(BaseInfo.DETAIL, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					DetailResult result = JsonUtils.getDeatilResult(response);
					bindData(result);
					Log.e("DetailResult---->", result.toString());
				}else {
					MyLoadingProgress.closeLoadingDialog();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				MyLoadingProgress.closeLoadingDialog();
				Toast.makeText(YueSaoDetailActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
			}
		});
		// mListview.setAdapter(commentAdapter);

		ordertext.setOnClickListener(this);
		video.setOnClickListener(this);
		addtowish.setOnClickListener(this);
		submit_comment.setOnClickListener(this);
		allcomment.setOnClickListener(this);
		check_all.setOnClickListener(this);
		// add_year.setOnClickListener(this);
		add_mouth.setOnClickListener(this);
		delete_mouth.setOnClickListener(this);
		// delete_year.setOnClickListener(this);
		work_express_content.setOnClickListener(this);
		day1.setOnClickListener(this);
		day2.setOnClickListener(this);
		day3.setOnClickListener(this);
		day4.setOnClickListener(this);
		day4.setOnClickListener(this);
		check_zhengshu.setOnClickListener(this);
		show_all_tv.setOnClickListener(this);
		
		onSkillsClicklisener clickListener = new onSkillsClicklisener();
		if ("SHORT_YS".equals(job) || "YS".equals(job)) {
			yuyingshiskills.setVisibility(View.GONE);
			cuirushiskills.setVisibility(View.GONE);
			check1.setOnClickListener(clickListener);
			check2.setOnClickListener(clickListener);
			check3.setOnClickListener(clickListener);
			check4.setOnClickListener(clickListener);
		}
		if ("SHORT_YYS".equals(job) || "YYS".equals(job)) {
			yuesaoskills.setVisibility(View.GONE);
			cuirushiskills.setVisibility(View.GONE);
			text1.setOnClickListener(clickListener);
			text4.setOnClickListener(clickListener);
			text6.setOnClickListener(clickListener);
			text8.setOnClickListener(clickListener);
			text10.setOnClickListener(clickListener);
			text12.setOnClickListener(clickListener);
			text14.setOnClickListener(clickListener);

		}
		if ("CRS".equals(job)) {
			yuesaoskills.setVisibility(View.GONE);
			yuyingshiskills.setVisibility(View.GONE);
		}

		kongjian.setOnClickListener(this);

	}

	private class onSkillsClicklisener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.check1:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/ys_project1.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.check2:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/ys_project2.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.check3:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/ys_project3.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.check4:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/ys_project4.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text1:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project1.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text4:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project2.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text6:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project3.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text8:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project4.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text10:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project5.html",
						R.drawable.baby_photo,"");
				break;
			case R.id.text12:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project6.html",
						R.drawable.baby_photo,"");
			case R.id.text14:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this, "file:///android_asset/yys_project7.html",
						R.drawable.baby_photo,"");
				break;
			
			}
		}

	}

	/**
	 * 绑定数据到界面
	 * 
	 * @param result
	 */
	protected void bindData(DetailResult result) {
		
		weeks= new ArrayList<>();
		weeks.add("日");
		weeks.add("一");
		weeks.add("二");
		weeks.add("三");
		weeks.add("四");
		weeks.add("五");
		weeks.add("六");
		/**
		 * 绑定nursejob部分
		 */
		nurseJobBean = result.getNursejob();
		baseBean = result.getNursebase();
		if (baseBean == null) {
			return;
		}
		if (nurseJobBean == null) {
			return;
		}
		if (nurseJobBean.getResume() != null) {
			work_express_content.setText(NetworkHelper.showFWBText(nurseJobBean.getResume()));
		} else {
			work_express_content.setText("");
		}
		orders = (ArrayList<Order>) result.getOrders();
		if (baseBean == null) {
			Toast.makeText(this, "没有baseBean信息", 0).show();
			return;
		}
		if (nurseJobBean == null) {
			Toast.makeText(this, "没有nurseJobBean信息", 0).show();
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

		if (nurseJobBean != null && baseBean != null) {
//			String backgroud=null;
//			if (nurseJobBean.getWorkImageArrays()!=null) {
//				String[] workimages=nurseJobBean.getWorkImageArrays();
//				if (workimages.length>0) {
//					backgroud=workimages[0];
//					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + backgroud, backgroudPhoto,
//							MyApplication.options_image,new imageLoadingLisenter());
//				}else {
//					MyLoadingProgress.closeLoadingDialog();
//				}
//			}
//			else {
//				MyLoadingProgress.closeLoadingDialog();
//			}
			if (nurseJobBean.getWorkImages()!=null&&!"".equals(nurseJobBean.getWorkImages()) ) {
				ArrayList<String> workimages=(ArrayList<String>) JSON.parseArray(nurseJobBean.getWorkImages(), String.class);
				if (workimages.size()>0) {
					ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + workimages.get(0), backgroudPhoto,
							MyApplication.options_image,new imageLoadingLisenter());
					backgroudPhoto.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ImageZoom.showBigImgaes(YueSaoDetailActivity.this, nurseJobBean.getWorkImages(),0);
						}
					});
				}else {
					MyLoadingProgress.closeLoadingDialog();
				}
			}
			else {
				MyLoadingProgress.closeLoadingDialog();
			}
			
			String image=baseBean.getImage();
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + image, nursePhoto,
					MyApplication.options_photo);
			// if (nurseJobBean.getNurseName()!=null) {
			nurseName.setText(baseBean.getRealName());
			// }
			// if (nurseJobBean.getJobNumber()!=null) {
			nurseJobNumber.setText("工号：" + nurseJobBean.getJobNumber());
			// }
			if (nurseJobBean.getJob() != null) {
				if ("YS".equals(nurseJobBean.getJob()) || "SHORT_YS".equals(nurseJobBean.getJob())) {
					ysoryys.setVisibility(View.VISIBLE);
					nurseType.setText("月嫂");
					nurseLevel.setText(nurseJobBean.getJobTitle() + "");
					nurseCurrentPrice.setText("￥" + nurseJobBean.getShowPrice());
					if ("SHORT_YS".equals(nurseJobBean.getJob())) {
						nurseMarketPrice.setText("市场价：" + nurseJobBean.getMarketPrice() + "元/天");
					}else {
						nurseMarketPrice.setText("市场价：" + nurseJobBean.getMarketPrice() + "元/26天");
					}
					
				}
				if ("YYS".equals(nurseJobBean.getJob()) || "SHORT_YYS".equals(nurseJobBean.getJob())) {
					ysoryys.setVisibility(View.VISIBLE);
					nurseType.setText("育婴师");
					nurseLevel.setText(nurseJobBean.getJobTitle() + "");
					nurseCurrentPrice.setText("￥" + nurseJobBean.getShowPrice());
					if ("SHORT_YYS".equals(nurseJobBean.getJob())) {
						nurseMarketPrice.setText("市场价：" + nurseJobBean.getMarketPrice() + "元/天");
					}else {
						nurseMarketPrice.setText("市场价：" + nurseJobBean.getMarketPrice() + "元/26天");
					}
					
				}
				if ("CRS".equals(nurseJobBean.getJob())) {
					cuishi_stars.setVisibility(View.GONE);
					ysoryys.setVisibility(View.VISIBLE);
					nurseType.setText("催乳师");
					nurseLevel.setText(nurseJobBean.getJobTitle() + "");
					nurseMarketPrice.setVisibility(View.GONE);
					nurseCurrentPrice.setText("￥" + nurseJobBean.getShowPrice());
					List<Skill> skills= result.getSkyiies();
					if (skills!=null) {
						SkillsAdapter adapter=new SkillsAdapter(YueSaoDetailActivity.this, skills, R.layout.skill_item);
						skilllistview.setAdapter(adapter);
					}
					if (nurseJobBean.getLevel() != null) {
						ratingBar1.setProgress(nurseJobBean.getLevelScore());
					} else {
						ratingBar1.setProgress(0);
					}
				}
			}
			nurseAge.setText(baseBean.getAge() + "岁");
			nurseWorkEx.setText("从业" + nurseJobBean.getSeniority() + "年");
			nurseMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			nurseAddress.setText("现居地：" + baseBean.getCurrentAddress());
			nurseHometown.setText("籍贯：" + baseBean.getHometown());
			if (result.getMediaAmount() == 0) {
				hasVideo = false;
			} else {
				hasVideo = true;
			}
		}
		like.setText(result.getScannerAmount() + "");
		comment_num.setText("有" + result.getOrdercommentAmunt() + "位雇主对她进行了评价");
		/**
		 * 绑定证书部分
		 */
		if (result.getCertificates() != null) {
			List<Certificate> certificates = result.getCertificates();
			YueSaoGridViewAdapter adapter = new YueSaoGridViewAdapter(this, certificates,
					R.layout.activity_yuesao_gridview_item);
			gridView.setAdapter(adapter);
		}
		/**
		 * 绑定评论部分
		 */
		if (result.getOrdercomments() != null) {
			List<OrderComments> comments = result.getOrdercomments();
			CommentListAdapter adapter = new CommentListAdapter(this, comments, R.layout.comment_item);
			mListview.setAdapter(adapter);
		}

		/**
		 * 绑定信件
		 */
		if (result.getLetter() != null) {
			if (result.getLetter().getTitle() != null) {
				letter_title.setText(result.getLetter().getTitle());
			}
			if (result.getLetter().getContent() != null) {
				String htmlcontent = result.getLetter().getContent();
				// String htmlcontent="<p>aaskdkas</p>";
				Log.e("hmtl", htmlcontent);

				Spanned content = Html.fromHtml(htmlcontent);
				letter_content.setText(content);
			} else {
				letter_content.setText("");
			}
			if (result.getLetter().getImages() != null) {
				ImageLoader.getInstance().displayImage(
						BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + result.getLetter().getImages(), letter_image,
						MyApplication.options_image);
			}
		} else {
			letter_content.setText("");
		}
		/**
		 * 绑定最下面nursebase部分
		 */
		if (baseBean.getHeight() != null) {
			message_height.setText("身高：" + baseBean.getHeight() + "cm");
		}
		if (baseBean.getEducation() != null) {
			message_edu.setText("文化程度：" + EasyMotherUtils.EDU[Integer.valueOf(baseBean.getEducation())] );
		}
		if (baseBean.getPersionCharacter() != null) {
			message_weight.setText("性格：" + baseBean.getPersionCharacter());
			message_weight.setLines(1);
		}
		if (baseBean.getProficiency() != null) {
			message_pth.setText("普通话水平：" + EasyMotherUtils.PTH[Integer.valueOf(baseBean.getProficiency())]);
		}
		if (baseBean.getYearLunar() != null) {
			message_sx.setText("生肖：" + baseBean.getYearLunar());
		}
		 if (baseBean.getConstellation() !=null) {
		 message_xz.setText("星座："+baseBean.getConstellation());
		 }
		if (nurseJobBean.getEmploymentDate()!= null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			message_time.setText("何时从业：" + dateFormat.format(nurseJobBean.getEmploymentDate()));
		}
		if (baseBean.getCurrentAddress() != null) {
			message_address.setText("现居地址：" + baseBean.getCurrentAddress());
		}
		if ("CRS".equals(job)) {
			calendar.setVisibility(View.GONE);
			crs_calendar.setVisibility(View.VISIBLE);
			aadpter1 = new OrderCRSPreocessGridViewAdapter(this, EasyMotherUtils.getTime(), R.layout.crs_gridview_item,
					orders);
			gridView2.setAdapter(aadpter1);

		} else {
			// 绑定calender
			
			WeekAdapter weekAdapter = new WeekAdapter(this, weeks, R.layout.crs_gridview_item2);
			week.setAdapter(weekAdapter);
			List<Integer> date = new ArrayList<>();
			for (int i = 0; i < 42; i++) {
				date.add(i);
			} 
			aadpter = new CalenderAadpter(this, date, R.layout.crs_gridview_item);
			aadpter.setOrders(orders);
			days.setAdapter(aadpter);
			Calendar currentDate = aadpter.setCurrtentMonth(0);
//			aadpter.notifyDataSetChanged();
			showdate.setText(currentDate.get(Calendar.YEAR) + "年" + (currentDate.get(Calendar.MONTH)+1 )+ "月");
		
			
		}
		
		
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		try {
//			Date date=dateFormat.parse("2015-10-7 00:00:00");
//			Calendar today_c=Calendar.getInstance();
//			today_c.setTime(date);
//			int day=today_c.get(Calendar.DAY_OF_WEEK);
//			int temp=day;
//			if (day+3>7) {
//				temp=(day+3)%7;
//			}
//			day4.setText("星期"+weeks.get(temp-1));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Date today=new Date(System.currentTimeMillis());
		Calendar today_c=Calendar.getInstance();
		today_c.setTime(today);
		int day=today_c.get(Calendar.DAY_OF_WEEK);
		int temp=day;
		if (day+3>7) {
			temp=(day+3)%7;
		}
		day4.setText("星期"+weeks.get(temp-1));

	}

	@Override
	public void onClick(View arg0) {
		
		Calendar currentDate = null;
		Intent intent =getIntent();
		intent.putParcelableArrayListExtra("orders", orders);
		intent.putExtra("nursebase", baseBean);
		intent.putExtra("nursejob", nurseJobBean);
		
		switch (arg0.getId()) {

		case R.id.buy_now:
			if ("YS".equals(nurseJobBean.getJob()) || "YYS".equals(nurseJobBean.getJob()) ||"SHORT_YS".equals(nurseJobBean.getJob()) || "SHORT_YYS".equals(nurseJobBean.getJob())) {
				intent.setClass(this, OrderYSandYYSProcess.class);
			}
			if ("CRS".equals(nurseJobBean.getJob())) {
				intent.setClass(this, CuiRuiShiProjectActivity.class);
			}
			startActivity(intent);
			break;

		case R.id.check_zhengshu:
			intent.putExtra("jobId", nurseJobBean.getId());
			intent.setClass(this, ZhengshuListActivity.class);
			startActivity(intent);
			break;
		case R.id.share:
			WeiXinUtils.shareDownloadUrl(this);
			break;
		case R.id.allcomment:
			intent.setClass(this, CommentListActivity.class);
			startActivity(intent);
			break;
		case R.id.show_all:
			if (show_all) {
				show_all = false;
				work_express_content.setEllipsize(null);
				work_express_content.setSingleLine(false);
			} else {
				show_all = true;
				work_express_content.setEllipsize(TruncateAt.valueOf("END"));
			}
			break;
		case R.id.check_all:
			intent.putExtra("nurseId", baseBean.getId());
			intent.putExtra("job", nurseJobBean.getJob());
			intent.setClass(this, LetterListActivity.class);
			startActivity(intent);
			break;
		case R.id.video:
			// if (hasVideo) {
			// EasyMotherUtils.goActivity(this, VideoListActivity.class);
			// }
			// 测试
			intent.setClass(this, VideoListActivity.class);
			startActivity(intent);
			break;

		case R.id.kongjian:
			intent.setClass(this, HuLiShiZoneDetailActivity.class);
			intent.putExtra("id", baseBean.getId());
			startActivity(intent);
			break;
		case R.id.submit_comment:
			
			intent.setClass(this, CommentActivity.class);
//			intent.putExtra("nursebase", baseBean);
			intent.putExtra("type", "detail");
//			intent.putExtra("nursejob", nurseJobBean);
			startActivity(intent);
			break;
		case R.id.add:
			RequestParams params = new RequestParams();
			params.put("jobId", nurseJobBean.getId());
			params.put("startTime", intent.getStringExtra("startTime"));
			params.put("endTime", intent.getStringExtra("endTime"));
			NetworkHelper.doGet(BaseInfo.ADD_TO_WISH, params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (JsonUtils.getRootResult(response).getIsSuccess()) {
						Toast.makeText(YueSaoDetailActivity.this, "添加心愿单成功", 0).show();
					} else {
						Toast.makeText(YueSaoDetailActivity.this,
								"您已收藏过该护理师！" , 0).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("添加心愿单连接服务器失败", responseString);
					Toast.makeText(YueSaoDetailActivity.this, "请检查网络！", 0).show();
				}
			});

			break;

		// case R.id.add_year:
		// currentDate=aadpter.setCurrtentYear(-1);
		// aadpter.notifyDataSetChanged();
		// showdate.setText(currentDate.get(Calendar.YEAR)+"年"+currentDate.get(Calendar.MONTH)+"月");
		// break;
		case R.id.add_mouth:
			currentDate = aadpter.setCurrtentMonth(1);
			aadpter.notifyDataSetChanged();
			showdate.setText(currentDate.get(Calendar.YEAR) + "年" + (currentDate.get(Calendar.MONTH)+1 )+ "月");
			break;
		case R.id.delete_mouth:
			currentDate = aadpter.setCurrtentMonth(-1);
			aadpter.notifyDataSetChanged();
			showdate.setText(currentDate.get(Calendar.YEAR) + "年" +(currentDate.get(Calendar.MONTH) +1)+ "月");
			break;
		// case R.id.delete_year:
		// currentDate=aadpter.setCurrtentYear(1);
		// aadpter.notifyDataSetChanged();
		// showdate.setText(currentDate.get(Calendar.YEAR)+"年"+currentDate.get(Calendar.MONTH)+"月");
		// break;
		case R.id.day1:
			clearState();
			day1.setTextColor(getResources().getColor(R.color.white));
			day1.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			aadpter1.setCurrentDay();
			aadpter1.notifyDataSetChanged();
			break;

		case R.id.day2:
			clearState();
			day2.setTextColor(getResources().getColor(R.color.white));
			day2.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			aadpter1.setTomorrow();
			aadpter1.notifyDataSetChanged();
			break;
		case R.id.day3:
			clearState();
			day3.setTextColor(getResources().getColor(R.color.white));
			day3.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			aadpter1.setTheDayAffterTomorrow();
			aadpter1.notifyDataSetChanged();
			break;
		case R.id.day4:
			clearState();
//			Date today=new Date(System.currentTimeMillis());
//			Calendar today_c=Calendar.getInstance();
//			today_c.setTime(today);
//			int day=today_c.get(Calendar.DAY_OF_WEEK)+3;
//			day4.setText("星期"+weeks.get(day));
			day4.setTextColor(getResources().getColor(R.color.white));
			day4.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			aadpter1.setForthDay();
			aadpter1.notifyDataSetChanged();
			break;

		}

	}

	private void clearState() {
		day1.setTextColor(getResources().getColor(R.color.blacktext));
		day2.setTextColor(getResources().getColor(R.color.blacktext));
		day3.setTextColor(getResources().getColor(R.color.blacktext));
		day4.setTextColor(getResources().getColor(R.color.blacktext));
		day1.setBackgroundColor(getResources().getColor(R.color.white));
		day2.setBackgroundColor(getResources().getColor(R.color.white));
		day3.setBackgroundColor(getResources().getColor(R.color.white));
		day4.setBackgroundColor(getResources().getColor(R.color.white));

	}
	
	/**
	 * 图片加载完成取消dialog
	 * @author asdfgh
	 *
	 */
	private class imageLoadingLisenter extends SimpleImageLoadingListener{
		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			MyLoadingProgress.closeLoadingDialog();
			super.onLoadingCancelled(imageUri, view);
		}
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			MyLoadingProgress.closeLoadingDialog();
			super.onLoadingComplete(imageUri, view, loadedImage);
		}
		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			MyLoadingProgress.closeLoadingDialog();
			super.onLoadingFailed(imageUri, view, failReason);
		}
	}

}
