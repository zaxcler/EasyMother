package com.easymother.main.homepage;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.Certificate;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.widget.TextView;

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
