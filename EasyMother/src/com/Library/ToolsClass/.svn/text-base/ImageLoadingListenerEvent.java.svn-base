package com.Library.ToolsClass;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoadingListenerEvent implements ImageLoadingListener
{
	public View loadingView;

	// public ImageView iv;
	// private Context _Context = null;

	public ImageLoadingListenerEvent(View loadingView)
	{
		this.loadingView = loadingView;
		this.loadingView.setVisibility(View.VISIBLE);
	}

	public void onLoadingStarted(String s, View view)
	{
		this.loadingView.setVisibility(View.VISIBLE);
		// this.iv.setVisibility(View.GONE);
	};

	public void onLoadingFailed(String s, View view, FailReason failreason)
	{
		 this.loadingView.setVisibility(View.GONE);
		// this.iv.setVisibility(View.GONE);
	};

	public void onLoadingComplete(String s, View view, Bitmap bitmap)
	{
		//((ImageView) view).setImageBitmap(getRoundedCornerBitmap(bitmap));
		this.loadingView.setVisibility(View.GONE);
		// this.iv.setVisibility(View.VISIBLE);
	};

	public void onLoadingCancelled(String s, View view)
	{
		// this.loadingView.setVisibility(View.VISIBLE);
		// this.iv.setVisibility(View.GONE);
	};

	/**
	 * 将图片转为圆型
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		final float roundPx = width > height ? height / 2 : width / 2;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
}