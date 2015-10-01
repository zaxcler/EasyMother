package com.easymother.main.homepage;

import java.text.SimpleDateFormat;
import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.NurseJobMediaBean;
import com.easymother.configure.BaseInfo;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListAdapter extends CommonAdapter<NurseJobMediaBean> {

	protected VideoListAdapter(Context context, List<NurseJobMediaBean> list,
			int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, NurseJobMediaBean nursemedia) {
		TextView video_name=holder.getView(R.id.video_name);
		TextView upload_time=holder.getView(R.id.upload_time);
		if (nursemedia.getName()!=null) {
			video_name.setText(nursemedia.getName());
		}
		if (nursemedia.getCreateTime()!=null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String date=dateFormat.format(nursemedia.getCreateTime());
			upload_time.setText(date);
		}
		ImageView imageView=holder.getView(R.id.imageView1);
		if (nursemedia.getImage()!=null) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursemedia.getImage(), imageView);
		}
	}

	
	
}
