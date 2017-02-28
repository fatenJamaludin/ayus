package com.app.tbd.ui.Activity.Picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.utils.DropDownItem;

import java.util.ArrayList;

public class SelectLanguageCountryAdapter extends BaseAdapter {
    Context context;
    ArrayList<DropDownItem> country;
    TextView tvCountry;
    DropDownItem currentItem;

    ArrayList<DropDownItem> arraylist;
    String[] filteredChar;
    SelectLanguageCountryFragment frag;

    public SelectLanguageCountryAdapter(Context context, SelectLanguageCountryFragment frag, ArrayList<DropDownItem> country) {
        this.context = context;
        this.country = country;
        this.frag = frag;
    }

    @Override
    public int getCount() {
        return country.size();
    }

    @Override
    public Object getItem(int position) {
        return country.get(position);
    }

    @Override
    public long getItemId(int position) {
        return country.get(position).getId();
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

    /*@Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }*/

    /*private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<DropDownItem> filterList = new ArrayList<DropDownItem>();
                for (int i = 0; i < stringCountryFilter.size(); i++) {
                    if (stringCountryFilter.get(i).getText().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        DropDownItem dropDownItem = stringCountryFilter.get(i);

                        filterList.add(dropDownItem);
                    }
                }
                filterResults.count = filterList.size();
                filterResults.values = filterList;
            } else {
                filterResults.count = stringCountryFilter.size();
                filterResults.values = stringCountryFilter;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countries = (ArrayList<DropDownItem>) results.values;
            notifyDataSetChanged();
        }
    }*/
}
