package com.app.tbd.ui.Activity.BookingFlight;

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
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragmentPagerAdapter;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class FlightListPagerAdapter extends PagerAdapter {

    List<AnotherList> listProductImages;
    List<String> fareSize;
    SearchFlightRequest flightRequest;

    //public FlightListPagerAdapter(FragmentManager fm) {
    //    super(fm);
    //}

    private Context mContext;
    private Vector<View> pages;

    public FlightListPagerAdapter(Context fm, Vector<View> pages) {
        ///super(fm);
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


    public void addAll(List<String> test, SearchFlightRequest flightRequest) {
        //this.listProductImages = listProductImages;
        this.fareSize = test;
        this.flightRequest = flightRequest;
        Log.e("Fare Size", Integer.toString(fareSize.size()));
        //notifyDataSetChanged();
    }

    /*@Override
    public Fragment getItem(int position) {


        //ListView newListView = new ListView();
        String type = fareSize.get(position);
        return FlightPriceFragment.newInstance(fareSize, listProductImages, type , flightRequest);

    }*/

    @Override
    public int getCount() {
        return fareSize.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        String type = fareSize.get(position);
        String tabTitle = "-";

        String fixed = AnalyticsApplication.getContext().getString(R.string.fixed_c);
        String regular = AnalyticsApplication.getContext().getString(R.string.regular_c);
        String promo = AnalyticsApplication.getContext().getString(R.string.promo_c);
        String premium_flex = AnalyticsApplication.getContext().getString(R.string.premium_flex_c);
        String premium_flatbed = AnalyticsApplication.getContext().getString(R.string.premium_flatbed_c);

        if (type.equals("BL")) {
            tabTitle = fixed;
        } else if (type.equals("EC")) {
            tabTitle = regular;
        } else if (type.equals("EP")) {
            tabTitle = promo;
        } else if (type.equals("HF")) {
            tabTitle = premium_flex;
        } else if (type.equals("PM") || type.equals("PP")) {
            tabTitle = premium_flatbed;
        }else{
            tabTitle = "-";
        }
        return tabTitle;
    }

}
