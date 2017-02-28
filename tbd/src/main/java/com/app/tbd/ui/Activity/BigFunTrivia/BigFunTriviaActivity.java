package com.app.tbd.ui.Activity.BigFunTrivia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class BigFunTriviaActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //@InjectView(R.id.btnLogin) Button btnLogin;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, BigFunTriviaFragment.newInstance()).commit();

        // [END shared_tracker]

    }

    /*@Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
