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

public class SelectionListAdapterV2 extends BaseAdapter {
    Context context;
    ArrayList<DropDownItem> countries;
    ArrayList<DropDownItem> originalCountries;
    TextView tvCountry;
    TextView txtCountry;
    DropDownItem currentItem;
    String whichList;

    ArrayList<DropDownItem> arraylist;
    String[] filteredChar;
    SelectionListFragmentV2 frag;

    public SelectionListAdapterV2(Context context, SelectionListFragmentV2 frag, ArrayList<DropDownItem> countries, ArrayList<DropDownItem> oriCountries, String whichList2) {
        this.context = context;
        this.countries = countries;
        this.frag = frag;
        this.arraylist = new ArrayList<DropDownItem>();
        this.arraylist.addAll(countries);
        this.whichList = whichList2;
        this.originalCountries = oriCountries;

    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return countries.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentItem = (DropDownItem) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_country, parent, false);
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        txtCountry = (TextView) view.findViewById(R.id.txtCountry);

        tvCountry.setText(currentItem.getText());

        if (whichList.equals("FLIGHT")) {
            txtCountry.setVisibility(View.VISIBLE);
            String codeSplit = currentItem.getCode();
            String[] str1 = codeSplit.split("/");
            String p2 = str1[1];
            txtCountry.setText(p2);
        } else {
            txtCountry.setVisibility(View.GONE);
        }

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        countries.clear();
        Log.e(charText, "originalCountries" + Integer.toString(originalCountries.size()));

        if (charText.length() == 0) {
            countries.addAll(originalCountries);
            frag.initControls();
            Log.e("?", charText);
            // frag.recreateAdapter(countries);
        } else {

            for (DropDownItem pic : originalCountries) {
                if (pic.getText().toLowerCase().contains(charText)) {
                    countries.add(pic);
                    Log.e(charText, pic.toString());
                }
            }
            frag.recreateAdapter(countries);
        }

    }
}
