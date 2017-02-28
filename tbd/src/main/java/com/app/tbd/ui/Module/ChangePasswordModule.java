package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.PasswordExpired.ChangePasswordFragment;
import com.app.tbd.ui.Presenter.ChangePasswordPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ChangePasswordFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ChangePasswordModule {

    private final ChangePasswordPresenter.ChangePasswordView ChangePasswordview;

    public ChangePasswordModule(ChangePasswordPresenter.ChangePasswordView ChangePasswordview) {
        this.ChangePasswordview = ChangePasswordview;
    }

    @Provides
    @Singleton
    ChangePasswordPresenter provideChangePasswordPresenter(Bus bus) {
        return new ChangePasswordPresenter(ChangePasswordview, bus);
    }
}
