package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.SplashScreen.FirstTimeUser.SC_Fragment;
import com.app.tbd.ui.Presenter.LanguagePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SC_Fragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class LanguageModule {

    private final LanguagePresenter.LanguageView languageView;

    public LanguageModule(LanguagePresenter.LanguageView languageView) {
        this.languageView = languageView;
    }

    @Provides
    @Singleton
    LanguagePresenter provideLanguagePresenter(Bus bus) {
        return new LanguagePresenter(languageView, bus);
    }
}

