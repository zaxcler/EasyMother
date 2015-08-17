package com.easymother.main.homepage;

import java.util.ArrayList;
import java.util.List;
import com.easymother.bean.TestBean;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.EasyMotherUtils.RightButtonLisenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView.MmyScrollListener;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class YuYingShiListActivity extends Activity {

	private ListView listView;// 
	private View search_layout;// 搜索布局
	// private TextView title;// 标题
	// private ImageView back;// 返回键
	private View title;// 标题栏
	private TextView filter;// 筛选
	private TextView sort;// 分类
	private TextView sort1;// 方式一
	private TextView sort2;// 方式二
	private TextView sort3;// 方式三
	private TextView sort4;// 方式四

	private LinearLayout bottom_layout;// 弹出框显示在这个view的上方
	private float y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yuyingshi_list);

		EasyMotherUtils.setRightButton(new RightButtonLisenter() {

			@Override
			public void addRightButton(ImageView imageView) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						EasyMotherUtils.goActivity(YuYingShiListActivity.this,
								MyWishListActivity.class);
					}
				});
			}
		});
		EasyMotherUtils.initTitle(this, "育婴师", true);
		findView();
		init();
	}

	private void findView() {
		// title = (TextView) findViewById(R.id.title);
		// back = (ImageView) findViewById(R.id.back);
		filter = (TextView) findViewById(R.id.filter);
		sort = (TextView) findViewById(R.id.sort);

		title = findViewById(R.id.title1);
		search_layout = findViewById(R.id.search_layout);
		bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
		listView = (ListView) findViewById(R.id.listview1);
		
//		View search
	}

	private void init() {

		// title.setText("月嫂");
		// back.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// YueSaoListActivity.this.finish();
		// }
		// });

		List<TestBean> list = new ArrayList<TestBean>();
		TestBean bean1 = new TestBean();
		TestBean bean2 = new TestBean();
		TestBean bean3 = new TestBean();
		TestBean bean4 = new TestBean();
		TestBean bean5 = new TestBean();
		TestBean bean6 = new TestBean();
		TestBean bean7 = new TestBean();
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		list.add(bean4);
		list.add(bean5);
		list.add(bean6);
		list.add(bean7);

		
		YuYingShiListAdapter adapter = new YuYingShiListAdapter(
				getApplicationContext(), list, R.layout.activity_yuesao_item);
		View view=LayoutInflater.from(this).inflate(R.layout.search_item, null);
		
		
		listView.addHeaderView(view);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				Log.e("log-------", "arg1=="+arg1+"    "+"arg2=="+arg1+"arg3=="+arg1);
				if (arg1<0) {
					search_layout.setVisibility(View.GONE);
				}
				
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				EasyMotherUtils.goActivity(YuYingShiListActivity.this,
						YuYingShiDetailActivity.class);
				// Toast.makeText(getApplicationContext(), "点击时间", 0).show();
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
		search_layout.setVisibility(View.GONE);
		// listView.setOnTouchListener(this);

	}

	protected void showFilterDialog(View arg0) {
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_filter);
		dialog.setCanceledOnTouchOutside(true);
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
		sort1 = (TextView) dialog.findViewById(R.id.sort1);
		sort2 = (TextView) dialog.findViewById(R.id.sort2);
		sort3 = (TextView) dialog.findViewById(R.id.sort3);
		sort4 = (TextView) dialog.findViewById(R.id.sort4);
		Button button = (Button) dialog.findViewById(R.id.submit);

		sortClickListener listener = new sortClickListener(dialog);
		sort1.setOnClickListener(listener);
		sort2.setOnClickListener(listener);
		sort3.setOnClickListener(listener);
		sort4.setOnClickListener(listener);
		button.setOnClickListener(listener);

		dialog.show();
		// Window dialogWindow=dialog.getWindow();
		// WindowManager.LayoutParams wl=dialogWindow.getAttributes();
		// wl.width=(int) (MyApplication.getScreen_width()*0.7);
		// view.measure(0, 0);
		// wl.y=MyApplication.getScreen_height()/2-arg0.getMeasuredHeight()-view.getMeasuredHeight()/2-(int)getResources().getDimension(R.dimen.dp_25);
		// // wl.x=MyApplication.getScreen_width()/2-(int)
		// (arg0.getMeasuredWidth()*((float)4.0/3));
		// wl.x=MyApplication.getScreen_width()/2;
		// dialogWindow.setAttributes(wl);
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
			int id = arg0.getId();
			switch (id) {
			case R.id.sort1:
				clearStatus();
				sort1.setTextColor(getResources().getColor(R.color.waterpink));
				break;

			case R.id.sort2:
				clearStatus();
				sort2.setTextColor(getResources().getColor(R.color.waterpink));

				break;
			case R.id.sort3:
				clearStatus();
				sort3.setTextColor(getResources().getColor(R.color.waterpink));

				break;
			case R.id.sort4:
				clearStatus();
				sort4.setTextColor(getResources().getColor(R.color.waterpink));
				break;
			case R.id.submit:

				dialog.dismiss();
				break;
			}

		}

		private void clearStatus() {
			sort1.setTextColor(getResources().getColor(R.color.blacktext));
			sort2.setTextColor(getResources().getColor(R.color.blacktext));
			sort3.setTextColor(getResources().getColor(R.color.blacktext));
			sort4.setTextColor(getResources().getColor(R.color.blacktext));
		}
	}

	/*
	 * 显示搜索框
	 */

	public void show() {

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		search_layout.setAnimation(animation);

		search_layout.startAnimation(animation);
		search_layout.setVisibility(View.VISIBLE);
	}

	/*
	 * 隐藏搜索框
	 */
	public void hide() {

		AnimationSet animationSet=new AnimationSet(false);
		title.measure(0, 0);
		int height=title.getMeasuredHeight();
		Animation animation = new AlphaAnimation(1.0f, 0.0f);
		Animation animation1 = new TranslateAnimation(0, 0, 0,-height);
		
		animation.setDuration(500);
		animation.setFillAfter(false);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				search_layout.setVisibility(View.GONE);

			}
		});
		animationSet.addAnimation(animation);
		animationSet.addAnimation(animation1);
		search_layout.setAnimation(animation);
		search_layout.startAnimation(animation);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent arg1) {
		// TODO Auto-generated method stub
		float dy;
		// float y = 0;
		Log.e("log大法", "-----" + y);
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:

			y = arg1.getY();
			Log.e("log大法", "ACTION_DOWN-----" + y);
			break;

		case MotionEvent.ACTION_UP:
			dy = arg1.getY();
			Log.e("log大法", "ACTION_UP-----" + "dy=" + dy + "y=" + y);
			if (y > dy) {
				if (!search_layout.isShown()) {
					break;
				}
				hide();
				Log.e("log大法", "-进入了---" + dy);
			} else {
				if (search_layout.isShown()) {
					break;
				}
				show();
				Log.e("log大法", "VISIBLE-----" + dy);
			}

			break;
		case MotionEvent.ACTION_MOVE:
			dy = arg1.getY();
			Log.e("log大法", "ACTION_UP-----" + "dy=" + dy + "y=" + y);
			if (y > dy) {
				if (!search_layout.isShown()) {
					break;
				}
				hide();
				Log.e("log大法", "-进入了---" + dy);
			} else {
				if (search_layout.isShown()) {
					break;
				}
				show();
				Log.e("log大法", "VISIBLE-----" + dy);
			}
			break;
		}
		return super.dispatchTouchEvent(arg1);
	}

	public void onAplaChanged(float value) {

	}

}
