package com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

public class OnBoardingListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DropDownItem> countries;
    ArrayList<DropDownItem> originalCountries;
    TextView tvCountry;
    TextView txtCountry;
    LinearLayout highlighted;
    DropDownItem currentItem, code;
    SharedPrefManager pref;

    ArrayList<DropDownItem> arraylist;
    String[] filteredChar;

    public OnBoardingListAdapter(Context context, ArrayList<DropDownItem> countries) {
        this.context = context;
        this.countries = countries;
        this.arraylist = new ArrayList<DropDownItem>();
        this.arraylist.addAll(countries);
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
        pref = new SharedPrefManager(context);
        currentItem = (DropDownItem) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.country_list_boarding, parent, false);
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        highlighted = (LinearLayout) view.findViewById(R.id.highlighted);

        tvCountry.setText(currentItem.getText());

        HashMap<String, String> initAppData = pref.getCurrentCountry();
        String currentCountry = initAppData.get(SharedPrefManager.CURRENT_COUNTRY);

        HashMap<String, String> initCode = pref.getCurrentCountryCode();
        String currentCountryCode = initCode.get(SharedPrefManager.CURRENT_COUNTRY_CODE);

        if (currentItem.getText().equals(currentCountry)){
            highlighted.setBackgroundResource(R.color.bright_red);
        }else if(currentItem.getCode().equals(currentCountryCode)){
            highlighted.setBackgroundResource(R.color.bright_red);
        }

        return view;
    }
}
