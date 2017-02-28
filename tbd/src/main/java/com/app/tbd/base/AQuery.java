package com.app.tbd.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AbstractAQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AQuery extends AbstractAQuery<AQuery> {

	LinkedHashMap<String, View> ivs = new LinkedHashMap<String, View>();

	public AQuery(Activity act)
	{
		super(act);
	}

	public AQuery(View root)
	{
		super(root);
	}

	public AQuery(Activity act, View root)
	{
		super(act, root);
	}

	public AQuery(Context context)
	{
		super(context);
	}

	@Override
	public AQuery id(View view)
	{
		return super.id(view);
	}

	@Override
	public AQuery image(Bitmap bm)
	{
		if (view instanceof ImageView)
		{
			ImageView iv = (ImageView) view;
			iv.setTag(AQuery.TAG_URL, null);
			iv.setImageBitmap(bm);
			return self();
		}

		return super.image(bm);
	}

	public AQuery image(String url, View view)
	{
		this.view = view;
		image(url, 0);
		return self();
	}

	/**
	 * Set the image of an ImageView for VRSM
	 *
	 * @param url
	 * @param targetWidth
	 * @return self
	 */
	public AQuery image(String url, final int targetWidth)
	{
		if (url == null)
		{
			return self();
		}

		final String fullUrl = url.replace("&amp;", "&");

		ivs.put(fullUrl, view);

		Uri uri = Uri.parse(fullUrl);
		Map<String, Object> params = new HashMap<String, Object>();
		String o = uri.getQueryParameter("o");
		String i = uri.getQueryParameter("i");
		String k = uri.getQueryParameter("k");
		params.put("o", o);
		params.put("i", i);
		params.put("k", k);
		if (o == null || i == null || k == null)
		{
			return self();
		}

		progress(progress).ajax(fullUrl, params, Bitmap.class, 1000 * 60 * 10, new AjaxCallback<Bitmap>() {
			@Override
			public void callback(String url, Bitmap bmp, AjaxStatus status)
			{
				if (progress instanceof View)
				{
					View bar = (View) progress;
					id(bar).gone();
				}

				Bitmap imageBitmap = null;
				if (targetWidth > 0 && bmp != null)
				{
					imageBitmap = resize(targetWidth, bmp);
				}
				else
				{
					imageBitmap = bmp;
				}

				id(ivs.get(url)).image(imageBitmap);
				ivs.remove(url);
			}
		});

		return self();
	}

	public Bitmap resize(final int targetWidth, Bitmap bmp)
	{
		Bitmap imageBitmap;
		imageBitmap = Bitmap.createBitmap(targetWidth, targetWidth, Config.ARGB_8888);
		float originalWidth = bmp.getWidth(), originalHeight = bmp.getHeight();

		Canvas canvas = new Canvas(imageBitmap);
		float scale = targetWidth / originalWidth;
		float xTranslation = 0.0f, yTranslation = (targetWidth - originalHeight * scale) / 2.0f;

		Matrix transformation = new Matrix();
		transformation.postTranslate(xTranslation, yTranslation);
		transformation.preScale(scale, scale);

		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		canvas.drawBitmap(bmp, transformation, paint);

		return imageBitmap;
	}

	/**
	 * Extended version of core ajax call which support file and memory cache
	 * for VRSM project
	 *
	 * @param url
	 * @param params
	 * @param type
	 * @param expire
	 * @param callback
	 * @return
	 */
	public <K> AQuery ajax(String url, Map<String, ?> params, Class<K> type, long expire, AjaxCallback<K> callback)
	{
		callback.type(type).url(url).params(params).fileCache(true).memCache(true).expire(expire);
		return ajax(callback);
	}
}
