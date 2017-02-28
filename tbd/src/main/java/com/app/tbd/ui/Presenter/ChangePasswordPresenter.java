package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.ui.Model.Receive.ChangePasswordReceive;
import com.app.tbd.ui.Model.Request.ChangePasswordRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ChangePasswordPresenter {

    public interface ChangePasswordView {
        void onUpdatePasswordSuccess(ChangePasswordReceive event);
    }

    private final ChangePasswordView view;
    private final Bus bus;

    public ChangePasswordPresenter(ChangePasswordView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }


    public void changePassword(ChangePasswordRequest data) {
        Log.e("xxxx",data.getEmail());
        Log.e("xxxx",data.getCurrentPassword());
        Log.e("xxxx",data.getNewPassword());
        bus.post(new ChangePasswordRequest(data));
    }



   // @Subscribe
    //public void onUserFailedReqPassword(SplashFailedConnect event) {
//
    @Subscribe
    public void onUserSuccessReqPassword(ChangePasswordReceive event) {

        //*Save Session And Redirect To Homepage*//*
        view.onUpdatePasswordSuccess(event.getUserObj());
    }

}
