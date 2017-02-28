package com.app.tbd.ui.Presenter;

import com.app.tbd.ui.Model.Receive.ForgotPasswordReceive;
import com.app.tbd.ui.Model.Request.ForgotPasswordRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ForgotPasswordPresenter {
    public interface ForgotPasswordView {
        void onForgotPasswordReceive(ForgotPasswordReceive obj);
    }

    private ForgotPasswordView forgotPasswordView;
    private final Bus bus;

    public ForgotPasswordPresenter(ForgotPasswordView view, Bus bus) {
        this.forgotPasswordView = view;
        this.bus = bus;
    }

    public void onRequestForgotPassword(ForgotPasswordRequest data) {
        bus.post(new ForgotPasswordRequest(data));
    }

    @Subscribe
    public void onForgotPasswordReceive(ForgotPasswordReceive event) {
        forgotPasswordView.onForgotPasswordReceive(event);
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }
}
