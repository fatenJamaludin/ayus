package com.app.tbd.ui.Activity.BookingFlight.Add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.MainController;
import com.app.tbd.R;
import com.app.tbd.application.AnalyticsApplication;
import com.app.tbd.application.MainApplication;
import com.app.tbd.ui.Activity.BookingFlight.Checkout.CheckoutActivity;
import com.app.tbd.base.BaseFragment;
import com.app.tbd.ui.Model.JSON.AddOnInfo;
import com.app.tbd.ui.Model.JSON.FlightInProgressJSON;
import com.app.tbd.ui.Model.JSON.UserInfoJSON;
import com.app.tbd.ui.Model.Receive.AddOnReceiveV2;
import com.app.tbd.ui.Model.Receive.BaggageMealReceive;
import com.app.tbd.ui.Model.Receive.BookingFromStateReceive;
import com.app.tbd.ui.Model.Receive.LoadInsuranceReceive;
import com.app.tbd.ui.Model.Receive.SearchFlightReceive;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.app.tbd.ui.Model.Receive.TBD.LoginReceive;
import com.app.tbd.ui.Model.Request.AddOnRequest;
import com.app.tbd.ui.Model.Request.BookingFromStateRequest;
import com.app.tbd.ui.Model.Request.LoadBaggageRequest;
import com.app.tbd.ui.Model.Request.LoadInsuranceRequest;
import com.app.tbd.ui.Model.Request.SeatInfoRequest;
import com.app.tbd.ui.Module.AddOnModule;
import com.app.tbd.ui.Presenter.BookingPresenter;
import com.app.tbd.ui.Realm.RealmObjectController;
import com.app.tbd.utils.SharedPrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;

import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddOnFragment extends BaseFragment implements BookingPresenter.AddOnView {

    @Inject
    BookingPresenter presenter;

    @InjectView(R.id.btnSeatSelection)
    LinearLayout btnSeatSelection;

    @InjectView(R.id.btnInsuranceClick)
    LinearLayout btnInsuranceClick;

    @InjectView(R.id.btnMealClick)
    LinearLayout btnMealClick;

    @InjectView(R.id.btnBaggageClick)
    LinearLayout btnBaggageClick;

    @InjectView(R.id.txtSeatAutoAssign)
    TextView txtSeatAutoAssign;

    @InjectView(R.id.txtInsuranceIncluded)
    TextView txtInsuranceIncluded;

    @InjectView(R.id.txtMealAdded)
    TextView txtMealAdded;

    @InjectView(R.id.txtBaggageAdded)
    TextView txtBaggageAdded;

    @InjectView(R.id.star1)
    ImageView star1;

    @InjectView(R.id.star2)
    ImageView star2;

    @InjectView(R.id.star3)
    ImageView star3;

    @InjectView(R.id.txtBaggagePts)
    TextView txtBaggagePts;

    @InjectView(R.id.txtMealPts)
    TextView txtMealPts;

    @InjectView(R.id.txtSelectionPts)
    TextView txtSelectionPts;

    @InjectView(R.id.txtInsurancePts)
    TextView txtInsurancePts;

    @InjectView(R.id.headAddOn)
    LinearLayout headAddOn;

    private String signature, token, username;
    private String travellerInfo;
    private String CURRENT;

    private int totalSegment;
    private FirebaseAnalytics mFirebaseAnalytics;

    static String seatChosen = "";
    static String baggageSelected = "";
    static String staticAlert, staticMessage1, staticMessage2, staticMessage3, staticMessage4, staticMessage5, staticMessage6;
    static int mealSelected = 0;
    static int departMeal = 0;
    static int returnMeal = 0;

    static BookingPresenter staticPresenter;

    static Boolean statusMeal = false;
    static Boolean statusSeat = false;
    static Boolean statusBaggage = false;
    static Boolean departStatusMeal = false;
    static Boolean departStatusSeat = false;
    static Boolean departBaggageStatus = false;
    static Boolean returnStatusMeal = false;
    static Boolean returnChosenSeatStatus = false;
    static Boolean returnBaggageStatus = false;
    static Boolean departBaggageFiled = false;
    static Boolean returnBaggageFiled = false;
    static Boolean returnSeatfilled = false;
    static Boolean departSeatfilled = false;
    static Boolean returnMealFiled = false;
    static Boolean departMealFiled = false;

    static ArrayList<String> departMealList = new ArrayList<String>();
    static ArrayList<String> returnMealList = new ArrayList<String>();
    private SharedPrefManager pref;

    AddOnReceiveV2 addOnReceive;

    ArrayList<SeatInfoReceive> seatInfoReceives = new ArrayList<SeatInfoReceive>();

    int totalSeat = 0;
    String seatSelected = "";
    /*String point = getString(R.string.addons_points);
    String autoAssign = getString(R.string.addons_auto_assign);
    String selected = getString(R.string.addons_selected);
    String none = getString(R.string.addons_none);
    String included = getString(R.string.addons_included);
    String kg = getString(R.string.addons_kg);
    String notIncluded = getString(R.string.addons_not_included);
    String meal = getString(R.string.addons_meal);
    String alert, message1, message2, message3, message4, message5, message6;*/

    Boolean seatChosenStatus = false;
    Boolean baggageChosenStatus = false;
    Boolean mealChosenStatus = false;


    public static AddOnFragment newInstance(Bundle bundle) {

        AddOnFragment fragment = new AddOnFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmObjectController.clearCachedResult(getActivity());
        MainApplication.get(getActivity()).createScopedGraph(new AddOnModule(this)).inject(this);

        //Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.addons, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        /*Realm realm = RealmObjectController.getRealmInstance(getActivity());
        final RealmResults<FreeItem> freeResult = realm.where(FreeItem.class).findAll();*/

        headAddOn.setVisibility(View.GONE);
        /*setAlertMessage();*/
        btnBaggageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBaggageInfo();
            }
        });

        btnMealClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.toastNotification(getActivity(), "Development in progress");
                loadMealInfo();
            }
        });

        btnInsuranceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInsuranceInfo();
            }
        });

        btnSeatSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int x = 0; x < totalSegment; x++) {
                    if (x < 2) {
                        totalSeat++;
                    }
                }
                initiateLoading(getActivity());
                loadSeatInfo(0);
            }

        });

        dataSetup();

        return view;
    }

    /*public void setAlertMessage(){
        alert = getString(R.string.addons_alert);
        message1 = getString(R.string.addons_message1);
        message2 = getString(R.string.addons_message2);
        message3 = getString(R.string.addons_message3);
        message4 = getString(R.string.addons_message4);
        message5 = getString(R.string.addons_message5);
        message6 = getString(R.string.addons_message6);

        staticAlert = alert;
        staticMessage1 = message1;
        staticMessage2 = message2;
        staticMessage3 = message3;
        staticMessage4 = message4;
        staticMessage5 = message5;
        staticMessage6 = message6;
    }*/

    public void loadBaggageInfo() {

        HashMap<String, String> hardCodeLanguage = pref.getHardCodeLanguage();
        String languageCN = hardCodeLanguage.get(SharedPrefManager.HARD_CODE_LANGUAGE);

        CURRENT = "BAGGAGE";
        initiateLoading(getActivity());

        LoadBaggageRequest loadBaggageRequest = new LoadBaggageRequest();
        loadBaggageRequest.setToken(token);
        loadBaggageRequest.setLanguageCode(languageCN);
        loadBaggageRequest.setSignature(signature);
        loadBaggageRequest.setUserName(username);
        //loadBaggageRequest.setVersion("2");

        presenter.onLoadBaggageRequest(loadBaggageRequest);
    }

    public void loadMealInfo() {

        HashMap<String, String> hardCodeLanguage = pref.getHardCodeLanguage();
        String languageCN = hardCodeLanguage.get(SharedPrefManager.HARD_CODE_LANGUAGE);

        CURRENT = "MEAL";
        initiateLoading(getActivity());

        LoadBaggageRequest loadBaggageRequest = new LoadBaggageRequest();
        loadBaggageRequest.setToken(token);
        loadBaggageRequest.setLanguageCode(languageCN);
        loadBaggageRequest.setSignature(signature);
        loadBaggageRequest.setUserName(username);
        //loadBaggageRequest.setVersion("2");

        presenter.onLoadBaggageRequest(loadBaggageRequest);
    }

    public void loadInsuranceInfo() {

        initiateLoading(getActivity());

        LoadInsuranceRequest insuranceRequest = new LoadInsuranceRequest();
        insuranceRequest.setToken(token);
        insuranceRequest.setSignature(signature);
        insuranceRequest.setUserName(username);

        presenter.onLoadInsuranceRequest(insuranceRequest);
    }

    public void dataSetup() {

        Bundle bundle = getArguments();
        Gson gsonUserInfo = new Gson();

        travellerInfo = bundle.getString("TRAVELLER");

        staticPresenter = presenter;

        Realm realm = RealmObjectController.getRealmInstance(getActivity());

        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        final RealmResults<FlightInProgressJSON> result3 = realm.where(FlightInProgressJSON.class).findAll();

        SearchFlightReceive searchFlightReceive = (new Gson()).fromJson(result3.get(0).getSearchFlightReceive(), SearchFlightReceive.class);
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        AddOnInfo addOnInfo = new AddOnInfo();
        addOnInfo.setSeatSelected("N");
        addOnInfo.setInsuranceIncluded("N");
        addOnInfo.setSetMealSelected("N");
        addOnInfo.setSetBaggageSelected("N");
        String addOnInfoString = gsonUserInfo.toJson(addOnInfo);
        RealmObjectController.saveAddOnInfo(getActivity(), addOnInfoString);

        token = loginReceive.getToken();
        signature = loginReceive.getSignature();
        username = loginReceive.getUserName();

        int sizeDepart = 0, sizeReturn = 0;
        //cal total segment available
        if (searchFlightReceive.getJourneyDateMarket().size() > 0) {
            if (searchFlightReceive.getJourneyDateMarket().get(0).getJourney().size() > 0) {
                sizeDepart = searchFlightReceive.getJourneyDateMarket().get(0).getJourney().get(0).getSegment().size();
            }
        }
        if (searchFlightReceive.getJourneyDateMarket().size() > 1) {
            if (searchFlightReceive.getJourneyDateMarket().get(1).getJourney().size() > 0) {
                sizeReturn = searchFlightReceive.getJourneyDateMarket().get(1).getJourney().get(0).getSegment().size();
            }
        }

        totalSegment = sizeDepart + sizeReturn;
    }

    public void loadSeatInfo(int flightSegment) {

        SeatInfoRequest seatInfoRequest = new SeatInfoRequest();
        seatInfoRequest.setToken(token);
        seatInfoRequest.setFlight(Integer.toString(flightSegment));
        seatInfoRequest.setSignature(signature);
        seatInfoRequest.setUserName(username);

        presenter.onSeatInfoRequest(seatInfoRequest);
    }

    public static void checkOut(Activity act) {

        staticAlert = AnalyticsApplication.getContext().getString(R.string.addons_alert);
        staticMessage1 = AnalyticsApplication.getContext().getString(R.string.addons_message1);
        staticMessage2 = AnalyticsApplication.getContext().getString(R.string.addons_message2);
        staticMessage3 = AnalyticsApplication.getContext().getString(R.string.addons_message3);
        staticMessage4 = AnalyticsApplication.getContext().getString(R.string.addons_message4);
        staticMessage5 = AnalyticsApplication.getContext().getString(R.string.addons_message5);
        staticMessage6 = AnalyticsApplication.getContext().getString(R.string.addons_message6);

        if (departMealFiled && departStatusMeal) {
            setAlertDialog(act, staticMessage1, staticAlert);

        } else if (returnMealFiled && returnStatusMeal) {
            setAlertDialog(act, staticMessage2, staticAlert);

        } else if (departBaggageFiled && departBaggageStatus) {
            setAlertDialog(act, staticMessage3, staticAlert);

        } else if (returnBaggageFiled && returnBaggageStatus) {
            setAlertDialog(act, staticMessage4, staticAlert);

        } else if (departSeatfilled && departStatusSeat) {
            setAlertDialog(act, staticMessage5, staticAlert);

        } else if (returnSeatfilled && returnChosenSeatStatus) {
            setAlertDialog(act, staticMessage6, staticAlert);

        } else {
            bookingFromStateRequest(act);
        }
    }

    public static void bookingFromStateRequest(Activity act) {

        initiateLoading(act);

        Realm realm = RealmObjectController.getRealmInstance(act);
        final RealmResults<UserInfoJSON> result2 = realm.where(UserInfoJSON.class).findAll();
        LoginReceive loginReceive = (new Gson()).fromJson(result2.get(0).getUserInfo(), LoginReceive.class);

        BookingFromStateRequest bookingFromStateRequest = new BookingFromStateRequest();
        bookingFromStateRequest.setUserName(loginReceive.getUserName());
        bookingFromStateRequest.setSignature(loginReceive.getSignature());
        bookingFromStateRequest.setToken(loginReceive.getToken());

        staticPresenter.onBookingFromStateRequest(bookingFromStateRequest);
    }

    @Override
    public void onSeatInfoReceive(SeatInfoReceive obj) {

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            //pass per object .. no need send in bulk
            seatInfoReceives.add(obj);
            Gson gsonUserInfo = new Gson();
            String info = gsonUserInfo.toJson(obj);
            RealmObjectController.saveSeatCache(getActivity(), info);

            if (seatInfoReceives.size() == totalSeat) {
                dismissLoading();

                Intent intent = new Intent(getActivity(), SeatTabActivity.class);
                intent.putExtra("TRAVELLER", travellerInfo);

                for (int y = 0; y < totalSeat; y++) {
                    intent.putExtra("SEAT_INFO_" + Integer.toString(y), (new Gson()).toJson(seatInfoReceives.get(y)));
                }
                intent.putExtra("ADDED_INFO", new Gson().toJson(addOnReceive));
                getActivity().startActivity(intent);
            } else {
                loadSeatInfo(1);
            }

        } else {
            dismissLoading();

        }
    }

    @Override
    public void onBaggageMealReceive(BaggageMealReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            Gson gsonUserInfo = new Gson();
            String info = gsonUserInfo.toJson(obj);
            RealmObjectController.saveSeatCache(getActivity(), info);

            if (CURRENT.equals("BAGGAGE")) {
                Intent intent = new Intent(getActivity(), BaggageActivity.class);
                intent.putExtra("ADDED_INFO", new Gson().toJson(addOnReceive));
                intent.putExtra("BAGGAGE_INFO", info);
                getActivity().startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), MealActivity.class);
                intent.putExtra("ADDED_INFO", new Gson().toJson(addOnReceive));
                intent.putExtra("MEAL_INFO", info);
                getActivity().startActivity(intent);
            }

        }
    }

    @Override
    public void onBookingFromState(BookingFromStateReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            fireBaseSendEvent(obj);
            RealmObjectController.saveSeatCache(getActivity(), new Gson().toJson(obj));

            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtra("TAX_FEE", new Gson().toJson(obj));
            getActivity().startActivity(intent);

        }

    }

    public void fireBaseSendEvent(BookingFromStateReceive obj) {

        int totalPassenger = obj.getPassenger().size();
        Bundle fireBaseBundle = new Bundle();


        fireBaseBundle.putString(FirebaseAnalytics.Param.VALUE, obj.getTotalQuotedAmount());
        fireBaseBundle.putString(FirebaseAnalytics.Param.CURRENCY, obj.getCurrency().getCurrencyCode());
        fireBaseBundle.putString(FirebaseAnalytics.Param.ORIGIN, "-");
        fireBaseBundle.putString(FirebaseAnalytics.Param.DESTINATION, "-");
        fireBaseBundle.putString(FirebaseAnalytics.Param.START_DATE, "-");
        fireBaseBundle.putString(FirebaseAnalytics.Param.END_DATE, "-");
        fireBaseBundle.putString(FirebaseAnalytics.Param.NUMBER_OF_PASSENGERS, String.valueOf(totalPassenger));

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, fireBaseBundle);

    }

    @Override
    public void onAddOnReceive(AddOnReceiveV2 obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            addOnReceive = obj;
            mealSelected = 0;
            String none = getString(R.string.addons_none);
            String meals = getString(R.string.addons_meal);
            String notIncluded = getString(R.string.addons_not_included);
            String included = getString(R.string.addons_included);
            String selected = getString(R.string.addons_selected);
            String point = getString(R.string.addons_points);
            String auto = getString(R.string.addons_auto_assign);
            String kg = getString(R.string.addons_kg);


            //count depart first
            for (int journey = 0; journey < obj.getJourney().size(); journey++) {
                for (int v = 0; v < obj.getJourney().get(journey).getSegment().size(); v++) {

                    for (int passenger = 0; passenger < obj.getJourney().get(journey).getSegment().get(v).getPassenger().size(); passenger++) {
                        mealSelected = mealSelected + obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal().size();
                        baggageSelected = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getBaggage();
                        seatSelected = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getSeat();

                        if (!seatSelected.equals("")) {
                            if (!seatChosenStatus) {
                                seatChosen = "Y";
                                seatChosenStatus = true;
                            }
                        }

                        if (!baggageSelected.equals("")) {
                            if (!baggageChosenStatus) {
                                baggageSelected = "Y";
                                baggageChosenStatus = true;
                            }
                        }
                    }
                }
            }

            if (mealSelected == 0) {
                txtMealAdded.setText(none);
            } else {
                String meal_text = mealSelected + " " + meals;
                txtMealAdded.setText(meal_text);
                mealChosenStatus = true;

            }

            if (obj.getInsurance().equals("N")) {
                txtInsuranceIncluded.setText(notIncluded);
            } else {
                txtInsuranceIncluded.setText(included);
            }

            if (baggageSelected.equals("")) {
                txtBaggageAdded.setText(none);
            } else {
                txtBaggageAdded.setText(selected);
            }

            if (!obj.getBaggageTotalPoints().equals("0")) {
                String newPoint = changeThousand(obj.getBaggageTotalPoints()) + " " + point;
                txtBaggagePts.setText("+" + newPoint);
                txtBaggagePts.setVisibility(View.VISIBLE);
            } else {
                txtBaggagePts.setVisibility(View.INVISIBLE);
            }

            if (!obj.getBaggageTotalWeight().equals("0")) {
                String baggage_text = obj.getBaggageTotalWeight() + " " + kg;
                txtBaggageAdded.setText(baggage_text);
            } else {
                txtBaggageAdded.setText(none);
            }

            if (seatChosenStatus) {
                String newPoint = changeThousand(obj.getSeatSelectionTotalPoints()) + " " + point;
                txtSeatAutoAssign.setText(selected);
                txtSelectionPts.setVisibility(View.VISIBLE);
                txtSelectionPts.setText(newPoint);
            } else {
                txtSeatAutoAssign.setText(auto);
                txtSelectionPts.setVisibility(View.INVISIBLE);
            }

            if (!obj.getInsuranceTotalPoints().equals("0")) {
                String newPoint = changeThousand(obj.getInsuranceTotalPoints()) + " " + point;
                txtInsurancePts.setText(newPoint);
                txtInsurancePts.setVisibility(View.VISIBLE);
            } else {
                txtInsurancePts.setVisibility(View.INVISIBLE);
            }

            if (mealChosenStatus) {
                String newPoint = changeThousand(obj.getMealTotalPoints()) + " " + point;
                txtMealPts.setText(newPoint);
                txtMealPts.setVisibility(View.VISIBLE);
            } else {
                txtMealPts.setVisibility(View.INVISIBLE);
            }

            //New validation free star per journey
            mealSelected = 0;
            departMeal = 0;
            returnMeal = 0;

            Boolean meal = false;
            Boolean baggage = false;
            Boolean seat = false;

            //count depart first
            for (int journey = 0; journey < obj.getJourney().size(); journey++) {

                if (journey == 0) {

                    //check star per each journey
                    if (obj.getJourney().get(journey).getFreeBaggage().equals("Y")) {
                        departBaggageStatus = true;
                        meal = true;
                    }

                    if (obj.getJourney().get(journey).getFreeMeal().equals("Y")) {
                        departStatusMeal = true;
                        baggage = true;
                    }

                    if (obj.getJourney().get(journey).getFreeSeatSelection().equals("Y")) {
                        departStatusSeat = true;
                        seat = true;
                    }

                    for (int v = 0; v < obj.getJourney().get(journey).getSegment().size(); v++) {

                        Boolean departMealCut = obj.getJourney().get(journey).getSegment().get(v).getFreeMeal().equals("C");

                        for (int passenger = 0; passenger < obj.getJourney().get(journey).getSegment().get(v).getPassenger().size(); passenger++) {
                            //mealSelected = mealSelected + obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal().size();
                            departMeal = departMeal + obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal().size();
                            departMealList = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal();
                            String departBaggage = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getBaggage();
                            String departChosenSeat = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getSeat();

                            if (!departMealCut) {
                                if (departMealList.size() == 0) {
                                    if (!departMealFiled) {
                                        departMealFiled = true;
                                    }
                                } else {
                                    departMealFiled = false;
                                }
                            } else {
                                departMealFiled = false;
                            }

                            if (departBaggage.equals("")) {
                                if (!departBaggageFiled) {
                                    departBaggageFiled = true;
                                }
                            }

                            if (departChosenSeat.equals("")) {
                                if (!departSeatfilled) {
                                    departSeatfilled = true;
                                }
                            }
                        }
                    }
                }

                if (journey == 1) {

                    //check star per each journey
                    if (obj.getJourney().get(journey).getFreeBaggage().equals("Y")) {
                        returnBaggageStatus = true;
                        baggage = true;
                    }

                    if (obj.getJourney().get(journey).getFreeMeal().equals("Y")) {
                        returnStatusMeal = true;
                        meal = true;
                    }

                    if (obj.getJourney().get(journey).getFreeSeatSelection().equals("Y")) {
                        returnChosenSeatStatus = true;
                        seat = true;
                    }

                    for (int v = 0; v < obj.getJourney().get(journey).getSegment().size(); v++) {

                        Boolean returnMealCut = obj.getJourney().get(journey).getSegment().get(v).getFreeMeal().equals("C");

                        for (int passenger = 0; passenger < obj.getJourney().get(journey).getSegment().get(v).getPassenger().size(); passenger++) {
                            mealSelected = mealSelected + obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal().size();
                            returnMeal = returnMeal + obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal().size();
                            returnMealList = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getMeal();
                            String returnBaggage = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getBaggage();
                            String returnChosenSeat = obj.getJourney().get(journey).getSegment().get(v).getPassenger().get(passenger).getSeat();
                            Log.e("returnChosenSeat", "Seat " + returnChosenSeat);

                            if (!returnMealCut) {
                                if (returnMealList.size() == 0) {
                                    if (!returnMealFiled) {
                                        returnMealFiled = true;
                                    }
                                } else {
                                    returnMealFiled = false;
                                }
                            } else {
                                returnMealFiled = false;
                            }

                            if (returnBaggage.equals("")) {
                                if (!returnBaggageFiled) {
                                    returnBaggageFiled = true;
                                }
                            }

                            if (returnChosenSeat.equals("")) {
                                if (!returnSeatfilled) {
                                    Log.e("Seat Null", "Y");
                                    Log.e("returnChosenSeatV2", "Seat " + returnChosenSeat);
                                    returnSeatfilled = true;
                                }
                            }
                        }
                    }
                }
            }

            if (baggage || meal || seat) {
                headAddOn.setVisibility(View.VISIBLE);
            } else {
                headAddOn.setVisibility(View.GONE);
            }

            if (baggage) {
                star1.setVisibility(View.VISIBLE);
                statusBaggage = true;
            }

            if (meal) {
                star2.setVisibility(View.VISIBLE);
                statusMeal = true;
            }

            if (seat) {
                star3.setVisibility(View.VISIBLE);
                statusSeat = true;
            }
        }
    }

    @Override
    public void onLoadInsuranceReceive(LoadInsuranceReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            RealmObjectController.saveSeatCache(getActivity(), new Gson().toJson(obj));

            Intent intent = new Intent(getActivity(), InsuranceActivity.class);
            intent.putExtra("PASSENFER_INSURANCE", new Gson().toJson(obj));
            intent.putExtra("ADDED_INFO", new Gson().toJson(addOnReceive));
            getActivity().startActivity(intent);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        initiateLoading(getActivity());
        AddOnRequest addOnRequest = new AddOnRequest();
        addOnRequest.setSignature(signature);
        addOnRequest.setToken(token);
        addOnRequest.setUserName(username);
        presenter.onAddOnRequest(addOnRequest);

        seatChosenStatus = false;
        departStatusMeal = false;
        departStatusSeat = false;
        departBaggageStatus = false;
        returnStatusMeal = false;
        returnChosenSeatStatus = false;
        returnBaggageStatus = false;
        departBaggageFiled = false;
        returnBaggageFiled = false;
        returnSeatfilled = false;
        departSeatfilled = false;
        departMealFiled = false;
        returnMealFiled = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();

    }

}
