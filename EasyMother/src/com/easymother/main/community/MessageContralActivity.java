package com.easymother.main.community;

import java.util.List;
import org.apache.http.Header;
import org.json.JSONObject;
import com.easymother.bean.ForumPost;
import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class MessageContralActivity extends Activity {
	private ListView listView;//消息
	private Intent intent;
	private String flag;
	private String url;//访问连接

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_contral);
		intent=getIntent();
		flag=intent.getStringExtra("flag");
		if ("unread".equals(flag)) {
			EasyMotherUtils.initTitle(this, "消息管理", false);
			url=BaseInfo.UNREAD;
		}else if ("reply_list".equals(flag)){
			EasyMotherUtils.initTitle(this, "我的回复", false);
			url=BaseInfo.REPLY_LIST;
		}
		findView();
		init();
	}

	private void findView() {
		listView=(ListView) findViewById(R.id.listview);
	}

	private void init() {
		loadData();
	}

	//加载数据
	private void loadData() {
		
		NetworkHelper.doGet(url, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					List<ForumPost> forumPosts=JsonUtils.getResultList(response, ForumPost.class);
					bindDate(forumPosts);
				}
			}
		});
	}

	protected void bindDate(List<ForumPost> forumPosts) {
		if (forumPosts!=null) {
			MessageAdapter adapter=new MessageAdapter(this, forumPosts, R.layout.message_control_item);
			listView.setAdapter(adapter);
		}
		
	}
}
