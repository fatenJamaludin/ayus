package com.app.tbd.ui.Activity.Terms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Presenter.TermsPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TermsFragment extends BaseFragment {

    @Inject
    TermsPresenter presenter;

    @InjectView(R.id.mainWebView)WebView mainWebView;

    String URL= "http://fyapidev.me-tech.com.my/api/displayTerm";
    private static final String SCREEN_LABEL = "FAQ";

    public static TermsFragment newInstance() {

        TermsFragment fragment = new TermsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.term, container, false);
        ButterKnife.inject(this, view);

        initiateLoading(getActivity());

        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mainWebView.setWebViewClient(new MyCustomWebViewClient());
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebView.loadUrl(URL);
        mainWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissLoading();
            }

        });
        return view;
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.onPause();
    }


}
