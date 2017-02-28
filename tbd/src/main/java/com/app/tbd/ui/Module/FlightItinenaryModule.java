package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.FlightItinenaryFragment;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = FlightItinenaryFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class FlightItinenaryModule {

    private final BookingPresenter.ProfileView profileView;

    public FlightItinenaryModule(BookingPresenter.ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides
    @Singleton
    BookingPresenter provideHomePresenter(Bus bus) {
        return new BookingPresenter(profileView, bus);
    }
}
