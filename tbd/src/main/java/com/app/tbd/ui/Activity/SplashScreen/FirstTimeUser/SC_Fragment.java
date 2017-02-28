package com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.AfterBoardingActivity;
import com.app.tbd.ui.Activity.SplashScreen.OnBoarding.OnBoardingActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.LanguageReceive;
import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.OnBoardingReceive;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LanguageCountryRequest;
import com.app.tbd.ui.Model.Request.LanguageRequest;
import com.app.tbd.ui.Model.Request.OnBoardingRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Module.LanguageModule;
import com.app.tbd.ui.Presenter.LanguagePresenter;
import com.app.tbd.ui.Realm.Cached.CachedLanguageCountry;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class SC_Fragment extends BaseFragment implements LanguagePresenter.LanguageView {

    @Inject
    LanguagePresenter presenter;

    @InjectView(R.id.lvCountries)
    ListView lvCountries;

    OnBoardingListAdapter adapter;

    private int fragmentContainerId;
    SharedPrefManager pref;
    private String languageCode, countryCode;
    private Locale myLocale;
    private ArrayList<DropDownItem> languageList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> country = new ArrayList<DropDownItem>();
    private String cn;
    private String selectedCountry;
    private AnimationAdapter mAnimAdapter;
    private String deviceToken = "";

    LanguageCountryReceive languageCountryReceive = new LanguageCountryReceive();

    public static SC_Fragment newInstance() {

        SC_Fragment fragment = new SC_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new LanguageModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sc_activity, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());
        /*AnalyticsApplication.sendScreenView("Select country loaded");*/

        deviceToken = FirebaseInstanceId.getInstance().getToken();

        HashMap<String, String> initAppData = pref.getFirstTimeUser();
        String firstTime = initAppData.get(SharedPrefManager.FIRST_TIME_USER);

        HashMap<String, String> init = pref.getLoginStatus();
        String loginStatus = init.get(SharedPrefManager.ISLOGIN);

        if (firstTime != null && firstTime.equals("N")) {

            Intent language;
            if (loginStatus == null) {
                language = new Intent(getActivity(), AfterBoardingActivity.class);
            } else if (loginStatus.equals("N")) {
                language = new Intent(getActivity(), AfterBoardingActivity.class);
            } else {
                language = new Intent(getActivity(), TabActivity.class);
            }
            getActivity().startActivity(language);
            getActivity().finish();
        } else {

            initiateLoading(getActivity());
            LanguageCountryRequest languageCountryRequest = new LanguageCountryRequest();
            presenter.onCountryRequest(languageCountryRequest);

            loadLocale();
        }

        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DropDownItem xx = (DropDownItem) parent.getAdapter().getItem(position);
                selectedCountry = xx.getText();
                countryCode = xx.getCode();

                if (languageCountryReceive.getCountryList().get(position).getLanguageList().size() > 0) {
                    retrieveLanguage(xx.getCode());
                } else {
                    Utils.toastNotification(getActivity(), "No language available");
                }

                Log.e("Country early set", selectedCountry);
                pref.setSavedCountry(selectedCountry);
                pref.setSavedCountryCode(countryCode);
                Log.e(selectedCountry, countryCode);
            }
        });

        return view;
    }


    @Override
    public void onOverlayReceive(OverlayReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            String overInfo = gsonUserInfo.toJson(obj);
            RealmObjectController.saveOverlayInfo(getActivity(), overInfo);

            //saveOverlayInbox(getActivity(), obj.getOverlay().getMessage(), obj.getOverlay().getTitle(), getCurrentTimeStamp());
            if (obj.getOverlay().getOverlay_Status().equalsIgnoreCase("Y")){
                saveOverlayInbox(getActivity(),obj.getOverlay().getMessage(),obj.getOverlay().getTitle(),getCurrentTimeStamp(),getCurrentTimeStamp());
            }else{
                Log.e("Overlay", "null");
            }

            getPromotionInfo();

        } else {
            dismissLoading();
        }
    }

    @Override
    public void onMessageReceive(MessageReceive obj) {

    }

    @Override
    public void onSuccessRequestState(StateReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            //save to pref

            pref.setFirstTimeUser("N");

            Gson gson = new Gson();
            String state = gson.toJson(obj.getStateList());
            pref.setState(state);

            //change .. get overlay image first.

            /*RealmInboxNotification.clearNotificationInbox(getActivity());*/
            getOverlayImage();
            //getPromotionInfo();

        } else {
            dismissLoading();
        }

    }

    public void getOverlayImage(){
        //initiateLoading(getActivity());
        OverlayRequest overlayRequest = new OverlayRequest();
        overlayRequest.setCountryCode(countryCode);
        overlayRequest.setLanguageCode(languageCode);
        presenter.onOverlayImage(overlayRequest);
        Log.e("Get promo send", countryCode + "->" + languageCode);
    }


    public void getPromotionInfo() {
        //initiateLoading(getActivity());
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setCountryCode(countryCode);
        promotionRequest.setLanguageCode(languageCode);
        presenter.onPromotionRequest(promotionRequest);
        Log.e("Get promo send", countryCode + "->" + languageCode);
    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, ArrayList<DropDownItem> language) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SL_Fragment countryListDialogFragment = SL_Fragment.newInstance(language);
                countryListDialogFragment.setTargetFragment(SC_Fragment.this, 0);
                countryListDialogFragment.setEnterTransition(R.anim.slide_from_right);
                countryListDialogFragment.show(fm, "countryListDialogFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPromotionReceive(PromotionReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            //savePromotionInfo
            //add URL to REALM User info
            Gson gsonUserInfo = new Gson();
            String promotionObj = gsonUserInfo.toJson(obj);
            RealmObjectController.savePromotion(getActivity(), promotionObj);

            pref.setPromoLastUpdate(obj.getLastUpdated());

            OnBoardingRequest onBoardingRequest = new OnBoardingRequest();
            onBoardingRequest.setCountryCode(countryCode);
            onBoardingRequest.setLanguageCode(languageCode);
            presenter.onBoardingRequest(onBoardingRequest);

        }
    }

    @Override
    public void loadingSuccess(InitialLoadReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getObj().getStatus(), obj.getObj().getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();

            String title = gson.toJson(obj.getObj().getData_title());
            pref.setUserTitle(title);

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

            //modify
            modifyRoute();
            /*Log.e("Status CN " + chinese, " Status TH " + thailand);
            if (chinese != null && thailand != null){
                if (chinese.equals("Y") || thailand.equals("Y")){
                    Log.e("SORTING", "NO");
                }else{
                    modifyRoute();
                    Log.e("SORTING", "YES");
                }
            }else{
                modifyRoute();
                Log.e("CHINESE NULL", "THAILAND NULL");
            }*/

            //load state - need to move later on
            //initiateLoading(getActivity());
            StateRequest stateRequest = new StateRequest();
            stateRequest.setLanguageCode(languageCode + "-" + cn);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (requestCode == 1) {
                DropDownItem selectedLanguage = data.getParcelableExtra("LANGUAGE_LIST");
                changeLanguage(selectedLanguage.getCode());
                loadInitialData(selectedLanguage.getCode());

                languageCode = selectedLanguage.getCode();
                pref.setSavedLanguageSCode(languageCode);
                pref.setSavedLanguage(selectedLanguage.getText());
                pref.setSavedLanguageCode(selectedLanguage.getCode() + cn);
                pref.setCn(cn);

                Gson gson = new Gson();
                String languageLang = gson.toJson(selectedLanguage);
                pref.setLanguageList(languageLang);
            }
        }
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        //updateTexts();
    }

    public void changeLanguage(String selectedLanguage) {
        String lang = "en";
        pref.setThailand("N");
        pref.setChinese("N");

        if (countryCode.equalsIgnoreCase("th") && selectedLanguage.equalsIgnoreCase("tt")) {
            pref.setThailand("Y");
        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            pref.setChinese("Y");
        }

        if (countryCode.equalsIgnoreCase("TH") && selectedLanguage.equalsIgnoreCase("tt")) {
            lang = "th";
            cn = "TT";

        } else if (selectedLanguage.equalsIgnoreCase("en")) {
            cn = "GB";
            lang = "en";

        } else if (selectedLanguage.equalsIgnoreCase("ms")) {
            lang = "ms";
            cn = "MY";

        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            lang = "zh";
            cn = "CN";

        } else if (selectedLanguage.equalsIgnoreCase("id")) {
            lang = "id";
            cn = "ID";

        } else {
            lang = "en";
            cn = "GB";
        }

        changeLang(lang);
        /*if(countryCode.equalsIgnoreCase("th") && selectedLanguage.equalsIgnoreCase("tt")){
            changeLang("th");
            pref.setThailand("Y");

        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            pref.setChinese("Y");

        }else{
            changeLang(lang);
            pref.setThailand("N");
            pref.setChinese("N");
        }*/
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null) {
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getActivity().getBaseContext().getResources().updateConfiguration(newConfig, getActivity().getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onSuccessRequestLanguageCountry(LanguageCountryReceive obj) {

        dismissLoading();

        country = new ArrayList<DropDownItem>();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            languageCountryReceive = obj;

		    /*Travel Doc*/
            for (int i = 0; i < obj.getCountryList().size(); i++) {

                DropDownItem itemDoc = new DropDownItem();
                itemDoc.setText(obj.getCountryList().get(i).getCountryName());
                itemDoc.setCode(obj.getCountryList().get(i).getCountryCode());
                country.add(itemDoc);
            }

            //SET COUNTRY LIST
            adapter = new OnBoardingListAdapter(getActivity(), country);
            mAnimAdapter = new SwingBottomInAnimationAdapter(new SwingBottomInAnimationAdapter(adapter));
            mAnimAdapter.setAbsListView(lvCountries);
            lvCountries.setAdapter(mAnimAdapter);

            /*if (lvCountries.equals("Hai")){
                tvCountry.setBackgroundResource(R.color.red);
            }*/
            /*RealmObjectController.clearCachedResult(getActivity());*/
            /*List<LanguageCountryReceive.CountryList> xx = obj.getCountryList();*/

            Gson gson = new Gson();
            String languageCountry = gson.toJson(obj);

            RealmObjectController.saveCountryLanguage(getActivity(), languageCountry);

        }

    }

    @Override
    public void onBoardingReceive(OnBoardingReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            Gson gson = new Gson();
            String boarding = gson.toJson(obj);
            pref.setOnBoardingImage(boarding);

            for (int c = 0; c < obj.getImageList().size(); c++) {
                download(obj.getImageList().get(c));
            }
            //.download();
            dismissLoading();
            home();
        } else {
            dismissLoading();
        }

    }

    public void download(String path) {
        Glide.with(this)
                .load(path)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {

                    }
                });
    }

    public void loadInitialData(String languageCode) {

        initiateLoading(getActivity());
        //retrieve back all data with selected language

        if (languageCode.equalsIgnoreCase("tt") && countryCode.equalsIgnoreCase("th")) {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode("en-TT");
            infoData.setUsername("");
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber("");

            pref.setHardCodeLanguageCode("en-TT");
            presenter.initialLoad(infoData);
        } else if (languageCode.equalsIgnoreCase("en") && countryCode.equalsIgnoreCase("th")) {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode("en-GB");
            infoData.setUsername("");
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber("");

            pref.setHardCodeLanguageCode("en-GB");
            presenter.initialLoad(infoData);
        } else {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode(languageCode + "-" + cn);
            infoData.setUsername("");
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber("");

            pref.setHardCodeLanguageCode(languageCode + "-" + cn);
            presenter.initialLoad(infoData);
        }

        pref.setLanguageCountry(languageCode + "-" + countryCode);
        pref.setLanguageCode(languageCode + "-" + countryCode);

        String fullLanguageCode = languageCode + "-" + cn;
        String lowerLanguageCode = languageCode.toLowerCase();

        Log.e("Subscribe 1 SC", countryCode);
        Log.e("Subscribe 2 SC", countryCode + "_" + lowerLanguageCode + "-" + cn);

        //subscribe to user here
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode + "_" + lowerLanguageCode + "-" + cn);
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode);

        Log.e("Save for Register", fullLanguageCode);

    }

    public void home() {
        Intent home = new Intent(getActivity(), OnBoardingActivity.class);
        getActivity().startActivity(home);
        getActivity().finish();
    }

    public void retrieveLanguage(String countryCode) {

        languageList = new ArrayList<DropDownItem>();

        // Gson gson = new Gson();
        // String gsonLanguageList = gson.toJson(languageCountryReceive.getLanguageList());
        // pref.setLanguageList(gsonLanguageList);

		/*Travel Doc*/
        for (int i = 0; i < languageCountryReceive.getCountryList().size(); i++) {
            if (countryCode.equals(languageCountryReceive.getCountryList().get(i).getCountryCode())) {
                for (int y = 0; y < languageCountryReceive.getCountryList().get(i).getLanguageList().size(); y++) {
                    DropDownItem itemDoc = new DropDownItem();
                    itemDoc.setText(languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageName());
                    itemDoc.setCode(languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageCode());
                    itemDoc.setTag(selectedCountry);
                    languageList.add(itemDoc);

                    Log.e(selectedCountry, selectedCountry);
                }
                break;
            }
        }

        showCountrySelector(getActivity(), languageList);

        /*Gson gson = new Gson();
        String languageOption = gson.toJson(languageCountryReceive.getCountryList());
        pref.setLanguageOption(languageOption);*/


        //initiateLoading(getActivity());

        //LanguageRequest languageRequest = new LanguageRequest();
        //languageRequest.setCountryCode(countryCode);

        //presenter.onLanguageRequest(languageRequest);
    }

    public void checkLanguageCountryResult() {
        RealmResults<CachedLanguageCountry> result = RealmObjectController.getCachedLanguageCountry(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Log.e("OnREsume", "1");

            Gson gson = new Gson();
            LanguageCountryReceive obj = gson.fromJson(result.get(0).getCachedResult(), LanguageCountryReceive.class);
            onSuccessRequestLanguageCountry(obj);
        }
        Log.e("OnREsume", "2");

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        checkLanguageCountryResult();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    /* UNUSED */
    @Override
    public void onSuccessRequestLanguage(LanguageReceive obj) {

        /*dismissLoading();
        languageList = new ArrayList<DropDownItem>();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();
            String gsonLanguageList = gson.toJson(obj.getLanguageList());
            pref.setLanguageList(gsonLanguageList);

            for (int i = 0; i < obj.getLanguageList().size(); i++) {

                DropDownItem itemDoc = new DropDownItem();
                itemDoc.setText(obj.getLanguageList().get(i).getLanguageName());
                itemDoc.setCode(obj.getLanguageList().get(i).getLanguageCode());
                itemDoc.setTag(selectedCountry);
                languageList.add(itemDoc);
            }

            showCountrySelector(getActivity(), languageList);

            //String languageCountry = gson.toJson(obj.getCountryList());
            //pref.setLanguageList(languageCountry);
        }*/

    }
}

