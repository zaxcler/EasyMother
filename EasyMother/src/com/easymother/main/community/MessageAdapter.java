package com.easymother.main.community;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alidao.mama.R;
import com.easymother.bean.ForumPost;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MessageAdapter extends CommonAdapter<ForumPost> {

	private List<ForumPost> list;
	private Context context;
	public MessageAdapter(Context context, List<ForumPost> list, int resource) {
		super(context, list, resource);
		this.list=list;
		this.context=context;
	}
	public void addList(List<ForumPost> list){
		this.list.addAll(list);
	}
	public void clear(){
		this.list.clear();
	}

	@Override
	public void setDataToItem(ViewHolder holder, final ForumPost t) {
		ImageView imageView1=holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getUserImage(), imageView1, MyApplication.options_photo);
		TextView user_name=holder.getView(R.id.user_name);
		if (t.getUserNickname()!=null) {
			user_name.setText(t.getUserNickname());
		}else {
			user_name.setText("");
		}
		
		TextView content=holder.getView(R.id.content);
		if (t.getContent()!=null) {
			content.setText(t.getContent());
		}else {
			content.setText("");
		}
		
		TextView create_time=holder.getView(R.id.create_time);
		if (t.getCreateTime()!=null) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("MM月dd HH:mm");
			create_time.setText(dateFormat.format(t.getCreateTime()));
		}else {
			create_time.setText("");
		}
		TextView delete=holder.getView(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				RequestParams params=new RequestParams();
				params.put("id", t.getId());
				NetworkHelper.doGet(BaseInfo.DELETE_REPLY, params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (JsonUtils.getRootResult(response).getIsSuccess()) {
							list.remove(t);
							notifyDataSetChanged();
							Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
						}
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString,
							Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("回复失败", responseString);
						Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		//回复
		ImageView reply=holder.getView(R.id.reply);
		reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog=new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				final View view=LayoutInflater.from(context).inflate(R.layout.message_reply, null);
				dialog.setContentView(view);
				int width=MyApplication.getScreen_width()/5*4;
				dialog.getWindow().setLayout(width, LayoutParams.WRAP_CONTENT);
				TextView send=(TextView) view.findViewById(R.id.send);
				TextView dimss=(TextView) view.findViewById(R.id.dimss);
				dimss.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						
					}
				});
				send.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//回复
						final EditText message=(EditText) view.findViewById(R.id.message);
						RequestParams params=new RequestParams();
						params.put("parentId", t.getId());
						String msg=message.getText().toString().trim();
						if (msg!=null && !"".equals(msg)) {
							params.put("content", msg);
						}
						NetworkHelper.doGet(BaseInfo.REPLY, params, new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (JsonUtils.getRootResult(response).getIsSuccess()) {
									Log.e("回复", response.toString());
									dialog.dismiss();
									Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(context, "回复失败", Toast.LENGTH_SHORT).show();
								}
							}
							@Override
							public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
								super.onFailure(statusCode, headers, responseString, throwable);
								Log.e("回复失败", responseString);
								Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
				dialog.show();

				
			}
		});
		
		
	}

}
