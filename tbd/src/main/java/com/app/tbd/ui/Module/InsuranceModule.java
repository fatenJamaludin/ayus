package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Add.AddOnFragment;
import com.app.tbd.ui.Activity.BookingFlight.Add.InsuranceFragment;
import com.app.tbd.ui.Activity.Homepage.HomeFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = InsuranceFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class InsuranceModule {

    private final BookingPresenter.InsuranceView insuranceView;

    public InsuranceModule(BookingPresenter.InsuranceView insuranceView) {
        this.insuranceView = insuranceView;
    }

    @Provides
    @Singleton
    BookingPresenter provideHomePresenter(Bus bus) {
        return new BookingPresenter(insuranceView, bus);
    }
}
