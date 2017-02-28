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

public class TermsFragment extends BaseFragment {

    // @Inject
    //TermsPresenter presenter;

    //@InjectView(R.id.mainWebView2)
    //WebView mainWebView;

    @InjectView(R.id.terms_content)
    TextView terms_content;

    private static final String SCREEN_LABEL = "Terms";

    public static TermsFragment newInstance(Bundle bundle) {

        TermsFragment fragment = new TermsFragment();
        fragment.setArguments(bundle);
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

        Bundle bundle = getArguments();
        String policy = bundle.getString("TERM");
        ContentReceive onBoardingReceive = (new Gson()).fromJson(policy, ContentReceive.class);

        terms_content.setText(Html.fromHtml(onBoardingReceive.getContent().replaceAll("</br>", "<p>")));
        terms_content.setLinksClickable(true);
        terms_content.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        terms_content.setMovementMethod(LinkMovementMethod.getInstance());

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
