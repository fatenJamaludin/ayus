package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.ui.Model.Receive.MessageReceive;
import com.app.tbd.ui.Model.Receive.MessageStatusReceive;
import com.app.tbd.ui.Model.Request.MessageRequest;
import com.app.tbd.ui.Model.Request.MessageStatusRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class PushNotificationPresenter {

    public interface PushNotificationView {
        void onMessageStatusReceive(MessageStatusReceive obj);
    }

    private PushNotificationView pushNotificationView;

    private final Bus bus;

    public PushNotificationPresenter(PushNotificationView view, Bus bus) {
        this.pushNotificationView = view;
        this.bus = bus;
    }


    public void onRequestMessageStatus(MessageStatusRequest data) {
        bus.post(new MessageStatusRequest(data));
    }

    @Subscribe
    public void onMessageStatusReceive(MessageStatusReceive event) {
        if (pushNotificationView != null) {
            pushNotificationView.onMessageStatusReceive(event);
        }
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}

