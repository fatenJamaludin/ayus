package com.app.tbd.ui.Activity.BigFunTrivia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class BigFunReadyFragment extends BaseFragment {

    SharedPrefManager pref;

    @InjectView(R.id.graph5)
    LinearLayout graph5;

    @InjectView(R.id.bigFunBtnPlay)
    Button bigFunBtnPlay;

    public static BigFunReadyFragment newInstance() {

        BigFunReadyFragment fragment = new BigFunReadyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bigfun_ready, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        bigFunBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comic_btnClicked(getActivity());
                Intent flightDetail = new Intent(getActivity(), BigFunPendingActivity.class);
                getActivity().startActivity(flightDetail);
            }
        });


        //graph5.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (int) (30 / 100 * 100)));
        graph5.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 80));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
