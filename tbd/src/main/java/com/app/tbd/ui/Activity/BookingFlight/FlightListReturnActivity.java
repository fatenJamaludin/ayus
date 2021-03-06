package com.app.tbd.ui.Activity.BookingFlight;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class FlightListReturnActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, FlightListFragmentV2.newInstance(bundle)).commit();

        setBackButton();
        setTitle(R.string.header_return);
        setContinueButtonV2();

    }

    @Override
    public void onResume() {
        super.onResume();
        //mTracker.setcreenName("Flight Details" + "A");
        //mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setContinueButtonFromFragment() {
        setContinueButton();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
