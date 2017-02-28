package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = FlightListFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SelectFlightModule {

    private final BookingPresenter.ListFlightView resetPasswordView;

    public SelectFlightModule(BookingPresenter.ListFlightView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Provides
    @Singleton
    BookingPresenter provideLoginPresenter(Bus bus) {
        return new BookingPresenter(resetPasswordView, bus);
    }
}
