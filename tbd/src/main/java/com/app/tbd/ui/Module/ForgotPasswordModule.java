package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.ForgotPassword.ForgotPasswordFragment;
import com.app.tbd.ui.Presenter.ForgotPasswordPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ForgotPasswordFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ForgotPasswordModule {

    private final ForgotPasswordPresenter.ForgotPasswordView forgotPasswordView;

    public ForgotPasswordModule(ForgotPasswordPresenter.ForgotPasswordView forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
    }

    @Provides
    @Singleton
    ForgotPasswordPresenter provideForgotPasswordPresenter(Bus bus) {
        return new ForgotPasswordPresenter(forgotPasswordView, bus);
    }
}
