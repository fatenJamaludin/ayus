package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = HomeFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class HomeModule {

    private final HomePresenter.HomeView homeView;

    public HomeModule(HomePresenter.HomeView homeView) {
        this.homeView = homeView;
    }

    @Provides
    @Singleton
    HomePresenter provideHomePresenter(Bus bus) {
        return new HomePresenter(homeView, bus);
    }
}
