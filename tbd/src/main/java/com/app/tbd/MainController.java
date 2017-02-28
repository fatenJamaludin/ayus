package com.app.tbd;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Homepage.HomeActivity;
import com.app.tbd.ui.Activity.Login.LoginFragment;
import com.app.tbd.ui.Activity.PasswordExpired.ChangePasswordActivity;
import com.app.tbd.ui.Activity.SplashScreen.AfterBoardingActivity;
import com.app.tbd.ui.Activity.SplashScreen.SplashScreenActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Activity.Terms.Terms;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainController extends BaseFragment {

    private static boolean homeStatus;

    public static boolean getHomeStatus() {
        return homeStatus;
    }

    public static void setHomeStatus() {
        homeStatus = true;
    }

    public static void clearAll(Activity act) {
        SharedPrefManager pref = new SharedPrefManager(act);
        pref.clearSignatureFromLocalStorage();
        pref.clearPassword();
        pref.clearUserEmail();
        pref.clearBookingID();
        pref.clearCustomerNumber();
        pref.clearPersonID();
        pref.clearFlightType();
        pref.clearNewsletterStatus();
        pref.clearPNR();
        pref.setLoginStatus("N");
        Log.e("SUCCESS", "ok");
    }


    private static SweetAlertDialog pDialog;

    /*public static void clickableBanner(Activity act,String page){

        Intent bannerIntent;
        if(page.equals("booking")){
            bannerIntent = new Intent(act,SearchFlightActivity.class);
            act.startActivity(bannerIntent);
        }else if(page.equals("faq")){
            bannerIntent = new Intent(act,Terms.class);
            act.startActivity(bannerIntent);
        }
    }*/

    public static void clickableBannerWithURL(Activity act, String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        act.startActivity(i);

        Log.e("url", url);

    }


    public static boolean connectionAvailable(Activity act) {

        Boolean internet;
        internet = Utils.isNetworkAvailable(act);

        return internet;
    }

    public static boolean getRequestStatus(String objStatus, String message, Activity act) {

        //SharedPrefManager pref;
        //pref = new SharedPrefManager(act);

        Boolean status = false;
        if (objStatus.contains("OK") || objStatus.equals("Redirect")) {
            status = true;
        /*} else if (message.contains("Session")) {
            *//*setAlertDialog(act, AnalyticsApplication.getContext().getString(R.string.main_session_expired),
                    AnalyticsApplication.getContext().getString(R.string.main_error));*//*
            MainController.sessionSearchTimeOut(act);*/

        } else if (message.equals("Invalid User Id")) {
            setAlertDialog(act,
                    AnalyticsApplication.getContext().getString(R.string.main_invalid_user),
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (message.equals("Invalid Token")) {
            status = false;
            MainController.clearAll(act);
            setAlertMaintance(act, message, AfterBoardingActivity.class,
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (message.contains("Invalid account number.")) {
            setAlertDialog(act, AnalyticsApplication.getContext().getString(R.string.main_invalid_card),
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (message.contains("Invalid account number.")) {
            setAlertDialog(act, AnalyticsApplication.getContext().getString(R.string.main_invalid_card),
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (message.contains("Your session is expired.")) {
            MainController.sessionSearchTimeOut(act);
            //autoLogout(act);

        } else if (objStatus.equals("ContactOtherPhone is required.")) {
            status = false;
            String msg = AnalyticsApplication.getContext().getString(R.string.error1);
            setAlertDialog(act, msg,
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (objStatus.equals("Insufficient points to proceed.")) {
            status = false;
            String msg = AnalyticsApplication.getContext().getString(R.string.error2);
            setAlertDialog(act, msg,
                    AnalyticsApplication.getContext().getString(R.string.addons_alert));

        } else if (objStatus.equals("Error") || objStatus.equals("error_validation")) {
            status = false;
            setAlertDialog(act, message,
                    AnalyticsApplication.getContext().getString(R.string.main_error));

        } else if (objStatus.equals("401")) {
            status = false;
            //setSignatureInvalid(act, message);
            //pref.clearSignatureFromLocalStorage();

        } else if (objStatus.equals("force_logout")) {
            MainController.clearAll(act);
            setAlertMaintance(act, message, AfterBoardingActivity.class,
                    AnalyticsApplication.getContext().getString(R.string.main_error));
            //resetPage(act);

        } else if (objStatus.equals("503")) {
            //MainController.clearAll(act);
            //resetPage(act);
            setAlertMaintance(act, message, SplashScreenActivity.class,
                    AnalyticsApplication.getContext().getString(R.string.main_sorry));

        } else if (objStatus.equals("change_password")) {
            goChangePasswordPage(act);

        } else if (objStatus.equals("error")) {
            //croutonAlert(getActivity(),obj.getMessage());
            //setSuccessDialog(getActivity(), obj.getMessage(), getActivity(), SearchFlightActivity.class);
            setAlertDialog(act, message, AnalyticsApplication.getContext().getString(R.string.error4));

        }

        return status;

    }

    public void retry() {

    }

    //Redirect
    public static void goChangePasswordPage(Activity act) {
        Intent loginPage = new Intent(act, ChangePasswordActivity.class);
        act.startActivity(loginPage);
        act.finish();
    }

    //Redirect
    public static void resetPage(Activity act) {
        Intent loginPage = new Intent(act, HomeActivity.class);
        act.startActivity(loginPage);
        act.finish();
    }

    public static void autoLogout(Activity act) {
        Intent logout = new Intent(act, AfterBoardingActivity.class);
        act.startActivity(logout);
        act.finish();
        Log.e("Status Auto Logout", "Success");
    }

    public static void sessionSearchTimeOut(Activity act) {
        setAlertMaintance(act, AnalyticsApplication.getContext().getString(R.string.error3), AfterBoardingActivity.class,
                AnalyticsApplication.getContext().getString(R.string.addons_alert));

        MainController.clearAll(act);

        /* Intent searchPage = new Intent(act, TabActivity.class);
        searchPage.putExtra("SEARCH", "INVALID_SESSION");
        act.startActivity(searchPage);
        act.finish();*/
    }

}
