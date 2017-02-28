package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Add.BaggageFragmentV2;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = BaggageFragmentV2.class,
        addsTo = AppModule.class,
        complete = false
)
public class BaggageModule {

    private final BookingPresenter.BaggageView baggageView;

    public BaggageModule(BookingPresenter.BaggageView homeView) {
        this.baggageView = homeView;
    }

    @Provides
    @Singleton
    BookingPresenter provideHomePresenter(Bus bus) {
        return new BookingPresenter(baggageView, bus);
    }
}
