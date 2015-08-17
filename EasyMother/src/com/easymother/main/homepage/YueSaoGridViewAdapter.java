package com.easymother.main.homepage;

import java.util.List;

import android.content.Context;

import com.easymother.bean.TestBean;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

/**
 * 月嫂证书的gridview的适配器
 * @author zaxcler
 *
 */
public class YueSaoGridViewAdapter extends CommonAdapter<TestBean> {

	protected YueSaoGridViewAdapter(Context context, List<TestBean> list,
			int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, TestBean t) {
		
	}

}
