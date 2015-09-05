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

	public OrderCRSPreocessGridViewAdapter(Context context, List<String> list, int resource, ArrayList<Order> orders) {
		super(context, list, resource);
		this.orders = orders;

	}

	@Override
	public void setDataToItem(ViewHolder holder, String s) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = dateFormat.parse(s);

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
			for (Order order : orders) {
				long result1=order.getRealHireStartTime().getTime()-date.getTime();
				long result2=order.getRealHireEndTime().getTime()-date.getTime();
				if (result1<=0&&result2>=0) {
					view.setBackgroundColor(context.getResources().getColor(R.color.boro));
					view.setClickable(false);
				}
			}
			if ( textView.getTag()!=null) {
				Log.e("tag", textView.getTag()+"");
			}
			
			if ("flag".equals(textView.getTag())) {
				isClick=true;
				flagshowtime++;
			}
			if (isClick) {
				view.setBackgroundColor(context.getResources().getColor(R.color.lightredwine));
				if (flagshowtime==2) {
					isClick=false;
				}
			}else {
				view.setBackgroundColor(context.getResources().getColor(R.color.white));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
