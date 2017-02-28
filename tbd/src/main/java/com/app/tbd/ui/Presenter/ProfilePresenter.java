package com.app.tbd.ui.Presenter;

import android.util.Log;

import com.app.tbd.ui.Model.Receive.ContentReceive;
import com.app.tbd.ui.Model.Receive.EditProfileReceive;
import com.app.tbd.ui.Model.Receive.InitialLoadReceive;
import com.app.tbd.ui.Model.Receive.LanguageCountryReceive;
import com.app.tbd.ui.Model.Receive.StateReceive;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;

import com.app.tbd.ui.Model.Receive.TBD.BigPointReceiveFailed;
import com.app.tbd.ui.Model.Receive.TBD.LogoutReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Receive.UploadPhotoReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.ContentRequest;
import com.app.tbd.ui.Model.Request.EditProfileRequest;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Model.Request.LanguageCountryRequest;
import com.app.tbd.ui.Model.Request.StateRequest;
import com.app.tbd.ui.Model.Request.TBD.BigPointRequest;
import com.app.tbd.ui.Model.Request.TBD.LogoutRequest;

import com.app.tbd.ui.Model.Request.TransactionHistoryRequest;
import com.app.tbd.ui.Model.Request.UploadPhotoRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ProfilePresenter {

    public interface ProfileView {
        void onBigPointReceive(BigPointReceive obj);

        void onViewUserSuccess(ViewUserReceive obj);

        void onBigPointReceiveFailed(BigPointReceiveFailed obj);

        void onTransactionHistorySuccess(TransactionHistoryReceive obj);
    }

    public interface AboutView {
        void onAboutUsReceive(ContentReceive obj);
    }

    public interface MyProfileView {
        void onSuccessRequestState(StateReceive obj);

        void onUploadPhotoSuccess(UploadPhotoReceive obj);

        void onUpdateUserSuccess(EditProfileReceive obj);
    }

    public interface OptionView {
        void onLogoutReceive(LogoutReceive obj);

        void onAboutUsReceive(ContentReceive obj);

        void onPrivacyPolicyReceive(ContentReceive obj);

        void onTerms(ContentReceive obj);

        void onTermsOfUse(ContentReceive obj);

        void onSuccessRequestLanguageCountry(LanguageCountryReceive obj);

    }

    /*public interface ResetPasswordView {
        void onResetPasswordReceive(ResetPasswordReceive obj);
    }*/


    public interface BigPointView {
    }


    private ProfileView loginView;
    private OptionView optionView;
    private BigPointView bigPointView;
    private MyProfileView myProfileView;
    /*private ResetPasswordView resetPasswordView;*/

    private final Bus bus;

    public ProfilePresenter(ProfileView view, Bus bus) {
        this.loginView = view;
        this.bus = bus;
    }

    public ProfilePresenter(OptionView view, Bus bus) {
        this.optionView = view;
        this.bus = bus;
    }

    public ProfilePresenter(BigPointView view, Bus bus) {
        this.bigPointView = view;
        this.bus = bus;
    }

    /*public ProfilePresenter(ResetPasswordView view, Bus bus) {
        this.resetPasswordView = view;
        this.bus = bus;
    }*/

    public ProfilePresenter(MyProfileView view, Bus bus) {
        this.myProfileView = view;
        this.bus = bus;
    }

    public void onRequestBigPoint(BigPointRequest data) {
        bus.post(new BigPointRequest(data));
    }

    public void onRequestLogout(LogoutRequest data) {
        bus.post(new LogoutRequest(data));
    }

    /*public void onRequestResetPassword(ResetPasswordRequest data) {
        bus.post(new ResetPasswordRequest(data));
    }*/

    public void onRequestTransactionHistory(TransactionHistoryRequest data) {
        bus.post(new TransactionHistoryRequest(data));
    }

    public void onRequestUploadPhoto(UploadPhotoRequest data) {
        bus.post(new UploadPhotoRequest(data));
    }

    public void onLoadContent(ContentRequest data) {
        bus.post(new ContentRequest(data));
    }


    public void showFunction(ViewUserRequest data) {
        bus.post(new ViewUserRequest(data));
    }

    public void onStateRequest(StateRequest obj) {
        bus.post(new StateRequest(obj));
    }

    public void initialLoad(InitialLoadRequest info) {
        bus.post(new InitialLoadRequest(info));
    }

    public void updateFunction(EditProfileRequest data) {
        bus.post(new EditProfileRequest(data));
    }

    public void onCountryRequest(LanguageCountryRequest obj) {
        bus.post(new LanguageCountryRequest(obj));
    }

    @Subscribe
    public void onAboutUsReceive(ContentReceive event) {

        if (event.getWhichContent().equals("About")) {
            optionView.onAboutUsReceive(event);
        } else if (event.getWhichContent().equals("PrivacyPolicy")) {
            optionView.onPrivacyPolicyReceive(event);
        } else if (event.getWhichContent().equals("TermsConditions")) {
            optionView.onTerms(event);
        } else if (event.getWhichContent().equals("Terms")) {
            optionView.onTermsOfUse(event);
        }
    }

    @Subscribe
    public void onBigPointReceiveFailed(BigPointReceiveFailed event) {
        loginView.onBigPointReceiveFailed(event);
    }


    @Subscribe
    public void onUplodPhotoSuccess(UploadPhotoReceive event) {
        if (myProfileView != null) {
            myProfileView.onUploadPhotoSuccess(event);
        }
    }

    @Subscribe
    public void onEditProfileSuccess(EditProfileReceive event) {
        if (myProfileView != null) {
            myProfileView.onUpdateUserSuccess(event);
        }
    }

    @Subscribe
    public void onSuccessSendDeviceInformation(InitialLoadReceive event) {
        // optionView.loadingSuccess(event);
    }

    @Subscribe
    public void onTransactionHistoryReceive(TransactionHistoryReceive event) {
        loginView.onTransactionHistorySuccess(event);
    }

    @Subscribe
    public void onProfileShowSuccess(ViewUserReceive event) {
        loginView.onViewUserSuccess(event);
    }

    @Subscribe
    public void onSuccessRequestState(StateReceive event) {
        if (optionView != null) {
            //optionView.onSuccessRequestState(event);
        }
    }

    @Subscribe
    public void onSuccessRequestState2(StateReceive event) {
        if (myProfileView != null) {
            myProfileView.onSuccessRequestState(event);
        }
    }

    /*@Subscribe
    public void onResetPasswordReceive(ResetPasswordReceive event) {
        resetPasswordView.onResetPasswordReceive(event);
    }*/

    @Subscribe
    public void onLogoutReceive(LogoutReceive event) {
        optionView.onLogoutReceive(event);
    }

    @Subscribe
    public void onBigPointReceive(BigPointReceive event) {
        if (loginView != null) {
            loginView.onBigPointReceive(event);
        }
    }

    @Subscribe
    public void onSuccessRequestLanguageCountry(LanguageCountryReceive event) {
        optionView.onSuccessRequestLanguageCountry(event);
        Log.e("Subscribe", "tRUE");

    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}
