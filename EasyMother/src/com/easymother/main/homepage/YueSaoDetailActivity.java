package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.Certificate;
import com.easymother.bean.DetailResult;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.Order;
import com.easymother.bean.OrderComments;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyListview;
import com.easymother.main.MainActivity;
import com.easymother.main.R;
import com.easymother.main.community.HuLiShiZoneDetailActivity;
import com.easymother.main.my.CommentActivity;
import com.easymother.main.my.CommentListActivity;
import com.easymother.main.my.CommentListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YueSaoDetailActivity extends Activity implements OnClickListener{ 
	private GridView gridView;//证书列表
	private PullToRefreshScrollView pullToRefreshScrollView;//下拉刷新空间
	
	private MyListview mListview;//雇主评价
	
	private RelativeLayout video;//点击显示video列表
	private RelativeLayout kongjian;//点击显示护理师空间
	
	private TextView ordertext;//预约
	private TextView addtowish;//添加到心愿单
	private TextView submit_comment;//评价
	private int id;//护理师id
	private String job;//护理师职务
	
	private ImageView backgroudPhoto;//最上面的背景
	private CircleImageView nursePhoto;//护理师头像
	private TextView nurseName;//护理师姓名
	private TextView nurseJobNumber;//护理师工号
	private TextView nurseType;//护理师类别
	private TextView nurseAge;//护理师年龄
	private TextView nurseWorkEx;//护理师工龄
	private TextView nurseLevel;//护理师等级
	private TextView nurseCurrentPrice;//护理师实际价格
	private TextView nurseHometown;//护理师籍贯
	private TextView nurseAddress;//护理师Z住址
	private TextView nurseMarketPrice;//护理师s市场价格
	private TextView like;//喜欢数量
	private TextView comment_num;//评论数量
	private TextView allcomment;//点击进入评论列表
	private TextView check_all;//查看所有信件
	private LinearLayout yuesaoskills;//月嫂skills
	private LinearLayout yuyingshiskills;//育婴师skills
	private LinearLayout cuirushiskills;//催乳师skills
	private LinearLayout ysoryys;//月嫂或者育婴师的价格
	private LinearLayout cuishi_stars;//催乳师的星星等级
	
	private TextView check1;//查看1
	private TextView check2;//查看2
	private TextView check3;//查看3
	private TextView check4;//查看4
	private TextView text1;//育婴师查看1
	private TextView text4;//育婴师查看2
	private TextView text6;//育婴师查看3
	private TextView text8;//育婴师查看4
	private TextView text10;//育婴师查看5
	private TextView text12;//育婴师查看6
	private TextView text14;//育婴师查看7
	private TextView text16;//育婴师查看8
	
	private TextView message_height;
	private TextView message_edu;
	private TextView message_weight;
	private TextView message_pth;
	private TextView message_sx;
	private TextView message_address;
	private TextView message_xz;
	private TextView message_time;
	
	
	private NurseJobBean nurseJobBean;//nursejob
	private NurseBaseBean baseBean;//
	private ArrayList<Order> orders;//订单 传递到后面的订单流程里
	private boolean hasVideo=false;//是否有视频
	private boolean hasKongjian=false;//是否有空间
	private Intent intent;
	
	private RatingBar ratingBar1;//催乳师的等级
	
	private TextView letter_title;//信件标题
	private TextView letter_content;//信件内容
	private ImageView letter_image;//信件图片
	
	private boolean show_all=false;//是否显示全部的
	private TextView work_express_content;//履历显示内容
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yuesao_detail);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						EasyMotherUtils.goActivity(YueSaoDetailActivity.this,
								MyWishListActivity.class);
					}
				});
			}
		});
		
		intent=getIntent();
		id=intent.getIntExtra("id", 0);
		job=intent.getStringExtra("job");
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
		gridView=(GridView) findViewById(R.id.gridView1);
		mListview=(MyListview) findViewById(R.id.comment);
		video=(RelativeLayout) findViewById(R.id.video);
		kongjian=(RelativeLayout) findViewById(R.id.kongjian);
		ordertext=(TextView) findViewById(R.id.buy_now);
		submit_comment=(TextView) findViewById(R.id.submit_comment);
		addtowish=(TextView) findViewById(R.id.add);
		like=(TextView) findViewById(R.id.like);
		allcomment=(TextView) findViewById(R.id.allcomment);
		check_all=(TextView) findViewById(R.id.check_all);
		check1=(TextView) findViewById(R.id.check1);
		check2=(TextView) findViewById(R.id.check2);
		check3=(TextView) findViewById(R.id.check3);
		check4=(TextView) findViewById(R.id.check4);
		
		work_express_content=(TextView) findViewById(R.id.work_express_content);
		
		
		letter_title=(TextView) findViewById(R.id.letter_title);
		letter_content=(TextView) findViewById(R.id.letter_content);
		letter_image=(ImageView) findViewById(R.id.letter_image);
		
		message_height=(TextView) findViewById(R.id.message_height);
		message_edu=(TextView) findViewById(R.id.message_edu);
		message_weight=(TextView) findViewById(R.id.message_weight);
		message_pth=(TextView) findViewById(R.id.message_pth);
		message_sx=(TextView) findViewById(R.id.message_sx);
		message_address=(TextView) findViewById(R.id.message_address);
		message_xz=(TextView) findViewById(R.id.message_xz);
		message_time=(TextView) findViewById(R.id.message_time);
		
		backgroudPhoto=(ImageView) findViewById(R.id.background_photo);
		nursePhoto=(CircleImageView) findViewById(R.id.photo);
		nurseName=(TextView) findViewById(R.id.name);
		nurseJobNumber=(TextView) findViewById(R.id.num);
		nurseType=(TextView) findViewById(R.id.label);
		nurseAge=(TextView) findViewById(R.id.age);
		nurseWorkEx=(TextView) findViewById(R.id.workyear);
		nurseLevel=(TextView) findViewById(R.id.label_level);
		nurseCurrentPrice=(TextView) findViewById(R.id.price);
		nurseHometown=(TextView) findViewById(R.id.area);
		nurseAddress=(TextView) findViewById(R.id.adrress);
		nurseMarketPrice=(TextView) findViewById(R.id.market_price);
		comment_num=(TextView) findViewById(R.id.comment_num);
		
		yuesaoskills=(LinearLayout) findViewById(R.id.yuesaoskills);
		yuyingshiskills=(LinearLayout) findViewById(R.id.yuyingshiskills);
		cuirushiskills=(LinearLayout) findViewById(R.id.cuirushiskills);
		ysoryys=(LinearLayout) findViewById(R.id.ysoryys);
		cuishi_stars=(LinearLayout) findViewById(R.id.cuishi_stars);
		ratingBar1=(RatingBar) findViewById(R.id.ratingBar1);
		
		//育婴师的技能
		text1=(TextView) findViewById(R.id.text1);
		text4=(TextView) findViewById(R.id.text4);
		text6=(TextView) findViewById(R.id.text6);
		text8=(TextView) findViewById(R.id.text8);
		text10=(TextView) findViewById(R.id.text10);
		text12=(TextView) findViewById(R.id.text12);
		text14=(TextView) findViewById(R.id.text14);
		text16=(TextView) findViewById(R.id.text16);
		
		
	}
	private void init() {
		
		RequestParams params=new RequestParams();
		params.put("job", job);
		params.put("nurseId", id);
		NetworkHelper.doGet(BaseInfo.DETAIL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					DetailResult result=JsonUtils.getDeatilResult(response);
					Log.e("数组", result.getNursejob().getWorkImageArrays()[0].toString());
					bindData(result);
					Log.e("DetailResult---->", result.toString());
				}
			}
		});
		//-------测试数据
//		List<Certificate> beans=new ArrayList<Certificate>();
		
		//----------后期改为网络访问	
//		List<Certificate> beans=new ArrayList<Certificate>();
//		YueSaoGridViewAdapter adapter=new YueSaoGridViewAdapter(this, beans, R.layout.activity_yuesao_gridview_item);
//		gridView.setAdapter(adapter);
		//-------测试数据
//				List<TestBean> beans1=new ArrayList<TestBean>();
//				beans1.add(bean1);
//				beans1.add(bean1);
//		EmployerCommentAdapter commentAdapter=new EmployerCommentAdapter(this, beans1, R.layout.comment_item);
//		mListview.setAdapter(commentAdapter);
		
		ordertext.setOnClickListener(this);
		video.setOnClickListener(this);
		addtowish.setOnClickListener(this);
		submit_comment.setOnClickListener(this);
		allcomment.setOnClickListener(this);
		check_all.setOnClickListener(this);
		onSkillsClicklisener clickListener=new onSkillsClicklisener();
		if ("SHORT_YS".equals(job)||"YS".equals(job)) {
			yuyingshiskills.setVisibility(View.GONE);
			cuirushiskills.setVisibility(View.GONE);
			check1.setOnClickListener(clickListener);
			check2.setOnClickListener(clickListener);
			check3.setOnClickListener(clickListener);
			check4.setOnClickListener(clickListener);
		}
		if ("SHORT_YYS".equals(job)||"YYS".equals(job)) {
			yuesaoskills.setVisibility(View.GONE);
			cuirushiskills.setVisibility(View.GONE);
			text1.setOnClickListener(clickListener);
			text4.setOnClickListener(clickListener);
			text6.setOnClickListener(clickListener);
			text8.setOnClickListener(clickListener);
			text10.setOnClickListener(clickListener);
			text12.setOnClickListener(clickListener);
			text14.setOnClickListener(clickListener);
			text16.setOnClickListener(clickListener);
			
		}
		if ("CRS".equals(job)) {
			yuesaoskills.setVisibility(View.GONE);
			yuesaoskills.setVisibility(View.GONE);
		}
		
		kongjian.setOnClickListener(this);
		
		
	}
	
	private class onSkillsClicklisener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.check1:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.check2:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.check3:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.check4:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text1:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text4:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text6:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text8:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text10:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text12:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
			case R.id.text14:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			case R.id.text16:
				EasyMotherUtils.showDialog(YueSaoDetailActivity.this,"file:///android_asset/demo.html",R.drawable.pic1);
				break;
			}
		}
		
	}

	/**
	 * 绑定数据到界面
	 * @param result
	 */
	protected void bindData(DetailResult result) {
		/**
		 * 绑定nursejob部分
		 */
		nurseJobBean=result.getNursejob();
		baseBean=result.getNursebase();
		
		if (nurseJobBean.getReferee()!=null) {
			work_express_content.setText(nurseJobBean.getReferee());
		}else {
			work_express_content.setText("");
		}
		orders=(ArrayList<Order>) result.getOrders();
		if (baseBean==null) {
			Toast.makeText(this, "没有baseBean信息", 0).show();
			return;
		}
		if (nurseJobBean==null) {
			Toast.makeText(this, "没有nurseJobBean信息", 0).show();
			return;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		
		try {
			Log.e("intent.getStringExtra()", intent.getStringExtra("startTime"));
			Date startTime = format.parse(intent.getStringExtra("startTime"));
			Date endTime=format.parse(intent.getStringExtra("endTime"));
			Log.e("startTime", startTime.toString());
			baseBean.setEmploymentStartTime(startTime);
			baseBean.setEmploymentEndTime(endTime);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		if (nurseJobBean!=null&& baseBean!=null) {
			String image=nurseJobBean.getWorkImageArrays()[0];
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+image,backgroudPhoto);
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+image,nursePhoto);
//			if (nurseJobBean.getNurseName()!=null) {
				nurseName.setText(baseBean.getRealName());
//			}
//			if (nurseJobBean.getJobNumber()!=null) {
				nurseJobNumber.setText("工号："+nurseJobBean.getJobNumber());
//			}
			if (nurseJobBean.getJob()!=null) {
				if ("YS".equals(nurseJobBean.getJob())||"SHORT_YS".equals(nurseJobBean.getJob())) {
					ratingBar1.setVisibility(View.GONE);
					cuishi_stars.setVisibility(View.GONE);
					nurseType.setText("月嫂");
					nurseLevel.setText(nurseJobBean.getJobTitle()+"");
					nurseCurrentPrice.setText("￥"+nurseJobBean.getPrice()+"元/26天");
					nurseMarketPrice.setText("市场价："+nurseJobBean.getMarketPrice()+"元/26天");
				}
				if ("YYS".equals(nurseJobBean.getJob())|| "SHORT_YYS".equals(nurseJobBean.getJob())) {
					ratingBar1.setVisibility(View.GONE);
					cuishi_stars.setVisibility(View.GONE);
					nurseType.setText("育婴师");
					nurseLevel.setText(nurseJobBean.getJobTitle()+"");
					nurseCurrentPrice.setText("￥"+nurseJobBean.getPrice()+"元/26天");
					nurseMarketPrice.setText("市场价："+nurseJobBean.getMarketPrice()+"元/26天");
				}
				if ("CRS".equals(nurseJobBean.getJob())) {
					ysoryys.setVisibility(View.GONE);
					nurseType.setText("催乳师");
					if (nurseJobBean.getLevel()!=null) {
						ratingBar1.setProgress(nurseJobBean.getLevelScore());
					}else {
						ratingBar1.setProgress(0);
					}
				}
			}
			nurseAge.setText(baseBean.getAge()+"岁");
			nurseWorkEx.setText("从业"+nurseJobBean.getSeniority()+"年");
			nurseMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			nurseAddress.setText("现居地："+baseBean.getCurrentAddress());
			nurseHometown.setText("籍贯："+baseBean.getHometown());
			if (result.getMediaAmount()==0) {
				hasVideo=false;
			}else {
				hasVideo=true;
			}
		}
		like.setText(result.getScannerAmount()+"");
		comment_num.setText("有"+result.getOrdercommentAmunt()+"位雇主对她进行了评价");
		/**
		 * 绑定证书部分
		 */
		if (result.getCertificates()!=null) {
			List<Certificate> certificates=result.getCertificates();
			YueSaoGridViewAdapter adapter=new YueSaoGridViewAdapter(this, certificates, R.layout.activity_yuesao_gridview_item);
			gridView.setAdapter(adapter);
		}
		/**
		 * 绑定评论部分
		 */
		if (result.getOrdercomments()!=null) {
			List<OrderComments> comments=result.getOrdercomments();
			CommentListAdapter adapter=new CommentListAdapter(this, comments, R.layout.comment_item);
			mListview.setAdapter(adapter);
		}
		
		/**
		 * 绑定信件
		 */
		if (result.getLetter()!=null) {
			if (result.getLetter().getTitle()!=null) {
				letter_title.setText(result.getLetter().getTitle());
			}
			if (result.getLetter().getContent()!=null) {
				String htmlcontent=result.getLetter().getContent();
//				String htmlcontent="<p>aaskdkas</p>";
				Log.e("hmtl", htmlcontent);
				
				Spanned content=Html.fromHtml(htmlcontent);
				letter_content.setText(content);
			}else {
				letter_content.setText("");
			}
			if (result.getLetter().getImages()!=null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+result.getLetter().getImages(), letter_image,MyApplication.options_image);
			}
		}
		else {
			letter_content.setText("");
		}
		
		/**
		 * 绑定最下面nursebase部分
		 */
		if (baseBean.getHeight()!=null) {
			message_height.setText("身高："+baseBean.getHeight()+"cm");
		}
		if (baseBean.getEducation()!=null) {
			message_edu.setText("文化程度："+baseBean.getEducation());
		}
		if (baseBean.getWeight()!=null) {
			message_weight.setText("体重："+baseBean.getWeight()+"kg");
		}
		if (baseBean.getProficiency()!=null) {
			message_pth.setText("普通话水平："+baseBean.getProficiency());
		}
		if (baseBean.getYearLunar()!=null) {
			message_sx.setText("生肖："+baseBean.getYearLunar());
		}
//		if (baseBean.get!=null) {
//			message_xz.setText("现居地址："+baseBean.getCurrentAddress());
//		}
		if (baseBean.getSeniority()!=null) {
			message_time.setText("工作经验："+baseBean.getSeniority()+"年");
		}
		
	}


	@Override
	public void onClick(View arg0) {
		Intent intent=new Intent();
		intent.putParcelableArrayListExtra("orders",  orders);
		intent.putExtra("nursebase", baseBean);
		intent.putExtra("nursejob", nurseJobBean);
		switch (arg0.getId()) {
		
		case R.id.buy_now:
			if ("YS".equals(nurseJobBean.getJob())||"YYS".equals(nurseJobBean.getJob())) {
				intent.setClass(this, OrderYSandYYSProcess.class);
			}
			if ("CRS".equals(nurseJobBean.getJob())) {
				intent.setClass(this, CuiRuiShiProjectActivity.class);
			}
			startActivity(intent);
			break;

		case R.id.allcomment:
			intent.setClass(this, CommentListActivity.class);
			startActivity(intent);
			break;
		case R.id.show_all:
			if (show_all) {
				show_all=false;
				work_express_content.setEllipsize(null);
				work_express_content.setSingleLine(false);
			}else {
				show_all=true;
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
//			if (hasVideo) {
//				EasyMotherUtils.goActivity(this, VideoListActivity.class);
//			}
			//测试
			intent.setClass(this, VideoListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.kongjian:
//			if (hasKongjian) {
//				EasyMotherUtils.goActivity(this, VideoListActivity.class);
//			}
			//测试
			EasyMotherUtils.goActivity(this, HuLiShiZoneDetailActivity.class);
			break;
		case R.id.submit_comment:
			intent.setClass(this, CommentActivity.class);
			intent.putExtra("nursebase", baseBean);
			intent.putExtra("nursejob", nurseJobBean);
			startActivity(intent);
			break;
		case R.id.add:
			RequestParams params=new RequestParams();
			params.put("jobId", nurseJobBean.getId());
			params.put("startTime", intent.getStringExtra("startTime"));
			params.put("endTime", intent.getStringExtra("endTime"));
			NetworkHelper.doGet(BaseInfo.ADD_TO_WISH, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (JsonUtils.getRootResult(response).getIsSuccess()) {
						Toast.makeText(YueSaoDetailActivity.this, "添加心愿单成功", 0).show();
					}else {
						Toast.makeText(YueSaoDetailActivity.this, "添加心愿单失败,"+ JsonUtils.getRootResult(response).getMessage(), 0).show();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("添加心愿单连接服务器失败", responseString);
					Toast.makeText(YueSaoDetailActivity.this, "连接服务器失败", 0).show();
				}
			});
				
				break;
		}
		
		
	}


}
