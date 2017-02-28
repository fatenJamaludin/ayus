package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.Option.ResetPasswordFragment;
import com.app.tbd.ui.Presenter.ResetPasswordPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ResetPasswordFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ResetPasswordModule {

    private final ResetPasswordPresenter.ResetPasswordView resetPasswordView;

    public ResetPasswordModule(ResetPasswordPresenter.ResetPasswordView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Provides
    @Singleton
    ResetPasswordPresenter provideResetPasswordPresenter(Bus bus) {
        return new ResetPasswordPresenter(resetPasswordView, bus);
    }
}
