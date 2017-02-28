package com.app.tbd.ui.Activity.SplashScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class Pop2NotificationActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        //BaseFragment.removeLogoHeader(this);
        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.splash_content, PopupNotificationFragment.newInstance(bundle)).commit();

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
