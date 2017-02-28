package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.LanguageReceive;
import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.OnBoardingReceive;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LanguageCountryRequest;
import com.app.tbd.ui.Model.Request.LanguageRequest;
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.OnBoardingRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class LanguagePresenter {

    public interface LanguageView {
        void onSuccessRequestLanguage(LanguageReceive obj);

        void onSuccessRequestLanguageCountry(LanguageCountryReceive obj);

        void loadingSuccess(InitialLoadReceive obj);

        void onSuccessRequestState(StateReceive obj);

        void onBoardingReceive(OnBoardingReceive obj);

        void onPromotionReceive(PromotionReceive obj);

        void onOverlayReceive(OverlayReceive obj);

        void onMessageReceive(MessageReceive obj);
    }


    private final Bus bus;
    private final LanguageView view;

    public LanguagePresenter(LanguageView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onLanguageRequest(LanguageRequest obj) {
        bus.post(new LanguageRequest(obj));
    }

    public void onCountryRequest(LanguageCountryRequest obj) {
        bus.post(new LanguageCountryRequest(obj));
    }

    public void onRequestMessage(MessageRequest data) {
        bus.post(new MessageRequest(data));
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void initialLoad(InitialLoadRequest info) {
        bus.post(new InitialLoadRequest(info));
    }

    public void onBoardingRequest(OnBoardingRequest obj) {
        bus.post(new OnBoardingRequest(obj));
    }

    public void onPromotionRequest(PromotionRequest info) {
        bus.post(new PromotionRequest(info));
    }

    public void onOverlayImage(OverlayRequest info) {
        bus.post(new OverlayRequest(info));
    }

    @Subscribe
    public void onOverlayReceive(OverlayReceive event) {
        view.onOverlayReceive(event);
    }

    @Subscribe
    public void onPromotionReceive(PromotionReceive event) {
        view.onPromotionReceive(event);
    }

    @Subscribe
    public void onSuccessSendDeviceInformation(InitialLoadReceive event) {
        view.loadingSuccess(event);
    }

    @Subscribe
    public void onBoardingReceive(OnBoardingReceive event) {
        view.onBoardingReceive(event);
    }

    @Subscribe
    public void onSuccessRequestLanguage(LanguageReceive event) {
        view.onSuccessRequestLanguage(event);
    }

    @Subscribe
    public void onSuccessRequestState(StateReceive event) {

        view.onSuccessRequestState(event);
    }

    @Subscribe
    public void onSuccessRequestLanguageCountry(LanguageCountryReceive event) {
        view.onSuccessRequestLanguageCountry(event);
        Log.e("Subscribe", "tRUE");

    }

    @Subscribe
    public void onMessageReceive(MessageReceive event) {
        if (view != null) {
            view.onMessageReceive(event);
        }
    }
}
