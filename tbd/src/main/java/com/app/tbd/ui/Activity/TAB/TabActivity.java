package com.app.tbd.ui.Activity.TAB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationActivity;
import com.app.tbd.ui.Activity.SlidePage.SlidingTabLayout;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.SC_Activity;
import com.app.tbd.ui.Model.JSON.PromoTransaction;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Module.HomeModule;
import com.app.tbd.ui.Module.PromotionModule;
import com.app.tbd.ui.Module.SplashScreenModule;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.ui.Presenter.TabPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.app.tbd.base.BaseFragment.comic_pauseBackgroundMusic;
import static com.app.tbd.base.BaseFragment.dismissLoading;

/**
 * Created by Dell on 9/23/2016.
 */

public class TabActivity extends MainFragmentActivity implements TabPresenter.PromotionView {

    @Inject
    TabPresenter presenter;

    TabPresenter.PromotionView act;

    // Declaring Your View and Variables
    Toolbar toolbar;
    static ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    //CharSequence Titles[] = {"Tab1", "Tab2", "Tab3", "Tab4"};
    int[] myImageList = new int[]{R.mipmap.icon_home_ori, R.mipmap.icon_profile, R.mipmap.icon_search_flight, R.mipmap.icon_puzzle,};
    int Numboftabs = 4;
    String newFlight;
    SharedPrefManager pref;
    Boolean reload = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment);

        getContext().setTitle(getResources().getString(R.string.TBD_home));
        MainApplication.get(this).createScopedGraph(new PromotionModule(act)).inject(this);

        try {
            Bundle bundle = getIntent().getExtras();
            newFlight = bundle.getString("SEARCH");
            if (newFlight != null) {
                pager.setCurrentItem(2);
                Log.e("Move Page", "Y");
            } else {
                Log.e("Search bundle", "null");
            }
        } catch (Exception e) {

        }


        // Creating The Toolbar and setting it as the Toolbar for the activity
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        pref = new SharedPrefManager(this);
        HashMap<String, String> init = pref.getLoginStatus();
        String loginStatus = init.get(SharedPrefManager.ISLOGIN);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), myImageList, Numboftabs, loginStatus, getContext(), this);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabView(R.layout.custom_tab, 0);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.white);
                //getResources().getColor(R.color.grey);
            }
        });

        hideLeftPart();

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) {
                    getContext().setTitle(getResources().getString(R.string.TBD_home));
                    AnalyticsApplication.sendScreenView("Homepage loaded");
                    comic_pauseBackgroundMusic();

                    //get pref here
                    //initiateLoading(getActivity());
                    HashMap<String, String> initAppData = pref.getSavedCountryCode();
                    String countryCode = initAppData.get(SharedPrefManager.SAVED_COUNTRY_CODE);

                    HashMap<String, String> lang = pref.getSavedLanguageSCode();
                    String languageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

                    HashMap<String, String> initReloadPromo = pref.getReloadPromo();
                    String reloadPromo = initReloadPromo.get(SharedPrefManager.RELOAD_PROMO);
                    if(reloadPromo == null){
                        reloadPromo = "Y";
                    }

                    if (reloadPromo.equals("Y")) {
                        reloadPromo = "N";

                        //save back to pref
                        pref.setReloadPromo(reloadPromo);

                        PromotionRequest promotionRequest = new PromotionRequest();
                        promotionRequest.setCountryCode(countryCode);
                        promotionRequest.setLanguageCode(languageCode);
                        presenter.onPromotionRequest(promotionRequest);

                    }

                    //pager.getAdapter().notifyDataSetChanged();
                    ((HomeFragment) adapter.getItem(position)).refresh(TabActivity.this);
                    //adapter.notifyDataSetChanged();
                    //HomeFragment.reloadPromotion(TabActivity.this);
                    HomeFragment.overlayFunction(TabActivity.this);

                } else if (position == 1) {
                    getContext().setTitle(getResources().getString(R.string.TBD_user_profile));
                    AnalyticsApplication.sendScreenView("User profile tab loaded");
                    comic_pauseBackgroundMusic();
                    ProfileFragment.triggerInbox(getContext());
                } else if (position == 2) {
                    getContext().setTitle(getResources().getString(R.string.TBD_flight_search));
                    AnalyticsApplication.sendScreenView("Flight search tab loaded");
                    comic_pauseBackgroundMusic();
                } else if (position == 3) {
                    getContext().setTitle(getResources().getString(R.string.TBD_big_fun));
                    AnalyticsApplication.sendScreenView("Big fun trivia tab loaded");
                    startMusic(TabActivity.this);
                }

                hideLeftPart();
            }
        });

        /*getNotification(TabActivity.this);*/
    }

    @Override
    public void onPromotionReceive(PromotionReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), this);
        if (status) {

            //savePromotionInfo
            Gson gsonUserInfo = new Gson();
            String promotionObj = gsonUserInfo.toJson(obj);
            RealmObjectController.savePromotion(this, promotionObj);

            //reload adapter with new data. if available
            //HomeFragment.reloadPromotion();
        }
    }

    public void startMusic(Activity act) {
        HashMap<String, String> initPassword = pref.getMusic();
        String music = initPassword.get(SharedPrefManager.MUSIC);

        if (music != null) {
            if (music == "Y") {
                pref.setMusic(music);
                BaseFragment.comic_playBackgroundMusic(act);
            }
        } else {
            music = "Y";
            pref.setMusic(music);
            BaseFragment.comic_playBackgroundMusic(act);
        }
    }

    public static void setPager(int position) {
        pager.setCurrentItem(position);
    }

    public static ViewPager getPagerInstance() {
        return pager;
    }

    public void exitApp() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.home_exit))
                .setContentText(getString(R.string.home_confirm_exit))
                .showCancelButton(true)
                .setCancelText(getString(R.string.home_cancel))
                .setConfirmText(getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        finishAffinity();
                        finish();
                        System.exit(0);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    /*public void getNotification(Activity activity){
        HashMap<String, String> promo = pref.getNotificationStatus();
        String notification = promo.get(SharedPrefManager.ISREAD);

        Log.e("PROMO KEY", notification); //promos/type:return/from:cgk/to:sin

        String type = notification.substring(notification.indexOf("type:") + 3 , notification.length());
        String from = notification.substring(notification.indexOf("from") + 3 , notification.length());
        String to = notification.substring(notification.indexOf("to") + 3 , notification.length());

        Log.e(type, from+""+to);

        *//*if (null != notification && notification.length() > 0 )
        {
            int endIndex = notification.lastIndexOf("/");
            if (endIndex != -1)
            {
                String newstr = notification.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
        }*//*

        *//*if (notification.equalsIgnoreCase("WITH_PROMO")) {
            TabActivity.setPager(2);

            //with information
            *//**//*PromoTransaction promoTransaction = new PromoTransaction();
            promoTransaction.setArrivalCode("");
            promoTransaction.setDepartureCode("");
            promoTransaction.setDepartText("");
            promoTransaction.setArrivalText("");
            promoTransaction.setFlightType("");
            promoTransaction.setFlightCode("FG");*//**//*

            //check currency
            *//**//*String currencyCode = getCurrencyCode(act, selectedDepartureCode);
            promoTransaction.setDepartureCurrencyCode(currencyCode);

            SearchFlightFragment.triggerPromoInfo(act, promoTransaction);*//**//*

        }else if (notification.equalsIgnoreCase("NO_PROMO")){
            Intent inbox = new Intent(activity, PushNotificationActivity.class);
            activity.getApplicationContext().startActivity(inbox);
        }*//*
    }*/

}
