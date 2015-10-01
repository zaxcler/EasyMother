package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.DoubleSeekBar;
import com.easymother.customview.DoubleSeekBar.OnSeekBarChangeListener;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.TimeCounter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommonListActivity extends Activity {

	private ListView listView;// 下拉刷新控件
//	private MyListview listView;// 下拉刷新控件
//	private ScrollView scrollview;
//	private MyScrollView scrollview;
	private View search_layout;// 搜索布局
	private View search_layout1;// 搜索布局
	// private TextView title;// 标题
	// private ImageView back;// 返回键
	private View title;// 标题栏
	private TextView filter;// 筛选
	private TextView sort;// 分类
	private TextView sort1;// 方式一 
	private TextView sort2;// 方式二
	private TextView sort3;// 方式三
	private TextView sort4;// 方式四
	private String currentSort="E";//当前排序方式
	
	private ImageView imgsort1;// 方式一
	private ImageView imgsort2;// 方式二
	private ImageView imgsort3;// 方式三
	private ImageView imgsort4;// 方式四

	private LinearLayout bottom_layout;// 弹出框显示在这个view的上方
	private float y;//控制搜索框的显示与隐藏的关键
	private RelativeLayout choose_date;//选择时间
	
	private Button loadMore;//加载更多按钮
	private TextView notice;//没有数据的提示信息
	private RequestParams params;//保存所以搜索条件
	private  CommonListAdapter adapter;
	
	private TextView search1;//有两个searche，控制隐藏和显示
	private TextView search2;
	private boolean loading=false;//数据是否在加载中
	private int pageNo=1;
	protected int lowPrice;
	protected int hieghtPrice;
	protected int lowAge;
	protected int hieghtAge;
	private String starttime;//开始时间
	private String endtime;//结束时间
	private TextView start_time_tv;
	private TextView end_time_tv;
	protected boolean isInit=true;//是否是初始化过程
	protected int state;
	private TextView counttime;//共多少天
	private int lastItem;//listview最后一个item
	
	private EditText editText;
	private boolean canReflash=true;//能否加载
	private CharSequence search_name="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yuesao_list);
		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						EasyMotherUtils.goActivity(CommonListActivity.this,
								MyWishListActivity.class);
					}
				});
			}
		});
		params=new RequestParams();
		params.put("sorting", currentSort);
		Intent intent=getIntent();
		String job=intent.getStringExtra("job");
		if ("YS".equals(job)) {
			
			params.put("job", "YS");
			EasyMotherUtils.initTitle(this, "月嫂", true);
		}
		if ("YYS".equals(job)) {
			params.put("job", "YYS");
//			params.put("job", "YYyS");//测试查不到的情况
			EasyMotherUtils.initTitle(this, "育婴师", true);
		}
		
		if ("CRS".equals(job)) {
			params.put("job", "CRS");
			EasyMotherUtils.initTitle(this, "催乳师", true);
		}
		if ("DQHLS".equals(job)) {
			params.put("job", "SHORT");
			EasyMotherUtils.initTitle(this, "短期护理师", true);
		}
		findView();
		init();
	}

	private void findView() {
		filter = (TextView) findViewById(R.id.filter);
		sort = (TextView) findViewById(R.id.sort);

		title = findViewById(R.id.title1);
		bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
		
		start_time_tv=(TextView) findViewById(R.id.starttime);
		end_time_tv=(TextView) findViewById(R.id.endtime);
		
		listView = (ListView) findViewById(R.id.listview);
//		scrollview=(ScrollView) findViewById(R.id.scrollview);
//		scrollview=(MyScrollView) findViewById(R.id.scrollview);
		counttime=(TextView) findViewById(R.id.counttime);
		search_layout=LayoutInflater.from(this).inflate(R.layout.search_item, null);
		search1=(TextView) search_layout.findViewById(R.id.search);
		choose_date=(RelativeLayout) findViewById(R.id.choose_date);
		editText=(EditText)search_layout. findViewById(R.id.search_text);
	}

	private void init() {
//		;

		Date currentdate=new Date(System.currentTimeMillis());
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(currentdate);
		start_time_tv.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
		end_time_tv.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
		
		/**
		 * 默认时间开始是当前时间 和结束时间是明天
		 */
		starttime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		endtime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)+1);
		//搜索的布局的点击时间
		search1.setOnClickListener(new onSeachLayoutClickLisener());
		listView.addHeaderView(search_layout);
		
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				search_name= s;
				RequestParams params1=params;
				params1.put("nurseName", s);
				Log.e("----","1111111");
				doFilter(params1);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				Log.e("----","22222");
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				Log.e("----","33333");
			}
		});
		/*
		 * 初始化加载更多按钮
		 */
		if(loadMore==null){
			loadMore=new Button(CommonListActivity.this);
			loadMore.setBackgroundColor(getResources().getColor(R.color.lightredwine));
			loadMore.setText("加载更多");
			loadMore.setPadding(10, 10, 10, 10);
			loadMore.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					++pageNo;
					Log.e("pageNo", pageNo+"");
					loading(pageNo);
					
				}
			});
		}
		
//		listView.addFooterView(loadMore);
//		listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/**
				 * 因为在listview上面添加了headerview 所以获取adapter的时候先获取包裹在外层的HeaderViewListAdapter，然后获取里层的adapter
				 * 否则会报java.lang.ClassCastException: android.widget.HeaderViewListAdapter cannot be cast to com.easymother.main.homepage.CommonListAdapter
				 * 同时，获取到自己的adapter后，adapter.getCount()方法的数量要减一（减去添加的头部）
				 */
				HeaderViewListAdapter headerViewListAdapter=(HeaderViewListAdapter) arg0.getAdapter();
				CommonListAdapter commonListAdapter=(CommonListAdapter) headerViewListAdapter.getWrappedAdapter();
				NurseBaseBean baseBean=commonListAdapter.getItem(arg2-1);
				if ("YS".equals(baseBean.getJob())) {
					Intent intent=new Intent(CommonListActivity.this,YueSaoDetailActivity.class);
					intent.putExtra("id", baseBean.getNurseId());
					intent.putExtra("job", baseBean.getJob());
					intent.putExtra("startTime", starttime);
					intent.putExtra("endTime", endtime);
					startActivity(intent);
				}
				if ("YYS".equals(baseBean.getJob())) {
					Intent intent=new Intent(CommonListActivity.this,YueSaoDetailActivity.class);
					intent.putExtra("id", baseBean.getNurseId());
//					intent.putExtra("id", 14);//测试
					intent.putExtra("job", baseBean.getJob());
					intent.putExtra("startTime", starttime);
					intent.putExtra("endTime", endtime);
					startActivity(intent);
				}
				if ("CRS".equals(baseBean.getJob())) {
					Intent intent=new Intent(CommonListActivity.this,YueSaoDetailActivity.class);
					intent.putExtra("id", baseBean.getNurseId());
//					intent.putExtra("id", 14);//测试
					intent.putExtra("job", baseBean.getJob());
					intent.putExtra("startTime", starttime);
					intent.putExtra("endTime", endtime);
					intent.putExtra("id", baseBean.getNurseId());
					startActivity(intent);
				}
				
				if ("SHORT_YS".equals(baseBean.getJob())) {
						Intent intent=new Intent(CommonListActivity.this,YueSaoDetailActivity.class);
						intent.putExtra("id", baseBean.getNurseId());
//						intent.putExtra("id", 14);//测试
						intent.putExtra("job", baseBean.getJob());
						intent.putExtra("startTime", starttime);
						intent.putExtra("endTime", endtime);
						intent.putExtra("id", baseBean.getNurseId());
						startActivity(intent);
				}
				if ("SHORT_YYS".equals(baseBean.getJob())) {
					Intent intent=new Intent(CommonListActivity.this,YueSaoDetailActivity.class);
					intent.putExtra("id", baseBean.getNurseId());
//					intent.putExtra("id", 14);//测试
					intent.putExtra("job", baseBean.getJob());
					intent.putExtra("startTime", starttime);
					intent.putExtra("endTime", endtime);
					intent.putExtra("id", baseBean.getNurseId());
					startActivity(intent);
			}
				
			}
		});
		filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showFilterDialog(arg0);
			}
		});
		sort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showSortDialog(arg0);
			}
		});
		LayoutAnimationController controller = new LayoutAnimationController(
				AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listView.setLayoutAnimation(controller);
		listView.startLayoutAnimation();
//		scrollview.setOnReflashListener(new onReflashLisenter() {
//			
//			@Override
//			public void onHeadReflash() {
//				
//			}
//			
//			@Override
//			public void onFootReflash() {
//				++pageNo;//加载之前先自增1
//		        loading(pageNo);
//		        Toast.makeText(CommonListActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				state=scrollState;
				if (view.getLastVisiblePosition()==view.getCount()-1) {
					switch (state) {
					//手指滚动状态
					case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//						ImageLoader.getInstance().pause();
						break;
					//手指抬起状态，屏幕还在滚动
					case OnScrollListener.SCROLL_STATE_FLING:
//						ImageLoader.getInstance().pause();
						break;
					//静止状态
					case OnScrollListener.SCROLL_STATE_IDLE:
//						ImageLoader.getInstance().resume();
						//不在加载状态才能加载
						if (!loading&& canReflash) {
							++pageNo;//加载之前先自增1
					        loading(pageNo);
					        Log.e("=====", "-------------");
						}
						
						break;

					}
					
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				lastItem=firstVisibleItem+visibleItemCount-1;
				//当滑到最下端的时候后显示加载更多按钮
			}
			
		});
		choose_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDateDialog(v);
			}
		});
		doFilter(params);
		isInit=false;

	}
	/**
	 * 显示时间选择框
	 * @param v
	 */
	protected void showDateDialog(View v) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate=dateFormat.parse(starttime);
			Date endDate=dateFormat.parse(endtime);
			final Dialog dialog=new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View view=LayoutInflater.from(this).inflate(R.layout.dialog_chosedate, null);
			dialog.setContentView(view);
			dialog.getWindow().setLayout(MyApplication.getScreen_width()/20*19, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
			DatePicker datePicker1=(DatePicker) view.findViewById(R.id.start_time);
			DatePicker datePicker2=(DatePicker) view.findViewById(R.id.end_time);
			
			Date currentdate;
			 Calendar calendar=Calendar.getInstance();
			if (starttime==null&& "".equals(starttime)) {
				currentdate=new Date(System.currentTimeMillis());
				starttime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+ calendar.get(Calendar.DAY_OF_MONTH);
			}else {
				currentdate=dateFormat.parse(starttime);
			}
			calendar.setTime(currentdate);
//			final Date currentdate=new Date(System.currentTimeMillis());
//			final Calendar calendar=Calendar.getInstance();
//			//开始时间
//			calendar.setTime(startDate);
//			starttime=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
////			endtime=starttime;
			datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
				
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					starttime=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
					start_time_tv.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
					
				}
			});
//			final Calendar calendar1=Calendar.getInstance();
//			//结束时间
//			calendar1.setTime(endDate);
			//初始化结束时间 ，记录上次选择的时间
			 Calendar calendar1=Calendar.getInstance();
			calendar1.setTime(endDate);
	        datePicker2.init(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
				
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			        endtime=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
					end_time_tv.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
					
				}
			});
	        Button dismisse=(Button) view.findViewById(R.id.dismisse);
			dismisse.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			view.findViewById(R.id.comfire).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date startDate=dateFormat.parse(starttime);
						Date endDate=dateFormat.parse(endtime);
						Date currentdate=new Date(System.currentTimeMillis());
						 Calendar calendar=Calendar.getInstance();
						 calendar.setTime(currentdate);
						Date current=dateFormat.parse(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
						int a=TimeCounter.countTimeOfDay(startDate, endDate);
						int b=TimeCounter.countTimeOfDay(current , startDate);
						if (TimeCounter.countTimeOfDay(startDate, endDate)>0 && TimeCounter.countTimeOfDay(current , startDate)>=0) {
							RequestParams params1=params;
							params1.put("startTime", starttime);
							params1.put("endTime", endtime);
							counttime.setText("共"+TimeCounter.countTimeOfDay(startDate, endDate)+"天");
							doFilter(params1);
							dialog.dismiss();
						}else {
							Toast.makeText(CommonListActivity.this,"请检查时间选择是否正确", Toast.LENGTH_SHORT).show();
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			dialog.show();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private class onSeachLayoutClickLisener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Log.e("点击ID--->", String.valueOf(v.getId()));
			switch (v.getId()) {
			case R.id.search:
				//进行搜索网络请求
				Log.e("搜索----》", String.valueOf(v.getId()));
				if ("".equals(search_name)||search_name.equals(null)) {
					Toast.makeText(CommonListActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
					return;
				}
				RequestParams params1=params;
				params1.put("nurseName", search_name);
				doFilter(params1);
				break;

			}
		}
		
	}
	/**
	 * 筛选和排序方法
	 * @param params
	 */
	private void doFilter(RequestParams params){
		//将传入的参数保存到本地params对象中
		params.put("pageNo","1");
		params.put("pageSize","10");
		
		NetworkHelper.doGet(BaseInfo.SEARCH_URL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<NurseBaseBean> list=JsonUtils.getNurseBaseBeanList(response);
//					List<NurseJobBean> list=JsonUtils.getResultList(response, NurseJobBean.class);
					if (notice==null) {
						notice=new TextView(CommonListActivity.this);
						notice.setText("没有匹配的结果");
						notice.setGravity(Gravity.CENTER_HORIZONTAL);
						AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						notice.setLayoutParams(params);
						notice.setBackgroundColor(getResources().getColor(R.color.background));
						notice.setTextColor(getResources().getColor(R.color.boroblacktext));
						listView.addFooterView(notice);
					}
//					if (list.size()>9) {
//						listView.addFooterView(loadMore);
//					}
					/*
					 * 如果list不为空，则显示加载更多，隐藏没有匹配结果
					 * 如果list为空，则显示没有匹配的结果，不显示加载更多
					 */
					if (list.size()<=0) {
						notice.setVisibility(View.VISIBLE);
						loadMore.setVisibility(View.GONE);
					}
					else {
						loadMore.setVisibility(View.VISIBLE);
						notice.setVisibility(View.GONE);
					}
					
					/*
					 * 没有adapter就创建
					 */
					if (adapter==null) {
						adapter = new CommonListAdapter(
								getApplicationContext(), list, R.layout.activity_yuesao_item);
						listView.setAdapter(adapter);
						
					}else {
						/*
						 * 有adapter就刷新一下数据
						 */
						adapter.relaceAll(list);
						Log.e("list-------->", list.toString());
						adapter.notifyDataSetChanged();
					}
//					adapter.addAll(list);
					listView.setAdapter(adapter);
				}
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Toast.makeText(CommonListActivity.this, "连接服务器失败", 0).show();
			}
		});
	}

	/**
	 * 显示筛选dialog
	 * @param arg0
	 */
	protected void showFilterDialog(View arg0) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_filter);
		dialog.getWindow().setLayout(MyApplication.getScreen_width()/20*19, LayoutParams.WRAP_CONTENT);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.alpha_solid);
		DoubleSeekBar seekBar1=(DoubleSeekBar) dialog.findViewById(R.id.seekbar1);
		DoubleSeekBar seekBar2=(DoubleSeekBar) dialog.findViewById(R.id.seekbar2);
		
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(DoubleSeekBar seekBar, double progressLow, double progressHigh) {
				lowAge=(int)progressLow;
				hieghtAge=(int)progressHigh;
			}
			
			@Override
			public void onProgressBefore() {
			}
			
			@Override
			public void onProgressAfter() {
			}
		});
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(DoubleSeekBar seekBar, double progressLow, double progressHigh) {
				lowPrice=(int) progressLow;
				hieghtPrice=(int) progressHigh;
			}
			
			@Override
			public void onProgressBefore() {
			}
			
			@Override
			public void onProgressAfter() {
			}
		});
		Button submit=(Button) dialog.findViewById(R.id.submit);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (lowAge==0 || hieghtAge==0) {
					params.put("firstAge", 20);
					params.put("secondAge", 60);
				}else {
					params.put("firstAge", lowAge);
					params.put("secondAge", hieghtAge);
				}
				if (lowPrice==0 || hieghtPrice==0) {
					params.put("firstPrice", 5000);
					params.put("secondPrice", 15000);
				}else {
					params.put("firstPrice", lowPrice*100);
					params.put("secondPrice", hieghtPrice*100);
				}
				doFilter(params);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/*
	 * 显示排序的dialog
	 */
	protected void showSortDialog(View arg0) {
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_sort);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setLayout(MyApplication.getScreen_width()/20*19, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.alpha_solid);
		sort1 = (TextView) dialog.findViewById(R.id.sort1);
		sort2 = (TextView) dialog.findViewById(R.id.sort2);
		sort3 = (TextView) dialog.findViewById(R.id.sort3);
		sort4 = (TextView) dialog.findViewById(R.id.sort4);
		
		imgsort1 = (ImageView) dialog.findViewById(R.id.imagesort1);
		imgsort2 = (ImageView) dialog.findViewById(R.id.imagesort2);
		imgsort3 = (ImageView) dialog.findViewById(R.id.imagesort3);
		imgsort4= (ImageView) dialog.findViewById(R.id.imagesort4);
		
		sortClickListener listener = new sortClickListener(dialog);
		listener.clearStatus();
		if ("D".equals(currentSort)){
			imgsort1.setVisibility(View.VISIBLE);
		}
		if ("C".equals(currentSort)){
			imgsort2.setVisibility(View.VISIBLE);
		}
		if ("B".equals(currentSort)){
			imgsort3.setVisibility(View.VISIBLE);
		}
		if ("A".equals(currentSort)){
			imgsort4.setVisibility(View.VISIBLE);
		}
		sort1.setOnClickListener(listener);
		sort2.setOnClickListener(listener);
		sort3.setOnClickListener(listener);
		sort4.setOnClickListener(listener);

		dialog.show();
		
	}

	/*
	 * 处理排序方式的dialog的点击事件
	 */
	private class sortClickListener implements OnClickListener {
		private Dialog dialog;

		public sortClickListener(Dialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onClick(View arg0) {
			clearStatus();
			int id = arg0.getId();
			switch (id) {
			case R.id.sort1:
				imgsort1.setVisibility(View.VISIBLE);
				params.put("sorting", "D");//d工龄减
				currentSort="D";
				doFilter(params);
				dialog.dismiss();
				break;

			case R.id.sort2:
				imgsort2.setVisibility(View.VISIBLE);
				params.put("sorting", "C");//c 工龄升
				currentSort="C";
				doFilter(params);
				dialog.dismiss();
				break;
			case R.id.sort3:
				imgsort3.setVisibility(View.VISIBLE);
				params.put("sorting", "B");//B价格减
				currentSort="B";
				doFilter(params);
				dialog.dismiss();
				break;
			case R.id.sort4:
				imgsort4.setVisibility(View.VISIBLE);
				
				params.put("sorting", "A"); //A价格升
				currentSort="A";
				doFilter(params);
				dialog.dismiss();
				break;
			}

		}

		public void clearStatus() {
			imgsort1.setVisibility(View.GONE);
			imgsort2.setVisibility(View.GONE);
			imgsort3.setVisibility(View.GONE);
			imgsort4.setVisibility(View.GONE);
		}
	}

	/**
	 *加载更多数据
	 * @param pageNo 加载的第几页
	 * @return
	 */
	public List<NurseBaseBean> loading(int pageNo){
		loading=true;
		Log.e("加载数据", "---------");
		params.put("pageNo", pageNo+"");
		Log.e("pageNo+loading", pageNo+"");
		Toast.makeText(CommonListActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
		NetworkHelper.doGet(BaseInfo.SEARCH_URL, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					
					List<NurseBaseBean> list=JsonUtils.getNurseBaseBeanList(response);
					if (list.size()==0) {
						CommonListActivity.this.pageNo=1;
//						loadMore.setVisibility(View.GONE);
						canReflash=false;
						Toast.makeText(CommonListActivity.this, "没有更多咯！", 0).show();
					}else {
						canReflash=true;
					}
					adapter.addAll(list);
					adapter.notifyDataSetChanged();
					loading=false;
					
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(CommonListActivity.this, "连接服务器失败！", 0).show();
				loading=false;
				canReflash=false;
			}
		});
		
		return null;
		
	}

	
	//-------------下面弃用-------

	/*
	 * 显示搜索框
	 */

	public void show() {

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		search_layout1.setAnimation(animation);
		search_layout1.startAnimation(animation);
		search_layout1.setVisibility(View.VISIBLE);
	}
	
	

	/*
	 * 隐藏搜索框
	 */
	public void hide() {

		AnimationSet animationSet=new AnimationSet(false);
		title.measure(0, 0);
		int height=title.getMeasuredHeight();
		Animation animation = new AlphaAnimation(1.0f, 0.0f);
		
		animationSet.addAnimation(animation);
		animationSet.setDuration(300);
		animationSet.setFillAfter(false);
		animationSet.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
				search_layout1.setVisibility(View.GONE);
			}
		});
		animationSet.addAnimation(animation);
		search_layout1.setAnimation(animationSet);
		search_layout1.startAnimation(animationSet);

	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent arg1) {
//		float dy;
//		Log.e("log大法", "-----" + y);
//		switch (arg1.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//
//			y = arg1.getY();
//			break;
//
//		case MotionEvent.ACTION_UP:
//
//			break;
//		case MotionEvent.ACTION_MOVE:
//			dy = arg1.getY();
//			if (y > dy) {
//				hide();
//			} else {
//				show();
//			}
//			break;
//		}
//		return super.dispatchTouchEvent(arg1);
//	}
	
}
