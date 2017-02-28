package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class AddOnActivity extends MainFragmentActivity implements FragmentContainerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, AddOnFragment.newInstance(bundle)).commit();

        setBackButton();
        setCheckOutButton();

        setTitle(R.string.header_addons);
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
