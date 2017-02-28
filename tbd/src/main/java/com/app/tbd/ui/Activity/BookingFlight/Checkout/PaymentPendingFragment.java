package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.SeatCached;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddPaymentReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.PaymentStatusReceive;
import com.app.tbd.ui.Model.Receive.RegisterReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.PaymentRequest;
import com.app.tbd.ui.Model.Request.PaymentStatusRequest;
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Module.LoginModule;
import com.app.tbd.ui.Module.PaymentModule;
import com.app.tbd.ui.Module.PaymentStatusModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentPendingFragment extends BaseFragment implements BookingPresenter.PaymentStatusView {


    @Inject
    BookingPresenter presenter;

    //@InjectView(R.id.txtTotalDue)
    //TextView txtTotalDue;

    private int fragmentContainerId;
    private String recordLocator;
    private String token, signature, username;
    private int recall = 0;
    private ProgressDialog progressDialog;

    public static PaymentPendingFragment newInstance(Bundle bundle) {

        PaymentPendingFragment fragment = new PaymentPendingFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new PaymentStatusModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.payment_pending, container, false);
        ButterKnife.inject(this, view);
        RealmObjectController.clearCachedResult(getActivity());

        dataSetup();

        return view;
    }

    public void dataSetup() {

        Bundle bundle = getArguments();

        recordLocator = bundle.getString("RECORD_LOCATOR");

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        token = loginReceive.getToken();
        signature = loginReceive.getSignature();
        username = loginReceive.getUserName();

        getPaymentStatus();
        initiateLoadingPayment(getActivity());

    }

    public void initiateLoadingPayment(Activity act) {

        String message_progress = getString(R.string.message_progress);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(act);
        progressDialog.show();
        progressDialog.setMessage(message_progress);
        progressDialog.setCancelable(false);

    }

    public void getPaymentStatus() {

        PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest();
        paymentStatusRequest.setToken(token);
        paymentStatusRequest.setUserName(username);
        paymentStatusRequest.setSignature(signature);
        paymentStatusRequest.setRecordLocator(recordLocator);

        presenter.onRequestPaymentStatus(paymentStatusRequest);
    }

    @Override
    public void onPaymentStatusReceive(PaymentStatusReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            if (recall < 15 && obj.getBookingStatus().equals("Pending")) {
                //recall the api
                getPaymentStatus();
                recall++;
            } else {
                dismissLoading();

                Intent intent = new Intent(getActivity(), FlightSummaryActivity.class);
                intent.putExtra("FLIGHT_SUMMARY", new Gson().toJson(obj));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            PaymentStatusReceive obj = gson.fromJson(result.get(0).getCachedResult(), PaymentStatusReceive.class);
            onPaymentStatusReceive(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
