package com.app.tbd.ui.Activity.Profile.Option;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Receive.ContentReceive;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutFragment extends BaseFragment {

    //@Inject
    //TermsPresenter presenter;

    @InjectView(R.id.about_content)
    TextView about_content;

    private static final String SCREEN_LABEL = "About";

    public static AboutFragment newInstance(Bundle bundle) {

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.aboutus, container, false);
        ButterKnife.inject(this, view);


        Bundle bundle = getArguments();
        String aboutUs = bundle.getString("ABOUT");
        ContentReceive onBoardingReceive = (new Gson()).fromJson(aboutUs, ContentReceive.class);

        about_content.setText(Html.fromHtml(onBoardingReceive.getContent().replaceAll("</br>", "<p>")));
        about_content.setLinksClickable(true);
        about_content.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        about_content.setMovementMethod(LinkMovementMethod.getInstance());

        /*initiateLoading(getActivity());

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

        });*/
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
