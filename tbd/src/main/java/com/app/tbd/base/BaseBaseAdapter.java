package com.app.tbd.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

public class BaseBaseAdapter<T> extends BaseAdapter
{
	private Activity activity;
	private LayoutInflater layoutInflater;
	protected com.app.tbd.base.AQuery aq;
	protected List<T> listItems = Collections.emptyList();

	public BaseBaseAdapter(Activity activity)
	{
		this.activity = activity;
		layoutInflater = LayoutInflater.from(activity);
		aq = new com.app.tbd.base.AQuery(activity);
	}

	public Activity getActivity()
	{
		return activity;
	}

	public LayoutInflater getLayoutInflater()
	{
		return layoutInflater;
	}

	public List<T> getListItems()
	{
		return listItems;
	}

	public void setItems(List<T> items)
	{
		this.listItems = items;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		if (listItems == null)
		{
			return 0;
		}
		return listItems.size();
	}

	@Override
	public T getItem(int position)
	{
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return convertView;
	}

}
