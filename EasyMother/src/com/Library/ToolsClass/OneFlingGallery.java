package com.Library.ToolsClass;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class OneFlingGallery extends Gallery
{
	private Handler handler;
	boolean bToRight = true;
	public boolean bStop = false;
	Context mContext;

	public OneFlingGallery(Context context)
	{
		super(context, null);
		mContext = context;
		handler = new Handler();
	}

	public OneFlingGallery(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		handler = new Handler();
	}

	public OneFlingGallery(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mContext = context;
		handler = new Handler();
	}

	public void postDelayedScrollNext()
	{
		handler.postDelayed(new Runnable()
		{
			public void run()
			{
				postDelayedScrollNext();
				if (!bStop)
				{
					int pos = getSelectedItemPosition();
//					if (bToRight)
//					{
						if (pos + 1 < getCount())
						{
							onScroll(null, null, 1, 0);
							onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
							// setSelection(pos+1, true);
						}
						else
						{
							setSelection(0, true);
//							onScroll(null, null, -1, 0);
//							onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
//							bToRight = false;
						}
//					}
//					else
//					{
//						if (pos > 0)
//						{
//							// setSelection(pos-1, true);
//							onScroll(null, null, -1, 0);
//							onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
//						}
//						else
//						{
//							// setSelection(pos+1, true);
//							onScroll(null, null, 1, 0);
//							onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
//							bToRight = true;
//						}
//					}
				}
			}
		}, 2000);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		int keyCode;

		if (e1 == null || e2 == null)
		{
			super.onFling(e1, e2, velocityX, velocityY);
		}
		else
		{
			float dx;
			dx = e1.getRawX() - e2.getRawX();
			if (dx > 0)
				bToRight = true;
			else
				bToRight = false;

			if (e2.getX() > e1.getX())
			{
				keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
			}
			else
			{
				keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
			}
			onKeyDown(keyCode, null);
		}

		return true;
	} 

}