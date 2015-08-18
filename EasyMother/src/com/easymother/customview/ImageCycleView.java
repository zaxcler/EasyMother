package com.easymother.customview;

import java.util.ArrayList;

import com.easymother.main.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;



/**
 * 广告图片自动轮播控件</br>
 * 
 */
public class ImageCycleView extends LinearLayout {
	/**
	 * 是否拖拽
	 */
	private boolean isDragging;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 图片轮播视图
	 */
	private ViewPager mAdvPager = null;
	/**
	 * 滚动图片视图适配
	 */
	private ImageCycleAdapter mAdvAdapter;
	/**
	 * 图片轮播指示器控件
	 */
	private ViewGroup mGroup;

	/**
	 * 图片轮播指示个图
	 */
	private ImageView mImageView = null;

	/**
	 * 滚动图片指示视图列表
	 */
	private ImageView[] mImageViews = null;

	/**
	 * 图片滚动当前图片下标
	 */

	ArrayList<String> imageNameList;
	private boolean isFrist = true;

	/**
	 * @param context
	 */
	public ImageCycleView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
		mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
		mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
		// 滚动图片右下指示器视
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
	}

	/**
	 * 装填图片数据
	 * 
	 * @param imageUrlList
	 * @param imageCycleViewListener
	 */
	public void setImageResources(ArrayList<String> imageUrlList, ImageCycleViewListener imageCycleViewListener) {
		// 清除
		mGroup.removeAllViews();
		// 图片广告数量
		final int imageCount = imageUrlList.size();
		mImageViews = new ImageView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 30;
			mImageView.setScaleType(ScaleType.FIT_XY);
			mImageView.setLayoutParams(params);

			mImageViews[i] = mImageView;
			if (i == 0) {
				mImageViews[i].setBackgroundResource(R.drawable.banner_dian_focus);
			} else {
				mImageViews[i].setBackgroundResource(R.drawable.banner_dian_blur);
			}
			mGroup.addView(mImageViews[i]);
		}

		mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList, imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
		if (isFrist)
		{
			autoScroll();
			isFrist =false;
		}
	}

	/**
	 * 自动滚动
	 */
	private void autoScroll()
	{
		mAdvPager.postDelayed(new Runnable()
		{

			@Override
			public void run()
			{
				if (!isDragging)
				{
					// 若用户没有拖拽，则自动滚动
					mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
				}
				mAdvPager.postDelayed(this, 3000);
			}
		}, 3000);
	}
	
	
	/**
	 * 轮播图片监听
	 * 
	 * @author minking
	 */
	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state)
			{
			case ViewPager.SCROLL_STATE_DRAGGING:
				// 用户拖拽
				isDragging = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				// 空闲状态
				isDragging = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				// 被释放时
				isDragging = false;
				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			index = index % mImageViews.length;
			// 设置当前显示的图片
			// 设置图片滚动指示器背
			mImageViews[index].setBackgroundResource(R.drawable.banner_dian_focus);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					mImageViews[i].setBackgroundResource(R.drawable.banner_dian_blur);
				}
			}
		}
	}

	private class ImageCycleAdapter extends PagerAdapter {

		/**
		 * 图片视图缓存列表
		 */
		private ArrayList<ImageView> mImageViewCacheList;

		/**
		 * 图片资源列表
		 */
		private ArrayList<String> mAdList = new ArrayList<String>();

		/**
		 * 广告图片点击监听
		 */
		private ImageCycleViewListener mImageCycleViewListener;

		private Context mContext;

		public ImageCycleAdapter(Context context, ArrayList<String> adList,
				ImageCycleViewListener imageCycleViewListener) {
			this.mContext = context;
			this.mAdList = adList;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<ImageView>();
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			String imageUrl = mAdList.get(position % mAdList.size());
			ImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				// 设置图片点击监听
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mImageCycleViewListener.onImageClick(position % mAdList.size(), v);
					}
				});
			} else {
				imageView = mImageViewCacheList.remove(0);
			}
			imageView.setTag(imageUrl);
			container.addView(imageView);
			ImageLoader.getInstance().displayImage(imageUrl, imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			mAdvPager.removeView(view);
			mImageViewCacheList.add(view);

		}

	}

	/**
	 * 轮播控件的监听事件
	 * 
	 * @author minking
	 */
	public static interface ImageCycleViewListener {

		/**
		 * 单击图片事件
		 * 
		 * @param position
		 * @param imageView
		 */
		public void onImageClick(int position, View imageView);
	}

}
