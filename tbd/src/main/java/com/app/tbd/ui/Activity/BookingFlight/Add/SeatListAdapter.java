package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragmentPagerAdapter;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class SeatListAdapter extends PagerAdapter {

    ArrayList<String> seatListTab;
    SearchFlightRequest flightRequest;

    private Context mContext;
    private Vector<View> pages;

    public SeatListAdapter(Context fm, Vector<View> pages) {
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


    public void addAll(ArrayList<String> seatListTab) {
        this.seatListTab = seatListTab;
        this.flightRequest = flightRequest;
    }

    @Override
    public int getCount() {
        return seatListTab.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        //String tabTitle = fareSize.get(position);
        return seatListTab.get(position);
    }

}
