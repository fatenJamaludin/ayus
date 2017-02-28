package com.app.tbd.ui.Activity.BookingFlight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
/*import android.view.animation.Animation;*/
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.MainFragmentActivity;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.Picker.CustomDatePickerFragment;
import com.app.tbd.ui.Activity.Picker.CustomPassengerPicker;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Activity.Picker.NewDatePickerFragment;
import com.app.tbd.ui.Activity.Picker.SelectionListFragment;
import com.app.tbd.ui.Model.JSON.PromoTransaction;
import com.app.tbd.ui.Model.JSON.RecentSearch;
import com.app.tbd.ui.Model.JSON.RecentSearchArrival;
import com.app.tbd.ui.Model.JSON.RecentSearchClass;
import com.app.tbd.ui.Model.JSON.RecentSearchList;
import com.app.tbd.ui.Model.JSON.RecentSearchListArrival;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SignatureReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.SearchFlightRequest;
import com.app.tbd.ui.Model.Request.SignatureRequest;
import com.app.tbd.ui.Module.SearchFlightModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.Cached.CachedResult;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.DropDownItem;
import com.app.tbd.utils.SharedPrefManager;
import com.app.tbd.utils.Utils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class SearchFlightFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, BookingPresenter.SearchFlightView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.departureDateBlock)
    LinearLayout departureDateBlock;

    @InjectView(R.id.btnSearchFlight)
    ImageView btnSearchFlight;

    @InjectView(R.id.btnDepartureFlight)
    LinearLayout btnDepartureFlight;

    @InjectView(R.id.btnArrivalFlight)
    LinearLayout btnArrivalFlight;

    @InjectView(R.id.bookFlightDepartureDate)
    TextView bookFlightDepartureDate;

    @InjectView(R.id.bookFlightReturnDate)
    TextView bookFlightReturnDate;

    @InjectView(R.id.txtDepartureFlight)
    TextView txtDepartureFlight;

    @InjectView(R.id.txtArrivalFlight)
    TextView txtArrivalFlight;

    @InjectView(R.id.btnReturn)
    LinearLayout btnReturn;

    @InjectView(R.id.btnOneWay)
    LinearLayout btnOneWay;

    @InjectView(R.id.returnDateBlock)
    LinearLayout returnDateBlock;

    @InjectView(R.id.departure_block_enlarge)
    LinearLayout departure_block_enlarge;

    @InjectView(R.id.returnDateTilt)
    LinearLayout returnDateTilt;

    @InjectView(R.id.passengerQty)
    LinearLayout passengerQty;

    @InjectView(R.id.txtPassengerQty)
    TextView txtPassengerQty;

    @InjectView(R.id.txtReturn)
    TextView txtReturn;

    @InjectView(R.id.txtOneWay)
    TextView txtOneWay;

    @InjectView(R.id.txtSwapDestination)
    ImageView txtSwapDestination;

    @InjectView(R.id.resetLayout)
    TextView resetLayout;

    @InjectView(R.id.btnDepartClick)
    LinearLayout btnDepartClick;

    @InjectView(R.id.btnReturnClick)
    LinearLayout btnReturnClick;

    @InjectView(R.id.block_date_round_trip)
    LinearLayout block_date_round_trip;

    @InjectView(R.id.block_date_one_way)
    LinearLayout block_date_one_way;

    @InjectView(R.id.returnLine)
    View returnLine;

    @InjectView(R.id.promo_message)
    LinearLayout promo_message;
   /* @InjectView(R.id.returnDateLayout)
    LinearLayout returnDateLayout; */


    //@InjectView(R.id.btnDepartDateClick)
    //LinearLayout btnDepartDateClick;

    //@InjectView(R.id.btnReturnDateClick)
    //LinearLayout btnReturnDateClick;

    //@InjectView(R.id.btnPassengerClick)
    //LinearLayout btnPassengerClick;

    //@InjectView(R.id.dayPickerView)
    //DayPickerView dayPickerView;

    private int fragmentContainerId;
    private static final String SCREEN_LABEL = "Book Flight: Search Flight";
    private String signature;
    private SharedPrefManager pref;
    /*private Animation animShow, animHide;*/

    //popup alert & tag
    private String DEPARTURE_FLIGHT/* = "Choose your departure airport"*/;
    private String ARRIVAL_FLIGHT/* = "Choose your destination airport"*/;
    private String DEPARTURE_FLIGHT_DATE/* = "Choose your departure date."*/;
    private String ARRIVAL_FLIGHT_DATE/* = "Choose your return date."*/;

    private String FLIGHT_OBJECT = "FLIGHT_OBJECT";

    private String DEPARTURE_DATE_PICKER = "DEPARTURE_DATE_PICKER";
    private String BLOCK_STATUS = "BLOCK_STATUS";
    private String RETURN_DATE_PICKER = "RETURN_DATE_PICKER";
    private String PICKER;
    public static final String DATEPICKER_TAG = "DATEPICKER_TAG";
    private String CURRENT_PICKER;
    final Calendar calendar = Calendar.getInstance();
    static ArrayList<DropDownItem> arrivalFlight = new ArrayList<DropDownItem>();
    static ArrayList<DropDownItem> flightMarket = new ArrayList<DropDownItem>();
    static String stationCode;
    static String currency;
    static Boolean arrivalClickable = false;
    SearchFlightRequest searchFlightRequest = new SearchFlightRequest();

    String totalAdult = "1", totalChild = "0", totalInfant = "0", username, token;
    Boolean swipe1 = false, swipe2 = false, swap;
    Date returnFormatDate, departFormatDate;
    String returnDay, departDay;
    String current_api = "";
    static Boolean oneWay = false;
    static View view;
    static Boolean blockCalendar = false;

    static String travellingPeriodFrom = "na";
    static String travellingPeriodTo = "na";

    /*public static final String MIXPANEL_TOKEN = "7969a526dc4b31f72f05ca4a060eda1c"; //Token AAB*/
    private static String MIXPANEL_TOKEN = AnalyticsApplication.getMixPanelToken(); //Token dr AA
    /*public static final String MIXPANEL_TOKEN = "12161458140eb25a9cecc6573ad97d1c"; //Token Paten*/

    private FirebaseAnalytics mFirebaseAnalytics;

    //NEED TO CREATE DIFFERENT INSTANCE FOR EACH CALENDAR TO AVOID DISPLAYING PREVIOUS SELECTED.
    DatePickerDialog searchFlight_DatePicker;
    MixpanelAPI mixPanel;
    Boolean block = false;

    public static SearchFlightFragment newInstance() {
        Log.e("OnResum1e", "OK");

        SearchFlightFragment fragment = new SearchFlightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        // new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new SearchFlightModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        //Analytics
        mixPanel = MixpanelAPI.getInstance(getActivity(), MIXPANEL_TOKEN);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        oneWay = false;
        blockCalendar = false;

        Log.e("Oncreate", "Y");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("OnResume", "OK1");

        view = inflater.inflate(R.layout.search_flight, container, false);
        ButterKnife.inject(this, view);

        //clear all data related to booking from realm
        insertMessage();
        datePickerSetting();
        dataSetup();
        //initiateFlightStation();

        resetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promo_message.setVisibility(View.GONE);

                txtDepartureFlight.setText("");
                txtDepartureFlight.setHint(getResources().getString(R.string.info_select_one));

                txtArrivalFlight.setText("");
                txtArrivalFlight.setHint(getResources().getString(R.string.info_select_one));

                bookFlightDepartureDate.setText("");
                bookFlightDepartureDate.setHint(getResources().getString(R.string.flight_select_return_date));

                bookFlightReturnDate.setText("");
                bookFlightReturnDate.setHint(getResources().getString(R.string.flight_select_return_date));

                bookFlightDepartureDate.setText("");
                bookFlightDepartureDate.setHint(getResources().getString(R.string.flight_select_return_date));

                txtDepartureFlight.setTag(DEPARTURE_FLIGHT);
                txtArrivalFlight.setTag(ARRIVAL_FLIGHT);
                bookFlightDepartureDate.setTag(DEPARTURE_FLIGHT_DATE);
                bookFlightReturnDate.setTag(ARRIVAL_FLIGHT_DATE);

                txtPassengerQty.setText(getResources().getString(R.string.flight_search_adult));
                totalAdult = "1";
                totalChild = "0";
                totalInfant = "0";

                //reset clickable
                btnReturnClick.setClickable(true);
                btnDepartClick.setClickable(true);
                btnReturn.setClickable(true);
                btnOneWay.setClickable(true);

                arrivalClickable = false;
                blockCalendar = false;

                txtSwapDestination.setVisibility(View.INVISIBLE);
            }
        });

        passengerQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passengerQtyFragment();
                CURRENT_PICKER = "PASSENGER";
            }
        });

        txtSwapDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String depart = txtDepartureFlight.getText().toString();
                String arrival = txtArrivalFlight.getText().toString();
                String departTag = txtDepartureFlight.getTag().toString();
                String arrivalTag = txtArrivalFlight.getTag().toString();

                //if (swap) {
                txtDepartureFlight.setText(arrival);
                txtArrivalFlight.setText(depart);
                txtDepartureFlight.setTag(arrivalTag);
                txtArrivalFlight.setTag(departTag);
                //   swap = false;
                /*} else {
                    txtDepartureFlight.setText(txtArrivalFlight.getText().toString());
                    txtArrivalFlight.setText(txtDepartureFlight.getText().toString());
                    txtDepartureFlight.setTag(txtArrivalFlight.getTag().toString());
                    txtArrivalFlight.setTag(txtDepartureFlight.getText().toString());
                    swap = true;
                }*/

            }
        });


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Utils.toastNotification(getActivity(), "Under Construction.");

                /*block_date_one_way.startAnimation( animHide );
                block_date_one_way.setVisibility(View.GONE);

                block_date_round_trip.setVisibility(View.VISIBLE);
                block_date_round_trip.startAnimation( animShow );*/

                returnDateBlock.setVisibility(View.VISIBLE);
                returnDateTilt.setVisibility(View.VISIBLE);
                returnLine.setVisibility(View.VISIBLE);

                oneWay = false;
                //returnDateLayout.setVisibility(View.VISIBLE);

                //LinearLayout.LayoutParams half04 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.48f);
                //departure_block_enlarge.setLayoutParams(half04);

                btnOneWay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.search_flight_button_transparent));
                btnReturn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.login_login_button_clicked));
                txtReturn.setTextColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
                txtOneWay.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

            }
        });

        //btnOneWay.performClick();
        btnOneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*slideToLeftGONE(block_date_round_trip);
                slideToLeftVISIBLE(block_date_one_way);*/

                /*block_date_round_trip.startAnimation( animHide );
                block_date_round_trip.setVisibility(View.GONE);

                block_date_one_way.setVisibility(View.VISIBLE);
                block_date_one_way.startAnimation( animShow );*/

                returnDateBlock.setVisibility(View.GONE);
                returnDateTilt.setVisibility(View.GONE);
                returnLine.setVisibility(View.GONE);
                //returnDateLayout.setVisibility(View.GONE);
                //LinearLayout.LayoutParams half04 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                //departure_block_enlarge.setLayoutParams(half04);

                oneWay = true;

                btnReturn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.search_flight_button_transparent));
                btnOneWay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.login_login_button_clicked));
                txtReturn.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                txtOneWay.setTextColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));

            }
        });

        btnDepartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_PICKER = "DEPARTURE_FLIGHT";
                txtArrivalFlight.setText("");
                showCountrySelector(getActivity(), CURRENT_PICKER, "NA");
            }
        });

        btnReturnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrivalClickable) {
                    CURRENT_PICKER = "ARRIVAL_FLIGHT";
                    showCountrySelector(getActivity(), CURRENT_PICKER, stationCode);
                } else {
                    Utils.toastNotification(getActivity(), DEPARTURE_FLIGHT);
                }
            }
        });


        btnSearchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //manual validation
                if (validate()) {
                    getSignature();
                }
            }
        });

        departureDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkFragmentAdded()) {
                    datePickerFragment(oneWay, blockCalendar, travellingPeriodFrom, travellingPeriodTo);
                }

                CURRENT_PICKER = DEPARTURE_DATE_PICKER;
            }
        });

                /*Arrival Date Clicked*/
        returnDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFragmentAdded()) {
                    datePickerFragment(true, blockCalendar, travellingPeriodFrom, travellingPeriodTo);
                }
                CURRENT_PICKER = RETURN_DATE_PICKER;
            }
        });


        return view;
    }

    public void insertMessage() {
        DEPARTURE_FLIGHT = getString(R.string.alert_text1);
        ARRIVAL_FLIGHT = getString(R.string.alert_text2);
        DEPARTURE_FLIGHT_DATE = getString(R.string.alert_text3);
        ARRIVAL_FLIGHT_DATE = getString(R.string.alert_text4);
    }

    //validation
    public Boolean validate() {

        String df = txtDepartureFlight.getTag().toString();
        String af = txtArrivalFlight.getTag().toString();

        String d1 = bookFlightDepartureDate.getTag().toString();
        String d2 = bookFlightReturnDate.getTag().toString();

        if (df.equals(DEPARTURE_FLIGHT)) {
            Log.e(df, DEPARTURE_FLIGHT);
            popupAlert(DEPARTURE_FLIGHT);
            return false;
        } else if (af.equals(ARRIVAL_FLIGHT)) {
            popupAlert(ARRIVAL_FLIGHT);
            return false;
        } else if (d1.equals(DEPARTURE_FLIGHT_DATE)) {
            popupAlert(DEPARTURE_FLIGHT_DATE);
            return false;
        } else if (!oneWay && d2.equals(ARRIVAL_FLIGHT_DATE)) {
            popupAlert(ARRIVAL_FLIGHT_DATE);
            return false;
        } else {
            return true;
        }
    }

    public void dataSetup() {

        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        username = loginReceive.getUserName();
        token = loginReceive.getToken();

        txtDepartureFlight.setTag(DEPARTURE_FLIGHT);
        txtArrivalFlight.setTag(ARRIVAL_FLIGHT);
        bookFlightDepartureDate.setTag(DEPARTURE_FLIGHT_DATE);
        bookFlightReturnDate.setTag(ARRIVAL_FLIGHT_DATE);

        final RealmResults<RecentSearch> recentSearchResult = realm.where(RecentSearch.class).findAll();
        if (recentSearchResult.size() > 0) {

            RecentSearchList recentSearchList = (new Gson()).fromJson(recentSearchResult.get(0).getRecentSearch(), RecentSearchList.class);

            //check ni
        /*    int position = recentSearchList.getRecentSearchList().size() - 1;
            String departName = recentSearchList.getRecentSearchList().get(position).getDepartureName();
            String departCode = recentSearchList.getRecentSearchList().get(position).getDepartureCode();

            stationCode = departCode;
            currency = recentSearchList.getRecentSearchList().get(position).getDepartureCurrency();

            txtDepartureFlight.setText(departName + " (" + departCode + ")");
            txtDepartureFlight.setTag(departCode);
            Log.e("departCode", departCode);
            arrivalClickable = true;
=======*/

            int size = recentSearchList.getRecentSearchList().size();
            Log.e("size recent search", String.valueOf(size));

            for (int i = 0; i < size; i++) {
                if (recentSearchList.getRecentSearchList().get(i).getType().equals("DEPART")) {
                    int position = size - 1;
                    String departName = recentSearchList.getRecentSearchList().get(position).getDepartureName();
                    String departCode = recentSearchList.getRecentSearchList().get(position).getDepartureCode();

                    stationCode = departCode;
                    currency = recentSearchList.getRecentSearchList().get(position).getDepartureCurrency();

                    txtDepartureFlight.setText(departName + " (" + departCode + ")");
                    txtDepartureFlight.setTag(departCode);
                    arrivalClickable = true;
                    swipe1 = true;

                }
            }
        }

        initiateFlightStation(getActivity());
    }

    public static void triggerPromoInfo(Activity act, PromoTransaction promoTransaction) {

        Log.e("Flight Promo ", promoTransaction.getDepartText());

        AnalyticsApplication.sendScreenView("Flight search (promo) tab loaded");

        final LinearLayout btnOneWay = (LinearLayout) view.findViewById(R.id.btnOneWay);
        final LinearLayout btnReturn = (LinearLayout) view.findViewById(R.id.btnReturn);
        final TextView txtReturn = (TextView) view.findViewById(R.id.txtReturn);
        final TextView txtOneWay = (TextView) view.findViewById(R.id.txtOneWay);

        final LinearLayout returnDateBlock = (LinearLayout) view.findViewById(R.id.returnDateBlock);
        final LinearLayout returnDateTilt = (LinearLayout) view.findViewById(R.id.returnDateTilt);
        final View returnLine = (View) view.findViewById(R.id.returnLine);

        final LinearLayout promo_message = (LinearLayout) view.findViewById(R.id.promo_message);

        final TextView bookFlightDepartureDate = (TextView) view.findViewById(R.id.bookFlightDepartureDate);
        final TextView bookFlightReturnDate = (TextView) view.findViewById(R.id.bookFlightReturnDate);

        bookFlightDepartureDate.setText("");
        bookFlightDepartureDate.setHint(act.getString(R.string.select_date));

        bookFlightReturnDate.setText("");
        bookFlightReturnDate.setHint(act.getString(R.string.select_date));
        promo_message.setVisibility(View.VISIBLE);

        if (promoTransaction.getFlightType().equalsIgnoreCase("Return")) {
            oneWay = false;
            btnOneWay.setClickable(false);
            returnDateBlock.setVisibility(View.VISIBLE);
            returnDateTilt.setVisibility(View.VISIBLE);
            returnLine.setVisibility(View.VISIBLE);

            btnOneWay.setBackground(ContextCompat.getDrawable(act, R.drawable.search_flight_button_transparent));
            btnReturn.setBackground(ContextCompat.getDrawable(act, R.drawable.login_login_button_clicked));
            txtReturn.setTextColor(ContextCompat.getColor(act, R.color.default_theme_colour));
            txtOneWay.setTextColor(ContextCompat.getColor(act, R.color.white));

        } else {
            oneWay = true;
            btnReturn.setClickable(false);
            returnDateBlock.setVisibility(View.GONE);
            returnDateTilt.setVisibility(View.GONE);
            returnLine.setVisibility(View.GONE);

            btnReturn.setBackground(ContextCompat.getDrawable(act, R.drawable.search_flight_button_transparent));
            btnOneWay.setBackground(ContextCompat.getDrawable(act, R.drawable.login_login_button_clicked));
            txtReturn.setTextColor(ContextCompat.getColor(act, R.color.white));
            txtOneWay.setTextColor(ContextCompat.getColor(act, R.color.default_theme_colour));

        }

        final LinearLayout btnDepartClick = (LinearLayout) view.findViewById(R.id.btnDepartClick);
        btnDepartClick.setClickable(false);

        final TextView txtDepartureFlight = (TextView) view.findViewById(R.id.txtDepartureFlight);
        txtDepartureFlight.setText(promoTransaction.getDepartText());
        txtDepartureFlight.setTextColor(ContextCompat.getColor(act, R.color.dark_lvl1));
        txtDepartureFlight.setTag(promoTransaction.getDepartureCode());

        final LinearLayout btnReturnClick = (LinearLayout) view.findViewById(R.id.btnReturnClick);
        btnReturnClick.setClickable(false);

        final TextView txtArrivalFlight = (TextView) view.findViewById(R.id.txtArrivalFlight);
        txtArrivalFlight.setText(promoTransaction.getArrivalText());
        txtArrivalFlight.setTextColor(ContextCompat.getColor(act, R.color.dark_lvl1));
        txtArrivalFlight.setTag(promoTransaction.getArrivalCode());

        currency = promoTransaction.getDepartureCurrencyCode();

        //block date based on travel period
        if (promoTransaction.getFlightCode().equals("FC") || promoTransaction.getFlightCode().equals("SE")) {
            //NEED TO BLOCK CALENDAR BASED ON THIS DATE.
            blockCalendar = true;
            Log.e(promoTransaction.getTravellingPeriodFrom(), promoTransaction.getTravellingPeriodTo());
            travellingPeriodFrom = promoTransaction.getTravellingPeriodFrom();
            travellingPeriodTo = promoTransaction.getTravellingPeriodTo();
        }

    }

    public static void triggerPromoInfoContext(Context act, PromoTransaction promoTransaction) {

        Log.e("Flight Promo ", promoTransaction.getDepartText());

        AnalyticsApplication.sendScreenView("Flight search (promo) tab loaded");

        final LinearLayout btnOneWay = (LinearLayout) view.findViewById(R.id.btnOneWay);
        final LinearLayout btnReturn = (LinearLayout) view.findViewById(R.id.btnReturn);
        final TextView txtReturn = (TextView) view.findViewById(R.id.txtReturn);
        final TextView txtOneWay = (TextView) view.findViewById(R.id.txtOneWay);

        final LinearLayout returnDateBlock = (LinearLayout) view.findViewById(R.id.returnDateBlock);
        final LinearLayout returnDateTilt = (LinearLayout) view.findViewById(R.id.returnDateTilt);
        final View returnLine = view.findViewById(R.id.returnLine);

        final LinearLayout promo_message = (LinearLayout) view.findViewById(R.id.promo_message);

        final TextView bookFlightDepartureDate = (TextView) view.findViewById(R.id.bookFlightDepartureDate);
        final TextView bookFlightReturnDate = (TextView) view.findViewById(R.id.bookFlightReturnDate);

        bookFlightDepartureDate.setText("");
        bookFlightDepartureDate.setHint(act.getString(R.string.select_date));

        bookFlightReturnDate.setText("");
        bookFlightReturnDate.setHint(act.getString(R.string.select_date));
        promo_message.setVisibility(View.VISIBLE);

        if (promoTransaction.getFlightType().equalsIgnoreCase("Return")) {
            oneWay = false;
            btnOneWay.setClickable(false);
            returnDateBlock.setVisibility(View.VISIBLE);
            returnDateTilt.setVisibility(View.VISIBLE);
            returnLine.setVisibility(View.VISIBLE);

            btnOneWay.setBackground(ContextCompat.getDrawable(act, R.drawable.search_flight_button_transparent));
            btnReturn.setBackground(ContextCompat.getDrawable(act, R.drawable.login_login_button_clicked));
            txtReturn.setTextColor(ContextCompat.getColor(act, R.color.default_theme_colour));
            txtOneWay.setTextColor(ContextCompat.getColor(act, R.color.white));

        } else {
            oneWay = true;
            btnReturn.setClickable(false);
            returnDateBlock.setVisibility(View.GONE);
            returnDateTilt.setVisibility(View.GONE);
            returnLine.setVisibility(View.GONE);

            btnReturn.setBackground(ContextCompat.getDrawable(act, R.drawable.search_flight_button_transparent));
            btnOneWay.setBackground(ContextCompat.getDrawable(act, R.drawable.login_login_button_clicked));
            txtReturn.setTextColor(ContextCompat.getColor(act, R.color.white));
            txtOneWay.setTextColor(ContextCompat.getColor(act, R.color.default_theme_colour));

        }

        final LinearLayout btnDepartClick = (LinearLayout) view.findViewById(R.id.btnDepartClick);
        btnDepartClick.setClickable(false);

        final TextView txtDepartureFlight = (TextView) view.findViewById(R.id.txtDepartureFlight);
        txtDepartureFlight.setText(promoTransaction.getDepartText());
        txtDepartureFlight.setTextColor(ContextCompat.getColor(act, R.color.dark_lvl1));
        txtDepartureFlight.setTag(promoTransaction.getDepartureCode());

        final LinearLayout btnReturnClick = (LinearLayout) view.findViewById(R.id.btnReturnClick);
        btnReturnClick.setClickable(false);

        final TextView txtArrivalFlight = (TextView) view.findViewById(R.id.txtArrivalFlight);
        txtArrivalFlight.setText(promoTransaction.getArrivalText());
        txtArrivalFlight.setTextColor(ContextCompat.getColor(act, R.color.dark_lvl1));
        txtArrivalFlight.setTag(promoTransaction.getArrivalCode());

        currency = promoTransaction.getDepartureCurrencyCode();

        //block date based on travel period
        if (promoTransaction.getFlightCode().equals("FC") || promoTransaction.getFlightCode().equals("SE")) {
            //NEED TO BLOCK CALENDAR BASED ON THIS DATE.
            blockCalendar = true;
            Log.e(promoTransaction.getTravellingPeriodFrom(), promoTransaction.getTravellingPeriodTo());
            travellingPeriodFrom = promoTransaction.getTravellingPeriodFrom();
            travellingPeriodTo = promoTransaction.getTravellingPeriodTo();
        }

    }

    public void passengerQtyFragment() {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        CustomPassengerPicker routeListDialogFragment = CustomPassengerPicker.newInstance(totalAdult, totalChild, totalInfant);
        routeListDialogFragment.setTargetFragment(SearchFlightFragment.this, 0);
        routeListDialogFragment.show(fm, "passenger_qty");

    }

    public void datePickerFragment(Boolean stat, Boolean blockThisCalendar, String dateFrom, String dateTo) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        CustomDatePickerFragment routeListDialogFragment;
        NewDatePickerFragment newDatePickerDialogFragment;

        //test new calendar
        /*if (!blockThisCalendar) {
            newDatePickerDialogFragment = NewDatePickerFragment.newInstance(stat);
            block = false;
        } else {
            //if need to block calendar. .use this
            newDatePickerDialogFragment = NewDatePickerFragment.newInstance(stat, blockThisCalendar, dateFrom, dateTo);
            block = true;
        }*/

        if (!blockThisCalendar) {
            routeListDialogFragment = CustomDatePickerFragment.newInstance(stat);
            block = false;
        } else {
            //if need to block calendar. .use this
            routeListDialogFragment = CustomDatePickerFragment.newInstance(stat, blockThisCalendar, dateFrom, dateTo);
            block = true;
        }

        routeListDialogFragment.setTargetFragment(SearchFlightFragment.this, 0);
        routeListDialogFragment.show(fm, "date_picker");

        //newDatePickerDialogFragment.setTargetFragment(SearchFlightFragment.this, 0);
        //newDatePickerDialogFragment.show(fm, "date_picker");


    }

    public void searchFlight() {

        current_api = "REQUEST_FLIGHT";

        searchFlightRequest = new SearchFlightRequest();
        searchFlightRequest.setUserName(username);
        searchFlightRequest.setToken(token);
        searchFlightRequest.setAdult(totalAdult);
        searchFlightRequest.setChild(totalChild);
        searchFlightRequest.setInfant(totalInfant);
        searchFlightRequest.setArrivalStation0(txtArrivalFlight.getTag().toString());
        searchFlightRequest.setDepartureStation0(txtDepartureFlight.getTag().toString());
        if (!oneWay) {
            searchFlightRequest.setArrivalStation1(txtDepartureFlight.getTag().toString());
            searchFlightRequest.setDepartureStation1(txtArrivalFlight.getTag().toString());
            searchFlightRequest.setDepartureDate1(bookFlightReturnDate.getTag().toString());
        }
        searchFlightRequest.setCurrencyCode(currency);
        searchFlightRequest.setDepartureDate0(bookFlightDepartureDate.getTag().toString());
        searchFlightRequest.setSignature(signature);

        presenter.onSearchFlight(searchFlightRequest);

    }

    public void getSignature() {

        fireBaseSearch();
        current_api = "REQUEST_SIGNATURE";
        initiateLoading(getActivity());

        SignatureRequest signatureRequest = new SignatureRequest();
        signatureRequest.setToken(token);
        signatureRequest.setUserName(username);
        presenter.onRequestSignature(signatureRequest);

    }

    public void fireBaseSearch() {

        int totalPassenger = Integer.parseInt(totalAdult + totalChild + totalInfant);
        Bundle fireBaseBundle = new Bundle();

        fireBaseBundle.putString(FirebaseAnalytics.Param.ORIGIN, txtDepartureFlight.getText().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.DESTINATION, txtArrivalFlight.getText().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.START_DATE, bookFlightDepartureDate.getTag().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.END_DATE, bookFlightReturnDate.getTag().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS, String.valueOf(totalPassenger));

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, fireBaseBundle);
    }

    public void fireBaseViewSearchResults() {

        int totalPassenger = Integer.parseInt(totalAdult + totalChild + totalInfant);
        Bundle fireBaseBundle = new Bundle();

        fireBaseBundle.putString(FirebaseAnalytics.Param.ORIGIN, txtDepartureFlight.getText().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.DESTINATION, txtArrivalFlight.getText().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.START_DATE, bookFlightDepartureDate.getTag().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.END_DATE, bookFlightReturnDate.getTag().toString());
        fireBaseBundle.putString(FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS, String.valueOf(totalPassenger));

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, fireBaseBundle);
    }

    @Override
    public void onSearchFlightReceive(SearchFlightReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            fireBaseViewSearchResults();
            mixPanelSendEvent();
            Log.e("Status", obj.getStatus());

            Gson gsonUserInfo = new Gson();
            String flightList = gsonUserInfo.toJson(obj);

            Intent flightDetail = new Intent(getActivity(), FlightListDepartActivity.class);
            flightDetail.putExtra("FLIGHT_LIST", flightList);
            flightDetail.putExtra("SEARCH_FLIGHT", (new Gson()).toJson(searchFlightRequest));
            flightDetail.putExtra("ONE_WAY", oneWay);

            getActivity().startActivity(flightDetail);

        }
    }

    public void mixPanelSendEvent() {

        int totalPassenger = Integer.parseInt(totalAdult + totalChild + totalInfant);

        //convert from realm cache data to basic class
        /*Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final LoginReceive obj = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        mixPanel.identify(obj.getCustomerNumber());*/

        JSONObject props = new JSONObject();
        try {
            props.put("Origin", txtDepartureFlight.getText().toString());
            props.put("Destination", txtArrivalFlight.getText().toString());
            props.put("StartDate", bookFlightDepartureDate.getTag().toString());
            props.put("EndDate", bookFlightReturnDate.getTag().toString());
            props.put("Passengers", String.valueOf(totalPassenger));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AnalyticsApplication.sendEvent("Search Flight", props);
    }

    @Override
    public void onSignatureReceive(SignatureReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            signature = obj.getSignature();

            Realm realm = RealmObjectController.getRealmInstance(getActivity());
            final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
            LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);
            loginReceive.setSignature(signature);

            Gson gsonUserInfo = new Gson();
            String userInfo = gsonUserInfo.toJson(loginReceive);
            RealmObjectController.saveUserInformation(getActivity(), userInfo);

            searchFlight();
        } else {
            dismissLoading();
        }
    }

    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act, String data, String code) {
        if (act != null) {
            try {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                SelectionListFragment routeListDialogFragment = SelectionListFragment.newInstance(data, code, true);
                routeListDialogFragment.setTargetFragment(SearchFlightFragment.this, 0);
                routeListDialogFragment.show(fm, "countryListDialogFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<DropDownItem> initiateFlightStation(Activity act) {

        Log.e("Initiate", "Y");
        initiateLoading(act);

        flightMarket = new ArrayList<DropDownItem>();
        JSONArray sortedJsonArray = getFlight(act);

        //Get All Airport - remove redundant
        List<String> al = new ArrayList<>();
        Set<String> hs = new LinkedHashSet<>();
        for (int i = 0; i < sortedJsonArray.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);
            if (!row.optString("DepartureStationName").equals("") && !row.optString("ArrivalStationName").equals("")) {
                al.add(row.optString("DepartureStationName") + "/-" + row.optString("DepartureStation") + "/-" + row.optString("DepartureCountryName") + "/-" + row.optString("DepartureStationCurrencyCode"));
            }
        }

        hs.addAll(al);
        al.clear();
        al.addAll(hs);

        for (int i = 0; i < al.size(); i++) {
            String flightSplit = al.get(i).toString();
            String[] str1 = flightSplit.split("/-");
            String departureStationName = str1[0];
            String departureStation = str1[1];
            String departureCountryName = str1[2];
            String departureStationCurrencyCode = str1[3];

            DropDownItem itemFlight = new DropDownItem();
            itemFlight.setText(departureStationName + " (" + departureStation + ")");
            itemFlight.setCode(departureStation + "/" + departureCountryName + "/" + departureStationCurrencyCode + "/" + departureStationName);
            itemFlight.setTag("FLIGHT");
            flightMarket.add(itemFlight);

        }

        dismissLoading();

        return flightMarket;
    }

    public void datePickerSetting() {

        //datePicker setting
        searchFlight_DatePicker = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        searchFlight_DatePicker.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
        searchFlight_DatePicker.setAccentColor(ContextCompat.getColor(getActivity(), R.color.default_theme_colour));
        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, output.get(Calendar.YEAR));
        output.set(Calendar.DAY_OF_MONTH, output.get(Calendar.DAY_OF_MONTH) + 1);
        output.set(Calendar.MONTH, output.get(Calendar.MONTH));
        searchFlight_DatePicker.setMinDate(output);
        //searchFlight_DatePicker.setHighlightedDays(vv());
        //searchFlight_DatePicker.setDisabledDays(vv());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (resultCode != Activity.RESULT_OK) {
            return;
        } else {*/

        Log.e("requestCode", Integer.toString(requestCode));

        if (requestCode == 1) {
            DropDownItem selectedFlight = data.getParcelableExtra(CURRENT_PICKER);

            if (CURRENT_PICKER.equals("DEPARTURE_FLIGHT")) {
                txtDepartureFlight.setText(selectedFlight.getText());

                String codeSplit = selectedFlight.getCode();
                String[] str1 = codeSplit.split("/");
                String departureStation = str1[0];
                String departureCountryName = str1[1];
                String departureStationCurrencyCode = str1[2];
                String departureStationName = str1[3];


                stationCode = departureStation;
                currency = departureStationCurrencyCode;

                txtDepartureFlight.setTag(departureStation);

                arrivalClickable = true;
                //arrivalFlight = new ArrayList<DropDownItem>();
                //arrivalFlight = initiateArrivalStation(getActivity(),stationCode);

                swipe1 = true;

                //add to recent search
                addToRecentSearch(departureStation, departureStationName, departureCountryName, departureStationCurrencyCode, "DEPART");

            } else if (CURRENT_PICKER.equals("ARRIVAL_FLIGHT")) {
                String codeSplit = selectedFlight.getCode();
                String[] str1 = codeSplit.split("/");
                String arrivalStationCode = str1[0]; //station code
                String arrivalStationName = str1[1]; //arrival station name
                String arrivalCountryName = str1[2]; //country code
                String departureStationCurrencyCode = str1[3]; //curreny code

                try {
                    Log.e("arrivalStationCode ", arrivalStationCode);
                } catch (Exception e) {

                }

                try {
                    Log.e("arrivalStationName ", arrivalStationName);
                } catch (Exception e) {

                }

                try {
                    Log.e("arrivalCountryName ", arrivalCountryName);
                } catch (Exception e) {

                }

                try {
                    Log.e("departureCurrencyCode ", departureStationCurrencyCode);
                } catch (Exception e) {

                }

                txtArrivalFlight.setText(selectedFlight.getText());
                txtArrivalFlight.setTag(arrivalStationCode);

                swipe2 = true;
                swap = true;

                if (swipe1 && swipe2) {
                    txtSwapDestination.setVisibility(View.VISIBLE);
                }

                Log.e("Currency Code", departureStationCurrencyCode);
                addToRecentSearch(arrivalStationCode, arrivalStationName, arrivalCountryName, departureStationCurrencyCode, "ARRIVAL");
                addToRecentSearchReturn(arrivalStationCode, arrivalStationName, arrivalCountryName, departureStationCurrencyCode, "ARRIVAL");


            } else if (CURRENT_PICKER.equals(DEPARTURE_DATE_PICKER)) {

                String departDate, returnDate;
                //if (oneWay) {
                departDate = data.getStringExtra(DEPARTURE_DATE_PICKER);
                Log.e("depart", departDate);

                    /*String[] str1 = departDate.split("-");
                    String p1 = str1[2] + "/" + str1[1] + "/" + str1[0];*/

                departFormatDate = stringToDate(departDate); //Thu Mar 23 00:00:00 GMT+08:00 2017
                departDay = convertDate(departFormatDate);

                String p1 = departDay;

                bookFlightDepartureDate.setTag(departDate);
                bookFlightDepartureDate.setText(p1);

                if (!oneWay) {

                    returnDate = data.getStringExtra(RETURN_DATE_PICKER);

                    if (returnDate.equals("")) {
                        returnDate = departDate;
                    }

                        /*String[] str2 = returnDate.split("-");
                        String p2 = str2[2] + "/" + str2[1] + "/" + str2[0];*/

                    returnFormatDate = stringToDate(returnDate);
                    returnDay = convertDate(returnFormatDate);

                    String p2 = returnDay;

                    bookFlightReturnDate.setTag(returnDate);
                    bookFlightReturnDate.setText(p2);

                    Log.e("ReturnDate", returnDate);
                }

            } else if (CURRENT_PICKER.equals(RETURN_DATE_PICKER)) {
                String departDate, returnDate;
                //if (oneWay) {
                returnDate = data.getStringExtra("DEPARTURE_DATE_PICKER");
                //    bookFlightDepartureDate.setTag(departDate);
                //   Log.e("DepartDate", departDate);
                ///} else {
                //returnDate = data.getStringExtra(CURRENT_PICKER);

                    /*String[] str2 = returnDate.split("-");
                    String p2 = str2[2] + "/" + str2[1] + "/" + str2[0];*/

                returnFormatDate = stringToDate(returnDate);
                returnDay = convertDate(returnFormatDate);

                String p2 = returnDay;

                bookFlightReturnDate.setTag(returnDate);
                bookFlightReturnDate.setText(p2);

                Log.e("ReturnDate", returnDate);

            } else if (CURRENT_PICKER.equals("PASSENGER")) {
                totalAdult = data.getStringExtra("ADULT");
                totalChild = data.getStringExtra("CHILD");
                totalInfant = data.getStringExtra("INFANT");

                String adult = getResources().getString(R.string.passenger_adult);
                String child = getResources().getString(R.string.passenger_child);
                String infant = getResources().getString(R.string.passenger_infant);
                String adults = getResources().getString(R.string.passenger_adult_more);
                String children = getResources().getString(R.string.passenger_child_more);
                String infants = getResources().getString(R.string.passenger_infant_more);

                if (Integer.parseInt(totalAdult) > 1) {

                    String txt = totalAdult + " " + adults + ", ";

                    if (Integer.parseInt(totalChild) > 1) {
                        txt += totalChild + " " + children + ", ";
                    } else if (Integer.parseInt(totalChild) == 1) {
                        txt += totalChild + " " + child + ", ";
                    }

                    if (Integer.parseInt(totalInfant) > 1) {
                        txt += totalInfant + " " + infants + ", ";
                    } else if (Integer.parseInt(totalInfant) == 1) {
                        txt += totalInfant + " " + infant + ", ";
                    }

                    String after = cut(txt);
                    txtPassengerQty.setText(after);

                } else {

                    String txt = totalAdult + " " + adult + ", ";

                    if (Integer.parseInt(totalChild) > 1) {
                        txt += totalChild + " " + children + ", ";
                    } else if (Integer.parseInt(totalChild) == 1) {
                        txt += totalChild + " " + child + ", ";
                    }

                    if (Integer.parseInt(totalInfant) > 1) {
                        txt += totalInfant + " " + infants + ", ";
                    } else if (Integer.parseInt(totalInfant) == 1) {
                        txt += totalInfant + " " + infant + ", ";
                    }

                    String after = cut(txt);
                    txtPassengerQty.setText(after);
                }
            }
        } else if (requestCode == 9) {
            //reopen
            datePickerFragment(oneWay, blockCalendar, travellingPeriodFrom, travellingPeriodTo);
            Log.e("REOPEN", "HERE");
            Log.e("requestCode", Integer.toString(requestCode));

        }
        //}
    }

    public void addToRecentSearchReturn(String arrivalStationCode, String arrivalStationName, String arrivalCountryName, String arrivalCurrency, String arrivalType) {

        Gson gson = new Gson();
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<RecentSearchArrival> recentSearchResult = realm.where(RecentSearchArrival.class).findAll();
        RecentSearchListArrival recentSearchListArrival = new RecentSearchListArrival();
        Boolean bypass = false;

        if (recentSearchResult.size() > 0) {

            recentSearchListArrival = (new Gson()).fromJson(recentSearchResult.get(0).getRecentSearch(), RecentSearchListArrival.class);

            for (int g = 0; g < recentSearchListArrival.getRecentSearchList().size(); g++) {

                if (arrivalStationCode.equals(recentSearchListArrival.getRecentSearchList().get(g).getDepartureCode())) {
                    Log.e("?" + arrivalStationCode, recentSearchListArrival.getRecentSearchList().get(g).getDepartureCode());
                    Log.e("ByPass", "Y");
                    bypass = true;
                    break;
                }
            }

            if (!bypass) {
                Log.e("ByPass", "N");

                if (recentSearchListArrival.getRecentSearchList().size() > 4) {
                    recentSearchListArrival.getRecentSearchList().remove(0);
                }

                RecentSearchClass recentSearchClass = new RecentSearchClass();
                recentSearchClass.setDepartureCode(arrivalStationCode);
                recentSearchClass.setDepartureName(arrivalStationName);
                recentSearchClass.setDepartureCountry(arrivalCountryName);
                recentSearchClass.setDepartureCurrency(arrivalCurrency);
                recentSearchClass.setType(arrivalType);

                recentSearchListArrival.getRecentSearchList().add(recentSearchClass);

                String userInfo = gson.toJson(recentSearchListArrival);
                RealmObjectController.saveRecentSearchArrival(getActivity(), userInfo);
            }

        } else {
            List<RecentSearchClass> recent = new ArrayList<>();

            RecentSearchClass recentSearchClass = new RecentSearchClass();
            recentSearchClass.setDepartureCode(arrivalStationCode);
            recentSearchClass.setDepartureName(arrivalStationName);
            recentSearchClass.setDepartureCountry(arrivalCountryName);
            recentSearchClass.setDepartureCurrency(arrivalCurrency);
            recentSearchClass.setType(arrivalType);

            recent.add(recentSearchClass);
            recentSearchListArrival.setRecentSearchList(recent);

            String userInfo = gson.toJson(recentSearchListArrival);
            RealmObjectController.saveRecentSearchArrival(getActivity(), userInfo);
        }

    }

    public void addToRecentSearch(String stationCode, String stationName, String countryName, String currency, String type) {

        Gson gson = new Gson();
        Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<RecentSearch> recentSearchResult = realm.where(RecentSearch.class).findAll();
        RecentSearchList recentSearchList = new RecentSearchList();
        Boolean bypass = false;

        if (recentSearchResult.size() > 0) {

            recentSearchList = (new Gson()).fromJson(recentSearchResult.get(0).getRecentSearch(), RecentSearchList.class);

            for (int g = 0; g < recentSearchList.getRecentSearchList().size(); g++) {

                if (stationCode.equals(recentSearchList.getRecentSearchList().get(g).getDepartureCode())) {
                    Log.e("?" + stationCode, recentSearchList.getRecentSearchList().get(g).getDepartureCode());
                    Log.e("ByPass", "Y");
                    bypass = true;
                    break;
                }
            }

            if (!bypass) {

                Log.e("ByPass", "N");

                if (recentSearchList.getRecentSearchList().size() > 4) {
                    recentSearchList.getRecentSearchList().remove(0);
                }

                RecentSearchClass recentSearchClass = new RecentSearchClass();
                recentSearchClass.setDepartureCode(stationCode);
                recentSearchClass.setDepartureName(stationName);
                recentSearchClass.setDepartureCountry(countryName);
                recentSearchClass.setDepartureCurrency(currency);
                recentSearchClass.setType(type);

                recentSearchList.getRecentSearchList().add(recentSearchClass);

                String userInfo = gson.toJson(recentSearchList);
                RealmObjectController.saveRecentSearch(getActivity(), userInfo);
            }

        } else {
            List<RecentSearchClass> recent = new ArrayList<>();

            RecentSearchClass recentSearchClass = new RecentSearchClass();
            recentSearchClass.setDepartureCode(stationCode);
            recentSearchClass.setDepartureName(stationName);
            recentSearchClass.setDepartureCountry(countryName);
            recentSearchClass.setDepartureCurrency(currency);
            recentSearchClass.setType(type);

            recent.add(recentSearchClass);
            recentSearchList.setRecentSearchList(recent);

            String userInfo = gson.toJson(recentSearchList);
            RealmObjectController.saveRecentSearch(getActivity(), userInfo);
        }

    }

    public static ArrayList<DropDownItem> returnArrivalStation() {
        return arrivalFlight;
    }

    public static ArrayList<DropDownItem> returnFlightMarket() {
        return flightMarket;
    }

    public static ArrayList<DropDownItem> initiateArrivalStation(Activity act, String stationCode) {

        arrivalFlight = new ArrayList<DropDownItem>();
        Log.e("State", stationCode);
        JSONArray jsonFlight = getFlight(act);

        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonFlight.length(); i++) {
            try {
                jsonList.add(jsonFlight.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(jsonList, new Comparator<JSONObject>() {

            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get("ArrivalStationName");
                    valB = (String) b.get("ArrivalStationName");
                } catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonFlight.length(); i++) {
            sortedJsonArray.put(jsonList.get(i));
        }

        //Get All Airport - remove redundant
        List<String> al = new ArrayList<>();
        Set<String> hs = new LinkedHashSet<>();
        for (int i = 0; i < sortedJsonArray.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);
            if (!row.optString("DepartureStationName").equals("") && !row.optString("ArrivalStationName").equals("")) {
                al.add(row.optString("ArrivalCountryName") + "/-" + row.optString("DepartureStation") + "/-" + row.optString("DepartureCountryName") + "/-" + row.optString("ArrivalStationCurrencyCode") + "/-" + row.optString("ArrivalStationName") + "/-" + row.optString("ArrivalStation"));
            }
        }

        hs.addAll(al);
        al.clear();
        al.addAll(hs);

        for (int i = 0; i < al.size(); i++) {
            String flightSplit = al.get(i).toString();
            String[] str1 = flightSplit.split("/-");
            String p1 = str1[0]; // arrival country name
            String p2 = str1[1]; // departure station
            String p3 = str1[2]; // departure country name
            String p4 = str1[3]; // arrival currency code
            String p5 = str1[4]; // arrival station name
            String p6 = str1[5]; // arrival station code


            DropDownItem itemFlight = new DropDownItem();
            if (stationCode.equals(p2)) {
                Log.e(stationCode, p2);

                if (!p2.equals(p5)) {
                    itemFlight.setText(p5 + " (" + p6 + ")");
                    itemFlight.setCode(p6 + "/" + p5 + "/" + p3 + "/" + p4);
                    itemFlight.setTag("ARRIVAL_STATION");
                    arrivalFlight.add(itemFlight);
                }
            }
        }


        /*for (int i = 0; i < sortedJsonArray.length(); i++) {
            JSONObject row = (JSONObject) sortedJsonArray.opt(i);

            DropDownItem item = new DropDownItem();
            if (stationCode.equals(row.optString("DepartureStation"))) {
                if (!row.optString("DepartureStationName").equals(row.optString("ArrivalStationName"))) {
                    item.setText(row.optString("ArrivalStationName") + " (" + row.optString("ArrivalStation") + ")");
                    item.setCode(row.optString("ArrivalStation") + "/" + row.optString("ArrivalCountryName"));
                    item.setTag("ARRIVAL_STATION");
                    arrivalFlight.add(item);
                }
            }
        }*/


        arrivalClickable = true;

        Log.e("SearchFlightFragment", Integer.toString(arrivalFlight.size()));
        return arrivalFlight;
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.e("OnResume", "OK2");

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        dismissLoading();

        /*if (current_api.equals("REQUEST_SIGNATURE")) {
            if (result.size() > 0) {
                Gson gson = new Gson();
                SignatureReceive obj = gson.fromJson(result.get(0).getCachedResult(), SignatureReceive.class);
                onSignatureReceive(obj);
            }
        } else {
            if (result.size() > 0) {
                Gson gson = new Gson();
                SearchFlightReceive obj = gson.fromJson(result.get(0).getCachedResult(), SearchFlightReceive.class);
                onSearchFlightReceive(obj);
            }
            Log.e("OnResume", "OK");
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if (month < 9) {
            varMonth = "0";
        }
        if (day < 10) {
            varDay = "0";
        }
        if (PICKER.equals(DEPARTURE_DATE_PICKER)) {

            // departureDay = day;
            // departureMonth = month;
            //departureYear = year;
            String deptText = day + "/" + varMonth + "" + (month + 1) + "/" + year;

            bookFlightDepartureDate.setText(deptText);
            bookFlightDepartureDate.setTag(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);

        } else if (PICKER.equals(RETURN_DATE_PICKER)) {
            String returnText = day + "/" + varMonth + "" + (month + 1) + "/" + year;

            bookFlightReturnDate.setText(returnText);
            bookFlightReturnDate.setTag(year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day);

        } else {
            //DeadBlock
        }

    }

    public Date stringToDate(String string) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = string;
        Date date = null;

        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("Date", String.valueOf(date));

        return date;
    }

    public String convertDate(Date data) {

        SimpleDateFormat reformat = new SimpleDateFormat("EEE, dd MMM");
        return reformat.format(data);

    }

    public String cut(String text) {

        String word = text;
        String comma = word.substring(0, word.length() - 2);

        return comma;
    }
}
