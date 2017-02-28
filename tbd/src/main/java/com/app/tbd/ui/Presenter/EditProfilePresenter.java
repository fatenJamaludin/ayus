package com.app.tbd.ui.Presenter;

import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Request.EditProfileRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class EditProfilePresenter {

    public interface EditProfileView {
        void onUpdateUserSuccess(EditProfileReceive obj);
        void onSuccessRequestState(StateReceive obj);
    }

    private EditProfileView editProfileView;
    private final Bus bus;

    public EditProfilePresenter(EditProfileView view, Bus bus) {
        this.editProfileView = view;
        this.bus = bus;
    }

    public void updateFunction(EditProfileRequest data) {
        bus.post(new EditProfileRequest(data));
    }

    @Subscribe
    public void onEditProfileSuccess(EditProfileReceive event) {
        editProfileView.onUpdateUserSuccess(event);
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}


