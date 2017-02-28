package com.app.tbd.ui.Activity.Profile.Option;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;

import butterknife.ButterKnife;

public class CreditCardFragment extends BaseFragment {

    private static final String SCREEN_LABEL = "CreditCard";

    public static CreditCardFragment newInstance() {

        CreditCardFragment fragment = new CreditCardFragment();
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

        View view = inflater.inflate(R.layout.credit_card, container, false);
        ButterKnife.inject(this, view);

        return    view;
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