package com.app.tbd.ui.Activity.SplashScreen;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;

import butterknife.ButterKnife;

public class PopupNotificationFragment extends BaseFragment {

    public static PopupNotificationFragment newInstance(Bundle bundle) {

        PopupNotificationFragment fragment = new PopupNotificationFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new SplashScreenModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);

        //MainFragmentActivity.setAppStatus("ready_for_notification");

        if(MainController.getHomeStatus()) {
            getActivity().finish();
        }else{


           /* Intent mStartActivity = new Intent(getActivity(), TokenActivity.class);
            int mPendingIntentId = 123456;

            PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
            */
            //System.exit(0);
            /**/
                Intent home = new Intent(getActivity(), TokenActivity.class);
                home.setAction("android.intent.action.MAIN");
                home.addCategory("android.intent.category.LAUNCHER");
                home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(home);
                getActivity().finish();

        }
        /*View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);

        Bundle bundle = getArguments();

        String gcmKey = bundle.getString("GCM_KEY");
        if(gcmKey == null){
            gcmKey = "";
        }
        //String gcmKey = "";

        //get push notification token
        //String pushNotificationToken = Push.getToken(getActivity());

        //Log.e("Token",pushNotificationToken);

        HashMap<String, String> initUserEmail = pref.getUserEmail();
        String userEmail = initUserEmail.get(SharedPrefManager.USER_EMAIL);

        HashMap<String, String> initUserPassword = pref.getUserPassword();
        String userPassword = initUserPassword.get(SharedPrefManager.PASSWORD);
        if( userEmail == null && userPassword == null){
            userEmail = "";
            userPassword = "";
        }
        //retrieve data
        String deviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String version = android.os.Build.VERSION.RELEASE;
        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        HashMap<String, String> initLogin = pref.getDataVesion();
        String localDataVersion = initLogin.get(SharedPrefManager.DATA_VERSION);

        if(localDataVersion == null && MainController.connectionAvailable(getActivity())){
            localDataVersion = "0";
            proceed = true;
        }else if(localDataVersion == null && MainController.connectionAvailable(getActivity()))
        {
            connectionRetry("No Internet Connection");
            proceed = false;
        }

        info = new InitialLoadRequest();
        info.setSdkVersion(Integer.toString(sdkVersion));
        info.setVersion(version);
        info.setDeviceId(deviceId);
        info.setBrand(Build.BRAND);
        info.setModel(Build.MODEL);
        //info.setModel("test");
        info.setDataVersion("0");
        info.setSignature("");
        info.setUsername(userEmail);
        info.setPassword(userPassword);
        info.setGCMKey(gcmKey);

        if(proceed){
            if(localDataVersion != null && MainController.connectionAvailable(getActivity())){
                sendDeviceInformationToServer(info);
            }else if(localDataVersion != null && !MainController.connectionAvailable(getActivity())){
                goHomepage();
            }
            running = true;
        }


        //RealmObjectController.deleteRealmFile(getActivity());
        */
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
