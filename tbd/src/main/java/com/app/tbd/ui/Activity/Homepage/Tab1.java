package com.app.tbd.ui.Activity.Homepage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Presenter.HomePresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import javax.inject.Inject;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class Tab1 extends BaseFragment {

    @Inject
    HomePresenter presenter;

    private int fragmentContainerId;

    private SharedPrefManager pref;
    View view;

    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new HomeModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);

        getActivity().setTitle(R.string.TBD_home);

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
        //presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.onPause();
    }


    public void exitApp() {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.home_exit))
                .setContentText(getString(R.string.home_confirm_exit))
                .showCancelButton(true)
                .setCancelText(getString(R.string.home_cancel))
                .setConfirmText(getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        getActivity().finish();
                        System.exit(0);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
