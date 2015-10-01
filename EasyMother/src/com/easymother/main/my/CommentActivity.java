package com.easymother.main.my;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.NurseJobBean;
import com.easymother.bean.OrderPayBean;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.customview.ImageZoom;
import com.easymother.customview.MyGridView;
import com.easymother.main.homepage.CommentImageAdapter;
import com.easymother.utils.EasyMotherUtils;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity {
	private Button confirm;//确认
	private ImageView image;//图片
	private ImageView add_photo;//添加
	private TextView nurse_name;//护理师姓名
	private TextView nurse_type;//护理师姓名
	private TextView work_express;//护理师工龄
	private TextView nurse_age;//护理师工龄
	private TextView nurse_area;//护理师地区
	private TextView nurse_address;//护理师地址
	private TextView price;//护理师价格
	private TextView marketprice;//护理师价格
	private RatingBar ratingBar1;//评论星星
	private MyGridView gridview;//评论图片
	private EditText comment_content;//评论内容
	
	private Intent intent;
	private NurseBaseBean nursebase;
	private NurseJobBean nursejob;
	
	private int CHOOSE_PHOTO=1;//选择图片的请求码
	private List<Bitmap> images;//图片集合
	private CommentImageAdapter adapter;//图片适配器
	private float stars=0;//评论得分
	private OrderPayBean order;//订单
	private String type;
	private Integer nurseId;
	private String nurseName;
	private String job;
	private String userId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mypage_comment);
		EasyMotherUtils.initTitle(this, "评论", false);
		intent=getIntent();
		type=intent.getStringExtra("type");
		stars=5;//默认给10分
		findView();
		init();
	}

	private void findView() {
		confirm=(Button) findViewById(R.id.submit);
		nurse_name=(TextView) findViewById(R.id.nurse_name);
		nurse_type=(TextView) findViewById(R.id.nurse_type);
		work_express=(TextView) findViewById(R.id.work_express);
		nurse_age=(TextView) findViewById(R.id.nurse_age);
		nurse_area=(TextView) findViewById(R.id.nurse_area);
		nurse_address=(TextView) findViewById(R.id.nurse_address);
		price=(TextView) findViewById(R.id.price);
		marketprice=(TextView) findViewById(R.id.marketprice);
		ratingBar1=(RatingBar) findViewById(R.id.ratingBar1);
		gridview=(MyGridView) findViewById(R.id.gridview);
		comment_content=(EditText) findViewById(R.id.comment_content);
		add_photo=(ImageView) findViewById(R.id.add_photo);
		image=(ImageView) findViewById(R.id.image);
	}

	private void init() {
		//清楚选中状态
		comment_content.clearFocus();
	
		if ("detail".equals(type)) {
			nursebase=(NurseBaseBean) intent.getSerializableExtra("nursebase");
			nursejob=(NurseJobBean) intent.getSerializableExtra("nursejob");
			Log.e("base", nursebase.toString());
			Log.e("nursejob", nursejob.toString());
			images=new ArrayList<>();
			
			ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursebase.getImage(), image,MyApplication.options_photo);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ImageZoom.showBigImgae(CommentActivity.this, BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+nursebase.getImage());
				}
			});
			if (nursebase.getRealName()!=null) {
				nurseName=nursebase.getRealName();
				nurse_name.setText(nursebase.getRealName());
			}else {
				nurse_name.setText("");
			}
			if (nursejob.getJob()!=null) {
				nurseId=nursejob.getNurseId();
				job=nursejob.getJob();
				if ("YS".equals(nursejob.getJob())) {
					nurse_type.setText("月嫂");
				}
				if ("YYS".equals(nursejob.getJob())) {
					nurse_type.setText("育婴师");
				}
				if ("CRS".equals(nursejob.getJob())) {
					nurse_type.setText("催乳师");
				}
				if ("SHORT_YS".equals(nursejob.getJob())) {
					nurse_type.setText("短期月嫂");
				}
				if ("SHORT_YYS".equals(nursejob.getJob())) {
					nurse_type.setText("短期育婴师");
				}
				if (nursejob.getLevelScore()!=null) {
					ratingBar1.setProgress(nursejob.getLevelScore()/2);
				}
			}
			if (nursejob.getSeniority()!=null) {
				work_express.setText("从业"+nursejob.getSeniority()+"年");
			}
			if (nursebase.getAge()!=null) {
				nurse_age.setText(nursebase.getAge()+"岁");
			}
			if (nursebase.getHometown()!=null) {
				nurse_area.setText(nursebase.getHometown());
			}
			if (nursebase.getCurrentAddress()!=null) {
				nurse_address.setText("现居地："+nursebase.getCurrentAddress());
			}else {
				nurse_address.setText("");
			}
//			if (nursejob.getPrice()!=null) {
//				price.setText(nursejob.getPrice()+"元/月");
//			}
			if (nursejob.getShowPrice()!=null) {
				price.setText(nursejob.getShowPrice());
			}
			if (nursejob.getMarketPrice()!=null) {
				marketprice.setText("市场价："+nursejob.getMarketPrice()+"元/月");
				marketprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		}else if ("order".equals(type)){
			order=(OrderPayBean) intent.getSerializableExtra("order");
			if (order!=null) {
				nurseId=order.getNurseId();
				images=new ArrayList<>();
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+order.getImage(), image,MyApplication.options_photo);
				image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ImageZoom.showBigImgae(CommentActivity.this, BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+order.getImage());
					}
				});
				
				if (order.getRealName()!=null) {
					nurseName=order.getRealName();
					nurse_name.setText(order.getRealName());
				}else {
					nurse_name.setText("");
				}
				if (order.getJob()!=null) {
					
					job=order.getJob();
					if ("YS".equals(order.getJob())) {
						nurse_type.setText("月嫂");
					}
					if ("YYS".equals(order.getJob())) {
						nurse_type.setText("育婴师");
					}
					if ("CRS".equals(order.getJob())) {
						nurse_type.setText("催乳师");
					}
					if ("SHORT_YS".equals(order.getJob())) {
						nurse_type.setText("短期月嫂");
					}
					if ("SHORT_YYS".equals(order.getJob())) {
						nurse_type.setText("短期育婴师");
					}
				}
				if (order.getSeniority()!=null) {
					work_express.setText("从业"+order.getSeniority()+"年");
				}
				if (order.getAge()!=null) {
					nurse_age.setText(order.getAge()+"岁");
				}
				if (order.getHometown()!=null) {
					nurse_area.setText(order.getHometown());
				}
				if (order.getCurrentAddress()!=null) {
					nurse_address.setText("现居地："+order.getCurrentAddress());
				}else {
					nurse_address.setText("");
				}
				if (order.getPrice()!=null) {
					price.setText(order.getPrice()+"元/月");
				}
				if (order.getMarketPrice()!=null) {
					marketprice.setText("市场价："+order.getMarketPrice()+"元/月");
					marketprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				}
			}
			
		}
		
		
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				Toast.makeText(CommentActivity.this, "评论成功", 0).show();
				saveComment();
//				CommentActivity.this.finish();
			}
		});
		add_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EasyMotherUtils.chosePhoto(CommentActivity.this, CHOOSE_PHOTO, null);
				
				
			}
		});
		
		ratingBar1.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				stars=rating;
				Log.e("得分+rating", rating+"");
				Log.e("得分+ratingBar.getNumStars()", ratingBar.getNumStars()+"");
			}
		});
		
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			if (requestCode==CHOOSE_PHOTO) {
				try {
					Uri uri=data.getData();
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
					bitmapOptions.inJustDecodeBounds = true;
					  bitmapOptions.inSampleSize = 4;  
					  bitmapOptions.inJustDecodeBounds = false;
					Bitmap  bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), null , bitmapOptions);
					//上传图片
					EasyMotherUtils.uploadPhoto(bitmap,BaseInfo.UPLOADPHTO , null);
					images.add(bitmap);//将图片保存到一个集合，后面上传
					if (adapter==null) {
						adapter=new CommentImageAdapter(this, images, R.layout.comment_image);
						gridview.setAdapter(adapter);
					}else {
//						adapter.addDate(bitmap);
						adapter.notifyDataSetChanged();
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 提交评价
	 */
	public void saveComment(){
		List<String> imagenames=new ArrayList<>();
		RequestParams params=new RequestParams();
		if (MyApplication.preferences.getInt("id", 0)==0) {
			Toast.makeText(this, "亲，你还没登陆哟！", 0).show();
			EasyMotherUtils.goActivity(this, LoginOrRegisterActivity.class);
			return;
		}
		
		params.put("userId",MyApplication.preferences.getInt("id", 0) );
	    if ("detail".equals(type)) {
	    	if (nursejob.getId()!=null) {
				params.put("nurseId", nurseId);
			}
			if (nursejob.getNurseName()!=null) {
				params.put("nurseName", nurseName);
			}
			if (nursejob.getJob()!=null) {
				params.put("job", job);
			}
				params.put("score", (int)stars*2+"");
			if (comment_content.getText().toString().trim()!=null) {
				params.put("content", comment_content.getText().toString());
			}
		}
	    if ("order".equals(type)) {
	    	if (order.getNurseId()!=null) {
				params.put("nurseId", nurseId);
			}
			if (order.getRealName()!=null) {
				params.put("nurseName", nurseName);
			}
			if (order.getJob()!=null) {
				params.put("job", job);
			}
				params.put("score", (int)stars*2+"");
			if (comment_content.getText().toString().trim()!=null) {
				params.put("content", comment_content.getText().toString());
			}
		}
		
		//获取上传图片时保存的名字
		imagenames=EasyMotherUtils.photosname;
		if (imagenames!=null&&imagenames.size()>0) {
			params.put("images", imagenames.toString());
		}
		//上传后将名字清空
		EasyMotherUtils.photosname.clear();
		NetworkHelper.doGet(BaseInfo.SAVE_COMMENT, params, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (JsonUtils.getRootResult(response).getIsSuccess()) {
					Toast.makeText(CommentActivity.this, "评论成功", 0).show();
					CommentActivity.this.finish();
				}else {
					Toast.makeText(CommentActivity.this,"评论失败" , 0).show();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("评论失败----", responseString);
				Toast.makeText(CommentActivity.this,"连接服务器失败" , 0).show();
			}
		});
		
	}
}
