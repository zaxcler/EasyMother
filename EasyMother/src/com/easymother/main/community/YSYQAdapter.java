package com.easymother.main.community;

import java.util.List;

import org.w3c.dom.Text;

import com.easymother.bean.CommunityHotBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class YSYQAdapter extends CommonAdapter<CommunityHotBean> {

	protected YSYQAdapter(Context context, List<CommunityHotBean> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, CommunityHotBean t) {
		ImageView imageView=holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getLogo(), imageView, MyApplication.options_image);
		TextView textView1=holder.getView(R.id.textView1);
		if (t.getTitle()!=null) {
			textView1.setText(Html.fromHtml(t.getTitle()));
		}else {
			textView1.setText("");
		}
		TextView textView2=holder.getView(R.id.textView2);
		if (t.getContent()!=null) {
			
			textView2.setText(Html.fromHtml(t.getContent()));
		}else {
			textView2.setText("");
		}
		
	}
	
	
}
