package com.app.tbd.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ExpandAbleGridView extends GridView
{

	ExpandAbleGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public ExpandAbleGridView(Context context)
	{
		super(context);
	}

	public ExpandAbleGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// Calculate entire height by providing a very large height hint.
		// View.MEASURED_SIZE_MASK represents the largest height possible.
		int expandSpec = View.MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, View.MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

		ViewGroup.LayoutParams params = getLayoutParams();
		params.height = getMeasuredHeight();
	}

}