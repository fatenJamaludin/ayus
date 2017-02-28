package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.BigPoint.BigPointBaseFragment;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = BigPointBaseFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class BigPointBaseModule {

    private final ProfilePresenter.BigPointView loginView;

    public BigPointBaseModule(ProfilePresenter.BigPointView bigPointView) {
        this.loginView = bigPointView;
    }

    @Provides
    @Singleton
    ProfilePresenter provideLoginPresenter(Bus bus) {
        return new ProfilePresenter(loginView, bus);
    }
}
