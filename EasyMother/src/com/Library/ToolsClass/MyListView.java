package com.Library.ToolsClass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by ishishuji on 14-7-4.
 */
public class MyListView extends ListView
{
	public MyListView(Context context)
	{
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public int measureFragment(View view)
	{
		if (view == null)
			return 0;

		view.measure(0, 0);
		return view.getMeasuredHeight();
	}
}
