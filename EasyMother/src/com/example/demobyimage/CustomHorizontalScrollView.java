package com.example.demobyimage;


import com.alidao.mama.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

//net.hotpk.h5box.view.CustomHorizontalScrollView
public class CustomHorizontalScrollView extends HorizontalScrollView {
	private View inner;
	private float x;
	private Rect normal = new Rect();
	private boolean bounceEnabled;
	private float lastX;
	private float lastY;
	private boolean isCount;

	public CustomHorizontalScrollView(Context context) {
		this(context, null);
	}

	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CustomHorizontalScrollView, defStyle, 0);
		bounceEnabled = a.getBoolean(
				R.styleable.CustomHorizontalScrollView_bounceEnabled, false);
		a.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			mOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	private void mOnTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = ev.getX();
			lastY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (ev.getX() - lastX != 0
					&& Math.abs((ev.getY() - lastY) / (ev.getX() - lastX)) < 2) {
				if (getParent() != null) {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			}
			if (bounceEnabled) {
				float preX = x;
				float nowX = ev.getX();
				int deltaX = (int) (preX - nowX);
				if (!isCount) {
					deltaX = 0;
				}
				x = nowX;
				isCount = true;
				if (isNeedMove()) {
					if (normal.isEmpty()) {
						normal.set(inner.getLeft(), inner.getTop(),
								inner.getRight(), inner.getBottom());
					}
					inner.layout(inner.getLeft() - deltaX / 2, inner.getTop(),
							inner.getRight() - deltaX / 2, inner.getBottom());
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (isNeedAnimation() && bounceEnabled) {
				animation();
				isCount = false;
			}
			if (getParent() != null) {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		default:
			break;
		}
	}

	private boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	private ValueAnimator animator;

	@SuppressLint("NewApi")
	public void animation() {
		// 开启移动动画
		if (animator != null) {
			animator.cancel();
		}
		if (animator == null) {
			animator = new ValueAnimator();
			animator.setTarget(inner);
			animator.setDuration(220);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					inner.setTranslationX((Float) animation.getAnimatedValue());
				}
			});
		}
		animator.setFloatValues(inner.getLeft() - normal.left, 0);
		animator.start();
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();

	}

	private boolean isNeedMove() {
		int offsetX = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		if (scrollX == 0 || scrollX == offsetX) {
			return true;
		}
		return false;
	}
}
