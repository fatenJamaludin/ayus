package com.app.tbd.ui.Activity.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.AfterBoardingActivity;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.UserFacebookInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginFacebookReceive;
import com.app.tbd.ui.Model.Receive.UserPhotoReceive;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LoginInfoRequest;
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.TBD.LoginFacebookRequest;
import com.app.tbd.ui.Model.Request.UserPhotoRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.app.tbd.MainController;
import com.app.tbd.application.MainApplication;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.ForgotPassword.ForgotPasswordActivity;
import com.app.tbd.ui.Activity.Profile.ProfileActivity;
import com.app.tbd.ui.Activity.Register.RegisterActivity;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Module.LoginModule;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Model.Request.TBD.LoginRequest;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.timeZone;

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView, Validator.ValidationListener {

    // Validator Attributes
    private static Validator mValidator;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Inject
    LoginPresenter presenter;

    @NotEmpty(sequence = 1, messageResId = R.string.email_empty)
    @Email(sequence = 2, messageResId = R.string.email_invalid)
    @Order(1)
    @InjectView(R.id.txtLoginEmail)
    EditText txtLoginEmail;

    @NotEmpty(sequence = 1, messageResId = R.string.password_empty)
    @Order(2)
    @InjectView(R.id.txtLoginPassword)
    EditText txtLoginPassword;

    @InjectView(R.id.txtForgotPassword)
    TextView txtForgotPassword;

    @InjectView(R.id.login_email_title)
    TextView login_email_title;

    /*@InjectView(R.id.dummyFBButton)
    Button dummyFBButton;*/

    /*@InjectView(R.id.login_button)
    LoginButton login_button;*/

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    private SharedPrefManager pref;
    /*public static final String MIXPANEL_TOKEN = "7969a526dc4b31f72f05ca4a060eda1c"; //Token AAB*/
    private static String MIXPANEL_TOKEN = AnalyticsApplication.getMixPanelToken(); //Token dr AA
    /*public static final String MIXPANEL_TOKEN = "12161458140eb25a9cecc6573ad97d1c"; //Token Paten*/
    CallbackManager callbackManager;
    MixpanelAPI mixPanel;
    Realm realm;
    Activity act;
    String country;
    String languageLanguageCode;

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();

        MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        callbackManager = CallbackManager.Factory.create();

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

        //Analytics
        mixPanel = MixpanelAPI.getInstance(getActivity(), MIXPANEL_TOKEN);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

    }

    public static void onBackPressed(Activity act) {
        Intent x = new Intent(act, AfterBoardingActivity.class);
        x.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(x);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.inject(this, view);

        RealmInboxNotification.clearNotificationInbox(getActivity());

        realm = RealmObjectController.getRealmInstance(getActivity());

        /*AnalyticsApplication.sendScreenView("Login loaded");*/
        pref = new SharedPrefManager(getActivity());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideKeyboard();
                mValidator.validate();
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        /*login_button.setReadPermissions(Arrays.asList("email"));
        login_button.setFragment(this);*/

        /*dummyFBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fb login
                login_button.performClick();
                Log.e("?", "?");
            }
        });*/

        //LoginManager.getInstance().logOut();

        // Callback registration
/*
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //friendProfilePicture.setProfileId(loginResult.getAccessToken().getUserId());

                */
/*try {
                    Profile profile = Profile.getCurrentProfile();
                    profile.ge
                    //shakeDevice.setText(profile.getName());
                } catch (Exception e) {

                }*//*


                final String userToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        Log.i("LoginActivity", object.toString());
                        try {
                            String fbEmail = response.getJSONObject().get("email").toString();
                            String fbFirstName = response.getJSONObject().get("first_name").toString();
                            String fbLastName = response.getJSONObject().get("last_name").toString();
                            String fbGender = response.getJSONObject().get("gender").toString();
                            String fbBirthday = response.getJSONObject().get("birthday").toString();

                            UserFacebookInfo userFacebookInfo = new UserFacebookInfo();
                            userFacebookInfo.setUserEmail(fbEmail);
                            userFacebookInfo.setUserFirstName(fbFirstName);
                            userFacebookInfo.setUserLastName(fbLastName);
                            userFacebookInfo.setUserGender(fbGender);
                            userFacebookInfo.setUserBirthday(fbBirthday);

                            //convert information to gson
                            Gson gsonUserInfo = new Gson();
                            facebookInfoGSON = gsonUserInfo.toJson(userFacebookInfo);


                            LoginFacebookRequest loginFacebookRequest = new LoginFacebookRequest();
                            loginFacebookRequest.setEmail(fbEmail);
                            loginFacebookRequest.setToken(userToken);
                            presenter.onCheckFBLogin(loginFacebookRequest);

                        } catch (Exception e) {

                        }


                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


                Log.e("FB ID", loginResult.getAccessToken().getUserId());
                Log.e("FB TOKEN", loginResult.getAccessToken().getToken());
                Log.e("Another ID", loginResult.getAccessToken().getApplicationId());
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("?", "2");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("Exception", exception.getMessage());
                Log.e("?", "3");
            }
        });
*/


        return view;
    }

    public static void onLogin() {
        mValidator.validate();
    }

    public void loginFromFragment(String username, String password) {
        /*Start Loading*/
        initiateLoading(getActivity());

        LoginRequest loginData = new LoginRequest();
        loginData.setUsername(username);
        loginData.setPassword(password);

        presenter.onLogin(loginData);

    }

    public void forgotPassword() {
        Intent profilePage = new Intent(getActivity(), ForgotPasswordActivity.class);
        getActivity().startActivity(profilePage);
    }

    @Override
    public void onLoginSuccess(LoginReceive obj) {

        /*dismissLoading();*/

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            if (isAdded()) {

                pref.setLoginStatus("Y");
                pref.setPromoStatus("No");
                pref.setUsername(obj.getUserName());
                pref.setToken(obj.getToken());
                pref.setUserPassword(txtLoginPassword.getText().toString());

                Gson gsonUserInfo = new Gson();
                String userInfo = gsonUserInfo.toJson(obj);


                RealmObjectController.saveUserInformation(getActivity(), userInfo);

                UserPhotoRequest userPhotoRequest = new UserPhotoRequest();
                userPhotoRequest.setToken(obj.getToken());
                userPhotoRequest.setUserName(obj.getUserName());
                presenter.onRequestUserPhoto(userPhotoRequest);
                //success login -> homepage*/

                HashMap<String, String> lang = pref.getSavedLanguageSCode();
                languageLanguageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

                HashMap<String, String> init = pref.getSavedCountryCode();
                country = init.get(SharedPrefManager.SAVED_COUNTRY_CODE);
                String bigShotID = obj.getCustomerNumber();

                HashMap<String, String> initAppData = pref.getFirstTimeLogin();
                String firstTime = initAppData.get(SharedPrefManager.FIRST_TIME_LOGIN);

                LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
                loginInfoRequest.setEmail(obj.getUserName());
                loginInfoRequest.setCountry(country);
                loginInfoRequest.setBsid(bigShotID);
                presenter.onGetLoginInfo(loginInfoRequest);

                mFirebaseAnalytics.setUserProperty("big_shot_id", obj.getCustomerNumber());
                mFirebaseAnalytics.setUserProperty("user_id", obj.getUserName());
                mFirebaseAnalytics.setUserProperty("country", country);

                Bundle bundle = new Bundle();
                bundle.putString("username", obj.getUserName());

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                mixPanel.identify(obj.getCustomerNumber());

                String loginDate = obj.getLoginDate().substring(0, 10);//2017-02-13
                String loginTime = obj.getLoginDate().substring(11);

                int y = Integer.parseInt(loginDate.substring(0, 4));
                int m = Integer.parseInt(loginDate.substring(5, 7));
                int d = Integer.parseInt(loginDate.substring(8));
                String date = d + "/" + m + "/" + y;

                if (firstTime != null && firstTime.equals("N")) {

                    try {

                        mixPanel.getPeople().identify(obj.getCustomerNumber());

                        JSONObject props = new JSONObject();
                        props.put("$name", obj.getCustomerNumber());
                        props.put("$email", obj.getUserName());
                        props.put("$ip", country);
                        props.put("Last Login", date + ", " + loginTime);
                        props.put("bigShotID", obj.getCustomerNumber());
                        mixPanel.getPeople().set(props);

                        mixPanel.track("User login", props);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    try {


                        mixPanel.getPeople().identify(obj.getCustomerNumber());

                        JSONObject props = new JSONObject();
                        props.put("bigShotID", obj.getCustomerNumber());
                        props.put("$name", obj.getCustomerNumber());
                        props.put("$email", obj.getUserName());
                        props.put("$ip", country);
                        props.put("Last Login", date + ", " + loginTime);
                        props.put("$created", obj.getLoginDate());
                        mixPanel.getPeople().set(props);

                        mixPanel.track("User login", props);

                        pref.setFirstTimeLogin("N");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {

            }

        } else {
            dismissLoading();
        }
    }

    @Override
    public void onRequestUserPhotoSuccess(UserPhotoReceive obj) {

        dismissLoading();

        //add URL to REALM User info
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), act);

        if (status) {

            loginReceive.setProfile_URL(obj.getURL());

            Gson gsonUserInfo = new Gson();
            String userInfo = gsonUserInfo.toJson(loginReceive);
            RealmObjectController.saveUserInformation(act, userInfo);

            HashMap<String, String> langCode = pref.getHardCodeLanguage();
            String languageCode = langCode.get(SharedPrefManager.HARD_CODE_LANGUAGE);
            loadInitialData(languageCode);

        } else {
            Intent flightDetail = new Intent(act, TabActivity.class);
            act.startActivity(flightDetail);
        }

        Log.e("getAllMessages(token)", loginReceive.getToken());
        getAllMessages(loginReceive.getToken(),country,languageLanguageCode);
    }

    @Override
    public void onMessageReceive(MessageReceive obj) {
        RealmInboxNotification.clearNotificationInbox(act);
        dismissLoading();

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

    @Override
    public void onCheckFBLoginSuccess(LoginFacebookReceive obj) {

    }

        /*dismissDefaultLoading(progress, getActivity());*/

    public void onSuccessRequestState(StateReceive obj) {


        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();
            String state = gson.toJson(obj.getStateList());
            pref.setState(state);

            Intent flightDetail = new Intent(act, TabActivity.class);
            act.startActivity(flightDetail);


        } else {
            dismissLoading();
        }
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



        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        InitialLoadRequest infoData = new InitialLoadRequest();
        infoData.setLanguageCode(languageCode);
        infoData.setUsername(userName);
        infoData.setFcmKey(deviceToken);
        infoData.setDeviceType("Android");
        infoData.setCustomerNumber(customerNumber);
        presenter.initialLoad(infoData);

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
    public void onValidationSucceeded() {
        /* Validation Success - Start send data to server */
        hideKeyboard();
        loginFromFragment(txtLoginEmail.getText().toString(), txtLoginPassword.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        /* Validation Failed - Toast Error */
        for (ValidationError error : errors) {
            View view = error.getView();
            setShake(view);
            view.setFocusable(true);
            view.requestFocus();

            /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            LoginReceive obj = gson.fromJson(result.get(0).getCachedResult(), LoginReceive.class);
            onLoginSuccess(obj);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
