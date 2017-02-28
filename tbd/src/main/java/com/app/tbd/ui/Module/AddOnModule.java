package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Add.AddOnFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = AddOnFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class AddOnModule {

    private final BookingPresenter.AddOnView addOnView;

    public AddOnModule(BookingPresenter.AddOnView homeView) {
        this.addOnView = homeView;
    }

    @Provides
    @Singleton
    BookingPresenter provideHomePresenter(Bus bus) {
        return new BookingPresenter(addOnView, bus);
    }
}
