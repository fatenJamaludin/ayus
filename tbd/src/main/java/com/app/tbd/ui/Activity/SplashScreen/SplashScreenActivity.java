package com.app.tbd.ui.Activity.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.PushNotificationActivity;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.SC_Activity;
import com.app.tbd.ui.Model.JSON.GCMClass;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.bugsnag.android.Bugsnag;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class SplashScreenActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Boolean proceed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bugsnag.init(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        /*String projectToken = "7969a526dc4b31f72f05ca4a060eda1c";//Token AAB*/
        String projectToken = AnalyticsApplication.getMixPanelToken(); //Token dr AA
        /*String projectToken = "0d36f0c4de62c80aa1884e748595d962"; //Token Paten*/
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);


        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        //hideTabButton();
        //BaseFragment.removeLogoHeader(this);

        try {
            String action = getIntent().getExtras().getString("action");
            String message = getIntent().getExtras().getString("message");
            String title = getIntent().getExtras().getString("title");
            String key = getIntent().getExtras().getString("key");
            String messageID = getIntent().getExtras().getString("messageId");


            Log.e("getExtras.getString", message+title+key+messageID);
            if (action.equals("NOTIFICATION")) {

                Log.e("NOTIFICATION","Y");
                //save message to realm object
                //RealmObjectController.clearNotificationMessage(getContext());
                RealmObjectController.saveNotificationMessage(getContext(), message, title);
                saveNotificationInbox("-", message, title, getCurrentTimeStamp(),messageID);

                if (key.equals("n/a")){
                    RealmObjectController.clearKey(this);
                    Log.e("NOT SAVE","y");
                }else {
                    RealmObjectController.saveKey(this, key);
                    Log.e("NOT SAVE","N");
                }

                proceed = false;

                Intent intent = new Intent(this, PushNotificationActivity.class);
                startActivity(intent);
                finish();

            } else {
                Log.e("notForward", action);
            }

        } catch (Exception e) {
            Log.e("NOTIFICATION","N");
            Log.e("notForward", "ERROR");
        }

        if (proceed) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.splash_content, SplashScreenFragment.newInstance()).commit();
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void saveNotificationInbox(String username, String message, String title, String date, String messageId) {

        //save push notification message
        GCMClass gcmClass = new GCMClass();
        gcmClass.setTitle(title);
        gcmClass.setMessage(message);
        gcmClass.setDate(date);
        gcmClass.setMessageID(messageId);
        RealmInboxNotification.notificationAddList(this, gcmClass, "-");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
