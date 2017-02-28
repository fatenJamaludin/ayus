package com.app.tbd.ui.Activity.SlidePage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter
{

	public BaseFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0)
	{
		return null;
	}

	@Override
	public int getCount()
	{
		return 0;
	}

}
