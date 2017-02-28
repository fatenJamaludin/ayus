package com.app.tbd.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageConverter
{
	public static String encode(Bitmap image)
	{
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		return imageEncoded;
	}

	public static Bitmap decode(String input)
	{
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}
