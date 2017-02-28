package com.app.tbd.ui.Activity.SplashScreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Model.Request.InitialLoadRequest;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.utils.SharedPrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForceUpdateFragment extends BaseFragment {

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.btnUpdate)
    Button btnUpdate;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private InitialLoadRequest info;
    private Boolean running = false;
    private static SweetAlertDialog pDialog;

    public static ForceUpdateFragment newInstance() {

        ForceUpdateFragment fragment = new ForceUpdateFragment();
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

        View view = inflater.inflate(R.layout.force_update_screen, container, false);
        ButterKnife.inject(this, view);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.airasiabig.redemption" +
                        "" +
                        ""));
                getActivity().startActivity(intent);
                System.exit(0);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
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
