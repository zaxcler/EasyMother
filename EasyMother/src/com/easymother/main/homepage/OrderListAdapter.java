package com.easymother.main.homepage;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

public class OrderListAdapter extends CommonAdapter<TestBean> {
	private List<TestBean> list;
	private Context context;

	protected OrderListAdapter(Context context, List<TestBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
	}

	@Override
	public void setDataToItem(final ViewHolder holder, final TestBean t) {
		final View convertview=holder.getConvertView();
		TextView imageView=(TextView)holder.getView(R.id.pay);
		holder.getView(R.id.delete).setVisibility(View.GONE);
		
	}

}
