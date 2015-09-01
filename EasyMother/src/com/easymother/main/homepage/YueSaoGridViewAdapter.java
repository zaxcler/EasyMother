package com.easymother.main.homepage;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.easymother.bean.Certificate;
import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

/**
 * 月嫂证书的gridview的适配器
 * @author zaxcler
 *
 */
public class YueSaoGridViewAdapter extends CommonAdapter<Certificate> {

	protected YueSaoGridViewAdapter(Context context, List<Certificate> list,
			int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Certificate certificate ) {
		TextView textView=holder.getView(R.id.name);
		if (certificate.getName()!=null) {
			textView.setText(certificate.getName());
		}
	}

}
