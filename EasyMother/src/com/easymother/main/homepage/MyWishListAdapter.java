package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.Root;
import com.easymother.bean.TestBean;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyWishListAdapter<T> extends CommonAdapter<T> {
	private List<T> list;
	private Context context;
	private String job;

	protected MyWishListAdapter(Context context, List<T> list,String job,
			int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
		this.job=job;
	}
	
	public List<T> getList(){
		return list;
	}

	@Override
	public void setDataToItem(final ViewHolder holder, final T t) {
		final View convertview=holder.getConvertView();
		
			
			final NurseBaseBean bean=(NurseBaseBean) t;
			TextView name=holder.getView(R.id.textView1);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getRealName()!=null) {
				name.setText(bean.getRealName());
			}
			TextView job=holder.getView(R.id.textView2);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getJob()!=null) {
				if ("YS".equals(bean.getJob())) {
					job.setText("月嫂");
				}
				if ("YYS".equals(bean.getJob())) {
					job.setText("育婴师");
				}
				if ("CRS".equals(bean.getJob())) {
					job.setText("催乳师");
				}
				if ("SHORT_YS".equals(bean.getJob())) {
					job.setText("短期月嫂");
				}
				if ("SHORT_YYS".equals(bean.getJob())) {
					job.setText("短期育婴师");
				}
				
			}
			TextView seniority=holder.getView(R.id.textView3);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getSeniority()!=null) {
				seniority.setText(bean.getSeniority());
			}
			
			TextView age=holder.getView(R.id.textView4);
			age.setText(bean.getAge()+"岁");
			
			TextView area=holder.getView(R.id.textView5);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getHometown()!=null) {
				area.setText(bean.getHometown());
			}
			TextView currentAddress=holder.getView(R.id.textView6);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getCurrentAddress()!=null) {
				currentAddress.setText(bean.getCurrentAddress());
			}
			
			TextView showPrice=holder.getView(R.id.textView7);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getShowPrice()!=null) {
				showPrice.setText(bean.getShowPrice());
			}
			
			TextView marketPrice=holder.getView(R.id.textView8);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getMarketPrice()!=null) {
				marketPrice.setText(bean.getMarketPrice());
				marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
			ImageView photo=holder.getView(R.id.image);
			if (bean.getImage()!=null) {
//				ImageLoader.getInstance().displayImage(MyApplication.BASE_PICTURE+bean.getImage(), photo);
				ImageLoader.getInstance().displayImage("http://zaxcler.oss-cn-beijing.aliyuncs.com/11.jpg", photo);
			}
			

		ImageView imageView=(ImageView)holder.getView(R.id.delete);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				RequestParams params=new RequestParams();
				params.put("collectionId", bean.getCollectionId());
				//删除心愿单
				NetworkHelper.doGet(BaseInfo.DELETE_WISH+bean.getCollectionId(), params, new JsonHttpResponseHandler(){
//					NetworkHelper.doGet("http://zaxcler.oss-cn-beijing.aliyuncs.com/deletewish.txt", params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Root root=JsonUtils.getRootResult(response);
						//成功删除，则移除列表中的item
						if (root.getIsSuccess()) {
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
							Log.e("动画是否执行", "___________");
							convertview.startAnimation(animation);
						}else{
							Toast.makeText(context,"未成功删除"+ response.toString(), 0).show();
						}
						
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable throwable,
							JSONObject errorResponse) {
						super.onFailure(statusCode, headers, throwable, errorResponse);
						Toast.makeText(context, "请求失败", 0).show();
					}
					
				});
				
			}
		});
		
	}

	

}
