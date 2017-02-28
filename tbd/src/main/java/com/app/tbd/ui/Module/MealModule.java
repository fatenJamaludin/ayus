package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.BookingFlight.Add.MealFragment;
import com.app.tbd.ui.Activity.Login.LoginFragment;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = MealFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class MealModule {

    private final BookingPresenter.MealView loginView;

    public MealModule(BookingPresenter.MealView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    BookingPresenter provideLoginPresenter(Bus bus) {
        return new BookingPresenter(loginView, bus);
    }
}
