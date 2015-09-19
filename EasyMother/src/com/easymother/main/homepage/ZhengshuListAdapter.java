package com.easymother.main.homepage;

import java.util.List;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import com.easymother.bean.Certificate;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;

public class ZhengshuListAdapter extends CommonAdapter<Certificate> {

	public ZhengshuListAdapter(Context context, List<Certificate> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Certificate t) {
		TextView letter_title=holder.getView(R.id.letter_title);
		TextView letter_content=holder.getView(R.id.letter_content);
		ImageView letter_image=holder.getView(R.id.letter_image);
		if (t.getName()!=null) {
			letter_title.setText(t.getName());
		}else {
			letter_title.setText("");
		}
		if (t.getRemarks()!=null) {
			letter_content.setText(Html.fromHtml(t.getRemarks()));
		}else {
			letter_content.setText("");
		}
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getImage(), letter_image,MyApplication.options_image);
	
		
	}
}
