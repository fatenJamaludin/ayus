package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.ui.Model.Receive.ForgotPasswordReceive;
import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginFacebookReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.UserPhotoReceive;
import com.app.tbd.ui.Model.Request.ForgotPasswordRequest;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LoginInfoRequest;
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.TBD.LoginFacebookRequest;
import com.app.tbd.ui.Model.Request.TBD.LoginRequest;
import com.app.tbd.ui.Model.Request.UserPhotoRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONException;

import java.util.HashMap;

public class LoginPresenter {

    public interface ForgotPasswordView {
//        void onForgotPasswordReceive(ForgotPasswordReceive obj);
    }

    public interface PushNotificationView {
        /*void onMessageReceive(MessageReceive obj);*/
    }

    public interface LoginView {

        void onLoginSuccess(LoginReceive obj);
        void onCheckFBLoginSuccess(LoginFacebookReceive obj);
        /*void onRequestPasswordSuccess(ForgotPasswordReceive obj);*/
        void onRequestUserPhotoSuccess(UserPhotoReceive obj);
        void onMessageReceive(MessageReceive obj);
        void loadingSuccess(InitialLoadReceive obj);
        void onSuccessRequestState(StateReceive obj);

    }

    private LoginView loginView;
    private ForgotPasswordView forgotPasswordView;
    private PushNotificationView pushNotificationView;

    private final Bus bus;

    public LoginPresenter(LoginView view, Bus bus) {
        this.loginView = view;
        this.bus = bus;
    }

    public LoginPresenter(ForgotPasswordView view, Bus bus) {
        this.forgotPasswordView = view;
        this.bus = bus;
    }

    public LoginPresenter(PushNotificationView view, Bus bus) {
        this.pushNotificationView = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        //bus.unregister(this);
    }

    public void onLogin(LoginRequest data) {
        bus.post(new LoginRequest(data));
        Log.e(data.getUsername(), data.getPassword());
    }

    //public void onLogin(HashMap<String, String> dicMap) {
    //   bus.post(new HashMap<String, String>(dicMap));
    //   //Log.e(data.getUsername(), data.getPassword());
    // }

    public void onGetLoginInfo(LoginInfoRequest data) {
        bus.post(new LoginInfoRequest(data));
        Log.e(data.getEmail(), data.getCountry() + data.getBsid());
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void onCheckFBLogin(LoginFacebookRequest data) {
        //bus.post(new LoginFacebookRequest(data));
        Log.e(data.getEmail(), data.getToken());
    }

    public void initialLoad(InitialLoadRequest info) {
        bus.post(new InitialLoadRequest(info));
    }

    public void onRequestUserPhoto(UserPhotoRequest data) {
        bus.post(new UserPhotoRequest(data));
    }


    public void onForgotPassword(ForgotPasswordRequest data) {
        bus.post(new ForgotPasswordRequest(data));
    }

    public void onRequestMessage(MessageRequest data) {
        bus.post(new MessageRequest(data));
    }

    @Subscribe
    public void onSuccessSendDeviceInformation(InitialLoadReceive event) {
        if (loginView != null) {
            loginView.loadingSuccess(event);
        }
    }

    @Subscribe
    public void onSuccessRequestState(StateReceive event) {
        if (loginView != null) {
            loginView.onSuccessRequestState(event);
        }
    }

    @Subscribe
    public void onRequestUserPhotoSuccess(UserPhotoReceive event) {
        if (loginView != null) {
            loginView.onRequestUserPhotoSuccess(event);
        }
    }


    @Subscribe
    public void onUserSuccessCheckFacebookLogin(LoginFacebookReceive event) {

        /*Save Session And Redirect To Homepage*/
        loginView.onCheckFBLoginSuccess(event);
    }


    @Subscribe
    public void onUserSuccessLogin(LoginReceive event) {

        if (loginView != null) {
            loginView.onLoginSuccess(event);
        }

    }


    @Subscribe
    public void onForgotPasswordReceive(ForgotPasswordReceive event) {
//        forgotPasswordView.onForgotPasswordReceive(event);
    }

    @Subscribe
    public void onMessageReceive(MessageReceive event) {
        if (loginView != null) {
            loginView.onMessageReceive(event);
        }
    }

}
