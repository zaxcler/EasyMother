package com.easymother.main.community;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.easymother.bean.ForumPost;
import com.easymother.bean.ForumPostBean;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.TestBean;
import com.easymother.bean.TopicHelpDetailResult;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.CircleImageView;
import com.easymother.customview.MyGridView;
import com.easymother.customview.MyListview;
import com.easymother.main.R;
import com.easymother.main.babytime.ImageAdapter;
import com.easymother.main.my.CollectionListAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HuLiShiReplyListActivity extends Activity {
	private PullToRefreshScrollView scrollView;// 下拉刷新控件
	private MyListview listview;//
	private TextView user_name;
	private TextView time;
	private TextView content;
	private TextView share;
	private TextView reply;
	private TextView star;
	private TextView reply_nums;
	private TextView textView7;
	private TextView send;
	private CircleImageView circleImageView1;
	private MyGridView pictures;
	public EditText editText1;
	public String parentContent;
	public String parentUserNickName;
	public HuLiShiReplyAdapter adapter;
	private ForumPost postInfo ;

	private Intent intent;
	public int id;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hulishi_zone_reply);
		EasyMotherUtils.initTitle(this, "话题详情", false);
		intent = getIntent();
		id = intent.getIntExtra("id", 0);
		type = intent.getStringExtra("type");
		findView();
		init();

	}

	private void findView() {
		scrollView = (PullToRefreshScrollView) findViewById(R.id.pulltoreflash);
		listview = (MyListview) findViewById(R.id.listview);
		user_name = (TextView) findViewById(R.id.user_name);
		time = (TextView) findViewById(R.id.time);
		content = (TextView) findViewById(R.id.content);
		share = (TextView) findViewById(R.id.share);
		reply = (TextView) findViewById(R.id.reply);
		star = (TextView) findViewById(R.id.star);
		reply_nums = (TextView) findViewById(R.id.reply_nums);
		circleImageView1 = (CircleImageView) findViewById(R.id.circleImageView1);
		pictures = (MyGridView) findViewById(R.id.pictures);
		textView7 = (TextView) findViewById(R.id.textView7);
		send = (TextView) findViewById(R.id.send);
		editText1 = (EditText) findViewById(R.id.editText1);
	}

	private void init() {
		loadData();
		share.setVisibility(View.GONE);
		reply.setVisibility(View.GONE);
		star.setVisibility(View.GONE);
		textView7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				id=postInfo.getId();
				editText1.requestFocus();
				editText1.setHint("回复" + postInfo.getUserNickname());
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				// imm.showSoftInput(m_receiverView(接受软键盘输入的视图(View)),InputMethodManager.SHOW_FORCED(提供当前操作的标记，SHOW_FORCED表示强制显示));
				imm.showSoftInput(editText1, InputMethodManager.SHOW_FORCED);
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RequestParams params = new RequestParams();
				String text = editText1.getText().toString().trim();
				if (text != null && !"".equals(text)) {
					params.put("content", text);
				}
				params.put("parentId", id);
				parentContent=Html.fromHtml(postInfo.getContent()).toString();
				params.put("parentContent", parentContent);

				// 回复
				NetworkHelper.doGet(BaseInfo.REPLY, params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							Log.e("回复", response.toString());
							ForumPost forumPost = JsonUtils.getResult(response, ForumPost.class);
							Log.e("回复父内容", forumPost.toString());
							Toast.makeText(HuLiShiReplyListActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
							reply_nums.setText("回复" + adapter.getCount() + "条");
							adapter.add(forumPost);
							editText1.setText("");
							adapter.notifyDataSetChanged();

							// 隐藏输入法
							InputMethodManager imm = (InputMethodManager) getSystemService(
									Context.INPUT_METHOD_SERVICE);
							if (imm.isActive()) {
								//如果开启
								imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
								//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
								}
							
						} else {
							Toast.makeText(HuLiShiReplyListActivity.this, JsonUtils.getRootResult(response).getMessage(), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("回复失败", responseString);
					}
				});

			}
		});

		//
		// listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// EasyMotherUtils.goActivity(HuLiShiReplyListActivity.this,
		// HuLiShiZoneDetailActivity.class);
		// }
		// });

	}

	/*
	 * 加载数据
	 */
	private void loadData() {
		RequestParams params = new RequestParams();
		if (id != 0) {
			params.put("id", id);
		}
		if (type != null && !"".equals(type)) {
			params.put("type", type);
		}
		NetworkHelper.doGet(BaseInfo.CHECK_TOPIC_DETAIL, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					Log.e("kongjian", response.toString());
					TopicHelpDetailResult detailResult = JsonUtils.getResult(response, TopicHelpDetailResult.class);
					bindData(detailResult);
				} else {
					Log.e("kongjian", response.toString());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("kongjian", responseString);
			}
		});
	}

	protected void bindData(TopicHelpDetailResult detailResult) {

		if (detailResult != null) {
			postInfo= detailResult.getPostInfo();
			NurseBaseBean baseBean = detailResult.getNurseinfo();
			List<ForumPost> list = detailResult.getReplys();
			if (baseBean != null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + baseBean.getImage(),
						circleImageView1, MyApplication.options_photo);
				if (baseBean.getRealName() != null) {
					user_name.setText(baseBean.getRealName());
				} else {
					user_name.setText("");
				}
			}
			if (postInfo.getParentCountent() != null) {
				parentContent = postInfo.getParentCountent();
			} else {
				parentContent = "";
			}
			if (postInfo != null) {
				if (postInfo.getCreateTime() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String createtime = dateFormat.format(postInfo.getCreateTime());
					time.setText(createtime);
				} else {
					time.setText("");
				}
			}
			if (postInfo != null) {
				if (postInfo.getContent() != null) {
					content.setText(NetworkHelper.showFWBText(postInfo.getContent()));
				} else {
					content.setText("");
				}
				ImageLoader.getInstance().displayImage(
						BaseInfo.BASE_URL + BaseInfo.BASE_PICTURE + postInfo.getUserImage(), circleImageView1,
						MyApplication.options_photo);
				if (postInfo.getUserNickname() != null) {
					user_name.setText(postInfo.getUserNickname());
				} else {
					user_name.setText("");
				}
			}
			if (postInfo.getImages()!=null&&!"".equals(postInfo.getImages()) &&!"[]".equals(postInfo.getImages())) {
				ArrayList<String> images=(ArrayList<String>) JSON.parseArray(postInfo.getImages(), String.class);
				ImageAdapter adapter=new ImageAdapter(HuLiShiReplyListActivity.this, images, R.layout.comment_image);
				pictures.setAdapter(adapter);
			}
			if (list != null) {
				reply_nums.setText("回复" + list.size() + "条");
				int resources[] = new int[] { R.layout.activity_hulishi_reply_item,
						R.layout.activity_hulishi_reply_item1 };
				adapter = new HuLiShiReplyAdapter(this, list, resources);
				listview.setAdapter(adapter);
			} else {
				reply_nums.setText("回复" + 0 + "条");
			}

		}
	}

}
