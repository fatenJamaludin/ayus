package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.ProfileFragment;
import com.app.tbd.ui.Activity.Register.RegisterFragment;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Presenter.ProfilePresenter;
import com.app.tbd.ui.Presenter.RegisterPresenter;
import com.app.tbd.ui.Presenter.TabPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = TabActivity.class,
        addsTo = AppModule.class,
        complete = false
)
public class PromotionModule {

    private final TabPresenter.PromotionView promotionView;

    public PromotionModule(TabPresenter.PromotionView promotionView) {
        this.promotionView = promotionView;
    }

    @Provides
    @Singleton
    TabPresenter provideLoginPresenter(Bus bus) {
        return new TabPresenter(promotionView, bus);
    }
}
