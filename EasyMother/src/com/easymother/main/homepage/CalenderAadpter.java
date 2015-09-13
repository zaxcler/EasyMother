package com.easymother.main.homepage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.easymother.bean.Order;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class CalenderAadpter extends CommonAdapter<Integer> {

	private List<Order> orders;
	private Calendar currentDate;
	private int day=1;
	private Date date;
	protected CalenderAadpter(Context context, List<Integer> list, int resource) {
		super(context, list, resource);
		date=new Date(System.currentTimeMillis());
		currentDate=Calendar.getInstance();
		currentDate.setTime(date);
	}
	
	
	@Override
	public void setDataToItem(ViewHolder holder, Integer t) {
		currentDate.set(Calendar.DAY_OF_MONTH, 1);//设置为当前月的第一天
		int Start_day=currentDate.get(Calendar.DAY_OF_WEEK);//当前周的第几天
		currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		int end_day=currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前月的最后一天
		
		//初始化月份
		if (t>=Start_day-1 && t<end_day+Start_day) {
			TextView current_day=holder.getView(R.id.time);
			
			//存在订单的就弄成灰色
				for (Order order : orders) {
					if (order.getRealHireStartTime()!=null&&order.getRealHireEndTime()!=null) {
						Date result1=order.getRealHireStartTime();
						Date result2=order.getRealHireEndTime();
						Calendar date1=Calendar.getInstance();
						Calendar date2=Calendar.getInstance();
						date1.setTime(result1);
						date2.setTime(result2);
						//如果是同一年
//						if (currentDate.get(Calendar.YEAR)==date1.get(Calendar.YEAR) && currentDate.get(Calendar.YEAR)==date2.get(Calendar.YEAR) ) {
//							if (condition) {
//								
//							}
//							if (t>=date1.get(Calendar.DAY_OF_MONTH)) {
//								current_day.setBackgroundColor(context.getResources().getColor(R.color.boro));
//							}
//						}
						
					}
					
			}
			if (day>9) {
				current_day.setText(""+day++);
			}else {
				current_day.setText(" "+day++);
			}
			
		}
		
	}
	/**
	 * 设置订单
	 * @param orders
	 */
	public void setOrders(List<Order> orders){
		this.orders=orders;
	}
	

}
