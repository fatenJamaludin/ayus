package com.app.tbd.ui.Activity.SlidePage;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.app.tbd.ui.Model.Request.PagerBoardingPassObj;

public class ProductImagesPagerAdapter extends BaseFragmentPagerAdapter
{

	private List<PagerBoardingPassObj> listProductImages;

	public ProductImagesPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	public void addAll(List<PagerBoardingPassObj> listProductImages)
	{
		this.listProductImages = listProductImages;
		Log.e("ProductImagesFragment", "listProductImages");

		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position)
	{
		Log.e("ProductImagesFragment", "ProductImagesFragment");
		return ProductImagesFragment.newInstance(listProductImages.get(position), listProductImages);

	}

	@Override
	public int getCount()
	{
		return this.listProductImages.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return super.getPageTitle(position);
	}

}
