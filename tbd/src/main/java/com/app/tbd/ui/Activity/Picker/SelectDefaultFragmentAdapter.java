package com.app.tbd.ui.Activity.Picker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;

public class SelectDefaultFragmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<DropDownItem> language;
    ArrayList<DropDownItem> originalCountries;
    TextView tvCountry;
    DropDownItem currentItem;

    ArrayList<DropDownItem> arraylist;
    String[] filteredChar;
    SelectDefaultFragment frag;

    public SelectDefaultFragmentAdapter(Context context, SelectDefaultFragment frag, ArrayList<DropDownItem> language) {
        this.context = context;
        this.language = language;
        this.frag = frag;
    }

    @Override
    public int getCount() {
        return language.size();
    }

    @Override
    public Object getItem(int position) {
        return language.get(position);
    }

    @Override
    public long getItemId(int position) {
        return language.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentItem = (DropDownItem) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_country, parent, false);
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        tvCountry.setText(currentItem.getText());

        return view;
    }

}
