package com.easymother.main.homepage;

import java.util.List;

import com.alidao.mama.R;
import com.easymother.bean.CuiRuShi;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter<T> extends CommonAdapter<T> {


	public GridViewAdapter(Context context, List<T> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, T t) {
		if (t instanceof YueSao) {
			YueSao yuesao=(YueSao)t;
			ImageView tuijian_photo=holder.getView(R.id.tuijian_photo);
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yuesao.getImage(), tuijian_photo,MyApplication.options_image);
			TextView tuijian_name=holder.getView(R.id.tuijian_name);
			if (yuesao.getRealName()!=null) {
				tuijian_name.setText(yuesao.getRealName());
			}else {
				tuijian_name.setText("");
			}
			
			TextView tuijian_num=holder.getView(R.id.tuijian_num);
			if (yuesao.getNums()!=null) {
				tuijian_num.setText("共有"+yuesao.getNums()+"人喜欢该护理师");
			}else {
				tuijian_num.setText("共有"+0+"人喜欢该护理师");
			}
			
			TextView tuijian_price=holder.getView(R.id.tuijian_price);
			if (yuesao.getShowPrice()!=null) {
				tuijian_price.setText(yuesao.getShowPrice());
			}else {
				tuijian_price.setText("");
			}
			
		}
		if (t instanceof YuYingShi) {
			YuYingShi yys=(YuYingShi) t;
			ImageView tuijian_photo=holder.getView(R.id.tuijian_photo);
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yys.getImage(), tuijian_photo,MyApplication.options_image);
			TextView tuijian_name=holder.getView(R.id.tuijian_name);
			if (yys.getRealName()!=null) {
				tuijian_name.setText(yys.getRealName());
			}else {
				tuijian_name.setText("");
			}
			
			TextView tuijian_num=holder.getView(R.id.tuijian_num);
			if (yys.getNums()!=null) {
				tuijian_num.setText("共有"+yys.getNums()+"喜欢该月嫂");
			}else {
				tuijian_num.setText("共有"+0+"喜欢该月嫂");
			}
			
			TextView tuijian_price=holder.getView(R.id.tuijian_price);
			if (yys.getShowPrice()!=null) {
				tuijian_price.setText(yys.getShowPrice());
			}else {
				tuijian_price.setText("");
			}
			
		}
		if (t instanceof CuiRuShi) {
			CuiRuShi crs=(CuiRuShi) t;
			ImageView tuijian_photo=holder.getView(R.id.tuijian_photo);
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+crs.getImage(), tuijian_photo,MyApplication.options_image);
			TextView tuijian_name=holder.getView(R.id.tuijian_name);
			if (crs.getRealName()!=null) {
				tuijian_name.setText(crs.getRealName());
			}else {
				tuijian_name.setText("");
			}
			
			TextView tuijian_num=holder.getView(R.id.tuijian_num);
			if (crs.getNums()!=null) {
				tuijian_num.setText("共有"+crs.getNums()+"喜欢该育婴师");
			}else {
				tuijian_num.setText("共有"+0+"喜欢该育婴师");
			}
			
			TextView tuijian_price=holder.getView(R.id.tuijian_price);
			if (crs.getShowPrice()!=null) {
				tuijian_price.setText(crs.getShowPrice());
			}else {
				tuijian_price.setText("");
			}
			
		}
	}

}
