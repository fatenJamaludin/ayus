package com.app.tbd.utils;

import android.app.Activity;

import com.app.tbd.base.BaseFragment;
import com.google.android.gcm.GCMRegistrar;

/**
 * Created by Dell on 4/1/2016.
 */
public class Push extends BaseFragment {



    public static String getToken(Activity act){

        String pushToken;

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(act);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        //GCMRegistrar.checkManifest(this);
        GCMRegistrar.checkManifest(act);

        pushToken = GCMRegistrar.getRegistrationId(act);

        return pushToken;
    }
}


