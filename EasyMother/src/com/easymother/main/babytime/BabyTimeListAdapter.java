package com.easymother.main.babytime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alidao.mama.R;
import com.alidao.mama.WeiXinUtils;
import com.easymother.bean.BabyTimeBean;
import com.easymother.configure.MyApplication;
import com.easymother.customview.MyGridView;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.TimeCounter;
import com.easymother.utils.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BabyTimeListAdapter extends CommonAdapter<BabyTimeBean> {
	private Context context;
	private List<BabyTimeBean> list;

	protected BabyTimeListAdapter(Context context, List<BabyTimeBean> list, int resource) {
		super(context, list, resource);
		this.context = context;
		this.list = list;
	}

	public void addList(List<BabyTimeBean> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	@Override
	public void setDataToItem(ViewHolder holder, final BabyTimeBean t) {
		TextView days = holder.getView(R.id.days);
		String birthdayString = MyApplication.preferences.getString("nannan_birthday", "");

		if (!"".equals(birthdayString) && birthdayString != null && !"".equals(t.getCreateTime())
				&& t.getCreateTime() != null) {
			// Date currentdate=new Date(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date birthday = format.parse(birthdayString);
				// Log.e("birthday", birthday.toString());
				// Log.e("currentdate", currentdate.toString());
				int day = TimeCounter.countTimeOfDay(birthday, t.getCreateTime());
				days.setText("" + day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			days.setText("0");
		}
		holder.getView(R.id.share).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WeiXinUtils.shareDownloadUrl((Activity) context);
			}
		});

		TextView createdate = holder.getView(R.id.createdate);
		if (t.getCreateTime() != null) {
			Date date = t.getCreateTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			createdate.setText("" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " "
					+ calendar.get(Calendar.YEAR));
		}
		TextView content = holder.getView(R.id.content);
		if (t.getContent() != null) {
			content.setText(t.getContent());
		} else {
			content.setText("");
		}


		MyGridView gridView = holder.getView(R.id.photos);
		gridView.setVisibility(View.GONE);
		if (t.getImages() != null && !"".equals(t.getImages())) {
			List<String> list = JSON.parseArray(t.getImages(), String.class);
			gridView.setVisibility(View.VISIBLE);
			ImageAdapter2 adapter = new ImageAdapter2(context, list, R.layout.comment_image);
			gridView.setAdapter(adapter);
		}

		holder.getConvertView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent((Activity) context, BabyTimeDetail.class);
				// arg2-1 因为加了头部view所以要减1
				intent.putExtra("id", t.getId());
				context.startActivity(intent);
			}
		});

		// List<String> list=JSON.parseArray(t.getImages(), String.class);
		// List<String> list=t.getBabyImages();

	}

}
