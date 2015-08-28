package com.easymother.main.homepage;

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
			if (yuesao.getImage()!=null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yuesao.getImage(), tuijian_photo);
//				ImageLoader.getInstance().displayImage("http://zaxcler.oss-cn-beijing.aliyuncs.com/12.jpg", tuijian_photo);
			}
			if (yuesao.getRealName()!=null) {
				tuijian_name.setText(yuesao.getRealName());
			}
			if (yuesao.getMarketPrice()!=null) {
				tuijian_price.setText(yuesao.getMarketPrice());
			}
		}
		//type为育婴师的时候
		if ("yuyingshi".equals(type)) {
			if (yuyingshi.getImage()!=null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+yuyingshi.getImage(), tuijian_photo);
//				ImageLoader.getInstance().displayImage("http://zaxcler.oss-cn-beijing.aliyuncs.com/13.jpg", tuijian_photo);
			}
			if (yuyingshi.getRealName()!=null) {
				tuijian_name.setText(yuyingshi.getRealName());
			}
			if (yuyingshi.getMarketPrice()!=null) {
				tuijian_price.setText(yuyingshi.getMarketPrice());
			}
		}
		//type为催乳师的时候
		if ("cuirushi".equals(type)) {
			if (cuirushi.getImage()!=null) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+cuirushi.getImage(), tuijian_photo);
//				ImageLoader.getInstance().displayImage("http://zaxcler.oss-cn-beijing.aliyuncs.com/15.jpg", tuijian_photo);
			}
			if (cuirushi.getRealName()!=null) {
				tuijian_name.setText(cuirushi.getRealName());
			}
			if (cuirushi.getMarketPrice()!=null) {
				tuijian_price.setText(cuirushi.getMarketPrice());
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
			startActivity(intent);
		}
		if ("yuyingshi".equals(type)) {
			intent.setClass(getActivity(), YuYingShiDetailActivity.class);
			intent.putExtra("id", yuyingshi.getNurseId());
			intent.putExtra("job", yuyingshi.getJob());
			startActivity(intent);
		}
		if ("cuirushi".equals(type)) {
			intent.setClass(getActivity(), CuiRuShiDetailActivity.class);
			intent.putExtra("id", cuirushi.getNurseId());
			intent.putExtra("job", cuirushi.getJob());
			startActivity(intent);
		}

	}
}
