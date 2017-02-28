package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;

import java.util.ArrayList;
import java.util.Vector;

public class BaggageInnerListAdapter extends PagerAdapter {

    ArrayList<String> seatListTab;
    SearchFlightRequest flightRequest;

    private Context mContext;
    private Vector<View> pages;

    public BaggageInnerListAdapter(Context fm, Vector<View> pages) {
        this.mContext = fm;
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page = pages.get(position);
        container.addView(page);
        return page;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object objects) {
        container.removeView((View) objects);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //String tabTitle = fareSize.get(position);
        return seatListTab.get(position);
    }

}
