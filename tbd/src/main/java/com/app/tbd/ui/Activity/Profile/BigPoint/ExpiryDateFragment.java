package com.app.tbd.ui.Activity.Profile.BigPoint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.PushNotificationInbox.PushNotificationActivity;
import com.app.tbd.ui.Model.Receive.TBD.BigPointReceive;
import com.app.tbd.ui.Model.Receive.TransactionHistoryReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExpiryDateFragment extends BaseFragment {

    //@Inject
    //ProfilePresenter presenter;

    @InjectView(R.id.txtExpiryDate1)
    TextView txtExpiryDate1;

    @InjectView(R.id.txtExpiryDate2)
    TextView txtExpiryDate2;

    @InjectView(R.id.txtExpiryDate3)
    TextView txtExpiryDate3;

    @InjectView(R.id.txtExpiryDate4)
    TextView txtExpiryDate4;

    @InjectView(R.id.txtExpiryDate5)
    TextView txtExpiryDate5;

    @InjectView(R.id.txtExpiryDate6)
    TextView txtExpiryDate6;

    @InjectView(R.id.txtPoints1)
    TextView txtPoints1;

    @InjectView(R.id.txtPoints2)
    TextView txtPoints2;

    @InjectView(R.id.txtPoints3)
    TextView txtPoints3;

    @InjectView(R.id.txtPoints4)
    TextView txtPoints4;

    @InjectView(R.id.txtPoints5)
    TextView txtPoints5;

    @InjectView(R.id.txtPoints6)
    TextView txtPoints6;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private String customerNumber;

    public static ExpiryDateFragment newInstance(Bundle bundle) {

        ExpiryDateFragment fragment = new ExpiryDateFragment();
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

        View view = inflater.inflate(R.layout.big_point_expiry_date, container, false);
        ButterKnife.inject(this, view);
        /*AnalyticsApplication.sendScreenView("Expiry date loaded");*/

        Bundle bundle = getArguments();
        String bigPointExpiry = bundle.getString("BIG_POINT_EXPIRY");

        Gson gson = new Gson();
        BigPointReceive obj = gson.fromJson(bigPointExpiry, BigPointReceive.class);

        setData(obj);

        return view;
    }

    public void setData(BigPointReceive obj) {

        String p1 = changeDouble(obj.getExpPts1());
        String p2 = changeDouble(obj.getExpPts2());
        String p3 = changeDouble(obj.getExpPts3());
        String p4 = changeDouble(obj.getExpPts4());
        String p5 = changeDouble(obj.getExpPts5());
        String p6 = changeDouble(obj.getExpPts6());

        String m1 = changeMonth(obj.getExpMonth1());
        String m2 = changeMonth(obj.getExpMonth2());
        String m3 = changeMonth(obj.getExpMonth3());
        String m4 = changeMonth(obj.getExpMonth4());
        String m5 = changeMonth(obj.getExpMonth5());
        String m6 = changeMonth(obj.getExpMonth6());

        txtPoints1.setText(p1);
        txtPoints2.setText(p2);
        txtPoints3.setText(p3);
        txtPoints4.setText(p4);
        txtPoints5.setText(p5);
        txtPoints6.setText(p6);

        txtExpiryDate1.setText(m1);
        txtExpiryDate2.setText(m2);
        txtExpiryDate3.setText(m3);
        txtExpiryDate4.setText(m4);
        txtExpiryDate5.setText(m5);
        txtExpiryDate6.setText(m6);

    }

    public String changeDouble(String db) {

        String x;
        double newPts1 = Double.parseDouble((db));
        int x2 = (int) newPts1;
        x = String.format(Locale.US,"%,d", x2);

        return x;
    }

    public String changeMonth(String mon) {

        String m = mon;
        DateFormat df = new SimpleDateFormat("MMM yyyy");
        Date startDate;
        try {
            startDate = df.parse(mon);
            Date myDate = startDate;
            m = (new SimpleDateFormat("MMMM yyyy").format(myDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;
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
