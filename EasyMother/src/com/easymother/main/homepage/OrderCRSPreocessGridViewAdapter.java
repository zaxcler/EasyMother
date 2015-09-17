package com.easymother.main.homepage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.easymother.bean.Order;
import com.easymother.bean.TestBean;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;

/**
 * 时间表的gridview的适配器
 * 
 * @author zaxcler
 *
 */
public class OrderCRSPreocessGridViewAdapter extends CommonAdapter<String> {
	ArrayList<Order> orders;
	private boolean isClick=false;//是否选择
	private int flagshowtime=0;//flag出现次数
	private Date currentdate;
	private Calendar currentCalendar;//
	private String starttime;//开始时间
	private String endtime;//结束时间
	public OrderCRSPreocessGridViewAdapter(Context context, List<String> list, int resource, ArrayList<Order> orders) {
		super(context, list, resource);
		this.orders = orders;
		currentdate=new Date(System.currentTimeMillis());
		currentCalendar=Calendar.getInstance();
		currentCalendar.setTime(currentdate);
	}
	/*
	 * 设置当前天数
	 */
	public void setCurrentDay(){
		currentCalendar.setTime(currentdate);
		//清空选择的时间
		starttime="";
		endtime="";
	}
	/*
	 * 设置明天天数
	 */
	public void setTomorrow(){
		currentCalendar.setTime(currentdate);
		currentCalendar.add(Calendar.DATE,1);
		//清空选择的时间
		starttime="";
		endtime="";
	}
	/*
	 * 设置后天天数
	 */
	public void setTheDayAffterTomorrow(){
		currentCalendar.setTime(currentdate);
		currentCalendar.add(Calendar.DATE, 2);
		//清空选择的时间
		starttime="";
		endtime="";
	}
	/*
	 * 设置第四天数
	 */
	public void setForthDay(){
		currentCalendar.setTime(currentdate);
		currentCalendar.add(Calendar.DATE, 3);
		//清空选择的时间
		starttime="";
		endtime="";
	}
	
	
	public String getStarttime() {
		try {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate=dateFormat.parse(starttime);
			Date endDate=dateFormat.parse(endtime);
			//比较开始和结束时间，小的设为开始时间，大的设为结束时间
			int result=TimeCounter.compareDate(startDate, endDate);
			if (result>=0) {
				return starttime;
			}else {
				return endtime;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		try {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate=dateFormat.parse(starttime);
			Date endDate=dateFormat.parse(endtime);
			//比较开始和结束时间，小的设为开始时间，大的设为结束时间
			int result=TimeCounter.compareDate(startDate, endDate);
			if (result<0) {
				return starttime;
			}else {
				return endtime;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Override
	public void setDataToItem(ViewHolder holder, String s) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//每个item对应的时间string
			String temptime=currentCalendar.get(Calendar.YEAR)+"-"+currentCalendar.get(Calendar.MONTH)+"-"+currentCalendar.get(Calendar.DAY_OF_MONTH)+" "+s+":00";
			Date date = dateFormat.parse(temptime);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			TextView textView = (TextView) holder.getView(R.id.time);
			// 把时间转换成 8：30这样的格式
			View view=holder.getConvertView();
			String minute;
			if (calendar.get(Calendar.MINUTE) < 10) {
				minute = "0" + calendar.get(Calendar.MINUTE);
			} else {
				minute = "" + calendar.get(Calendar.MINUTE);
			}
			textView.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + minute);
			textView.setTag(temptime);
			for (Order order : orders) {
				if (order.getRealHireStartTime()!=null&&order.getRealHireEndTime()!=null) {
					long result1=order.getRealHireStartTime().getTime()-date.getTime();
					long result2=order.getRealHireEndTime().getTime()-date.getTime();
					if (result1<=0&&result2>=0) {
						view.setBackgroundColor(context.getResources().getColor(R.color.boro));
						view.setClickable(false);
					}
				}
				
			}
			
			if (starttime!=null&& !"".equals(starttime) && endtime!=null&& !"".equals(endtime)) {
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate=dateFormat2.parse(starttime);
				Date endDate=dateFormat2.parse(endtime);
				if (TimeCounter.compareDate(startDate, date)>=0&&TimeCounter.compareDate(endDate, date)<=0) {
					view.setBackgroundColor(context.getResources().getColor(R.color.lightredwine));
				}
			}else {
				view.setBackgroundColor(context.getResources().getColor(R.color.white));
			}
			
			
//			if ("flag".equals(textView.getTag())) {
//				isClick=true;
//				flagshowtime++;
//			}
//			if (isClick) {
//				view.setBackgroundColor(context.getResources().getColor(R.color.lightredwine));
//				if (flagshowtime==2) {
//					isClick=false;
//				}
//			}else {
//				view.setBackgroundColor(context.getResources().getColor(R.color.white));
//			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
