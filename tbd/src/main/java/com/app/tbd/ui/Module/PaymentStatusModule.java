package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.PaymentFragment;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.PaymentPendingFragment;
import com.app.tbd.ui.Activity.Profile.Option.OptionsFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = PaymentPendingFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class PaymentStatusModule {

    private final BookingPresenter.PaymentStatusView paymentStatusView;

    public PaymentStatusModule(BookingPresenter.PaymentStatusView paymentStatusView) {
        this.paymentStatusView = paymentStatusView;
    }

    @Provides
    @Singleton
    BookingPresenter provideProfilePresenter(Bus bus) {
        return new BookingPresenter(paymentStatusView, bus);
    }
}
