package com.easymother.main.homepage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonListAdapter extends CommonAdapter<NurseBaseBean> {
	private List<NurseBaseBean> list;
	private Context context;
	private Map<String, String> photos;
	

	protected CommonListAdapter(Context context, List<NurseBaseBean> list,
			int resource) {
		super(context, list, resource);
		this.list=list;
		photos=new HashMap<String, String>();
		
	}
	
	/*
	 * 添加一个数据
	 */
	public void add(NurseBaseBean yuesao){
		this.list.add(yuesao);
	}
	
	/*
	 * 添加一个集合
	 */
	public void addAll(List<NurseBaseBean> list){
		this.list.addAll(list);
	}
	
	/*
	 * 添加一个集合
	 */
	public void relaceAll(List<NurseBaseBean> list){
		this.list.clear();
		this.list.addAll(list);
	}
	@Override
	public void setDataToItem(ViewHolder holder, NurseBaseBean yuesao) {
		holder.getView(R.id.delete).setVisibility(View.GONE);
		holder.getView(R.id.pay).setVisibility(View.GONE);
		
		final NurseBaseBean bean=(NurseBaseBean) yuesao;
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
		if (bean.getSeniority()!=null){
			seniority.setText("从业"+bean.getSeniority()+"年");
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
			currentAddress.setText("现居地："+bean.getCurrentAddress());
		}
		
		TextView Price=holder.getView(R.id.textView7);
		/*
		 * 设置之前先判断空
		 */
//		if (bean.getPrice()!=null) {
//			Price.setText(bean.getPrice()*26+"元");
//		}
		TextView textView=holder.getView(R.id.textView10);
		textView.setVisibility(View.GONE);
		if (bean.getShowPrice()!=null) {
			Price.setText(bean.getShowPrice());
		}
		TextView marketPrice=holder.getView(R.id.textView8);
		/*
		 * 设置之前先判断空
		 */
		if (bean.getMarketPrice()!=null) {
			if ("CRS".equals(bean.getJob())) {
				marketPrice.setText("                                 ");
			}else {
				marketPrice.setText("市场价："+bean.getMarketPrice()+"元/26天");
				marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
			
		}
		TextView dengji=holder.getView(R.id.textView9);
		if (bean.getJobTitle()!=null) {
			dengji.setText(bean.getJobTitle());
		}
		
		ImageView photo=holder.getView(R.id.image);
//		UILUtils.downSet(photo, BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage());
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo, MyApplication.options_photo);
//		ImageUtils.setPhoto(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo);
//		if (bean.getImage()!=null) {
//			if (!bean.getImage().equals(photo.getTag())){
//				ImageUtils.setPhoto(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo);
////				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo,MyApplication.options_image);
//			}
//		}
//		photo.setTag(bean.getImage());
//		if (bean.getImage()!=null) {
//			if (photos.get(bean.getImage())==null) {
//				photos.put(bean.getImage(), bean.getImage());
//				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo,MyApplication.options_image);
//			}
//		}
		holder.getView(R.id.line1).setVisibility(View.GONE);
		holder.getView(R.id.price_tv).setVisibility(View.GONE);
		holder.getView(R.id.price_tv2).setVisibility(View.GONE);
	}

}
