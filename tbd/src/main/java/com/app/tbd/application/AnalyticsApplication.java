package com.app.tbd.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONObject;

public class AnalyticsApplication extends MultiDexApplication {

    private static Context context;

    /*private static String MixPanelToken = "7969a526dc4b31f72f05ca4a060eda1c"; //Token AAB*/
    private static String MixPanelToken = "12161458140eb25a9cecc6573ad97d1c"; //Token Paten

    @Override
    public void onCreate() {
        AnalyticsApplication.context = this;
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }

    public static String getMixPanelToken (){
        return MixPanelToken;
    }

    public static void sendScreenView(String screenName) {

        MixpanelAPI mixPanel = MixpanelAPI.getInstance(context, MixPanelToken);

        mixPanel.track(screenName);
    }

    public static void sendEvent(String category, JSONObject properties) {

        MixpanelAPI mixPanel = MixpanelAPI.getInstance(context, MixPanelToken);

        mixPanel.track(category, properties);
    }

}
