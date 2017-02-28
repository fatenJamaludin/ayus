package com.app.tbd.ui.Activity.BookingFlight.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.FragmentContainerActivity;
import com.app.tbd.ui.Activity.Homepage.HomeActivity;
import com.app.tbd.ui.Activity.Login.LoginActivity;
import com.app.tbd.ui.Activity.TAB.TabActivity;
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
import com.app.tbd.ui.Model.Request.RegisterRequest;
import com.app.tbd.ui.Module.LoginModule;
import com.app.tbd.ui.Module.PaymentModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Presenter.LoginPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FlightSummaryFragment extends BaseFragment {

    // Validator Attributes
    private static Validator mValidator;

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.txtRecordLocator)
    TextView txtRecordLocator;

    @InjectView(R.id.txtDepartCode)
    TextView txtDepartCode;

    @InjectView(R.id.txtArrivalCode)
    TextView txtArrivalCode;

    @InjectView(R.id.txtDepartDate)
    TextView txtDepartDate;

    @InjectView(R.id.txtDepartTime)
    TextView txtDepartTime;

    @InjectView(R.id.txtDepartState)
    TextView txtDepartState;

    @InjectView(R.id.txtDepartCountry)
    TextView txtDepartCountry;

    @InjectView(R.id.txtArrivalState)
    TextView txtArrivalState;

    @InjectView(R.id.txtArrivalCountry)
    TextView txtArrivalCountry;

    @InjectView(R.id.returnDateLayout)
    LinearLayout returnDateLayout;

    @InjectView(R.id.txtReturnDate)
    TextView txtReturnDate;

    @InjectView(R.id.txtReturnDateTime)
    TextView txtReturnDateTime;

    @InjectView(R.id.txtDepartCarrierCode)
    TextView txtDepartCarrierCode;

    @InjectView(R.id.txtReturnCarrierCode)
    TextView txtReturnCarrierCode;

    @InjectView(R.id.returnCarrierLayout)
    LinearLayout returnCarrierLayout;

    @InjectView(R.id.txtPassengerQty)
    TextView txtPassengerQty;

    @InjectView(R.id.summary_redeemed)
    TextView summary_redeemed;

    @InjectView(R.id.summary_paid)
    TextView summary_paid;

    @InjectView(R.id.summary_total)
    TextView summary_total;

    @InjectView(R.id.contact_email)
    TextView contact_email;

    @InjectView(R.id.txtbooking_num)
    TextView txtbooking_num;

    @InjectView(R.id.txtBookingStatus)
    TextView txtBookingStatus;

    @InjectView(R.id.payment_status_icon)
    ImageView payment_status_icon;

    @InjectView(R.id.summary_email)
    LinearLayout summary_email;

    /*@InjectView(R.id.total_paid)
    TextView total_paid;

    @InjectView(R.id.tick)
    ImageView tick;*/

    private int fragmentContainerId;
    private FirebaseAnalytics mFirebaseAnalytics;
    MixpanelAPI mixPanel;

    /*public static final String MIXPANEL_TOKEN = "7969a526dc4b31f72f05ca4a060eda1c"; //Token AAB*/
    private static String MIXPANEL_TOKEN = AnalyticsApplication.getMixPanelToken(); //Token dr AA
    /*public static final String MIXPANEL_TOKEN = "12161458140eb25a9cecc6573ad97d1c"; //Token Paten*/

    public static FlightSummaryFragment newInstance(Bundle bundle) {

        FlightSummaryFragment fragment = new FlightSummaryFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainApplication.get(getActivity()).createScopedGraph(new PaymentModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        //Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mixPanel = MixpanelAPI.getInstance(getActivity(), MIXPANEL_TOKEN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.flight_summary, container, false);
        ButterKnife.inject(this, view);

        dataSetup();

        return view;
    }

    public void dataSetup() {

        Bundle bundle = getArguments();

        String obj = bundle.getString("FLIGHT_SUMMARY");
        PaymentStatusReceive paymentStatusReceive = (new Gson()).fromJson(obj, PaymentStatusReceive.class);

        String recordLocator = paymentStatusReceive.getRecordLocator();
        String departCode = paymentStatusReceive.getJourney().get(0).getSegment().get(0).getDepartureStation();
        String departDate = paymentStatusReceive.getJourney().get(0).getSegment().get(0).getSTD();

        String depart = getFlightDetail(getActivity(), departCode);
        String[] separated = depart.split("/-");
        String airportDepartName = separated[0];
        String airportDepartCountryName = separated[1];

        String arrivalCode = "";
        String arrivalDate = "";

        //set total passenger
        String adult = paymentStatusReceive.getAdult();
        String child = paymentStatusReceive.getChild();
        String infant = paymentStatusReceive.getInfant();

        int totalPassenger = Integer.parseInt(adult) + Integer.parseInt(child) + Integer.parseInt(infant);
        txtPassengerQty.setText(Integer.toString(totalPassenger));

        if (paymentStatusReceive.getJourney().get(0).getSegment().size() > 1) {
            int size = paymentStatusReceive.getJourney().get(0).getSegment().size();
            arrivalCode = paymentStatusReceive.getJourney().get(0).getSegment().get(size - 1).getArrivalStation();
            arrivalDate = paymentStatusReceive.getJourney().get(0).getSegment().get(size - 1).getSTA();
            String destination = getFlightDetail(getActivity(), arrivalCode);

            String CurrentString = destination;
            String[] separated2 = CurrentString.split("/-");

            if (separated2[0] != null) {
                txtArrivalState.setText(separated2[0]);
            }

            if (separated2[1] != null) {
                txtArrivalCountry.setText(separated2[1]);
            }

        } else {
            arrivalCode = paymentStatusReceive.getJourney().get(0).getSegment().get(0).getArrivalStation();
            arrivalDate = paymentStatusReceive.getJourney().get(0).getSegment().get(0).getSTA();
            String destination = getFlightDetail(getActivity(), arrivalCode);

            String CurrentString = destination;
            String[] separated3 = CurrentString.split("/-");

            if (separated3[0] != null) {
                txtArrivalState.setText(separated3[0]);
            }

            if (separated3[1] != null) {
                txtArrivalCountry.setText(separated3[1]);
            }
        }

        String bookingStatus;
        String email_message = getString(R.string.email_message);

        if (paymentStatusReceive.getBookingStatus().equals("Need Payment")) {
            bookingStatus = getString(R.string.need_payment);
            /*String total = "Total Due";*/
            txtBookingStatus.setText(bookingStatus);
            txtBookingStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.need_payment));
            payment_status_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.need_payment));
            /*total_paid.setText(total);*/
            summary_email.setVisibility(View.VISIBLE);
            //tick.setVisibility(View.GONE);
            contact_email.setText(email_message);


        } else if (paymentStatusReceive.getBookingStatus().equals("Pending")) {
            bookingStatus = getString(R.string.pending_payment);
            /*String total = "Total Due";*/
            txtBookingStatus.setText(bookingStatus);
            txtBookingStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.need_payment));
            payment_status_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.need_payment));
            /*total_paid.setText(total);*/
            summary_email.setVisibility(View.VISIBLE);
            //tick.setVisibility(View.GONE);
            contact_email.setText(email_message);

        } else {

            mixPanelSendEvent(paymentStatusReceive, arrivalCode, departCode, totalPassenger);

            Bundle firebaseBundle = new Bundle();

            firebaseBundle.putString(FirebaseAnalytics.Param.CURRENCY,paymentStatusReceive.getCurrencyCode() );
            firebaseBundle.putString(FirebaseAnalytics.Param.VALUE,paymentStatusReceive.getTotalAmount()  );
            firebaseBundle.putString("Points",paymentStatusReceive.getTotalPoints() );
            firebaseBundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID,paymentStatusReceive.getTransactionNumber() );
            firebaseBundle.putString(FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS, String.valueOf(totalPassenger));
            firebaseBundle.putString(FirebaseAnalytics.Param.ORIGIN, departCode );
            firebaseBundle.putString(FirebaseAnalytics.Param.DESTINATION, arrivalCode);

            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, firebaseBundle);

            /*bookingStatus = paymentStatusReceive.getBookingStatus();*/
            bookingStatus = getString(R.string.success_payment);
            /*String total = "Total Payment";*/
            txtBookingStatus.setText(bookingStatus);
            txtBookingStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_tick));
            payment_status_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_green_tick));
            /*total_paid.setText(total);*/
            summary_email.setVisibility(View.VISIBLE);
            String email_message2 = getString(R.string.email_message2);
            contact_email.setText(email_message2 + paymentStatusReceive.getContact().get(0).getEmailAddress());
        }

        txtDepartCarrierCode.setText(paymentStatusReceive.getJourney().get(0).getSegment().get(0).getCarrierCode() + " " + paymentStatusReceive.getJourney().get(0).getSegment().get(0).getFlightNumber());
        txtRecordLocator.setText(recordLocator);
        txtbooking_num.setText(recordLocator);
        txtDepartCode.setText(departCode);
        txtArrivalCode.setText(arrivalCode);

        String points_text = getString(R.string.pts);

        summary_redeemed.setText(changeThousand(paymentStatusReceive.getTotalPoints()) + " " + points_text);
        summary_paid.setText(paymentStatusReceive.getTotalAmount() + " " + paymentStatusReceive.getCurrencyCode());
        summary_total.setText(paymentStatusReceive.getTransactionNumber());

        txtDepartDate.setText(parseStringDate(departDate));
        txtDepartTime.setText(parseStringDateToTime(departDate) + " - " + parseStringDateToTime(arrivalDate));

        txtDepartState.setText(airportDepartName);
        txtDepartCountry.setText(airportDepartCountryName);

        //Check Return Flight
        if (paymentStatusReceive.getJourney().size() > 1) {

            returnCarrierLayout.setVisibility(View.VISIBLE);
            String returnDepartDate = paymentStatusReceive.getJourney().get(1).getSegment().get(0).getSTD();
            String returnArrivalDate = "";

            if (paymentStatusReceive.getJourney().get(1).getSegment().size() > 1) {
                int size = paymentStatusReceive.getJourney().get(0).getSegment().size();
                returnArrivalDate = paymentStatusReceive.getJourney().get(1).getSegment().get(size - 1).getSTA();
            } else {
                returnArrivalDate = paymentStatusReceive.getJourney().get(1).getSegment().get(0).getSTA();
            }

            txtReturnDate.setText(parseStringDate(returnDepartDate));
            txtReturnDateTime.setText(parseStringDateToTime(returnDepartDate) + " - " + parseStringDateToTime(returnArrivalDate));
            txtReturnCarrierCode.setText(paymentStatusReceive.getJourney().get(1).getSegment().get(0).getCarrierCode() + " " + paymentStatusReceive.getJourney().get(1).getSegment().get(0).getFlightNumber());

        } else {
            returnDateLayout.setVisibility(View.GONE);
            returnCarrierLayout.setVisibility(View.GONE);

        }
    }

    public static void backToHomepage(Activity act) {
        Intent intent = new Intent(act, TabActivity.class);
        act.startActivity(intent);
        act.finish();
    }

    public void mixPanelSendEvent(PaymentStatusReceive paymentStatusReceive, String arrivalCode, String departCode, int totalPassenger){

        //convert from realm cache data to basic class
        /*Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        mixPanel.identify(obj.getCustomerNumber());*/

        JSONObject props = new JSONObject();
        try {
            props.put("Currency", paymentStatusReceive.getCurrencyCode());
            props.put("Amount", paymentStatusReceive.getTotalAmount());
            props.put("Points", paymentStatusReceive.getTotalPoints());
            props.put("TransactionID", paymentStatusReceive.getTransactionNumber());
            props.put("Passengers", String.valueOf(totalPassenger));
            props.put("Origin", departCode);
            props.put("Destination", arrivalCode);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AnalyticsApplication.sendEvent("Redeem", props);
    }

    public static void newFlight(Activity act) {
        Intent intent = new Intent(act, TabActivity.class);
        intent.putExtra("NEW_FLIGHT", "Y");
        act.startActivity(intent);
        act.finish();
    }

    public String parseStringDateToTime(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("HHmm");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String parseStringDate(String unparseDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat reformat = new SimpleDateFormat("E, dd MMM");

        try {

            Date date = formatter.parse(unparseDate);
            return reformat.format(date).toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
        //String reformatDate = reformat.format(date).toString();
    }

    //public static (Activity act){
    //
    // }
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
}
