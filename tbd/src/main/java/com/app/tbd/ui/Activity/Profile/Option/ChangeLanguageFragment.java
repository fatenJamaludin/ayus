package com.app.tbd.ui.Activity.Profile.Option;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.SC_Activity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.JSON.countryLanguageJSON;
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
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Module.ChangeLanguageModule;
import com.app.tbd.ui.Presenter.LanguagePresenter;
import com.app.tbd.ui.Realm.Cached.CachedLanguageCountry;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ChangeLanguageFragment extends BaseFragment implements LanguagePresenter.LanguageView {

    @Inject
    LanguagePresenter presenter;

    public static LanguagePresenter staticPresenter;
    ProgressDialog progress;
    private View view;
    private int fragmentContainerId;
    private Locale myLocale;
    private ArrayList<DropDownItem> languageList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> country = new ArrayList<DropDownItem>();
    private String CURRENT_PICKER;
    private SharedPrefManager pref;
    private static SharedPrefManager staticPref;
    private Boolean languageClickable = true;
    private static String cn;
    private static String countryCode, countryName, languageName, languageCode;
    private static TextView txtLangCountry, txtLangLanguage;
    private static Button btn_nxt;
    LanguageCountryReceive languageCountryReceive;
    static private String previousCountryCode, previousLanguageCode;
    static String deviceToken = "";
    Activity act;

    public static ChangeLanguageFragment newInstance() {

        ChangeLanguageFragment fragment = new ChangeLanguageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new ChangeLanguageModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.language, container, false);
        ButterKnife.inject(this, view);

        staticPresenter = presenter;

        pref = new SharedPrefManager(getActivity());
        progress = new ProgressDialog(getActivity());

        staticPref = pref;
        deviceToken = FirebaseInstanceId.getInstance().getToken();

        dataDeclaration();

        HashMap<String, String> lang = pref.getSavedLanguage();
        languageName = lang.get(SharedPrefManager.SAVED_LANGUAGE);

        HashMap<String, String> init = pref.getSavedCountry();
        countryName = init.get(SharedPrefManager.SAVED_COUNTRY);

        HashMap<String, String> langCode = pref.getSavedLanguageCode();
        languageCode = langCode.get(SharedPrefManager.SAVED_LANGUAGE_CODE);

        HashMap<String, String> initCode = pref.getSavedCountryCode();
        countryCode = initCode.get(SharedPrefManager.SAVED_COUNTRY_CODE);

        HashMap<String, String> cnCode = pref.getCn();
        cn = cnCode.get(SharedPrefManager.CN);

        //use to unsubscribe user from topic
        previousCountryCode = countryCode;
        previousLanguageCode = languageCode + "-" + cn;
        Log.e("Unsubsribe from topic", previousCountryCode + "_" + previousLanguageCode);


        txtLangLanguage.setText(languageName);
        Log.e("Language Code", languageCode);
        txtLangLanguage.setTag(languageCode + cn);
        txtLangCountry.setTag(countryCode);
        txtLangCountry.setText(countryName);

        LanguageCountryRequest languageCountryRequest = new LanguageCountryRequest();
        presenter.onCountryRequest(languageCountryRequest);

        loadLocale();

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<countryLanguageJSON> result9 = realm.where(countryLanguageJSON.class).findAll();

        LanguageCountryReceive languageCountryReceive2 = (new Gson()).fromJson(result9.get(0).getCountryLanguageReceive(), LanguageCountryReceive.class);

        country = getCountryOption(languageCountryReceive2);
        languageList = getLanguageOption(languageCountryReceive2, countryCode);

        //country selection
        txtLangCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    showCountrySelector(getActivity(), country, "CHANGE_COUNTRY");
                    CURRENT_PICKER = "CHANGE_COUNTRY";
                }
            }
        });

        //language selection
        txtLangLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToast = getString(R.string.please_select_country);

                if (txtLangCountry.getText().toString() != "") {
                    if (languageClickable) {
                        if (checkFragmentAdded()) {
                            showCountrySelector(getActivity(), languageList, "LANGUAGE");
                            CURRENT_PICKER = "LANGUAGE";
                        }
                    }

                } else {
                    Utils.toastNotification(getActivity(), textToast);
                }
            }
        });

        return view;
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

            Gson gson = new Gson();
            String languageCountry = gson.toJson(languageCountryReceive);
            pref.setLanguageCountry(languageCountry);

        }
    }

    @Override
    public void onPromotionReceive(PromotionReceive obj) {

        LoginReceive loginReceive;
        String token = "";

        //call realm - user info
        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

        if (result2.size() > 0) {
            loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
            token = loginReceive.getToken();
        } else {
            token = "";
            Log.e("token","null");
        }

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            dismissLoading();

            Gson gsonUserInfo = new Gson();
            String promotionObj = gsonUserInfo.toJson(obj);
            RealmObjectController.savePromotion(getActivity(), promotionObj);

            pref.setPromoLastUpdate(obj.getLastUpdated());

            changeLanguage(languageCode);
            Log.e(languageCode, languageName);

            RealmObjectController.clearRecentSearch(getActivity());

            Intent changeLang = new Intent(getActivity(), TabActivity.class);
            changeLang.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(changeLang);
            getActivity().finish();

            Log.e("getAllMessages(token)", token);
            getAllMessages(token,countryCode,languageCode);

        } else {
            dismissLoading();
        }
    }

    public static void loadInitialData(Activity act, String languageCode) {

        initiateLoading(act);

        String cut1 = previousLanguageCode.substring(0, 2).toLowerCase();
        String cut3 = previousLanguageCode.substring(Math.max(previousLanguageCode.length() - 3, 0));
        String lowerLanguage = languageCode.toLowerCase();

        Log.e("cut1", cut1);
        Log.e("cut3", cut3);

        Log.e("prev Subscribe 1 CL", previousCountryCode);
        Log.e("prev Subscribe 2 CL", previousCountryCode + "_" + cut1 + cut3);

        Log.e("Subscribe 1 CL", countryCode);
        Log.e("Subscribe 2 CL", countryCode + "_" + lowerLanguage + "-" + cn);

        //unsubscribeFromTopic first
        FirebaseMessaging.getInstance().unsubscribeFromTopic(previousCountryCode + "_" + cut1 + cut3);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(previousCountryCode);

        //subscribe to user here
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode + "_" + lowerLanguage + "-" + cn);
        FirebaseMessaging.getInstance().subscribeToTopic(countryCode);

        String customerNumber = "";
        String userName = "";
        LoginReceive loginReceive;

        //call realm - user info
        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();

        if (result2.size() > 0) {
            loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
            customerNumber = loginReceive.getCustomerNumber();
            userName = loginReceive.getUserName();
        } else {
            customerNumber = "";
            userName = "";
        }

        if (languageCode.equalsIgnoreCase("tt") && countryCode.equalsIgnoreCase("th")) {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode("en-TT");
            infoData.setUsername(userName);
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber(customerNumber);

            staticPref.setHardCodeLanguageCode("en-TT");
            staticPresenter.initialLoad(infoData);
        } else if (languageCode.equalsIgnoreCase("en") && countryCode.equalsIgnoreCase("th")) {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode("en-GB");
            infoData.setUsername(userName);
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber(customerNumber);

            staticPref.setHardCodeLanguageCode("en-GB");
            staticPresenter.initialLoad(infoData);
        } else {
            InitialLoadRequest infoData = new InitialLoadRequest();
            infoData.setLanguageCode(languageCode + "-" + cn);
            infoData.setUsername(userName);
            infoData.setFcmKey(deviceToken);
            infoData.setDeviceType("Android");
            infoData.setCustomerNumber(customerNumber);

            staticPref.setHardCodeLanguageCode(languageCode + "-" + cn);
            staticPresenter.initialLoad(infoData);
        }

        staticPref.setLanguageCountry(languageCode + "-" + countryCode);
        staticPref.setLanguageCode(languageCode + "-" + countryCode);
    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, ArrayList constParam, String data) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment countryListDialogFragment = SelectionListFragment.newInstance(data, "na", false);
                countryListDialogFragment.setTargetFragment(ChangeLanguageFragment.this, 0);
                countryListDialogFragment.show(fm, "countryListDialogFragment");
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dataDeclaration() {

        txtLangCountry = (TextView) view.findViewById(R.id.txtLangCountry);
        txtLangLanguage = (TextView) view.findViewById(R.id.txtLangLanguage);
        btn_nxt = (Button) view.findViewById(R.id.btn_nxt);

    }

    @Override
    public void loadingSuccess(InitialLoadReceive obj) {

        Log.e("", "");
        Boolean status = MainController.getRequestStatus(obj.getObj().getStatus(), obj.getObj().getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();

            String title = gson.toJson(obj.getObj().getData_title());
            pref.setUserTitle(title);

            String country = gson.toJson(obj.getObj().getData_country());
            pref.setCountry(country);

            String route = gson.toJson(obj.getObj().getRouteList());
            pref.setRoute(route);

            HashMap<String, String> init = pref.getChinese();
            String chinese = init.get(SharedPrefManager.CHINESE);

            HashMap<String, String> init2 = pref.getThailand();
            String thailand = init2.get(SharedPrefManager.THAILAND);

            //modify
            /*modifyRoute();*/

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
            modifyRoute();

            //load state - need to move later on
            StateRequest stateRequest = new StateRequest();
            stateRequest.setLanguageCode(languageCode + "-" + cn);
            stateRequest.setCountryCode(countryCode);
            stateRequest.setPresenterName("LanguagePresenter");
            presenter.onStateRequest(stateRequest);
        }
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
            DropDownItem selectedLanguage = data.getParcelableExtra(CURRENT_PICKER);
            if (CURRENT_PICKER.equals("LANGUAGE")) {

                txtLangLanguage.setText(selectedLanguage.getText());
                txtLangLanguage.setTag(selectedLanguage.getCode());

                languageCode = selectedLanguage.getCode();
                languageName = selectedLanguage.getText();

                changeLanguage2(languageCode);

            } else if (CURRENT_PICKER.equals("CHANGE_COUNTRY")) {
                DropDownItem selectedCountry = data.getParcelableExtra(CURRENT_PICKER);
                txtLangCountry.setText(selectedCountry.getText());
                txtLangCountry.setTag(selectedCountry.getCode());
                countryCode = selectedCountry.getCode();

                countryName = selectedCountry.getText();
                countryCode = selectedCountry.getCode();

                pref.setSavedCountryCode(countryCode);

                //retrieveLanguage(selectedCountry.getCode());
                txtLangLanguage.setText(getString(R.string.register_select_language));
            }
        }
    }

    @Override
    public void onOverlayReceive(OverlayReceive obj) {

        //dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            String overInfo = gsonUserInfo.toJson(obj);
            RealmObjectController.saveOverlayInfo(getActivity(), overInfo);

            if (obj.getOverlay().getOverlay_Status().equalsIgnoreCase("Y")) {
                saveOverlayInbox(getActivity(), obj.getOverlay().getMessage(), obj.getOverlay().getTitle(), getCurrentTimeStamp(),getCurrentTimeStamp());
            } else {
                Log.e("Overlay", "null");
            }

            getPromotionInfo();

        } else {
            dismissLoading();
        }

    }

    @Override
    public void onMessageReceive(MessageReceive obj) {

        dismissLoading();
        RealmInboxNotification.clearNotificationInbox(act);

        Boolean status = MainController.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status) {
            int position = obj.getAllMessage().size();

            for (int i = 0; i < position; i++){
                String date = stringDate(obj.getAllMessage().get(i).getDeliveredDate());

                saveNotificationInbox(act,
                        obj.getAllMessage().get(i).getMessage(),obj.getAllMessage().get(i).getTitle(),date,
                        obj.getAllMessage().get(i).getBody(),obj.getAllMessage().get(i).getStatus(),obj.getAllMessage().get(i).getMessageID(),
                        obj.getAllMessage().get(i).getType(),obj.getAllMessage().get(i).getId());
            }

        } else {
            dismissLoading();
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

    /* LOCALIZATION */
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

        Log.e("Change Lang to this", lang);
        dismissLoading();

        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    private void updateTexts() {
        btn_nxt.setText(R.string.btn_nxt);
    }

    /*public void changeLanguage2(String selectedLanguage) {
        String lang = "en";
        /*String lang = "en";

        if (selectedLanguage.equalsIgnoreCase("en")) {
            cn = "GB";
            lang = "en";
        } else if (selectedLanguage.equalsIgnoreCase("ms")) {
            lang = "ms";
            cn = "MY";

        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            lang = "zh";
            cn = "CN";

        } else if (selectedLanguage.equalsIgnoreCase("th")) {
            lang = "th";
            cn = "TH";

        } else {
            lang = "en";
            cn = "GB";

        } //else if (selectedLanguage.equals("th")) {
        //lang = "th";
        //}


        //initiateLoading(getActivity());


        if (selectedLanguage.equalsIgnoreCase("tt")) {
            lang = "th";
        } else {
            lang = selectedLanguage;
        }
        changeLang(lang);
    }*/

    public void changeLanguage(String selectedLanguage) {
        String lang = "en";
        /*pref.setThailand("N");
        pref.setChinese("N");*/

        if (countryCode.equalsIgnoreCase("th") && selectedLanguage.equalsIgnoreCase("tt")) {
            pref.setThailand("Y");
        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            pref.setChinese("Y");
        } else {
            pref.setThailand("N");
            pref.setChinese("N");
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
    }

    public void changeLanguage2(String selectedLanguage) {
        String lang = "en";

        if (selectedLanguage.equalsIgnoreCase("en")) {
            cn = "GB";
            lang = "en";
        } else if (selectedLanguage.equalsIgnoreCase("ms")) {
            lang = "ms";
            cn = "MY";

        } else if (selectedLanguage.equalsIgnoreCase("zh")) {
            lang = "zh";
            cn = "CN";

        } else if (selectedLanguage.equalsIgnoreCase("th")) {
            lang = "th";
            cn = "TH";

        } else if (selectedLanguage.equalsIgnoreCase("id")) {
            lang = "id";
            cn = "ID";
        } else {
            lang = "en";
            cn = "GB";

        } //else if (selectedLanguage.equals("th")) {
        //lang = "th";
        //}
        //changeLang(lang);
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
    public void onSuccessRequestState(StateReceive obj) {


        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            //save to pref

            Gson gson = new Gson();
            String state = gson.toJson(obj.getStateList());
            pref.setState(state);

            //initiateLoading(getActivity());
            //HashMap<String, String> initAppData = pref.getSavedCountryCode();
            //String countryCode = initAppData.get(SharedPrefManager.SAVED_COUNTRY_CODE);

            //getPromotionInfo();
            /*RealmInboxNotification.clearNotificationInbox(getActivity());*/
            getOverlayImage();


        } else {
            dismissLoading();
        }
    }

    public void getOverlayImage() {
        //initiateLoading(getActivity());
        OverlayRequest overlayRequest = new OverlayRequest();
        overlayRequest.setCountryCode(countryCode);
        overlayRequest.setLanguageCode(languageCode);
        presenter.onOverlayImage(overlayRequest);
        Log.e("Get promo send", countryCode + "->" + languageCode);
    }

    public void getPromotionInfo() {
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setCountryCode(countryCode);
        promotionRequest.setLanguageCode(languageCode);
        presenter.onPromotionRequest(promotionRequest);
    }

    public static void onSavePassword(Activity act) {
        saveToPref();
        loadInitialData(act, languageCode);
    }

    //changeLanguage(selectedLanguage.getCode());
    //pref.setSavedLanguageSCode(languageCode);

    //Gson gson = new Gson();
    //String languageLang = gson.toJson(selectedLanguage);
    //pref.setLanguageList(languageLang);

    public static void saveToPref() {

        Log.e("Change early country", countryName);
        staticPref.setSavedCountry(countryName);
        staticPref.setSavedCountryCode(countryCode);

        Log.e("Change early language", languageName);
        staticPref.setSavedLanguage(languageName);
        staticPref.setSavedLanguageCode(languageCode);

        staticPref.setSavedLanguageCode(languageCode);
        staticPref.setSavedLanguageSCode(languageCode);

    }

   /* public void retrieveLanguage(String countryCode) {

        languageList = new ArrayList<DropDownItem>();

        for (int i = 0; i < languageCountryReceive.getCountryList().size(); i++) {
            if (countryCode.equals(languageCountryReceive.getCountryList().get(i).getCountryCode())) {
                for (int y = 0; y < languageCountryReceive.getCountryList().get(i).getLanguageList().size(); y++) {
                    DropDownItem itemDoc = new DropDownItem();
                    itemDoc.setText(languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageName());
                    itemDoc.setCode(languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageCode());

                    Log.e(languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageName(),languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageCode());
                    Log.e("Change early language", languageCountryReceive.getCountryList().get(i).getLanguageList().get(y).getLanguageCode());

                    languageList.add(itemDoc);
                }
                break;

            }
        }
    }*/

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
    public void onBoardingReceive(OnBoardingReceive obj) {


    }

    public void getAllMessages(String token, String countryCode, String languageCode){
        /*initiateLoading(getActivity());*/
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setToken(token);
        messageRequest.setCountryCode(countryCode);
        messageRequest.setLanguageCode(languageCode);
        presenter.onRequestMessage(messageRequest);
        Log.e("Get promo send",token);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        checkLanguageCountryResult();
        Log.e("OnREsume", "true");
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();

    }

    /* UNUSED */
    @Override
    public void onSuccessRequestLanguage(LanguageReceive obj) {

        /*languageList = new ArrayList<DropDownItem>();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();
            String gsonLanguageList = gson.toJson(obj.getLanguageList());
            pref.setLanguageList(gsonLanguageList);

            languageClickable = true;
            txtLangLanguage.setClickable(true);
            txtLangLanguage.setText("");
            txtLangLanguage.setHint(getResources().getString(R.string.register_newsletter_language_hint));

		    *//*Travel Doc*//*
            for (int i = 0; i < obj.getLanguageList().size(); i++) {

                DropDownItem itemDoc = new DropDownItem();
                itemDoc.setText(obj.getLanguageList().get(i).getLanguageName());
                itemDoc.setCode(obj.getLanguageList().get(i).getLanguageCode());
                languageList.add(itemDoc);
            }
            //String languageCountry = gson.toJson(obj.getCountryList());
            //pref.setLanguageList(languageCountry);
        }*/

    }
}

