package com.app.tbd.ui.Activity.Profile.Option;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.Receive.ContentReceive;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PrivacyPolicyFragment extends BaseFragment {

    private static final String SCREEN_LABEL = "PrivacyPolicy";


    @InjectView(R.id.privacy_content)
    TextView privacy_content;

    public static PrivacyPolicyFragment newInstance(Bundle bundle) {

        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.privacy_policy, container, false);
        ButterKnife.inject(this, view);

        Bundle bundle = getArguments();
        String policy = bundle.getString("POLICY");
        ContentReceive onBoardingReceive = (new Gson()).fromJson(policy, ContentReceive.class);

        privacy_content.setText(Html.fromHtml(onBoardingReceive.getContent().replaceAll("</br>", "<p>")));
        privacy_content.setLinksClickable(true);
        privacy_content.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        privacy_content.setMovementMethod(LinkMovementMethod.getInstance());

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

    /*private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
