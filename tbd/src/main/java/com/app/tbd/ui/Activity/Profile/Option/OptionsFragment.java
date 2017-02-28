package com.app.tbd.ui.Activity.Profile.Option;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.RealmInboxNotification;
import com.app.tbd.ui.Activity.SplashScreen.AfterBoardingActivity;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.OnBoardingListAdapter;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Model.Receive.ContentReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.TBD.LogoutReceive;
import com.app.tbd.ui.Model.Request.ContentRequest;
import com.app.tbd.ui.Model.Request.LanguageCountryRequest;
import com.app.tbd.ui.Model.Request.TBD.LogoutRequest;
import com.app.tbd.ui.Module.OptionModule;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.bitmap;

public class OptionsFragment extends BaseFragment implements ProfilePresenter.OptionView {
    private int fragmentContainerId;

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.resetPasswordLayout)
    LinearLayout resetPasswordLayout;

    @InjectView(R.id.changeLanguageLayout)
    LinearLayout changeLanguageLayout;

    @InjectView(R.id.options_about)
    LinearLayout options_about;

    @InjectView(R.id.tncHead)
    LinearLayout tncHead;

    @InjectView(R.id.options_terms)
    LinearLayout options_terms;

    @InjectView(R.id.options_policy)
    LinearLayout options_policy;

    @InjectView(R.id.termOfUse)
    LinearLayout termOfUse;

    @InjectView(R.id.hideContainer)
    LinearLayout hideContainer;

    @InjectView(R.id.changes_icon)
    ImageView changes_icon;

    @InjectView(R.id.qrImage)
    ImageView qrImage;

    private SharedPrefManager pref;
    private ProgressDialog progress;
    private String CURRENT_PICKER;
    private Locale myLocale;
    private String languageCode;
    private String countryCode;
    private String cn;
    private String latestCountryCode;
    static ProfilePresenter staticPresenter;
    static String token, userName;

    LanguageCountryReceive languageCountryReceive = new LanguageCountryReceive();

    public String statusTab = "CLOSE";
    public String languageLanguageCode, languageCountryCode;

    private ArrayList<DropDownItem> languageList = new ArrayList<DropDownItem>();

    public static OptionsFragment newInstance() {

        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new OptionModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.options, container, false);
        ButterKnife.inject(this, view);
        setupData();
        /*AnalyticsApplication.sendScreenView("Options loaded");*/

        HashMap<String, String> lang = pref.getSavedLanguageSCode();
        languageLanguageCode = lang.get(SharedPrefManager.SAVED_S_LANGUAGE);

        HashMap<String, String> initCode = pref.getSavedCountryCode();
        languageCountryCode = initCode.get(SharedPrefManager.SAVED_COUNTRY_CODE);

        resetPasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resetPassword = new Intent(getActivity(), ResetPasswordActivity.class);
                getActivity().startActivity(resetPassword);

            }
        });

        changeLanguageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initiateLoading(getActivity());
                LanguageCountryRequest languageCountryRequest = new LanguageCountryRequest();
                presenter.onCountryRequest(languageCountryRequest);

            }
        });

        tncHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusTab.equals("CLOSE")) {
                    hideContainer.setVisibility(View.VISIBLE);
                    tncHead.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey_light));
                    changes_icon.setImageResource(R.drawable.ic_red_up);

                    statusTab = "OPEN";

                } else if (statusTab.equals("OPEN")) {
                    hideContainer.setVisibility(View.GONE);
                    tncHead.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                    changes_icon.setImageResource(R.drawable.ic_red_down);
                    statusTab = "CLOSE";
                }
            }
        });


        options_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                ContentRequest aboutUsRequest = new ContentRequest();
                aboutUsRequest.setCountryCode(languageCountryCode);
                aboutUsRequest.setContentName("About");
                aboutUsRequest.setLanguageCode(languageLanguageCode);

                staticPresenter.onLoadContent(aboutUsRequest);

            }
        });

        termOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                ContentRequest aboutUsRequest = new ContentRequest();
                aboutUsRequest.setCountryCode(languageCountryCode);
                aboutUsRequest.setContentName("TermsConditions");
                aboutUsRequest.setLanguageCode(languageLanguageCode);

                staticPresenter.onLoadContent(aboutUsRequest);


            }
        });

        options_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                ContentRequest aboutUsRequest = new ContentRequest();
                aboutUsRequest.setCountryCode(languageCountryCode);
                aboutUsRequest.setContentName("PrivacyPolicy");
                aboutUsRequest.setLanguageCode(languageLanguageCode);

                staticPresenter.onLoadContent(aboutUsRequest);

            }
        });

        options_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                ContentRequest aboutUsRequest = new ContentRequest();
                aboutUsRequest.setCountryCode(languageCountryCode);
                aboutUsRequest.setContentName("Terms");
                aboutUsRequest.setLanguageCode(languageLanguageCode);

                staticPresenter.onLoadContent(aboutUsRequest);

            }
        });

        HashMap<String, String> initTicketId = pref.getToken();
        token = initTicketId.get(SharedPrefManager.TOKEN);

        HashMap<String, String> initUserName = pref.getUsername();
        userName = initUserName.get(SharedPrefManager.USERNAME);


        return view;
    }


    public static void setLogout() {

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setToken(token);
        logoutRequest.setUsername(userName);

        staticPresenter.onRequestLogout(logoutRequest);
    }

    public void setupData() {

        staticPresenter = presenter;
        pref = new SharedPrefManager(getActivity());
        //languageList = getLanguage(getActivity());

        //retrieve back all data with selected language
        HashMap<String, String> init = pref.getLanguageCountry();
        String langCountry = init.get(SharedPrefManager.LANGUAGE_COUNTRY);


        //String[] parts = langCountry.split("-");
        //latestCountryCode = parts[1];
    }

    @Override
    public void onAboutUsReceive(ContentReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Intent about = new Intent(getActivity(), AboutActivity.class);
            about.putExtra("ABOUT", new Gson().toJson(obj));
            getActivity().startActivity(about);

        }
    }

    @Override
    public void onPrivacyPolicyReceive(ContentReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Intent about = new Intent(getActivity(), PrivacyPolicyActivity.class);
            about.putExtra("POLICY", new Gson().toJson(obj));
            getActivity().startActivity(about);

        }
    }

    @Override
    public void onTerms(ContentReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Intent about = new Intent(getActivity(), TermsActivity.class);
            about.putExtra("TERM", new Gson().toJson(obj));
            getActivity().startActivity(about);

        }
    }

    @Override
    public void onTermsOfUse(ContentReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Intent about = new Intent(getActivity(), TermOfUseActivity.class);
            about.putExtra("TERMOFUSE", new Gson().toJson(obj));
            getActivity().startActivity(about);

        }
    }

    @Override
    public void onSuccessRequestLanguageCountry(LanguageCountryReceive obj) {

        dismissLoading();
        Log.e("YE", "SAY!!");

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gson = new Gson();
            String languageCountry = gson.toJson(obj);

            RealmObjectController.saveCountryLanguage(getActivity(), languageCountry);

            if (checkFragmentAdded()) {
                // showCountrySelector(getActivity(), languageList, "LANGUAGE_LIST");
                // CURRENT_PICKER = "LANGUAGE";
                Intent changeLang = new Intent(getActivity(), ChangeLanguageActivity.class);
                getActivity().startActivity(changeLang);
            }
        }
    }

    @Override
    public void onLogoutReceive(LogoutReceive obj) {

        dismissLoading();

        RealmObjectController.clearLogout(getActivity());

        Intent logout = new Intent(getActivity(), AfterBoardingActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(logout);
        getActivity().finish();
        getActivity().finishAffinity();

        RealmObjectController.clearLogout(getActivity());
        RealmInboxNotification.clearNotificationInbox(getActivity());

        pref.setLoginStatus("N");

        /*System.exit(0);*/

        /*Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Intent profilePage = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(profilePage);
            getActivity().finish();

        }*/
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

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}


