package com.easymother.main.community;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.NewsInfoBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleListAdapter extends CommonAdapter<NewsInfoBean> {
	private Context context;
	protected ArticleListAdapter(Context context, List<NewsInfoBean> list, int resource) {
		super(context, list, resource);
		this.context=context;
	}
	
	public void addList(List<NewsInfoBean> list){
		this.list=list;
	}
	public void clearList(){
		this.list.clear();
	}

	@Override
	public void setDataToItem(ViewHolder holder, final NewsInfoBean t) {
		ImageView imageView1=holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getLogo(), imageView1, MyApplication.options_image);
		
		TextView title=holder.getView(R.id.title);
		if (t.getTitle()!=null) {
			title.setText(t.getTitle());
		}else {
			title.setText("");
		}
		
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
			content.setText(Html.fromHtml(t.getContent()));
//			content.setText(NetworkHelper.showFWBText(t.getContent()));
		}else {
			content.setText("");
		}
		TextView check_all=holder.getView(R.id.check_all);
		check_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass((Activity)context, ArticleActivity.class);
				intent.putExtra("id", t.getId());
				context.startActivity(intent);
			}
		});
	}
	
}