package com.app.tbd.ui.Activity.Profile.Option;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.Receive.ResetPasswordReceive;
import com.app.tbd.ui.Model.Request.ResetPasswordRequest;
import com.app.tbd.ui.Module.ResetPasswordModule;
import com.app.tbd.ui.Presenter.ResetPasswordPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ResetPasswordFragment extends BaseFragment implements ResetPasswordPresenter.ResetPasswordView, Validator.ValidationListener {

    static Validator mValidator;

    @Inject
    ResetPasswordPresenter presenter;

    @NotEmpty(sequence = 1, messageResId = R.string.email_empty)
    @Email(sequence = 2, messageResId = R.string.email_invalid)
    @Order(1)
    @InjectView(R.id.updatePwUsername)
    EditText updatePwUsername;

    @NotEmpty(sequence = 1, messageResId = R.string.password_empty)
    /*@Password(sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE, messageResId = R.string.password_invalid)*/
    @Length(sequence = 3, min = 8, max = 15, messageResId = R.string.password_rule)
    @Order(2)
    @InjectView(R.id.updatePwPassword)
    EditText updatePwPassword;

    @NotEmpty(sequence = 1, messageResId = R.string.password_empty)
    /*@Password(sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE, messageResId = R.string.password_invalid)*/
    @Length(sequence = 3, min = 8, max = 15, messageResId = R.string.password_rule)
    @Order(3)
    @InjectView(R.id.updatePwNewPassword)
    EditText updatePwNewPassword;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Login";
    private SharedPrefManager pref;

    public static ResetPasswordFragment newInstance() {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new ResetPasswordModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update_password, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        HashMap<String, String> initUserName = pref.getUsername();
        String userName = initUserName.get(SharedPrefManager.USERNAME);

        updatePwUsername.setText(userName);

        return view;
    }

    public static void submitResetPassword() {

        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        hideKeyboard();
        requestResetPassword(updatePwUsername.getText().toString(), updatePwPassword.getText().toString(), updatePwNewPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            setShake(view);

            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            }
        }
    }

    public void requestResetPassword(String username, String password, String newPassword) {

        HashMap<String, String> initToken = pref.getToken();
        String token = initToken.get(SharedPrefManager.TOKEN);

        initiateLoading(getActivity());
        ResetPasswordRequest data = new ResetPasswordRequest();
        data.setToken(token);
        data.setUserName(username);
        data.setOldPassword(password);
        data.setNewPassword(newPassword);

        presenter.onRequestResetPassword(data);
    }

    @Override
    public void onResetPasswordReceive(ResetPasswordReceive obj) {

        String success = getString(R.string.general_success);
        String message = getString(R.string.update_password_success_send);
        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            setSuccessDialog(getActivity(), message, TabActivity.class, success);
        }
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