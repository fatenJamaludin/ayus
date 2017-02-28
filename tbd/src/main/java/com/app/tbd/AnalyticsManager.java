/*
package com.app.tbd;

import android.app.Application;
import android.content.Context;

*/
/*import com.google.android.gms.analytics.Tracker;*//*

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONObject;

public class AnalyticsManager extends Application {
    */
/*private static Tracker mTracker;
    private final static String TAG = "Event";*//*


    */
/*String MixPanelToken = "7969a526dc4b31f72f05ca4a060eda1c"; //Token AAB *//*

    String MixPanelToken = "12161458140eb25a9cecc6573ad97d1c"; //Token Paten

    public void sendScreenView(String screenName) {
        MixpanelAPI mixPanel = MixpanelAPI.getInstance(this, MixPanelToken);

        mixPanel.track(screenName);

        */
/*if(mTracker != null){
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.AppViewBuilder().build());
        }*//*

    }

    public void sendEvent(String category, JSONObject properties) {

        MixpanelAPI mixPanel = MixpanelAPI.getInstance(this, MixPanelToken);

        mixPanel.track(category, properties);
        */
/*if(mTracker != null){
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .build());
        }*//*

    }

    */
/**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     *//*

    */
/*synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }*//*


}


*/
/*package com.app.tbd.application;

*//*
*/
/**
 * Created by metechuser on 12/11/2015.
 *//*
*/
/*

        import android.app.Application;
        import android.support.multidex.MultiDexApplication;

        import com.google.android.gms.analytics.GoogleAnalytics;
        import com.google.android.gms.analytics.HitBuilders;
        import com.google.android.gms.analytics.Tracker;

*//*
*/
/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 *//*
*/
/*
public class AnalyticsApplication extends MultiDexApplication {
    private static Tracker mTracker;
    private final static String TAG = "Event";


    public static void sendScreenView(String screenName) {
        if(mTracker != null){
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.AppViewBuilder().build());
        }
    }

    public static void sendEvent(String category, String action) {

        if(mTracker != null){
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .build());
        }
    }

    *//*
*/
/**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     *//*
*/
/*
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            //mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}*/
