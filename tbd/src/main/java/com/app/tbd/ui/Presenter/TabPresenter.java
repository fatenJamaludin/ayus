package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Model.Receive.AppVersionReceive;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.PromotionReceive;
import com.app.tbd.ui.Model.Receive.SplashFailedConnect;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.AppVersionRequest;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.PromotionRequest;
import com.app.tbd.ui.Model.Request.PushNotificationObj;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class TabPresenter {

    private SharedPrefManager pref;


    public interface PromotionView {
        void onPromotionReceive(PromotionReceive obj);
    }

    private PromotionView promotionView;
    private final Bus bus;

    public TabPresenter(PromotionView view, Bus bus) {
        this.promotionView = view;
        this.bus = bus;
    }

    public void onPromotionRequest(PromotionRequest info) {
        bus.post(new PromotionRequest(info));
    }

    @Subscribe
    public void onPromotionReceive(PromotionReceive event) {
        /*if (promotionView != null) {
            Log.e("X","X");
            promotionView.onPromotionReceive(event);
        }*/
        //HomeFragment.reloadPromotion();
        Log.e("y", "y");

    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}
