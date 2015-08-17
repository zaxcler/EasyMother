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

import com.easymother.bean.TestBean;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

public class MyWishListAdapter extends CommonAdapter<TestBean> {
	private List<TestBean> list;
	private Context context;

	protected MyWishListAdapter(Context context, List<TestBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
	}

	@Override
	public void setDataToItem(final ViewHolder holder, final TestBean t) {
		final View convertview=holder.getConvertView();
		ImageView imageView=(ImageView)holder.getView(R.id.delete);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				list.remove(t);
				Animation animation=AnimationUtils.loadAnimation(context, R.anim.zoom_left_out);
				convertview.setAnimation(animation);
				
				animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						MyWishListAdapter.this.notifyDataSetChanged();
					}
				});
				convertview.startAnimation(animation);
				
			}
		});
	}

}
