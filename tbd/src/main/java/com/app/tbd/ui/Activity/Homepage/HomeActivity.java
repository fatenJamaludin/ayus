package com.app.tbd.ui.Activity.Homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Profile.ProfileFragment;

import butterknife.ButterKnife;


public class HomeActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, HomeFragment.newInstance(), "Home").commit();

        setTitle(R.string.TBD_home);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        final FragmentManager manager = getSupportFragmentManager();
        HomeFragment fragment = (HomeFragment) manager.findFragmentByTag("Home");
        fragment.exitApp();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
