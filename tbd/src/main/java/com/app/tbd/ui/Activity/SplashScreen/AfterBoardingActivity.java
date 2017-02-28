package com.app.tbd.ui.Activity.SplashScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class AfterBoardingActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //@InjectView(R.id.btnLogin) Button btnLogin;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        //setTitle(getResources().getString(R.string.));
        //BaseFragment.removeLogoHeader(this);
        //setBackButton();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, AfterBoardingFragment.newInstance()).commit();
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