package com.app.tbd.ui.Presenter;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.ui.Model.Receive.AppVersionReceive;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.OverlayReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.SplashFailedConnect;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.AppVersionRequest;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.OverlayRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.PushNotificationObj;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HomePresenter {

    private SharedPrefManager pref;

    public interface PushNotification {

    }

    public interface HomeView {
    }

    public interface SplashScreen {
        void onConnectionFailed();

        void onPromotionReceive(PromotionReceive obj);

        void onAppVersionReceive(AppVersionReceive obj);

        void loadingSuccess(InitialLoadReceive obj);

        void onSuccessRequestState(StateReceive obj);

        void onOverlayReceive(OverlayReceive obj);
    }

    private HomeView view;
    private SplashScreen view2;
    private PushNotification view3;


    private final Bus bus;

    public HomePresenter(HomeView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public HomePresenter(SplashScreen view, Bus bus) {
        this.view2 = view;
        this.bus = bus;
    }

    public HomePresenter(PushNotification view, Bus bus) {
        this.view3 = view;
        this.bus = bus;
    }


    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onRegisterNotification(PushNotificationObj info) {
        bus.post(new PushNotificationObj(info));
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }


    public void checkVersion(AppVersionRequest info) {
        bus.post(new AppVersionRequest(info));
    }

    public void onPromotionRequest(PromotionRequest info) {
        bus.post(new PromotionRequest(info));
    }

    public void initialLoad(InitialLoadRequest info) {
        bus.post(new InitialLoadRequest(info));
    }

    public void onOverlayImage(OverlayRequest info) {
        bus.post(new OverlayRequest(info));
    }

    @Subscribe
    public void onOverlayReceive(OverlayReceive event) {
        view2.onOverlayReceive(event);
    }

    @Subscribe
    public void onSuccessSendDeviceInformation(InitialLoadReceive event) {
        pref = new SharedPrefManager(MainFragmentActivity.getContext());
        if (view2 != null) {
            view2.loadingSuccess(event);
        }
    }

    @Subscribe
    public void onSuccessRequestState(StateReceive event) {
        if (view2 != null) {
            view2.onSuccessRequestState(event);
        }
    }

    @Subscribe
    public void onAppVersionReceive(AppVersionReceive event) {
        if (view2 != null) {
            view2.onAppVersionReceive(event);
        }
    }

    @Subscribe
    public void onFailedConnect(SplashFailedConnect event) {
        view2.onConnectionFailed();
    }

    @Subscribe
    public void onPromotionReceive(PromotionReceive event) {
        if (view2 != null) {
            view2.onPromotionReceive(event);
        }
    }
}
