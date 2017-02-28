package com.app.tbd.ui.Presenter;

import com.app.tbd.ui.Model.Receive.ResetPasswordReceive;
import com.app.tbd.ui.Model.Request.ResetPasswordRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ResetPasswordPresenter {
    public interface ResetPasswordView {
        void onResetPasswordReceive(ResetPasswordReceive obj);
    }

    private ResetPasswordView resetPasswordView;
    private final Bus bus;

    public ResetPasswordPresenter(ResetPasswordView view, Bus bus) {
        this.resetPasswordView = view;
        this.bus = bus;
    }

    public void onRequestResetPassword(ResetPasswordRequest data) {
        bus.post(new ResetPasswordRequest(data));
    }

    @Subscribe
    public void onResetPasswordReceive(ResetPasswordReceive event) {
        resetPasswordView.onResetPasswordReceive(event);
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }
}
