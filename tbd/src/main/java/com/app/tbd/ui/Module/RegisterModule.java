package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Register.RegisterFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragmentPending;
import com.app.tbd.ui.Presenter.RegisterPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = RegisterFragmentPending.class,
        addsTo = AppModule.class,
        complete = false
)
public class RegisterModule {

    private final RegisterPresenter.RegisterView registerView;

    public RegisterModule(RegisterPresenter.RegisterView registerView) {
        this.registerView = registerView;
    }

    @Provides
    @Singleton
    RegisterPresenter provideLoginPresenter(Bus bus) {
        return new RegisterPresenter(registerView, bus);
    }
}
