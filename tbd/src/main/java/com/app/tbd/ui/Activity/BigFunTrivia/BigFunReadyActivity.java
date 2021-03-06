package com.app.tbd.ui.Activity.BigFunTrivia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import butterknife.ButterKnife;

public class BigFunReadyActivity extends MainFragmentActivity implements FragmentContainerActivity {


    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, BigFunReadyFragment.newInstance()).commit();

        setBackButton();
        setTitle(R.string.bigfun_setting_title);

        // [END shared_tracker]

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
