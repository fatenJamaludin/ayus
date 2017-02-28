package com.app.tbd.ui.Activity.TAB;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab4 extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab4, container, false);
        return v;
    }
}