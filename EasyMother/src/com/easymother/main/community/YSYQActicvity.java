package com.easymother.main.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.easymother.bean.CommunityHotBean;
import com.easymother.bean.TestBean;
import com.easymother.bean.YSYQItemBean;
import com.easymother.bean.YSYQResult;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class YSYQActicvity extends Activity {
	
	private RelativeLayout photos1;
	private RelativeLayout photos2;
	private RelativeLayout photos3;
	private RelativeLayout photos4;
	private GridView gridview;
	private ListView listview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commnuity_yishiyiqu);
		EasyMotherUtils.initTitle(this, "医食衣趣", false);
		findView();
		init();
	}


	private void findView() {
		photos1=(RelativeLayout) findViewById(R.id.layout1);
		photos2=(RelativeLayout) findViewById(R.id.layout2);
		photos3=(RelativeLayout) findViewById(R.id.layout3);
		photos4=(RelativeLayout) findViewById(R.id.layout4);
		gridview=(GridView) findViewById(R.id.gridview);
		listview=(ListView) findViewById(R.id.listview);
	}


	private void init() {
		List<YSYQItemBean> beans=new ArrayList<>();
		YSYQItemBean bean1=new YSYQItemBean();
		bean1.setImage(R.drawable.yi);
		bean1.setTitle("医");
		bean1.setDesc("宝贝健康随我来");
		YSYQItemBean bean2=new YSYQItemBean();
		bean2.setImage(R.drawable.shi);
		bean2.setTitle("食");
		bean2.setDesc("白白胖胖笑嘻嘻");
		YSYQItemBean bean3=new YSYQItemBean();
		bean3.setImage(R.drawable.yi2);
		bean3.setTitle("衣");
		bean3.setDesc("打扮宝宝我拿手");
		YSYQItemBean bean4=new YSYQItemBean();
		bean4.setImage(R.drawable.qu);
		bean4.setTitle("趣");
		bean4.setDesc("宝贝健康随我来");
		beans.add(bean1);
		beans.add(bean2);
		beans.add(bean3);
		beans.add(bean4);
		YSYQGridviewAdapter adapter=new YSYQGridviewAdapter(this, beans, R.layout.yishiyiqu_item);
		gridview.setAdapter(adapter);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		NetworkHelper.doGet(BaseInfo.YSYQ_INFO, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					YSYQResult result=JsonUtils.getResult(response, YSYQResult.class);
					bindData(result);
				}
			}
		});
		
		
	}

	/**
	 * 绑定数据
	 * @param result
	 */

	protected void bindData(YSYQResult result) {
		
		if (result!=null) {
			if (result.getNewsinfos()!=null) {
				List<CommunityHotBean> beans=result.getNewsinfos();
				YSYQAdapter adapter1=new YSYQAdapter(this, beans, R.layout.activity_commnuity_yishiyiqu_item);
				listview.setAdapter(adapter1);
			}
		}
		
		
		
		
	}

}
