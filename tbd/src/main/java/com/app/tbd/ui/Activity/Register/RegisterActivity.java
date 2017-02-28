package com.app.tbd.ui.Activity.Register;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;


public class RegisterActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);


        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, RegisterFragment.newInstance(bundle), "xx").commit();

        setBackButton();
        setTitle(getResources().getString(R.string.register_title));

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }

}
