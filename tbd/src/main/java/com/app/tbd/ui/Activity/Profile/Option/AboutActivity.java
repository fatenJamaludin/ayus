package com.app.tbd.ui.Activity.Profile.Option;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class AboutActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //@InjectView(R.id.btnLogin) Button btnLogin;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        setTitle(getResources().getString(R.string.options_about));
        setBackButton();

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, AboutFragment.newInstance(bundle)).commit();

    }


    @Override
    public void onResume() {
        super.onResume();
        // presenter.onResume();

    }


    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}