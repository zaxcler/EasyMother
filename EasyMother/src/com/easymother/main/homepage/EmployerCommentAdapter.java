package com.easymother.main.homepage;

import java.util.List;

import android.content.Context;

import com.easymother.bean.TestBean;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;


/**
 * 雇主评论的适配器
 * @author zaxcler
 *
 */
public class EmployerCommentAdapter extends CommonAdapter<TestBean> {

	protected EmployerCommentAdapter(Context context, List<TestBean> list,
			int resource) {
		super(context, list, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDataToItem(ViewHolder holder, TestBean t) {
		// TODO Auto-generated method stub
		
	}

}
