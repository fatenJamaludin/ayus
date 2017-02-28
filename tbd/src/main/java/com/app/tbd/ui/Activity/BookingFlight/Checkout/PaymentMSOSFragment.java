package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PaymentMSOSFragment extends BaseFragment {

    // Validator Attributes
    private Validator mValidator;

    @Inject
    LoginPresenter presenter;

    //@InjectView(R.id.btnSSRNext)
    //ImageView btnSSRNext;
    private int fragmentContainerId;


    public static PaymentMSOSFragment newInstance() {

        PaymentMSOSFragment fragment = new PaymentMSOSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment2, container, false);
        ButterKnife.inject(this, view);

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
