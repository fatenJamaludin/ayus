package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SearchFlightFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SearchFlightModule {

    private final BookingPresenter.SearchFlightView resetPasswordView;

    public SearchFlightModule(BookingPresenter.SearchFlightView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Provides
    @Singleton
    BookingPresenter provideLoginPresenter(Bus bus) {
        return new BookingPresenter(resetPasswordView, bus);
    }
}
