package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.BookingFlight.SearchFlightFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Activity.TAB.TabActivity;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.BillingCountry;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddPaymentReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.PaymentRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Module.LoginModule;
import com.app.tbd.ui.Module.PaymentModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.Utils;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentFailedFragment extends BaseFragment {

    // Validator Attributes
    private static Validator mValidator;

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.txtCancelFlight)
    TextView txtCancelFlight;

    @InjectView(R.id.txtRetry)
    TextView txtRetry;


    private int fragmentContainerId;


    public static PaymentFailedFragment newInstance(Bundle bundle) {

        PaymentFailedFragment fragment = new PaymentFailedFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.payment_failed, container, false);
        ButterKnife.inject(this, view);

        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        txtCancelFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }


    public void dataSetup() {

        Bundle bundle = getArguments();

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
        //  presenter.onPause();
    }
}
