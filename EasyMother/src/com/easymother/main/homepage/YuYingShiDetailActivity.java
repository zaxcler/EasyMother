package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.DetailResult;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.community.HuLiShiZoneDetailActivity;
import com.easymother.main.my.CommentActivity;
import com.easymother.main.my.CommentListActivity;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YuYingShiDetailActivity extends Activity implements OnClickListener{
	private GridView gridView;//证书列表
	private PullToRefreshScrollView pullToRefreshScrollView;//下拉刷新空间
	
	private MyListview mListview;//雇主评价
	
	private RelativeLayout video;//点击显示video列表
	private RelativeLayout kongjian;//点击显示护理师空间
	
	private TextView order;//预约
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
	private TextView check1;//查看1
	private TextView check2;//查看2
	private TextView check3;//查看3
	private TextView check4;//查看4
	
	private NurseJobBean nurseJobBean;//nursejob
	private NurseBaseBean baseBean;//
	
	private boolean hasVideo=false;//是否有视频
	private boolean hasKongjian=false;//是否有空间
	private Intent intent;
	
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
						EasyMotherUtils.goActivity(YuYingShiDetailActivity.this,
								MyWishListActivity.class);
					}
				});
			}
		});
		EasyMotherUtils.initTitle(this, "育婴师详情", true);
		intent=getIntent();
		id=intent.getIntExtra("id", 0);
		job=intent.getStringExtra("job");
		
		findView();
		init();
	}

	
	private void findView() {
		gridView=(GridView) findViewById(R.id.gridView1);
		mListview=(MyListview) findViewById(R.id.comment);
		video=(RelativeLayout) findViewById(R.id.video);
		kongjian=(RelativeLayout) findViewById(R.id.kongjian);
		order=(TextView) findViewById(R.id.buy_now);
		submit_comment=(TextView) findViewById(R.id.submit_comment);
		addtowish=(TextView) findViewById(R.id.add);
		like=(TextView) findViewById(R.id.like);
		allcomment=(TextView) findViewById(R.id.allcomment);
		check_all=(TextView) findViewById(R.id.check_all);
		check1=(TextView) findViewById(R.id.check1);
		check2=(TextView) findViewById(R.id.check2);
		check3=(TextView) findViewById(R.id.check3);
		check4=(TextView) findViewById(R.id.check4);
		
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
		
		
	}
	private void init() {
		
		//-------测试数据
		List<TestBean> beans=new ArrayList<TestBean>();
		TestBean bean1=new TestBean();
		
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
		beans.add(bean1);
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
		//----------后期改为网络访问	
//		YueSaoGridViewAdapter adapter=new YueSaoGridViewAdapter(this, beans, R.layout.activity_yuesao_gridview_item);
//		gridView.setAdapter(adapter);
		//-------测试数据
				List<TestBean> beans1=new ArrayList<TestBean>();
				beans1.add(bean1);
				beans1.add(bean1);
		EmployerCommentAdapter commentAdapter=new EmployerCommentAdapter(this, beans1, R.layout.comment_item);
		mListview.setAdapter(commentAdapter);
		
		order.setOnClickListener(this);
		video.setOnClickListener(this);
		addtowish.setOnClickListener(this);
		submit_comment.setOnClickListener(this);
		allcomment.setOnClickListener(this);
		check_all.setOnClickListener(this);
		check1.setOnClickListener(this);
		check2.setOnClickListener(this);
		check3.setOnClickListener(this);
		check4.setOnClickListener(this);
		kongjian.setOnClickListener(this);
		
		
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
				if ("YS".equals(nurseJobBean.getJob())) {
					nurseType.setText("月嫂");
				}
				if ("YYS".equals(nurseJobBean.getJob())) {
					nurseType.setText("育婴师");
				}
			}
			nurseAge.setText(baseBean.getAge()+"岁");
			nurseWorkEx.setText("从业"+nurseJobBean.getSeniority()+"年");
			nurseLevel.setText(nurseJobBean.getLevel()+"");
			nurseCurrentPrice.setText("￥"+nurseJobBean.getPrice()+"元/26天");
			
			nurseMarketPrice.setText("市场价："+nurseJobBean.getMarketPrice()+"元/26天");
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
		
	}


	@Override
	public void onClick(View arg0) {
		Intent intent=new Intent();
		switch (arg0.getId()) {
		case R.id.check1:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.check2:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.check3:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.check4:
			EasyMotherUtils.showDialog(this,"file:///android_asset/demo.html",R.drawable.pic1);
			break;
		case R.id.buy_now:
			intent.setClass(this, OrderYSandYYSProcess.class);
			intent.putExtra("nursebase", baseBean);
			intent.putExtra("nursejob", nurseJobBean);
			startActivity(intent);
			break;

		case R.id.allcomment:
			EasyMotherUtils.goActivity(this, CommentListActivity.class);
			break;
		case R.id.check_all:
			EasyMotherUtils.goActivity(this, LetterListActivity.class);
			break;
		case R.id.video:
//			if (hasVideo) {
//				EasyMotherUtils.goActivity(this, VideoListActivity.class);
//			}
			//测试
			EasyMotherUtils.goActivity(this, VideoListActivity.class);
			break;
			
		case R.id.kongjian:
//			if (hasKongjian) {
//				EasyMotherUtils.goActivity(this, VideoListActivity.class);
//			}
			//测试
			EasyMotherUtils.goActivity(this, HuLiShiZoneDetailActivity.class);
			break;
		case R.id.submit_comment:
			EasyMotherUtils.goActivity(this, CommentActivity.class);
			break;
		case R.id.add:
			RequestParams params=new RequestParams();
			params.put("jobId", id);
			params.put("startTime", intent.getStringExtra("startTime"));
			params.put("endTime", intent.getStringExtra("endTime"));
			NetworkHelper.doGet(BaseInfo.ADD_TO_WISH, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (JsonUtils.getRootResult(response).getIsSuccess()) {
						Toast.makeText(YuYingShiDetailActivity.this, "添加心愿单成功", 0).show();
					}else {
						Toast.makeText(YuYingShiDetailActivity.this, "添加心愿单失败,"+ JsonUtils.getRootResult(response).getMessage(), 0).show();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("添加心愿单连接服务器失败", responseString);
					Toast.makeText(YuYingShiDetailActivity.this, "连接服务器失败", 0).show();
				}
			});
				
				break;
		}
		
		
	}


}
