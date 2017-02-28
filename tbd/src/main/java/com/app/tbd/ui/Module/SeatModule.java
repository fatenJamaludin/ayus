package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Add.SeatTabFragment;
import com.app.tbd.ui.Activity.BookingFlight.FlightListFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SeatTabFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SeatModule {

    private final BookingPresenter.SeatView seatView;

    public SeatModule(BookingPresenter.SeatView seatView) {
        this.seatView = seatView;
    }

    @Provides
    @Singleton
    BookingPresenter provideLoginPresenter(Bus bus) {
        return new BookingPresenter(seatView, bus);
    }
}
