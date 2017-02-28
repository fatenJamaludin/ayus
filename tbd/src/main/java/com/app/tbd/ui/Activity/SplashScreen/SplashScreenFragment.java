package com.app.tbd.ui.Activity.SplashScreen;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.MyLocation;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.SC_Activity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AppVersionReceive;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.AppVersionRequest;
import com.app.tbd.ui.Model.Request.OnBoardingRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.App;
import com.app.tbd.utils.Utils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.app.tbd.application.MainApplication;
import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Module.SplashScreenModule;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.WIFI_SERVICE;

public class SplashScreenFragment extends BaseFragment implements HomePresenter.SplashScreen {

    @Inject
    HomePresenter presenter;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private InitialLoadRequest info;
    private ProgressDialog progress;
    private Dialog dialog;
    private Locale myLocale;

    TelephonyManager tm;
    Geocoder geoCoder;
    String deviceToken = "";
    Activity act;

    public static SplashScreenFragment newInstance() {

        SplashScreenFragment fragment = new SplashScreenFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new SplashScreenModule(this)).inject(this);
        //RealmObjectController.clearCachedResult(getActivity());

        tm = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        geoCoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(act);
        MainController.setHomeStatus();

        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (Exception e) {
            Log.e("FailedRemove Instance", "Y");
        }

        deviceToken = FirebaseInstanceId.getInstance().getToken();

        //check code version - auto update
        checkVersion();

        return view;

    }

    /*public void campaignTracker() {
        // Get tracker.
        Tracker t = ((AnalyticsSampleApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("Splash Screen");

        // In this example, campaign information is set using
        // a url string with Google Analytics campaign parameters.
        // Note: This is for illustrative purposes. In most cases campaign
        //       information would come from an incoming Intent.
        String campaignData = "http://examplepetstore.com/index.html?" + "utm_source=email&utm_medium=email_marketing&utm_campaign=summer" + "&utm_content=email_variation_1";

        // Campaign data sent with this hit.
        t.send(new HitBuilders.ScreenViewBuilder().setCampaignParamsFromUrl(campaignData).build());
    }*/

    @Override
    public void onSuccessRequestState(StateReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), act);
        if (status) {
            //save to pref

            //pref.setFirstTimeUser("N");

            Gson gson = new Gson();
            String state = gson.toJson(obj.getStateList());
            pref.setState(state);

            HashMap<String, String> init = pref.getLoginStatus();
            String loginStatus = init.get(SharedPrefManager.ISLOGIN);
            Log.e("LoginStatus", "a" + loginStatus);
            Intent language = null;
            if (loginStatus == null) {
                language = new Intent(getActivity(), AfterBoardingActivity.class);
            } else {
                if (loginStatus.equals("N")) {
                    language = new Intent(getActivity(), AfterBoardingActivity.class);
                } else {
                    language = new Intent(getActivity(), TabActivity.class);
                }
                Log.e("TabActivity", "Y");
            }
            getActivity().startActivity(language);
            getActivity().finish();

        } else {
            dismissLoading();
        }
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        //saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        //updateTexts();
    }

    public void checkVersion() {

        AppVersionRequest appVersion = new AppVersionRequest();
        appVersion.setDevice("Android");
        appVersion.setVersion(App.APP_VERSION);

        presenter.checkVersion(appVersion);

    }

    @Override
    public void onOverlayReceive(OverlayReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            String overInfo = gsonUserInfo.toJson(obj);
            RealmObjectController.saveOverlayInfo(getActivity(), overInfo);

            if (obj.getOverlay().getOverlay_Status().equalsIgnoreCase("Y")){
                saveOverlayInbox(getActivity(),obj.getOverlay().getMessage(),obj.getOverlay().getTitle(),getCurrentTimeStamp(),getCurrentTimeStamp()+""+Math.random());
            }else{
                Log.e("Overlay", "null");
            }

            getPromotionInfo();

        } else {
            dismissLoading();
        }

    }

    public void getPromotionInfo() {

        //initiateLoading(getActivity());
        HashMap<String, String> initAppData = pref.getSavedCountryCode();
        String countryCode = initAppData.get(SharedPrefManager.SAVED_COUNTRY_CODE);

        HashMap<String, String> lang = pref.getSavedLanguageSCode();
        String languageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

        HashMap<String, String> hardCodeLanguage = pref.getHardCodeLanguage();
        String languageCN = hardCodeLanguage.get(SharedPrefManager.HARD_CODE_LANGUAGE);

        String cut1 = languageCN.substring(0, 2).toLowerCase();
        String cut2 = languageCN.substring(2);

        Log.e("cut1", cut1);
        Log.e("cut2", cut2);

        Log.e("Subscribe 1 Splash", countryCode);
        Log.e("Subscribe 2 Splash", countryCode + "_" + cut1 + cut2);

        //subscribe to user here
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode + "_" + cut1 + cut2);
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode);


        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setCountryCode(countryCode);
        promotionRequest.setLanguageCode(languageCode);
        presenter.onPromotionRequest(promotionRequest);
    }

    public void sendDeviceInformationToServer(InitialLoadRequest info) {

        //initiateDefaultLoading(progress, getActivity());
        if (MainController.connectionAvailable(getActivity())) {
            presenter.initialLoad(info);

        } else {
            //connectionRetry("No Internet Connection");
        }

    }

    public void getCountryDetails(Double latitude, Double longitude) {

        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                String countryCode = addresses.get(0).getCountryCode();
                String countryName = addresses.get(0).getCountryName();

                pref.setCurrentCountry(countryName);
                pref.setCurrentCountryCode(countryCode);
                for (int g = 0; g < addresses.size(); g++) {
                    Log.e("City", addresses.get(g).getAddressLine(1));
                    Log.e("City", addresses.get(g).getAddressLine(2));
                    Log.e("City", addresses.get(g).getAddressLine(3));
                }

            } else {
                Log.e("Error", "No address");
            }
        } catch (IOException e1) {
            Log.e("Error", "Geocode Problem");
        }
    }

    public void getLatLong() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                pref.setLongitude(Double.toHexString(latitude));
                pref.setLatitude(Double.toHexString(longitude));

                Log.e(Double.toString(latitude), Double.toString(longitude));
                getCountryDetails(latitude, longitude);

            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);

    }

    public void getIP() {

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            checkWifi();
        } else {
            checkMobileData();
        }
    }

    public void checkWifi() {
        WifiManager wm = (WifiManager) getActivity().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        if (ip != null) {
            pref.setIP(ip);
            Log.e("IP stored", "from Wifi");
            Log.e("IP Address Wifi", ip);
        } else {
            Log.e("Note", "wifi not connected");
        }

    }

    public void checkMobileData() {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);

            if (mobileDataEnabled == true) {
                pref.setIP(ipFromMobile());
                Log.e("IP stored", "from Mobile Data");
                Log.e("IP Address Phone data", ipFromMobile());
            } else {
                Log.e("Status", "Not connected to any Internet");
            }
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
    }

    public static String ipFromMobile() {
        try {
            for (final Enumeration<NetworkInterface> enumerationNetworkInterface = NetworkInterface.getNetworkInterfaces(); enumerationNetworkInterface.hasMoreElements(); ) {
                final NetworkInterface networkInterface = enumerationNetworkInterface.nextElement();
                for (Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses(); enumerationInetAddress.hasMoreElements(); ) {
                    final InetAddress inetAddress = enumerationInetAddress.nextElement();
                    final String ipAddress = inetAddress.getHostAddress();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return ipAddress;
                    }
                }
            }
            return null;
        } catch (final Exception e) {

            return null;
        }
    }

    public void connectionRetry(String msg) {
/*
        pDialog.setTitleText("Connection Error");
        pDialog.setCancelable(false);
        pDialog.setContentText(msg);
        pDialog.setConfirmText("Retry");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sendDeviceInformationToServer(info);
            }
        })
                .show();
*/
    }

    public void goHomepage() {
        //go to on boarding first

        //first time user
        HashMap<String, String> initAppData = pref.getFirstTimeUser();
        String firstTime = initAppData.get(SharedPrefManager.FIRST_TIME_USER);

        HashMap<String, String> init = pref.getLoginStatus();
        String loginStatus = init.get(SharedPrefManager.ISLOGIN);

        if (firstTime != null) {
            Intent language;
            if (loginStatus == null) {
                language = new Intent(getActivity(), AfterBoardingActivity.class);
            } else if (loginStatus.equals("N")) {
                language = new Intent(getActivity(), AfterBoardingActivity.class);
            } else {
                language = new Intent(getActivity(), TabActivity.class);
            }
            startActivity(language);
            getActivity().finish();
        } else {
            Intent language = new Intent(getActivity(), SC_Activity.class);
            startActivity(language);
            getActivity().finish();
        }
    }

    public void forceUpdate() {
        Intent home = new Intent(getActivity(), ForceUpdateActivity.class);
        getActivity().startActivity(home);
        getActivity().finish();
    }

    @Override
    public void onAppVersionReceive(AppVersionReceive obj) {

        String updateTitle = getString(R.string.updateTitle);
        String updateBody = getString(R.string.updateBody);

        HashMap<String, String> lang = pref.getSavedLanguageSCode();
        String languageLanguageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

        if (languageLanguageCode != null) {
            if (languageLanguageCode.equalsIgnoreCase("tt")) {
                changeLang("th");
            } else {
                changeLang(languageLanguageCode);
            }
        } else {
            changeLang("en");
        }

        //if Y - force Update
        if (obj.getUpdate() != null) {
            if (obj.getUpdate().equals("Y")) {

                dismissLoading();
                alertForceUpdate(getActivity(), updateTitle, updateBody);

            } else {

                //first time user
                HashMap<String, String> initAppData = pref.getFirstTimeUser();
                String firstTime = initAppData.get(SharedPrefManager.FIRST_TIME_USER);

                //not first time user
                if (firstTime != null) {
                    //LOAD GET ALL DATA

                    //get overlay
                    /*RealmInboxNotification.clearNotificationInbox(getActivity());*/

                    getOverlayImage();
                    //getPromotionInfo();

                } else {
                    dismissLoading();
                    goHomepage();
                }

            }
        }
        //else No Update

        //}
    }

    public void getOverlayImage() {

        //initiateLoading(getActivity());
        HashMap<String, String> initAppData = pref.getSavedCountryCode();
        String countryCode = initAppData.get(SharedPrefManager.SAVED_COUNTRY_CODE);

        HashMap<String, String> lang = pref.getSavedLanguageSCode();
        String languageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

        //initiateLoading(getActivity());
        OverlayRequest overlayRequest = new OverlayRequest();
        overlayRequest.setCountryCode(countryCode);
        overlayRequest.setLanguageCode(languageCode);
        presenter.onOverlayImage(overlayRequest);
        Log.e("Get promo send", countryCode + "->" + languageCode);

    }

    @Override
    public void loadingSuccess(InitialLoadReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getObj().getStatus(), obj.getObj().getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();

            String title = gson.toJson(obj.getObj().getData_title());
            pref.setUserTitle(title);
            Log.e("TITLE", title);

            String country = gson.toJson(obj.getObj().getData_country());
            pref.setCountry(country);

            String bookingCountry = gson.toJson(obj.getObj().getBookingCountryStateList());
            pref.setBookingCountry(bookingCountry);

            String route = gson.toJson(obj.getObj().getRouteList());
            pref.setRoute(route);

            HashMap<String, String> init = pref.getChinese();
            String chinese = init.get(SharedPrefManager.CHINESE);

            HashMap<String, String> init2 = pref.getThailand();
            String thailand = init2.get(SharedPrefManager.THAILAND);

            //modify(Sorting)
            /*modifyRoute();*/

            /*if (chinese != null && thailand != null) {
                if (chinese.equals("N") || thailand.equals("N")) {
                    modifyRoute();
                    Log.e("SORTING", "YES");
                } else {
                    Log.e("SORTING", "NO");

                }
            } else {
                modifyRoute();
                Log.e("CHINESE NULL", "THAILAND NULL");
            }*/

            modifyRoute();

            //first time user
            HashMap<String, String> initAppData = pref.getSavedCountryCode();
            String countryCode = initAppData.get(SharedPrefManager.SAVED_COUNTRY_CODE);

            //first time user
            HashMap<String, String> hardCodeLanguage = pref.getHardCodeLanguage();
            String languageCN = hardCodeLanguage.get(SharedPrefManager.HARD_CODE_LANGUAGE);
            Log.e("HARD_CODE_1", languageCN);

            //load state - need to move later on
            StateRequest stateRequest = new StateRequest();
            stateRequest.setLanguageCode(languageCN);
            stateRequest.setCountryCode(countryCode);
            stateRequest.setPresenterName("LanguagePresenter");

            presenter.onStateRequest(stateRequest);

        }
    }

    public void modifyRoute() {

        JSONArray jsonFlight = getFlight(getActivity());

        //sort here
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonFlight.length(); i++) {
            try {
                jsonList.add(jsonFlight.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(jsonList, new Comparator<JSONObject>() {

            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get("DepartureStationName");
                    valB = (String) b.get("DepartureStationName");
                } catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonFlight.length(); i++) {
            sortedJsonArray.put(jsonList.get(i));
        }

        pref.setRoute(sortedJsonArray.toString());

    }

    public void loadInitialData(String languageCode) {

        LoginReceive loginReceive;

        initiateLoading(getActivity());

        String customerNumber = "";
        String userName = "";

        HashMap<String, String> initAppData = pref.getLoginStatus();
        String loginStatus = initAppData.get(SharedPrefManager.ISLOGIN);

        if (loginStatus != null) {
            if (loginStatus.equals("Y")) {
                //call realm - user info
                Realm realm = RealmObjectController.getRealmInstance(getActivity());
                final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

                if (result2.size() > 0) {
                    loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
                    customerNumber = loginReceive.getCustomerNumber();
                    userName = loginReceive.getUserName();
                } else {
                    customerNumber = "";
                    userName = "";
                }
            } else {
                customerNumber = "";
                userName = "";
            }
        }

        Log.e("Username", "a" + userName);

        InitialLoadRequest infoData = new InitialLoadRequest();
        infoData.setLanguageCode(languageCode);
        infoData.setUsername(userName);
        infoData.setFcmKey(deviceToken);
        infoData.setDeviceType("Android");
        infoData.setCustomerNumber(customerNumber);
        presenter.initialLoad(infoData);

    }

    public void alertForceUpdate(Activity act, String message, String title) {

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.force_update_box, null);
        Button cont = (Button) myView.findViewById(R.id.push_close_btn);
        Button update = (Button) myView.findViewById(R.id.push_update_btn);

        TextView pushTitle = (TextView) myView.findViewById(R.id.push_content);
        TextView pushMessage = (TextView) myView.findViewById(R.id.push_title);

        pushTitle.setText(title);
        pushMessage.setText(message);

        dialog = new Dialog(act, R.style.DialogThemePush);
        dialog.setContentView(myView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                System.exit(0);
            }

        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.airasiabig.redemption" + "" + ""));
                getActivity().startActivity(intent);
                System.exit(0);
            }

        });

    }

    @Override
    public void onPromotionReceive(PromotionReceive obj) {
        //dismissDefaultLoading(progress, getActivity());
        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            //savePromotionInfo
            //add URL to REALM User info
            Gson gsonUserInfo = new Gson();
            String promotionObj = gsonUserInfo.toJson(obj);
            RealmObjectController.savePromotion(act, promotionObj);

            //save promo last update date for auto reload
            pref.setPromoLastUpdate(obj.getLastUpdated());

            HashMap<String, String> initAppData = pref.getFirstTimeUser();
            String firstTime = initAppData.get(SharedPrefManager.FIRST_TIME_USER);

            if (firstTime != null && firstTime.equals("N")) {

                //if not firsttime .. languagecode & country code already available
                HashMap<String, String> langCode = pref.getHardCodeLanguage();
                String languageCode = langCode.get(SharedPrefManager.HARD_CODE_LANGUAGE);

                HashMap<String, String> init = pref.getLoginStatus();
                String loginStatus = init.get(SharedPrefManager.ISLOGIN);

                if (loginStatus == null) {
                    //not login - no need to call getAllData
                    Intent intent = new Intent(getActivity(), AfterBoardingActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();

                } else {
                    if (loginStatus.equals("N")) {
                        //not login - no need to call getAllData
                        Intent intent = new Intent(getActivity(), AfterBoardingActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    } else {
                        //login - call getAllData
                        loadInitialData(languageCode);
                    }
                }
            } else {
                Log.e("SC_Activity", "Y");
                Intent intent = new Intent(getActivity(), SC_Activity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

        }
    }

    @Override
    public void onConnectionFailed() {
        // connectionRetry("Unable to connect to server");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        /*RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            AppVersionReceive obj = gson.fromJson(result.get(0).getCachedResult(), AppVersionReceive.class);
            onAppVersionReceive(obj);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
