package com.app.tbd.ui.Activity.BookingFlight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Activity.SplashScreen.TokenActivity;
import com.app.tbd.ui.Model.JSON.GCMClass;

import butterknife.ButterKnife;

public class PushNotificationFragment extends BaseFragment {

    public static PushNotificationFragment newInstance(Bundle bundle) {

        PushNotificationFragment fragment = new PushNotificationFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);

        MainFragmentActivity.setAppStatus("ready_for_notification");

        if (MainController.getHomeStatus()) {
            getActivity().finish();
            Log.e("FINISH","Y");
        } else {
            Log.e("FINISH","N");
            Intent home = new Intent(getActivity(), SplashScreenActivity.class);
            //home.setAction("android.intent.action.MAIN");
            //home.addCategory("android.intent.category.LAUNCHER");
            //home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(home);
            getActivity().finish();

        }

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
