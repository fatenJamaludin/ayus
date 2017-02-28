package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Realm.RealmObjectController;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PaymentWebviewFragment extends BaseFragment {

    //@Inject
    //BookingPresenter presenter;
    @InjectView(R.id.webView)
    WebView webview;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Book Flight: Payment Details(Payment Web)";
    private static final String SCREEN_LABEL_MANAGE = "Manage Flight: Payment Details(Payment Web)";
    private String paymentFrom;
    private String paymentCode;
    boolean loadingFinished = true;
    boolean redirect = false;
    private String paymentPopup;
    private boolean override = true;
    private Activity act;
    private Dialog dialog;

    public static PaymentWebviewFragment newInstance(Bundle bundle) {

        PaymentWebviewFragment fragment = new PaymentWebviewFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment_webview, container, false);
        ButterKnife.inject(this, view);

        dataSetup();


        return view;
    }

    public void setWebview(String url) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new PaymentWebviewFragment(), "Android");
        webview.loadUrl(url);
        override = true;

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;

                //split url
                String[] parts = url.split("/");
                String recordLocator = (parts[parts.length - 1]);

                if (url.contains("http://airasiabig.me-tech.com.my/Booking/Payment/Success")) { //STAGING
                //if (url.contains("https://appapi.airasiabig.com/Booking/Payment/Success")) { //LIVE


                    Intent intent = new Intent(getActivity(), PaymentPendingActivity.class);
                    intent.putExtra("RECORD_LOCATOR", recordLocator);
                    getActivity().startActivity(intent);
                    System.gc();
                    getActivity().finish();

               // } else if (url.equals("https://appapi.airasiabig.com/Booking/Payment/Failed")) { //LIVE
                } else if (url.equals("http://airasiabig.me-tech.com.my/Booking/Payment/Failed")) {  //STAGING


                    Intent intent = new Intent(getActivity(), PaymentFailedActivity.class);
                    getActivity().startActivity(intent);
                    System.gc();
                    getActivity().finish();

                } else {
                    webview.setWebChromeClient(new WebChromeClient());
                    webview.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
                loadingFinished = false;
                initiateLoadingPayment(getActivity());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    dismissLoadingPayment();
                } else {
                    redirect = false;
                }
            }

        });


    }

    public void dataSetup() {
        Bundle bundle = getArguments();
        String url = bundle.getString("PAYMENT_URL");
        // String recordLocator = bundle.getString("RECORD_LOCATOR");

        setWebview(url);
    }

    @JavascriptInterface
    public void PaymentFinished(String success) {
        newIntent();
    }

    public void newIntent() {
        Intent intent = new Intent(getActivity(), TabActivity.class);
        getActivity().startActivity(intent);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    /*public void paymentBackButton() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Cancel payment and redirect to homepage.")
                .showCancelButton(true)
                .setCancelText("Cancel")
                .setConfirmText("Confirm")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(intent);
                        System.gc();
                        getActivity().finish();

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

    }*/

    public void initiateLoadingPayment(Activity act) {

        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = new Dialog(act, R.style.DialogTheme);

        LayoutInflater li = LayoutInflater.from(act);
        final View myView = li.inflate(R.layout.loading_screen, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(myView);

        dialog.setContentView(myView);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCFFFFFF")));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    public void dismissLoadingPayment() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                Log.e("Dismiss", "Y");
            }
        }
    }
}
