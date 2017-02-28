package com.app.tbd.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseBaseAdapter;

public class DropMenuAdapter extends BaseBaseAdapter<DropDownItem>
{
	public DropMenuAdapter(Activity activity)
	{
		super(activity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		// object item based on the position
		DropDownItem objectItem = getItem(position);

		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(getActivity()).inflate(R.layout.simple_text, parent, false);
			aq.recycle(convertView);
			holder.txtText = aq.id(R.id.text1).getTextView();
			holder.txtText.setTextSize(20);
			holder.txtText.setPadding(30, 30, 30, 30);
			holder.txtText.setGravity(Gravity.CENTER_HORIZONTAL);

			// store the holder with the view.
			convertView.setTag(holder);

		}
		else
		{
			// we've just avoided calling findViewById() on resource
			// everytime just use the viewHolder
			holder = (ViewHolder) convertView.getTag();
		}

		// assign values if the object is not null
		if (objectItem != null)
		{
			aq.id(holder.txtText).text(objectItem.getText());
		}

		return convertView;
	}

	private class ViewHolder
	{
		public TextView txtText;

		public ViewHolder()
		{
		}
	}
}
