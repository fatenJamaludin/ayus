package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Login.LoginFragment;
import com.app.tbd.ui.Activity.Profile.UserProfile.MyProfileFragment;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = MyProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class MyProfileModule {

    private final ProfilePresenter.MyProfileView loginView;

    public MyProfileModule(ProfilePresenter.MyProfileView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    ProfilePresenter provideLoginPresenter(Bus bus) {
        return new ProfilePresenter(loginView, bus);
    }
}
