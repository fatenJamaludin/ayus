package com.app.tbd.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class RotateAnimationClass
{
	private int min_swipe_distance = 60;
	private int array_size = 0;
	private int delay_duration = 200;
	private int new_image_pos = 0;
	private int initial_touch_cordi = 0;
	private int skip_image = 1;
	private ImageView imageview;
	private int rotation_image_position;
	private ArrayList<String> main_array;
	private Handler handler;
	private Runnable runnable;
	private Thread th;

	public RotateAnimationClass(ArrayList<String> bitmapArray, ImageView imageview)
	{
		this.main_array = bitmapArray;
		this.imageview = imageview;
		this.array_size = bitmapArray.size();
		SetTouchListner();
	}

	private void SetTouchListner()
	{
		try
		{
			this.imageview.setLongClickable(true);
			this.imageview.setOnTouchListener(new OnTochImplement(null));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("Error", "imageview is null");
		}
	}

	private int CheckSwipeDistace(int x_cordinate)
	{
		if (x_cordinate < 0)
		{
			x_cordinate *= -1;
		}
		if (x_cordinate > this.min_swipe_distance)
		{
			return 1;
		}
		return 0;
	}

	private void LoadImage(int x_cordinate)
	{
		if (x_cordinate < 0)
		{
			this.new_image_pos += this.skip_image;
		}
		else
		{
			this.new_image_pos -= this.skip_image;
		}
		if (this.new_image_pos < 0)
		{
			this.new_image_pos = (this.array_size - 1);
		}
		if (this.new_image_pos >= this.array_size)
		{
			this.new_image_pos = 0;
		}

		Drawable d = Drawable.createFromPath(main_array.get(this.new_image_pos));
		this.imageview.setImageDrawable(d);
	}

	public void SetMinSwipeDistance(int swipe_distance)
	{
		this.min_swipe_distance = swipe_distance;
	}

	public void SkipImages(int skip_no_image)
	{
		if (skip_no_image > 0)
		{
			this.skip_image = skip_no_image;
		}
	}

	public void AutoPlay()
	{
		if (!isAutoPlayRunning())
		{
			this.handler = new Handler();
			this.rotation_image_position = this.new_image_pos;
			LoadImage(1);
			StartRunnable();
		}
		else
		{
			StopThrread();
		}
	}

	private void SetDuration(int duration)
	{
		this.delay_duration = duration;
	}

	private void StartRunnable()
	{
		this.th = new Thread(new Runnable()
		{
			public void run()
			{
				while (RotateAnimationClass.this.rotation_image_position != RotateAnimationClass.this.new_image_pos)
				{
					try
					{
						Thread.sleep(RotateAnimationClass.this.delay_duration);
						RotateAnimationClass.this.handler.post(new Runnable()
						{
							public void run()
							{
								RotateAnimationClass.this.LoadImage(1);
							}
						});
					}
					catch (Exception localException)
					{
					}
				}
			}
		});
		this.th.start();
	}

	public boolean isAutoPlayRunning()
	{
		if (this.th != null)
		{
			return this.th.isAlive();
		}
		return false;
	}

	public void StopThrread()
	{
		this.rotation_image_position = this.new_image_pos;
		this.th = null;
	}

	private class OnTochImplement implements View.OnTouchListener
	{
		private OnTochImplement(Object object)
		{
		}

		public boolean onTouch(View v, MotionEvent event)
		{
			RotateAnimationClass.this.StopThrread();
			if (event.getAction() == 0)
			{
				RotateAnimationClass.this.initial_touch_cordi = ((int) event.getX());
			}
			else if (event.getAction() != 1)
			{
				if (event.getAction() == 2)
				{
					int x_cordinate = (int) event.getX();
					if (x_cordinate < 0)
					{
						x_cordinate *= -1;
					}
					if (RotateAnimationClass.this.CheckSwipeDistace(RotateAnimationClass.this.initial_touch_cordi - x_cordinate) == 1)
					{
						RotateAnimationClass.this.LoadImage(RotateAnimationClass.this.initial_touch_cordi - x_cordinate);
						RotateAnimationClass.this.initial_touch_cordi = ((int) event.getX());
					}
				}
			}
			return false;
		}
	}
}
