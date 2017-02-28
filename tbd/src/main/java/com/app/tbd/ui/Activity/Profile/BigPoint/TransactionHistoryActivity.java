package com.app.tbd.ui.Activity.Profile.BigPoint;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class TransactionHistoryActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //@InjectView(R.id.btnLogin) Button btnLogin;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, TransactionHistoryFragment.newInstance(bundle)).commit();

        setTitle(getResources().getString(R.string.transaction_history));
        setBackButton();

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
