package com.app.tbd.ui.Module;

import com.app.tbd.AppModule;
import com.app.tbd.ui.Activity.Profile.UserProfile.EditProfileFragment;
import com.app.tbd.ui.Presenter.EditProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = EditProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class EditProfileModule {

    private final EditProfilePresenter.EditProfileView editProfileView;

    public EditProfileModule(EditProfilePresenter.EditProfileView editProfileView) {
        this.editProfileView = editProfileView;
    }

    @Provides
    @Singleton
    EditProfilePresenter provideEditProfilePresenter(Bus bus) {
        return new EditProfilePresenter(editProfileView, bus);
    }
}

