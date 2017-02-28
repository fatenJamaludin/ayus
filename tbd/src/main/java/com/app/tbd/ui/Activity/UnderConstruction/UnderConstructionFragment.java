package com.app.tbd.ui.Activity.UnderConstruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;

import butterknife.ButterKnife;

public class UnderConstructionFragment extends BaseFragment {

    public static UnderConstructionFragment newInstance() {

        UnderConstructionFragment fragment = new UnderConstructionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab4, container, false);
        ButterKnife.inject(this, view);

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
