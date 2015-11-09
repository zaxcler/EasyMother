package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.OrderListBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.my.PayListActivity;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderListAdapter<T> extends CommonAdapter<T> {
	private List<T> list;
	private Context context;
	private String job;
	private String flag;

	public OrderListAdapter(Context context, List<T> list,String job,
			int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
		this.job=job;
		if (context instanceof OrderListActivity) {
			flag="allorder";
		}else if (context instanceof PayListActivity) {
			flag="payorder";
		}
	}
	
	public List<T> getList(){
		return list;
	}

	@Override
	public void setDataToItem(final ViewHolder holder, final T t) {
		final View convertview=holder.getConvertView();
		
		TextView day=holder.getView(R.id.textView10);
		day.setVisibility(View.GONE);
			final OrderListBean bean=(OrderListBean) t;
			TextView name=holder.getView(R.id.textView1);
			/*
			 * 设置之前先判断空
			 */
//			if ("payorder".equals(flag)) {
//				if (bean.getNurseName()!=null) {
//					name.setText(bean.getNurseName());
//				}
//			}else if ("allorder".equals(flag)) {
				if (bean.getRealName()!=null) {
					name.setText(bean.getRealName());
				}
//			}
			
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
				currentAddress.setText(bean.getCurrentAddress());
			}
			
			TextView showPrice=holder.getView(R.id.textView7);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getShowPrice()!=null) {
				showPrice.setText(bean.getShowPrice()+"");
			}
			TextView dengji=holder.getView(R.id.textView9);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getJobTitle()!=null) {
				dengji.setText(bean.getJobTitle());
			}
			TextView marketPrice=holder.getView(R.id.textView8);
			/*
			 * 设置之前先判断空
			 */
			if (bean.getMarketPrice()!=null) {
				marketPrice.setText("市场价："+bean.getMarketPrice()+"元/26天");
				marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
			ImageView photo=holder.getView(R.id.image);
			if (bean.getImage()!=null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+bean.getImage(), photo,MyApplication.options_image);
			}

		ImageView delete=holder.getView(R.id.delete);
		delete.setVisibility(View.INVISIBLE);
		
		holder.getView(R.id.pay).setVisibility(View.GONE);
		holder.getView(R.id.line1).setVisibility(View.GONE);
		holder.getView(R.id.price_tv).setVisibility(View.GONE);
		holder.getView(R.id.price_tv2).setVisibility(View.GONE);
		TextView price_tv2=holder.getView(R.id.price_tv2);
		if ("payorder".equals(flag)) {
			holder.getView(R.id.line1).setVisibility(View.VISIBLE);
			holder.getView(R.id.price_tv).setVisibility(View.VISIBLE);
			price_tv2.setVisibility(View.VISIBLE);
			if (bean.getPayMoney()!=null) {
				price_tv2.setText("￥"+bean.getPayMoney()+"元");
			}else {
				price_tv2.setText("");
			}
		}
		if (bean.getStatus()!=null) {
			//如果是需要支付的状态则显示
			if ("25".equals(bean.getStatus())){
				TextView pay=holder.getView(R.id.pay);
				pay.setText("待付服务款");
				pay.setVisibility(View.VISIBLE);
				if (bean.getPayMoney()!=null) {
					price_tv2.setText("￥"+bean.getPayMoney()+"元");
				}else {
					price_tv2.setText("");
				}
			}
			if ("10".equals(bean.getStatus())){
				TextView pay=holder.getView(R.id.pay);
				pay.setText("待付定金");
				pay.setVisibility(View.VISIBLE);
				price_tv2.setText("￥"+bean.getPayMoney()+"元");
				delete.setVisibility(View.VISIBLE);
				delete.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						RequestParams params=new RequestParams();
						params.put("id",bean.getOrderId());
						NetworkHelper.doGet(BaseInfo.DELETE_ORDER, params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								Log.e("response",response.toString());
								if(response.optBoolean("isSuccess")){
									Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
									
									/**
									 * 订单列表
									 */
									if (context instanceof OrderListActivity) {
										OrderListActivity activity=(OrderListActivity)context;
										activity.loadData();
									}
									/**
									 * 付款列表
									 */
									else if(context instanceof PayListActivity){
										PayListActivity activity=(PayListActivity)context;
										activity.loadData();
									}
									
								}else {
									if (response.optString("result")!=null) {
										Toast.makeText(context,response.optString("result"), Toast.LENGTH_SHORT).show();
									}
									
								}
							}
							@Override
							public void onFailure(int statusCode, Header[] headers, String responseString,
									Throwable throwable) {
								super.onFailure(statusCode, headers, responseString, throwable);
								Log.e("responseString",responseString);
							}
						});
					}
				});
				
			}
			if ("40".equals(bean.getStatus())){
				TextView pay=holder.getView(R.id.pay);
				pay.setText("服务结束");
				pay.setVisibility(View.VISIBLE);
				price_tv2.setText("￥"+bean.getPayMoney()+"元");
			}
		}
		
	}

	

}
