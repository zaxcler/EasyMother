package com.easymother.main.my;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.OrderComments;
import com.easymother.bean.TestBean;
import com.easymother.configure.BaseInfo;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class CommentListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_topic);
		EasyMotherUtils.initTitle(this, "评论列表", false);
		intent = getIntent();
		nursebase = (NurseBaseBean) intent.getSerializableExtra("nursebase");
		nursejob = (NurseJobBean) intent.getSerializableExtra("nursejob");
		findView();
		init();

	}

	private void findView() {
		scrollView = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview = (MyListview) findViewById(R.id.listview);

	}

	private void init() {
		

		RequestParams params = new RequestParams();
		if (nursejob.getId()!= null) {
			params.put("jobId", nursejob.getId());
		}
		if (nursebase.getId()!= null) {
			params.put("job", nursejob.getJob());
		}
		NetworkHelper.doGet(BaseInfo.CHCLK_ALL_COMMENTS, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<OrderComments> comments=JsonUtils.getResultList(response, OrderComments.class);
					CommentListAdapter adapter=new CommentListAdapter(CommentListActivity.this, comments, R.layout.comment_item);
					LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(CommentListActivity.this, R.anim.zoom_right_in));
					controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
					listview.setLayoutAnimation(controller);
					listview.startLayoutAnimation();
					listview.setAdapter(adapter);
				}
			}
		});
//		CommentListAdapter adapter = new CommentListAdapter(this, list, R.layout.comment_item);
//		LayoutAnimationController controller = new LayoutAnimationController(
//				AnimationUtils.loadAnimation(this, R.anim.zoom_right_in));
//		listview.setAdapter(adapter);
//		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//		listview.setLayoutAnimation(controller);
//		listview.startLayoutAnimation();

	}

}
