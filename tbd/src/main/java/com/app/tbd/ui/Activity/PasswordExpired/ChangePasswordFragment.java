package com.app.tbd.ui.Activity.PasswordExpired;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.tbd.application.AnalyticsApplication;
import com.google.gson.Gson;
import com.app.tbd.MainController;
import com.app.tbd.application.MainApplication;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Model.Receive.ChangePasswordReceive;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Homepage.HomeActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Module.ChangePasswordModule;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Model.Request.ChangePasswordRequest;
import com.app.tbd.ui.Presenter.ChangePasswordPresenter;
import com.app.tbd.utils.AESCBC;
import com.app.tbd.utils.App;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordPresenter.ChangePasswordView, Validator.ValidationListener {


    private Validator mValidator;
    private AlertDialog dialog;
    private SharedPrefManager pref;
    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Login: Password Expired";
    private View view;

    @Inject
    ChangePasswordPresenter presenter;

    //@InjectView(R.id.search_edit_text) EditText searchEditText;
    @Order(1)
    @NotEmpty(sequence = 1)
    @Email(sequence = 2)
    @InjectView(R.id.editTextemail)
    EditText editTextemail;

    @Order(2)
    @NotEmpty(sequence = 1)
    @InjectView(R.id.editTextforgotPasswordCurrent)
    EditText editTextPasswordCurrent;

    @Order(3)
    @NotEmpty(sequence = 1)
    @ConfirmPassword(messageResId = R.string.password_mismatch)
    @InjectView(R.id.editTextforgotPasswordConfirm)
    EditText editTextPasswordConfirm;

    @Order(4)
    @NotEmpty(sequence = 1)
    @Length(sequence = 2, min = 6, max = 16, messageResId = R.string.password_length)
    @Password(sequence = 3)// Password validator
    @InjectView(R.id.editTextforgotPasswordNew)
    EditText editTextPasswordNew;

    @InjectView(R.id.txtPasswordHint)
    TextView txtPasswordHint;

    @InjectView(R.id.btnchangepassword)
    Button changepasswordButton;

    public static ChangePasswordFragment newInstance() {

        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new ChangePasswordModule(this)).inject(this);
        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_password_expired, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        //password
        editTextPasswordCurrent.setTransformationMethod(new PasswordTransformationMethod());
        editTextPasswordConfirm.setTransformationMethod(new PasswordTransformationMethod());
        editTextPasswordNew.setTransformationMethod(new PasswordTransformationMethod());

        HashMap<String, String> userinfo = pref.getUserEmail();
        String email = userinfo.get(SharedPrefManager.USER_EMAIL);

        editTextemail.setFocusable(false);
        editTextemail.setFocusableInTouchMode(false);
        editTextemail.setClickable(false);
        editTextemail.setText(email);

        changepasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
            }
        });

           /*Display Password Hint*/
        txtPasswordHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNormalDialog(getActivity(), getActivity().getResources().getString(R.string.register_password_hint), getActivity().getResources().getString(R.string.register_password_policy));
            }
        });

        return view;
    }


    public void requestChangePassword(String username, String password, String new_password) {
        initiateLoading(getActivity());
        ChangePasswordRequest data = new ChangePasswordRequest();
        data.setEmail(username);
        data.setNewPassword(new_password);
        data.setCurrentPassword(password);
        presenter.changePassword(data);
    }


    public void goLoginPage() {

        Intent loginPage = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(loginPage);
        getActivity().finish();
    }


    @Override
    public void onUpdatePasswordSuccess(ChangePasswordReceive obj) {
        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            String changed_message = getString(R.string.password_changed);
            setSuccessDialog(getActivity(), obj.getMessage(), HomeActivity.class, changed_message);
        }

    }


    //Validator Result//
    @Override
    public void onValidationSucceeded() {

        String error_message = getString(R.string.error_message);
        //check if new password same with confirm password
        if (!isValidatePassword(editTextPasswordNew.getText().toString())) {
            //Crouton.makeText(getActivity(), "Password must contain uppercase char,number and symbols", Style.ALERT).show();
            //EditText txtCurrentPassword = (EditText) view.findViewById(R.id.editTextforgotPasswordNew);
            //txtCurrentPassword.setError(null);
            editTextPasswordNew.setError(error_message);
        } else {
            requestChangePassword(editTextemail.getText().toString(), AESCBC.encrypt(App.KEY, App.IV, editTextPasswordCurrent.getText().toString()), AESCBC.encrypt(App.KEY, App.IV, editTextPasswordNew.getText().toString()));
        }
    }


    private boolean isValidatePassword(String password) {

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
        //Agent-17c
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

        //Crouton.makeText(getActivity(), message, Style.ALERT).show();

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

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            ChangePasswordReceive obj = gson.fromJson(result.get(0).getCachedResult(), ChangePasswordReceive.class);
            onUpdatePasswordSuccess(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
