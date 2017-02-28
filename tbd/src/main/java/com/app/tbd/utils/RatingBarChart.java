package com.app.tbd.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class RatingBarChart extends View
{
	Paint textPaint;
	Paint linePaint;
	Paint boxPaint1;
	Paint boxPaint2;
	Paint textPaint1;
	Paint textPaint2;
	double cost;
	double earnings;
	float scaleFactor;

	public RatingBarChart(Context context)
	{
		super(context);
		initialise();
	}

	public RatingBarChart(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialise();
	}

	public RatingBarChart(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initialise();
	}

	void initialise()
	{
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		scaleFactor = metrics.density;

		textPaint = new Paint();
		linePaint = new Paint();
		boxPaint1 = new Paint();
		boxPaint2 = new Paint();
		textPaint1 = new Paint();
		textPaint2 = new Paint();
		linePaint.setStrokeWidth(1);
		linePaint.setColor(0xFFC5C5C5);
		textPaint.setColor(0xFFC5C5C5);
		textPaint.setTextSize(14 * scaleFactor);
		boxPaint1.setColor(0xFFFFBB33);
		boxPaint2.setColor(0xFF218b21);
		textPaint1.setColor(0xFFFFBB33);
		textPaint2.setColor(0xFF218b21);
		textPaint1.setTextSize(14 * scaleFactor);
		textPaint2.setTextSize(14 * scaleFactor);

	}

	// Earnings and cost are calculated values based on user inputs.
	// The Main app Activity calculates these and calls the below methods
	// when the user inputs a new value

	public void setCost(double value)
	{
		cost = value;
		invalidate();
	}

	public void setEarnings(double value)
	{
		earnings = value;
		invalidate();
	}

	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		int fullWidth = getWidth();
		int fullHeight = getHeight();
		int padding = (int) (10 * scaleFactor);
		int maxBarHeight = fullHeight - 5 * padding;
		float bar1height;
		float bar2height;

		if (earnings > cost)
		{
			bar2height = (float) maxBarHeight;
			bar1height = (float) (cost / earnings * maxBarHeight);
		}
		else
		{
			bar1height = (float) maxBarHeight;
			bar2height = (float) (earnings / cost * maxBarHeight);
		}

		canvas.drawRect(padding, 100, 300, 200, boxPaint1);
		canvas.drawRect(padding, 300, 350, 400, boxPaint1);
	}
}
