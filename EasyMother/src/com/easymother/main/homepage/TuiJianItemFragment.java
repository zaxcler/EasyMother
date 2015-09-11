package com.easymother.main.homepage;

import com.alipay.apmobilesecuritysdk.a.e;
import com.easymother.bean.CuiRuShi;
import com.easymother.bean.YuYingShi;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.EasyMotherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

public class TuiJianItemFragment extends Fragment implements OnClickListener {
	private View tuijianItem;
	private String type;
	private YueSao yuesao;
	private YuYingShi yuyingshi;
	private CuiRuShi cuirushi;
	private ImageView tuijian_photo;
	private TextView tuijian_name;
	private TextView tuijian_price;
	private TextView tuijian_num;
	

	public TuiJianItemFragment(String type, YueSao yuesao) {
		this.type = type;
		this.yuesao = yuesao;
	}
	public TuiJianItemFragment(String type, YuYingShi yuyingshi) {
		this.type = type;
		this.yuyingshi = yuyingshi;
	}
	public TuiJianItemFragment(String type, CuiRuShi cuirushi) {
		this.type = type;
		this.cuirushi = cuirushi;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tuijianItem = inflater.inflate(R.layout.fragment_tuijian_item, null);
		tuijianItem.setOnClickListener(this);
		findView(tuijianItem);
		init();
		return tuijianItem;
	}

	private void findView(View view) {
		tuijian_photo=(ImageView) view.findViewById(R.id.tuijian_photo);
		tuijian_name=(TextView) view.findViewById(R.id.tuijian_name);
		tuijian_price=(TextView) view.findViewById(R.id.tuijian_price);
		tuijian_num=(TextView) view.findViewById(R.id.tuijian_num);
		
	}

	private void init(){
		//type为月嫂的时候
		if ("yuesao".equals(type)) {
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yuesao.getImage(), tuijian_photo,MyApplication.options_image);
			if (yuesao.getRealName()!=null) {
				tuijian_name.setText(yuesao.getRealName());
			}else {
				tuijian_name.setText("");
			}
			if (yuesao.getPrice()!=null) {
				tuijian_price.setText("￥："+yuesao.getPrice());
			}
			else {
				tuijian_price.setText("");
			}
			if (yuesao.getNums()!=null) {
				tuijian_num.setText("共"+yuesao.getNums()+"位雇主喜欢");
			}else {
				tuijian_num.setText("共"+0+"位雇主喜欢");
			}
		}
		//type为育婴师的时候
		if ("yuyingshi".equals(type)) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yuyingshi.getImage(), tuijian_photo,MyApplication.options_image);
			if (yuyingshi.getRealName()!=null) {
				tuijian_name.setText(yuyingshi.getRealName());
			}else {
				tuijian_name.setText("");
			}
			
			if (yuyingshi.getPrice()!=null) {
				tuijian_price.setText("￥："+yuyingshi.getPrice());
			}
			else {
				tuijian_price.setText("");
			}
			if (yuyingshi.getNums()!=null) {
				tuijian_num.setText("共"+yuyingshi.getNums()+"位雇主喜欢");
			}else {
				tuijian_num.setText("共"+0+"位雇主喜欢");
			}
		}
		//type为催乳师的时候
		if ("cuirushi".equals(type)) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+cuirushi.getImage(), tuijian_photo,MyApplication.options_image);
			if (cuirushi.getRealName()!=null) {
				tuijian_name.setText(cuirushi.getRealName());
			}else {
				tuijian_name.setText("");
			}
			if (cuirushi.getPrice()!=null) {
				tuijian_price.setText("￥："+cuirushi.getPrice());
			}else {
				tuijian_price.setText("");
			}
			if (cuirushi.getNums()!=null) {
				tuijian_num.setText("共"+cuirushi.getNums()+"位雇主喜欢");
			}else {
				tuijian_num.setText("共"+0+"位雇主喜欢");
			}
		}
		
	}

	@Override
	public void onClick(View arg0) {
		Intent intent=new Intent();
		if ("yuesao".equals(type)) {
			intent.setClass(getActivity(), YueSaoDetailActivity.class);
			intent.putExtra("id", yuesao.getNurseId());
			intent.putExtra("job", yuesao.getJob());
			intent.putExtra("startTime", "2015-08-30");
			intent.putExtra("endTime", "2015-09-20");
			startActivity(intent);
		}
		if ("yuyingshi".equals(type)) {
			intent.setClass(getActivity(), YueSaoDetailActivity.class);
			intent.putExtra("id", yuyingshi.getNurseId());
			intent.putExtra("job", yuyingshi.getJob());
			intent.putExtra("startTime", "2015-08-30");
			intent.putExtra("endTime", "2015-09-20");
			startActivity(intent);
		}
		if ("cuirushi".equals(type)) {
			intent.setClass(getActivity(), YueSaoDetailActivity.class);
			intent.putExtra("id", cuirushi.getNurseId());
			intent.putExtra("job", cuirushi.getJob());
			intent.putExtra("startTime", "2015-08-30");
			intent.putExtra("endTime", "2015-09-20");
			startActivity(intent);
		}

	}
}
