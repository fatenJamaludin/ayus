package com.app.tbd.ui.Activity.ForgotPassword;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, ForgotPasswordFragment.newInstance()).commit();

        setTitle(getResources().getString(R.string.forgot_password_title));
        setBackButton();
        setSubmitButton2();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
