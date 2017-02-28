package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Activity.Profile.Option.OptionsFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = PassengerInfoFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class PassengerInfoModule {

    private final BookingPresenter.TravellerView travellerView;

    public PassengerInfoModule(BookingPresenter.TravellerView optionView) {
        this.travellerView = optionView;
    }

    @Provides
    @Singleton
    BookingPresenter provideProfilePresenter(Bus bus) {
        return new BookingPresenter(travellerView, bus);
    }
}
