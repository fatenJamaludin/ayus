package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Login.LoginFragment;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = LoginFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class LoginModule {

    private final LoginPresenter.LoginView loginView;

    public LoginModule(LoginPresenter.LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    LoginPresenter provideLoginPresenter(Bus bus) {
        return new LoginPresenter(loginView, bus);
    }
}
