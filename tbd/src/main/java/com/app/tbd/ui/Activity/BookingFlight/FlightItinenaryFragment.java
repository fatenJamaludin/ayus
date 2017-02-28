package com.app.tbd.ui.Activity.BookingFlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.SelectedFlightInfo;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Receive.ViewUserReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.ui.Model.Request.SelectFlightRequest;
import com.app.tbd.ui.Model.Request.ViewUserRequest;
import com.app.tbd.ui.Module.FlightItinenaryModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.google.gson.Gson;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FlightItinenaryFragment extends BaseFragment implements BookingPresenter.ProfileView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.returnFlightLayout)
    LinearLayout returnFlightLayout;

    @InjectView(R.id.departureFlightLayout)
    LinearLayout departureFlightLayout;

    @InjectView(R.id.txtFlightDepart)
    TextView txtFlightDepart;

    @InjectView(R.id.txtDepartPts)
    TextView txtDepartPts;

    @InjectView(R.id.txtDepartureTime)
    TextView txtDepartureTime;

    @InjectView(R.id.txtDepartCarrierCode)
    TextView txtDepartCarrierCode;

    @InjectView(R.id.txtDepartureStation)
    TextView txtDepartureStation;

    @InjectView(R.id.txtArrivalStation)
    TextView txtArrivalStation;

    @InjectView(R.id.txtFlightReturn)
    TextView txtFlightReturn;

    @InjectView(R.id.txtReturnFlightNumber)
    TextView txtReturnFlightNumber;

    @InjectView(R.id.txtReturnDuration)
    TextView txtReturnDuration;

    @InjectView(R.id.txtReturnPts)
    TextView txtReturnPts;

    @InjectView(R.id.passengerLayout)
    LinearLayout passengerLayout;

    @InjectView(R.id.txtDepartureDate0)
    TextView txtDepartureDate0;

    @InjectView(R.id.txtDepartureDate1)
    TextView txtDepartureDate1;

    @InjectView(R.id.txtReturnDate0)
    TextView txtReturnDate0;

    @InjectView(R.id.departMultiAirport)
    LinearLayout departMultiAirport;

    @InjectView(R.id.returnMultiAirport)
    LinearLayout returnMultiAirport;

    @InjectView(R.id.txtFlightDepart1)
    TextView txtFlightDepart1;

    @InjectView(R.id.txtDepartureTime1)
    TextView txtDepartureTime1;

    @InjectView(R.id.txtDepartCarrierCode1)
    TextView txtDepartCarrierCode1;

    @InjectView(R.id.txtFlightReturn1)
    TextView txtFlightReturn1;

    @InjectView(R.id.txtReturnFlightNumber1)
    TextView txtReturnFlightNumber1;

    @InjectView(R.id.txtReturnDuration1)
    TextView txtReturnDuration1;

    @InjectView(R.id.txtReturnDate1)
    TextView txtReturnDate1;

    @InjectView(R.id.checked)
    CheckBox checked;

    @InjectView(R.id.itinerary_link1)
    TextView itinerary_link1;

    @InjectView(R.id.itinerary_link2)
    TextView itinerary_link2;

    @InjectView(R.id.itinerary_link3)
    TextView itinerary_link3;

    static Activity act;
    private int fragmentContainerId;
    private static String username, firstName;
    static int totalPassenger, adult, child, infant;
    static String token, date;
    static BookingPresenter presenter2;
    int totalSegment;
    static CheckBox agree;


    public static FlightItinenaryFragment newInstance(Bundle bundle) {

        FlightItinenaryFragment fragment = new FlightItinenaryFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new FlightItinenaryModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.flight_itinenary, container, false);
        ButterKnife.inject(this, view);
        act = getActivity();
        agree = checked;
        //checked.setChecked(true);

        setupURL();
        dataSetup();

        return view;
    }

    public void dataSetup() {

        //Bundle bundle = getArguments();

        presenter2 = presenter;
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final RealmResults<FlightInProgressJSON> result3 = realm.where(FlightInProgressJSON.class).findAll();

        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        SearchFlightRequest searchFlightRequest = (new Gson()).fromJson(result3.get(0).getSearchFlightRequest(), SearchFlightRequest.class);
        SelectFlightRequest selectFlightRequest = (new Gson()).fromJson(result3.get(0).getSelectReceive(), SelectFlightRequest.class);
        SelectedFlightInfo selectedFlightInfo = (new Gson()).fromJson(result3.get(0).getSelectedSegment(), SelectedFlightInfo.class);
        totalSegment = Integer.parseInt(result3.get(0).getTotal());

        //totalSegment = bundle.getInt("TOTAL_SEGMENT");
        ///SearchFlightRequest searchFlightRequest = gson.fromJson(searchFlight, SearchFlightRequest.class);
        ///SelectFlightRequest selectFlightRequest = gson.fromJson(selectFlight, SelectFlightRequest.class);
        ///SelectedFlightInfo selectedFlightInfo = gson.fromJson(selectedFragment, SelectedFlightInfo.class);

        username = loginReceive.getUserName();
        firstName = loginReceive.getFirstName();
        token = loginReceive.getToken();

        //String searchFlight = bundle.getString("SEARCH_FLIGHT");
        //String searchFlightReceive = bundle.getString("SEARCH_FLIGHT");
        //String selectFlight = bundle.getString("SELECT_FLIGHT");
        //String selectedFragment = bundle.getString("SELECT_SEGMENT");


        //Gson gson = new Gson();

        //RealmObjectController.saveFlightSearchInfo(getActivity(), searchFlightRequest, searchReceive,selectReceive, selectedSegment, total);

        appendFlightInfo(searchFlightRequest, selectFlightRequest, selectedFlightInfo);
        appendPassengerInfo(searchFlightRequest);

    }

    public void appendFlightInfo(SearchFlightRequest searchFlightRequest, SelectFlightRequest obj, SelectedFlightInfo selectedFlightInfo) {

        String pts_text = getString(R.string.pts);

        txtFlightDepart.setText(selectedFlightInfo.getSelectedFlightInfo());
        txtDepartPts.setText(changeDouble(selectedFlightInfo.getSelectedFlightPoint()) + " " + pts_text);
        txtDepartCarrierCode.setText(selectedFlightInfo.getSelectedFlightCarrierCode());
        txtDepartureTime.setText(removeSymbol(selectedFlightInfo.getSelectedFlightDuration()));
        txtDepartureStation.setText(selectedFlightInfo.getSelectedDepartureCode());
        txtArrivalStation.setText(selectedFlightInfo.getSelectedArrivalCode());
        txtDepartureDate0.setText(selectedFlightInfo.getSelectedDepartDate());

        //check multi airport
        if (selectedFlightInfo.getMultiDepart() != null) {
            departMultiAirport.setVisibility(View.VISIBLE);
            txtFlightDepart1.setText(selectedFlightInfo.getMultiDepart());
            txtArrivalStation.setText(selectedFlightInfo.getArrivalMulticode());
            txtDepartCarrierCode1.setText(selectedFlightInfo.getMultiDepartCode());
            txtDepartureTime1.setText(removeSymbol(selectedFlightInfo.getMultiDepartDuration()));
            txtDepartureDate1.setText(selectedFlightInfo.getMultiDepartDate());
        }

        //show related layout only
        if (obj.getFareSellKey1() == null) {
            returnFlightLayout.setVisibility(View.GONE);
        } else {
            returnFlightLayout.setVisibility(View.VISIBLE);
            txtFlightReturn.setText(selectedFlightInfo.getSelectedReturnFlightInfo());
            txtReturnPts.setText(changeDouble(selectedFlightInfo.getSelectedReturnFlightPoint()) + " " + pts_text);
            txtReturnFlightNumber.setText(selectedFlightInfo.getSelectedReturnFlightCarrierCode());
            txtReturnDuration.setText(selectedFlightInfo.getSelectedReturnFlightDuration());
            txtReturnDate0.setText(selectedFlightInfo.getSelectedReturnDate());

            //txtArrivalStation.setText(selectedFlightInfo.getArrivalMulticode());
            //txtArrivalStation.setText(selectedFlightInfo.getSelectedDepartureCode());

            //check multi airport
            if (selectedFlightInfo.getMultiReturn() != null) {
                returnMultiAirport.setVisibility(View.VISIBLE);
                txtFlightReturn1.setText(selectedFlightInfo.getMultiReturn());
                txtReturnFlightNumber1.setText(selectedFlightInfo.getMultiReturnCode());
                txtReturnDuration1.setText(selectedFlightInfo.getMultiReturnDuration());
                txtReturnDate1.setText(selectedFlightInfo.getMultiReturnDate());
            }

        }

    }

    public String changeDouble(String db) {

        String x;
        double newPts1 = Double.parseDouble((db));
        int x2 = (int) newPts1;
        x = String.format(Locale.US, "%,d", x2);

        return x;
    }

    public void setupURL() {

        String itinerary1 = getResources().getString(R.string.insurance_text5);
        String itinerary2 = getResources().getString(R.string.insurance_text7);
        String itinerary3 = getResources().getString(R.string.insurance_text6);

        /*String upToNCharacters0 = itinerary.substring(0, Math.min(itinerary.length(), 180));
        itinerary_link1.setText(upToNCharacters0 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo0 = upToNCharacters0 + "..." + "[more]";
        filterMoreText(itinerary, filterNo0, itinerary_link1);*/

        itinerary_link1.setText(Html.fromHtml(itinerary1));
        itinerary_link1.setLinksClickable(true);
        itinerary_link1.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        itinerary_link1.setMovementMethod(LinkMovementMethod.getInstance());


        /*String itinerary2 = getResources().getString(R.string.insurance_text2);
        String upToNCharacters1 = itinerary2.substring(0, Math.min(itinerary2.length(), 180));
        itinerary_link2.setText(upToNCharacters1 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo1 = upToNCharacters1 + "..." + "[more]";
        filterMoreText(itinerary2, filterNo1, itinerary_link2);*/

        itinerary_link2.setText(Html.fromHtml(itinerary2));
        itinerary_link2.setLinksClickable(true);
        itinerary_link2.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        itinerary_link2.setMovementMethod(LinkMovementMethod.getInstance());

        /*String itinerary3 = getResources().getString(R.string.insurance_text3);
        String upToNCharacters2 = itinerary3.substring(0, Math.min(itinerary3.length(), 180));
        itinerary_link3.setText(upToNCharacters2 + "..." + "more", CheckBox.BufferType.SPANNABLE);
        String filterNo2 = upToNCharacters2 + "..." + "[more]";
        filterMoreText(itinerary3, filterNo2, itinerary_link3);*/

        itinerary_link3.setText(Html.fromHtml(itinerary3));
        itinerary_link3.setLinksClickable(true);
        itinerary_link3.setLinkTextColor(ContextCompat.getColor(getActivity(), R.color.textLinkColor));
        itinerary_link3.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void appendPassengerInfo(SearchFlightRequest obj) {

        //Services & Fee
        LinearLayout.LayoutParams matchParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        //count total passenger
        totalPassenger = Integer.parseInt(obj.getChild()) + Integer.parseInt(obj.getAdult()) + Integer.parseInt(obj.getInfant());
        adult = Integer.parseInt(obj.getAdult());
        child = Integer.parseInt(obj.getChild());
        infant = Integer.parseInt(obj.getInfant());

        //get flight departure date
        date = obj.getDepartureDate0();


        int totalAdult = Integer.parseInt(obj.getAdult());
        int totalInfant = Integer.parseInt(obj.getInfant());
        int totalChild = Integer.parseInt(obj.getChild());
        int p = 1;
        String passenger_text = getString(R.string.passenger_text);
        String adult_text = getString(R.string.adult_text);
        String child_text = getString(R.string.child_text);
        String infant_text = getString(R.string.infant_text);

        for (int x = 0; x < totalAdult; x++) {

            LinearLayout flightType = new LinearLayout(getActivity());
            flightType.setWeightSum(1);
            flightType.setLayoutParams(matchParent);
            flightType.setGravity(Gravity.LEFT);

            TextView txtSeatType = new TextView(getActivity());
            txtSeatType.setPadding(0, 10, 10, 10);
            txtSeatType.setTextColor(getResources().getColor(R.color.black));
            txtSeatType.setTextSize(16);
            if (x == 0) {
                txtSeatType.setText(passenger_text + " " + p + " " + adult_text + " " + firstName);
            } else {
                txtSeatType.setText(passenger_text + " " + p + " " + adult_text);
            }

            flightType.addView(txtSeatType);
            passengerLayout.addView(flightType);
            p++;

        }

        for (int x = 0; x < totalChild; x++) {

            LinearLayout flightType = new LinearLayout(getActivity());
            flightType.setWeightSum(1);
            flightType.setLayoutParams(matchParent);
            flightType.setGravity(Gravity.LEFT);

            TextView txtSeatType = new TextView(getActivity());
            txtSeatType.setTextSize(16);
            txtSeatType.setPadding(0, 10, 10, 10);
            txtSeatType.setText(passenger_text + " " + p + " " + child_text);

            flightType.addView(txtSeatType);

            passengerLayout.addView(flightType);
            p++;

        }

        for (int x = 0; x < totalInfant; x++) {

            LinearLayout flightType = new LinearLayout(getActivity());
            flightType.setWeightSum(1);
            flightType.setLayoutParams(matchParent);
            flightType.setGravity(Gravity.LEFT);

            TextView txtSeatType = new TextView(getActivity());
            txtSeatType.setTextSize(16);
            txtSeatType.setPadding(0, 10, 10, 10);
            txtSeatType.setText(passenger_text + " " + p + " " + infant_text);

            flightType.addView(txtSeatType);

            passengerLayout.addView(flightType);
            p++;

        }

    }

    public String removeSymbol(String yourString) {

        String result = yourString.replaceAll("[:]", "");
        return result;
    }

    public static void loadProfileInfo(Activity act) {

        if (agree.isChecked()) {
            initiateLoading(act);

            ViewUserRequest data = new ViewUserRequest();
            data.setUserName(username);
            data.setToken(token);

            presenter2.onProfileRequest(data);
        } else {
            String alert = AnalyticsApplication.getContext().getString(R.string.addons_alert);
            String tnc_Text = AnalyticsApplication.getContext().getString(R.string.tnc_text);
            setAlertDialog(act, tnc_Text, alert);
        }
    }

    @Override
    public void onViewUserSuccess(ViewUserReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
            LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
            loginReceive.setUserEmail(obj.getPersonalEmail());
            String titleCode;

            //stupid api
            if (obj.getTitle().equals("MR")) {
                titleCode = "1";
            } else {
                titleCode = "2";
            }

            loginReceive.setSalutation(titleCode);
            loginReceive.setPhoneNo(obj.getMobilePhone());
            loginReceive.setCountryCode(obj.getNationality());

            String day = obj.getDOB().substring(0, 2);
            String month = obj.getDOB().substring(2, 4);
            String year = obj.getDOB().substring(4);

            loginReceive.setDob(year + "-" + month + "-" + day);

            Gson gsonUserInfo = new Gson();
            String userInfo = gsonUserInfo.toJson(loginReceive);
            RealmObjectController.saveUserInformation(getActivity(), userInfo);

            Intent intent = new Intent(getActivity(), PassengerInfoActivity.class);
            intent.putExtra("ADULT", adult);
            intent.putExtra("CHILD", child);
            intent.putExtra("INFANT", infant);
            intent.putExtra("DEPARTURE_DATE", date);

            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //getActivity().finishAffinity();
            //getActivity().finish();
            getActivity().startActivity(intent);
            //System.exit(0);

            //Runtime.getRuntime().gc();

        }
    }

    @Override

    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
