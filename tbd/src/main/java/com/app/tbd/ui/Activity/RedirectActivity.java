package com.app.tbd.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.PromoTransaction;
import com.app.tbd.ui.Model.JSON.SelectedFlightInfo;
import com.google.gson.Gson;


import butterknife.ButterKnife;

//import android.view.WindowManager;

public class RedirectActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        String promoTransactionString = bundle.getString("REDIRECT");

        Gson gson = new Gson();

        PromoTransaction promoTransaction = gson.fromJson(promoTransactionString, PromoTransaction.class);

        TabActivity.setPager(2);
        SearchFlightFragment.triggerPromoInfo(this, promoTransaction);
        this.finish();



    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
