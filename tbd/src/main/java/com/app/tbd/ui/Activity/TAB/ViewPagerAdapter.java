package com.app.tbd.ui.Activity.TAB;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import com.app.tbd.ui.Activity.BigFunTrivia.BigFunTriviaFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.utils.SharedPrefManager;

import java.util.HashMap;

/**
 * Created by hp1 on 21-01-2015.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String status;
    Context context;
    private SharedPrefManager pref;
    Activity act;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, int myImageList[], int mNumbOfTabsumb, String status, Context context, Activity act) {
        super(fm);

        this.Titles = myImageList;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.status = status;
        this.context = context;
        this.act = act;
        pref = new SharedPrefManager(context);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            HomeFragment tab1 = new HomeFragment();
            return tab1;

        } else if (position == 1) {
            ProfileFragment tab2 = new ProfileFragment();
            return tab2;

        } else if (position == 2) {
            SearchFlightFragment tab3 = new SearchFlightFragment();
            return tab3;

        } else {
            //Tab4 tab4 = new Tab4();
            BigFunTriviaFragment tab4 = new BigFunTriviaFragment();
            return tab4;

        }

    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    //@Override
    //public CharSequence getPageTitle(int position) {
    //    return Titles[position];
    //}


    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(context, Titles[position]);
        //image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        Log.e("image.()", Integer.toString(image.getIntrinsicWidth()));
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}