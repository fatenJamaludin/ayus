package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragmentV2;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = FlightListFragmentV2.class,
        addsTo = AppModule.class,
        complete = false
)
public class SelectFlightModuleV2 {

    private final BookingPresenter.ListFlightView resetPasswordView;

    public SelectFlightModuleV2(BookingPresenter.ListFlightView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Provides
    @Singleton
    BookingPresenter provideLoginPresenter(Bus bus) {
        return new BookingPresenter(resetPasswordView, bus);
    }
}
