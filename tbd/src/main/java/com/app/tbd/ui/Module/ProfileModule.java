package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragment;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.app.tbd.ui.Presenter.RegisterPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ProfileModule {

    private final ProfilePresenter.ProfileView registerView;

    public ProfileModule(ProfilePresenter.ProfileView registerView) {
        this.registerView = registerView;
    }

    @Provides
    @Singleton
    ProfilePresenter provideLoginPresenter(Bus bus) {
        return new ProfilePresenter(registerView, bus);
    }
}
