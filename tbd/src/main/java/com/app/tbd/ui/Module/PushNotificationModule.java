package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationFragment;
import com.app.tbd.ui.Presenter.PushNotificationPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = PushNotificationFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class PushNotificationModule {

    private final PushNotificationPresenter.PushNotificationView pushNotificationView;

    public PushNotificationModule(PushNotificationPresenter.PushNotificationView pushNotificationView) {
        this.pushNotificationView = pushNotificationView;
    }

    @Provides
    @Singleton
    PushNotificationPresenter providePushNotificationPresenter(Bus bus) {
        return new PushNotificationPresenter(pushNotificationView, bus);
    }
}
