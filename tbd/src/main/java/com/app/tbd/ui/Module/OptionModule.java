package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.Option.OptionsFragment;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = OptionsFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class OptionModule {

    private final ProfilePresenter.OptionView optionView;

    public OptionModule(ProfilePresenter.OptionView optionView) {
        this.optionView = optionView;
    }

    @Provides
    @Singleton
    ProfilePresenter provideProfilePresenter(Bus bus) {
        return new ProfilePresenter(optionView, bus);
    }
}
