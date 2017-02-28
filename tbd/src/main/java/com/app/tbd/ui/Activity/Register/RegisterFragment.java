package com.app.tbd.ui.Activity.Register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.utils.SharedPrefManager;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterFragment extends BaseFragment {

    @InjectView(R.id.mainWebView)
    WebView mainWebView;

    private static final String SCREEN_LABEL = "Register";
    boolean loadingFinished = true;
    boolean redirect = false;
    private SharedPrefManager pref;
    private String languageCode;
    Activity act;

    public static RegisterFragment newInstance(Bundle bundle) {
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register_webview, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(act);

        HashMap<String, String> hardCodeLanguage = pref.getHardCodeLanguage();
        String languageCN = hardCodeLanguage.get(SharedPrefManager.HARD_CODE_LANGUAGE);
        languageCode = languageCN;

        String URL = "https://member.airasia.com/register.aspx?referral=BIG&app=big&culture=" + languageCode;

        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        loadURL(URL);
        return view;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void focus() {
        croutonAlert(act, getString(R.string.register_error));
    }

    public void loadURL(String url) {

        Boolean override = false;
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.setWebChromeClient(new WebChromeClient());
        mainWebView.addJavascriptInterface(new RegisterFragment(), "Android");
        mainWebView.loadUrl(url);

        mainWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;

                if (url.contains("member-reg-submit")) {
                    dismissLoading();

                    setSuccessDialog(act, getString(R.string.register_success_message),
                            LoginActivity.class, getString(R.string.success));

                } else {
                    initiateLoading(act);
                    view.loadUrl(url);
                    Log.e("B", "B");
                }
                Log.e("URL", url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                loadingFinished = false;
                initiateLoading(act);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    dismissLoading();
                } else {
                    redirect = false;
                }
            }

        });
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
