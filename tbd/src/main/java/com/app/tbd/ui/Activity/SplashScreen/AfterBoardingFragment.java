package com.app.tbd.ui.Activity.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.Register.RegisterActivity;
import com.app.tbd.utils.SharedPrefManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AfterBoardingFragment extends BaseFragment {

    @InjectView(R.id.before_login)
    LinearLayout before_login;

    @InjectView(R.id.before_signup)
    LinearLayout before_signup;

    private SharedPrefManager pref;

    public static AfterBoardingFragment newInstance() {

        AfterBoardingFragment fragment = new AfterBoardingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setDefaultFont(getActivity(), "GRUNDSCHRIFT", "fonts/grundschrift_regular.otf");
        pref = new SharedPrefManager(getActivity());
        pref.clearLoginStatus();
        pref.setLoginStatus("N");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.before_login, container, false);
        ButterKnife.inject(this, view);
        /*AnalyticsApplication.sendScreenView("MainPage loaded");*/

        before_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signup = new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(signup);
                // getActivity().finish();
            }
        });

        before_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(login);
                // getActivity().finish();
            }
        });

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
