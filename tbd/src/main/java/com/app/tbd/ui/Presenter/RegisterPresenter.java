package com.app.tbd.ui.Presenter;

import com.app.tbd.ui.Model.Receive.NewsletterLanguageReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.NewsletterLanguageRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class RegisterPresenter {

    public interface RegisterView {

        void onSuccessRegister(RegisterReceive obj);
        void onSuccessRequestState(StateReceive obj);
        void onSuccessRequestLanguage(NewsletterLanguageReceive obj);

    }

    private final RegisterView view;
    private final Bus bus;

    public RegisterPresenter(RegisterView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onRequestRegister(RegisterRequest obj) {
        bus.post(new RegisterRequest(obj));
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void onRetrieveLanguage(NewsletterLanguageRequest obj) {
        bus.post(new NewsletterLanguageRequest(obj));
    }


    @Subscribe
    public void onUserSuccessRegister(RegisterReceive event) {
        view.onSuccessRegister(event.getUserObj());
    }

    @Subscribe
    public void onSuccessRequestState(StateReceive event) {

        view.onSuccessRequestState(event);
    }

    @Subscribe
    public void onSuccessRequestNewsletterLanguage(NewsletterLanguageReceive event) {
        view.onSuccessRequestLanguage(event);
    }

}
