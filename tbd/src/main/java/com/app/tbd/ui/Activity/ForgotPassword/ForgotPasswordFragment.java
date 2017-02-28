package com.app.tbd.ui.Activity.ForgotPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.tbd.application.MainApplication;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Model.Receive.ForgotPasswordReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Module.ForgotPasswordModule;
import com.app.tbd.ui.Model.Request.ForgotPasswordRequest;
import com.app.tbd.ui.Presenter.ForgotPasswordPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordPresenter.ForgotPasswordView, Validator.ValidationListener {

    @Inject
    ForgotPasswordPresenter presenter;

    @NotEmpty(sequence = 1, messageResId =  R.string.email_empty)
    @Email(sequence = 2, messageResId =  R.string.email_invalid)
    @InjectView(R.id.txtResetThisEmail)
    EditText txtResetThisEmail;

    static Validator mValidator;

    private int fragmentContainerId;

    public static ForgotPasswordFragment newInstance() {

        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new ForgotPasswordModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgot_password_screen, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    public static void submitForgotPassword() {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        requestForgotPassword(txtResetThisEmail.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {

            View view = error.getView();
            setShake(view);

            /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            }
        }
    }

    public void requestForgotPassword(String username) {

        initiateLoading(getActivity());
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setUserName(username);

        presenter.onRequestForgotPassword(forgotPasswordRequest);
    }

    @Override
    public void onForgotPasswordReceive(ForgotPasswordReceive obj) {

        dismissLoading();
        String success_text = getString(R.string.forgot_success);
        String body_text = getString(R.string.reset_password_success_send);
        setSuccessDialog(getActivity(), body_text, LoginActivity.class, success_text);


        /*Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            RealmObjectController.clearCachedResult(getActivity());
            setSuccessDialog(getActivity(), getResources().getString(R.string.reset_password_success_send), LoginActivity.class, "Success!");
        }*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
