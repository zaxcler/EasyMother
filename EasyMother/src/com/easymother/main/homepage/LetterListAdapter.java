package com.easymother.main.homepage;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easymother.bean.Letter;
import com.easymother.bean.NurseBaseBean;
import com.easymother.bean.Root;
import com.easymother.bean.TestBean;
import com.easymother.bean.YueSao;
import com.easymother.configure.BaseInfo;
import com.easymother.configure.MyApplication;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.JsonUtils;
import com.easymother.utils.NetworkHelper;
import com.easymother.utils.ViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LetterListAdapter extends CommonAdapter<Letter> {
	private List<Letter> letters;

	public LetterListAdapter(Context context, List<Letter> list, int resource) {
		super(context, list, resource);
		this.letters=list;
	}
	public void add(List<Letter> letters){
		this.letters.addAll(letters);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Letter t) {
		TextView letter_title=holder.getView(R.id.letter_title);
		TextView letter_content=holder.getView(R.id.letter_content);
		ImageView letter_image=holder.getView(R.id.letter_image);
		if (t.getTitle()!=null) {
			letter_title.setText(t.getTitle());
		}
		if (t.getContent()!=null) {
			letter_content.setText(Html.fromHtml(t.getContent()));
		}
		if (t.getImages()!=null) {
			if (!letter_image.getTag().equals(t.getImages())) {
				ImageLoader.getInstance().displayImage(BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getImages(), letter_image,MyApplication.options_image);
			}
			
			Log.e("信件图片地址", BaseInfo.BASE_URL+BaseInfo.BASE_PICTURE+t.getImages());
		}
		
	}
}
