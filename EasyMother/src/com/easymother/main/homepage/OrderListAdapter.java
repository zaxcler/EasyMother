package com.easymother.main.homepage;

import java.util.List;

import com.easymother.bean.NurseBaseBean;
import com.easymother.configure.BaseInfo;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter<T> extends CommonAdapter<T> {
	private List<T> list;
	private Context context;
	private String job;

	protected OrderListAdapter(Context context, List<T> list,String job,
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
				seniority.setText(bean.getSeniority()+"");
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
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo);
			}
			

		holder.getView(R.id.delete).setVisibility(View.GONE);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		
		
	}

	

}
