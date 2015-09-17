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
	private int day = 1;
	private Date date;
	private String isShowShadow="false";

	protected CalenderAadpter(Context context, List<Integer> list, int resource) {
		super(context, list, resource);
		date = new Date(System.currentTimeMillis());
		currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		Log.e("currentDate", currentDate.toString());
	}

	/**
	 * 
	 * @param p
	 *            为1就是月份加，p 为-1就是月份减
	 */
	public Calendar setCurrtentMonth(int p) {
		currentDate.add(Calendar.MONTH, p);
		//初始化状态
		day = 1;
		isShowShadow="false";
		Log.e("currentDate", currentDate.toString());
		return currentDate;
	}

	/**
	 * 
	 * @param p
	 *            为1就是年份加，p 为-1就是年份减
	 */
	public Calendar setCurrtentYear(int p) {
		currentDate.add(Calendar.YEAR, p);
		//初始化状态
		day = 1;
		isShowShadow="false";
		Log.e("currentDate", currentDate.toString());
		return currentDate;
	}

	@Override
	public void setDataToItem(ViewHolder holder, Integer t) {
		currentDate.set(Calendar.DAY_OF_MONTH, 1);// 设置为当前月的第一天
		int Start_day = currentDate.get(Calendar.DAY_OF_WEEK);// 当前周的第几天
		currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		int end_day = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);// 获取当前月的最后一天
		TextView current_day = holder.getView(R.id.time);
		current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
		// 初始化月份
		if (t >= Start_day - 1 && t < end_day + Start_day-1) {
			// 存在订单的就弄成灰色
			for (Order order : orders) {
				if (order.getRealHireStartTime() != null && order.getRealHireEndTime() != null) {
					Date result1 = order.getRealHireStartTime();
					Date result2 = order.getRealHireEndTime();
					Calendar date1 = Calendar.getInstance();
					Calendar date2 = Calendar.getInstance();
					date1.setTime(result1);
					date2.setTime(result2);
					int start_month = date1.get(Calendar.MONTH);
					int end_month = date2.get(Calendar.MONTH);
					int current_month = currentDate.get(Calendar.MONTH);
					// 如果是同一年
					if (currentDate.get(Calendar.YEAR) == date1.get(Calendar.YEAR)
							&& currentDate.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
						// 如果开始月份和结束月份都是当前月份
						if (start_month == end_month&& start_month == current_month && end_month == current_month) {
							// 在开始和结束的日子中间的都变灰色
							if (t >= date1.get(Calendar.DAY_OF_MONTH)+Start_day-2&& t <= date2.get(Calendar.DAY_OF_MONTH)+1) {
								current_day.setBackgroundColor(context.getResources().getColor(R.color.boro));
							}
//							else {
//								current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
//							}
						}
						// 如果开始的月份和结束的月份不同，并且开始月份小于当前月份，结束月份等于当前月份
						else if (start_month != end_month && start_month < current_month
								&& end_month == current_month) {
							// 小于结束的时间全部变成灰色
							if (t <= date2.get(Calendar.DAY_OF_MONTH)+1) {
								current_day.setBackgroundColor(context.getResources().getColor(R.color.boro));
							} 
//							else {
//								current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
//							}
						}
						// 如果开始的月份和结束的月份不同，并且开始月份等于当前月份，结束月份大于当前月份
						else if (start_month != end_month && start_month == current_month
								&& end_month > current_month) {
							// 大于开始的时间全部变成灰色
							if (t >= date1.get(Calendar.DAY_OF_MONTH)+Start_day-2){
								current_day.setBackgroundColor(context.getResources().getColor(R.color.boro));
							} 
//							else {
//								current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
//							}
						}
						// 如果开始的月份和结束的月份不同，并且开始月份小于当前月份，结束月份大于当前月份
						else if (start_month != end_month && start_month < current_month && end_month > current_month) {
							// 全部变成灰色
							current_day.setBackgroundColor(context.getResources().getColor(R.color.boro));
						}
//						else {
//							current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
//						}
					}
					else {
						current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
					}

				}

			}

			if (day > 9) {
				current_day.setText("" + day++);
			} else {
				current_day.setText(" " + day++);
			}

		}else{
			current_day.setText("");
			current_day.setBackgroundColor(context.getResources().getColor(R.color.white));
			
		}

	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
